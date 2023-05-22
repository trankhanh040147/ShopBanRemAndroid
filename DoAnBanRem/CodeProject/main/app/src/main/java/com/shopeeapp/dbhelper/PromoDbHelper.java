package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shopeeapp.entity.Promo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PromoDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "Promo";

    public PromoDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Promo ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    productId    INTEGER NOT NULL, " +
                        "    type    TEXT , " +
                        "    expirationDate    TEXT , " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private Promo cursorToPromo(Cursor cursor) {
        return new Promo(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );
    }

    public ArrayList<Promo> getAllPromos() {
        ArrayList<Promo> promos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            promos.add(
                    cursorToPromo(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return promos;
    }

    public ArrayList<Promo> getTopPromos(int limit) {
        ArrayList<Promo> promos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " LIMIT ?", new String[]{String.valueOf(limit)});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            promos.add(
                    cursorToPromo(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return promos;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Promo promo) {
        ContentValues values = new ContentValues();
        values.put("id", promo.getId());
        values.put("productId", promo.getProductId());
        values.put("type", promo.getType());
        values.put("expirationDate", promo.getExpirationDate());
        values.put("status", promo.getStatus());
        return values;
    }

    public long insert(Promo promo) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(promo);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }
}
