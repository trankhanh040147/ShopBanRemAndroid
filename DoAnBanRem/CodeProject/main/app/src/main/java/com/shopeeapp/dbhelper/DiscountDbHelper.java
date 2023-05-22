package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Store;

import org.jetbrains.annotations.NotNull;

public class DiscountDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Discount";

    public DiscountDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Discount ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    productId    INTEGER NOT NULL, " +
                        "    value    REAL NOT NULL, " +
                        "    status    TEXT, " +
                        "    FOREIGN KEY(productId) REFERENCES Product(id), " +
                        "    PRIMARY KEY(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public ArrayList<Discount> getAllDiscounts() {
        ArrayList<Discount> discounts = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            discounts.add(
                    new Discount(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getFloat(2),
                            cursor.getString(3)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return discounts;
    }

    private Discount cursorToDiscount(Cursor cursor) {
        return new Discount(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getFloat(2),
                cursor.getString(3)
        );
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Discount discount) {
        ContentValues values = new ContentValues();
        values.put("id", discount.getId());
        values.put("productId", discount.getProductId());
        values.put("value", discount.getValue());
        values.put("status", discount.getStatus());
        return values;
    }

    public long insert(Discount discount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(discount);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public int update(Discount discount) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(discount);
        return db.update(TABLE_NAME, values,   "id = ?", new String[]{String.valueOf(discount.getId())});
    }

    public int delete(Discount discount) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,  "id = ?", new String[]{String.valueOf(discount.getId())});
    }


    public Discount getDiscountById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        Discount discount = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            discount = cursorToDiscount(cursor);
        }
        cursor.close();
        return discount;
    }

    public Discount getDiscountByProduct(Integer productId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE productId = ?", new String[]{productId.toString()});
        Discount discount = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            discount = cursorToDiscount(cursor);
        }
        cursor.close();
        return discount;
    }
}
