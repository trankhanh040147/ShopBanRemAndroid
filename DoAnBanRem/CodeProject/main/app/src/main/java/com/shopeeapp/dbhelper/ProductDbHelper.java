package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.R;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.Promo;
import com.shopeeapp.entity.Store;
import com.shopeeapp.utilities.ImageConverter;

import org.jetbrains.annotations.NotNull;

public class ProductDbHelper extends SQLiteOpenHelper {
    private final Context context;
    public static final String TABLE_NAME = "Product";
    private static final String PRODUCT_ID = "id";
    private static final String PRODUCT_STORE_ID = "storeId";
    private static final String PRODUCT_TYPE = "type";
    private static final String PRODUCT_NAME = "name";
    private static final String PRODUCT_PRICE = "price";
    private static final String PRODUCT_IMAGE = "image";
    private static final String PRODUCT_DETAIL = "detail";
    private static final String PRODUCT_STAR = "star";
    private static final String PRODUCT_STATUS = "status";

    public ProductDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Product ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    storeId    INTEGER, " +
                        "    type    INTEGER NOT NULL, " +
                        "    name    INTEGER NOT NULL, " +
                        "    price    REAL, " +
                        "    image    String, " +
                        "    detail    TEXT, " +
                        "    star    REAL, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id), " +
                        "    FOREIGN KEY(type) REFERENCES ProductType(id), " +
                        "    FOREIGN KEY(storeId) REFERENCES Store(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private Product cursorToProduct(Cursor cursor) {
        return new Product(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getDouble(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getFloat(7),
                cursor.getString(8));
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public Product getProductById(Integer id) {
        ArrayList<Product> products = getProductByField(PRODUCT_ID, id);
        if (products.size() > 0)
            return products.get(0);
        return null;
    }

    public ArrayList<Product> getProductByName(String name) {
        return getProductByField(PRODUCT_NAME, name);
    }

    public ArrayList<Product> getProductByType(Integer type) {
        return getProductByField(PRODUCT_TYPE, type);
    }

    public ArrayList<Product> getProductByStar(Float star) {
        return getProductByField(PRODUCT_STAR, star);
    }

    public ArrayList<Product> getProductByPrice(Double price) {
        return getProductByField(PRODUCT_STAR, price);
    }

    public ArrayList<Product> getProductByTypeName(String typeName) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        Cursor cursor = db.rawQuery(
                "SELECT Product.id, Product.storeId, Product.type, Product.name, Product.price, Product.image, Product.detail, Product.star, Product.status" +
                        " FROM Product INNER JOIN ProductType ON Product.type = ProductType.id WHERE ProductType.name = ?",
                new String[]{typeName});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public ArrayList<Product> getProductByTypeId(Integer typeId) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_TYPE + " = ?",
                new String[]{String.valueOf(typeId)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            products.add(cursorToProduct(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public ArrayList<Product> getProductByListTypeId(ArrayList<String> productTypeIDs) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        for (String id : productTypeIDs) {
            cursor = db.rawQuery(
                    "SELECT * FROM " + TABLE_NAME + " WHERE " + PRODUCT_TYPE + " = ?",
                    new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                products.add(cursorToProduct(cursor));
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            if (!cursor.isClosed())
                cursor.close();
        }
        return products;
    }

    public ArrayList<Product> getTopProducts(Integer type, int limit) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                PRODUCT_TYPE + " = ?",
                new String[]{String.valueOf(type)},
                null,
                null,
                null,
                String.valueOf(limit));
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    private Cursor getCursorWithStringValue(SQLiteDatabase db, String field, String value) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + field + " LIKE ?", new String[]{"%" + value + "%"});
        return cursor;
    }

    private Cursor getCursorWithNumberValue(SQLiteDatabase db, String field, String value) {
        return db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + field + " = ?", new String[]{value});
    }

