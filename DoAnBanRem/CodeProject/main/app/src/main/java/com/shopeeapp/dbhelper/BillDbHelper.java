package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shopeeapp.dto.StatisDTO;
import com.shopeeapp.entity.Bill;
import com.shopeeapp.entity.Cart;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.Store;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.BlockingDeque;

public class BillDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Bill";
    private static final String BILL_ID = "id";
    private static final String BILL_USER_ID = "userId";
    private static final String BILL_PHONE = "phone";
    private static final String BILL_ADDRESS = "address";
    private static final String BILL_CART_ID = "cartId";
    private static final String BILL_DATE = "date";
    private static final String BILL_DISCOUNT = "discount";
    private static final String BILL_PRICE = "price";
    private static final String BILL_STATUS = "status";

    private final Context context;
    public BillDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Bill ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    userId    INTEGER NOT NULL, " +
                        "    cartId    INTEGER NOT NULL, " +
                        "    phone    TEXT NOT NULL, " +
                        "    address    TEXT NOT NULL, " +
                        "    date    TEXT, " +
                        "    discount    REAL NOT NULL, " +
                        "    price    REAL NOT NULL, " +
                        "    status    TEXT, " +
                        "    FOREIGN KEY(cartId) REFERENCES Cart(id), " +
                        "    FOREIGN KEY(userId) REFERENCES User(id), " +
                        "    PRIMARY KEY(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private Bill cursorToBill(Cursor cursor) {
        return new Bill(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getInt(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getFloat(6),
                cursor.getFloat(7),
                cursor.getString(8)
        );
    }

    private ContentValues createContentValues(Bill bill) {
        ContentValues values = new ContentValues();
        values.put(BILL_USER_ID, bill.getUserId());
        values.put(BILL_CART_ID, bill.getCartId());
        values.put(BILL_PHONE, bill.getPhone());
        values.put(BILL_ADDRESS, bill.getAddress());
        values.put(BILL_DATE, bill.getDate());
        values.put(BILL_DISCOUNT, bill.getDiscount());
        values.put(BILL_PRICE, bill.getPrice());
        values.put(BILL_STATUS, bill.getStatus());
        return values;
    }

    public long insert(Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(bill);
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(bill);
        return db.update(
                TABLE_NAME, values, BILL_ID + " = ?",
                new String[]{String.valueOf(bill.getId())});
    }

    public int delete(@NotNull Bill bill) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, BILL_ID + " = ?",
                new String[]{String.valueOf(bill.getId())});
    }

    public ArrayList<Bill> getAllBills(Integer userId) {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartId = Cart.id WHERE Bill.userId = ?",
                new String[]{String.valueOf(userId)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bill bill = cursorToBill(cursor);
            Cart cart = new Cart(
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getInt(11),
                    cursor.getInt(12),
                    cursor.getString(13)
            );
            ProductDbHelper productDbHelper = new ProductDbHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
            bill.setCart(cart);
            bills.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills;
    }

    public ArrayList<Bill> getUnpaidBills(Integer userId) {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartId = Cart.id WHERE Bill.userId = ? AND Bill.status = 'Unpaid'",
                new String[]{String.valueOf(userId)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bill bill = cursorToBill(cursor);
            Cart cart = new Cart(
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getInt(11),
                    cursor.getInt(12),
                    cursor.getString(13)
            );
            ProductDbHelper productDbHelper = new ProductDbHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
            bill.setCart(cart);
            bills.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills;
    }

    public ArrayList<Bill> getAllUnpaidBills() {
        ArrayList<Bill> bills = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM Bill INNER JOIN Cart ON Bill.cartId = Cart.id WHERE  Bill.status = 'Unpaid'", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Bill bill = cursorToBill(cursor);
            Cart cart = new Cart(
                    cursor.getInt(9),
                    cursor.getInt(10),
                    cursor.getInt(11),
                    cursor.getInt(12),
                    cursor.getString(13)
            );
            ProductDbHelper productDbHelper = new ProductDbHelper(this.context);
            Product product = productDbHelper.getProductById(cart.getProductId());
            cart.setProduct(product);
            bill.setCart(cart);
            bills.add(bill);
            cursor.moveToNext();
        }
        cursor.close();
        return bills;
    }

    public ArrayList<StatisDTO> statis() {
        ArrayList<StatisDTO> statis = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT SUM(Cart.quantity) as total_quantity, ProductType.name, ProductType.id " +
                "FROM Bill INNER JOIN Cart INNER JOIN Product  INNER JOIN ProductType " +
                "WHERE Bill.cartId = Cart.id AND Cart.productId = Product.id " +
                "AND Product.type = ProductType.id AND Bill.status = 'Paid' GROUP BY ProductType.id",
                null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            StatisDTO statisDTO = new StatisDTO(
                    cursor.getInt(2),
                    cursor.getString(1),
                    cursor.getInt(0)
            );
            statis.add(statisDTO);
            cursor.moveToNext();
        }
        cursor.close();
        return statis;
    }

    public Bill getBillById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        Bill bill = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            bill = cursorToBill(cursor);
        }
        cursor.close();
        return bill;
    }

}
