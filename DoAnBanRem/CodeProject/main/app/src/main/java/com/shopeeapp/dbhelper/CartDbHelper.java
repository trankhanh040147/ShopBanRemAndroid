package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import com.shopeeapp.entity.Cart;
import com.shopeeapp.entity.Product;
import com.shopeeapp.utilities.ImageConverter;

public class CartDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Cart";
    private static final String CART_ID = "id";
    private static final String CART_USER_ID = "userId";
    private static final String CART_PRODUCT_ID = "productId";
    private static final String CART_QUANTITY = "quantity";
    private static final String CART_STATUS = "status";

    private final Context context;

    public CartDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Cart ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    userId    INTEGER NOT NULL, " +
                        "    productId    INTEGER NOT NULL, " +
                        "    quantity    INTEGER NOT NULL, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id), " +
                        "    FOREIGN KEY(userId) REFERENCES User(id), " +
                        "    FOREIGN KEY(productId) REFERENCES Product(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Cart cart) {
        ContentValues values = new ContentValues();
        values.put(CART_USER_ID, cart.getUserId());
        values.put(CART_PRODUCT_ID, cart.getProductId());
        values.put(CART_QUANTITY, cart.getQuantity());
        values.put(CART_STATUS, cart.getStatus());
        return values;
    }

    public long insert(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(cart);
        long result = db.insert(TABLE_NAME, null, values);
        if (result > 0) {
            ProductDbHelper productDbHelper = new ProductDbHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
        }
        return result;
    }

    public int update(Cart cart) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(cart);
        return db.update(TABLE_NAME, values, CART_ID + " = ?", new String[]{String.valueOf(cart.getId())});
    }

    public int delete(Integer cartId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, CART_ID + " = ?", new String[]{String.valueOf(cartId)});
    }

    private Cart cursorToCart(Cursor cursor) {
        return new Cart(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getInt(3),
                cursor.getString(4)
        );
    }

    private ArrayList<Cart> getCart(Cursor cursor) {
        ArrayList<Cart> carts = new ArrayList<>();
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this.context);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Cart cart = cursorToCart(cursor);
            Product product = new Product(
                    cursor.getInt(5),
                    cursor.getInt(6),
                    cursor.getInt(7),
                    cursor.getString(8),
                    cursor.getDouble(9),
                    cursor.getString(10),
                    cursor.getString(11),
                    cursor.getFloat(12),
                    cursor.getString(13)
            );
            product.addProductImage(productImageDbHelper.getAllImageByProduct(product.getId()));
            cart.setProduct(product);
            carts.add(cart);
            cursor.moveToNext();
        }
        cursor.close();
        return carts;
    }

    public ArrayList<Cart> getAllCarts(Integer userId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.userId = ?",
                new String[]{userId.toString()});
        return getCart(cursor);
    }

    private ArrayList<Cart> getCartByStatus(Integer userId, String status) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Cart INNER JOIN Product ON Cart.productId = Product.id WHERE Cart.userId = ? AND Cart.status LIKE ?",
                new String[]{userId.toString(), "%" + status + "%"});
        return getCart(cursor);
    }

    public ArrayList<Cart> getUnpaidCart(Integer userId) {
        return getCartByStatus(userId, Cart.CART_UNPAID);
    }

    public Integer getCartIdByRowId(long rowId) {
        return getCartByRowId(rowId).getId();
    }

    public Cart getCartByRowId(long rowID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE rowid = ?",
                new String[]{String.valueOf(rowID)});
        Cart cart = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            cart = cursorToCart(cursor);
        }
        cursor.close();
        return cart;
    }
}