    private ArrayList<Product> getProductByField(String field, Object value) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        Cursor cursor = null;
        if (value instanceof String)
            cursor = getCursorWithStringValue(db, field, value.toString());
        else
            cursor = getCursorWithNumberValue(db, field, value.toString());

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    private ContentValues createContentValues(Product product) {
        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID, product.getId());
        values.put(PRODUCT_TYPE, product.getType());
        values.put(PRODUCT_NAME, product.getName());
        values.put(PRODUCT_PRICE, product.getPrice());
        values.put(PRODUCT_IMAGE, product.getImage());
        values.put(PRODUCT_DETAIL, product.getDetail());
        values.put(PRODUCT_STAR, product.getStar());
        values.put(PRODUCT_STATUS, product.getStatus());
        values.put(PRODUCT_STORE_ID, product.getStoreId());
        return values;
    }

    public long insert(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(product);
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(product);
        return db.update(TABLE_NAME, values, PRODUCT_ID + " = ?", new String[]{String.valueOf(product.getId())});
    }

    public int delete(Product product) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, PRODUCT_ID + " = ?", new String[]{String.valueOf(product.getId())});
    }

    public int updateDefaultImage(Product product) {
        switch (product.getType()) {
            case 1:
                product.setImage(R.drawable.clothes);
                break;
            case 2:
                product.setImage(R.drawable.bread);
                break;
            case 3:
                product.setImage(R.drawable.group_of_fruits);
                break;
            case 4:
                product.setImage(R.drawable.drink);
                break;
            case 5:
                product.setImage(R.drawable.laptop);
                break;
            case 6:
                product.setImage(R.drawable.fish);
                break;
            case 7:
                product.setImage(R.drawable.hatchet);
                break;
        }
        return update(product);
    }

    private Cursor getCursorPromoProduct(SQLiteDatabase db, int limit) {
        if (limit <= 0)
            return db.rawQuery("SELECT * FROM " + PromoDbHelper.TABLE_NAME, null);
        else
            return db.rawQuery("SELECT * FROM " + PromoDbHelper.TABLE_NAME + " LIMIT ?", new String[]{String.valueOf((limit))});
    }

    public ArrayList<Product> getAllPromoProducts() {
        return getPromoProducts(-1);
    }

    public ArrayList<Product> getPromoProducts(int limit) {
        ArrayList<Integer> promoId = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        PromoDbHelper promoDbHelper = new PromoDbHelper(context);
        ArrayList<Promo> promos;
        if (limit<= 0){
            promos = promoDbHelper.getAllPromos();
        }
        else {
            promos = promoDbHelper.getTopPromos(limit);
        }

        for (Promo item : promos){
            promoId.add(item.getId());
        }

        ArrayList<Product> products = new ArrayList<>();
        promoId.forEach(id -> {
            products.add(getProductById(id));
        });

        return products;
    }

    public Store getStore(Integer storeId) {
        StoreDbHelper storeDbHelper = new StoreDbHelper(this.context);
        return storeDbHelper.getStoreById(storeId);
    }

    public ArrayList<Product> getDiscountProducts() {
        ArrayList<Product> products = new ArrayList<>();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Product INNER JOIN Discount ON Product.id = Discount.productId", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            Discount discount = new Discount(
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getFloat(11),
                    cursor.getString(12)
            );
            product.setDiscount(discount);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public ArrayList<Product> getDiscountProductByName(String name, String discountValue) {
        ArrayList<Product> products = new ArrayList<>();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (!discountValue.equals("-1")) {
            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM Product INNER JOIN Discount ON Product.id = Discount.productId " +
                            "WHERE Discount.status = 'OK' AND Discount.value = '" + discountValue +"' AND Product.name LIKE ?",
                    new String[]{"%" + name + "%"});
        }
        else {
            cursor = db.rawQuery(
                    "SELECT * " +
                            "FROM Product INNER JOIN Discount ON Product.id = Discount.productId " +
                            "WHERE Discount.status = 'OK' AND Discount.value > '" + discountValue + "' AND Product.name LIKE ?",
                    new String[]{"%" + name + "%"});
        }
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Product product = cursorToProduct(cursor);
            Discount discount = new Discount(
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getFloat(11),
                    cursor.getString(12)
            );
            product.setDiscount(discount);
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            products.add(product);
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    public ArrayList<Product> getFullSearchResult(String text) {
        ArrayList<Product> products = new ArrayList<>();
        products.addAll(getProductByName(text));
        products.addAll(getProductByTypeName(text));
        //TODO
        //getProductByStoreName
        return products;
    }



}
