package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.Store;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class StoreDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "Store";
    private static final String STORE_NAME = "name";
    private static final String STORE_IMAGE = "image";
    private static final String STORE_ID = "id";

    public StoreDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public static Store cursorToStore(@NotNull Cursor cursor) {
        return new Store(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6)
        );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Store ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    name    TEXT NOT NULL, " +
                        "    phone    TEXT NOT NULL, " +
                        "    email    TEXT NOT NULL, " +
                        "    image    TEXT, " +
                        "    address    TEXT, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id) " +
                        ")";
        db.execSQL(query);
    }


    public ArrayList<Store> getAllStores() {
        ArrayList<Store> stores = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            stores.add(
                    cursorToStore(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return stores;
    }

    public Store getStoreById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        Store store = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            store = cursorToStore(cursor);
        }
        cursor.close();
        return store;
    }

    public ArrayList<Store> getStoreByName(String name) {
        return getStoreByField(STORE_NAME, name);
    }

    private ArrayList<Store> getStoreByField(String field, String value) {
        ArrayList<Store> products = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + field + " LIKE %?%", new String[]{value});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            products.add(
                    cursorToStore(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return products;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Store store) {
        ContentValues values = new ContentValues();
        values.put("id", store.getId());
        values.put("name", store.getName());
        values.put("phone", store.getPhone());
        values.put("email", store.getEmail());
        values.put("image", store.getImage());
        values.put("address", store.getAddress());
        values.put("status", store.getStatus());
        return values;
    }

    public long insert(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(store);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public int update(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(store);
        return db.update(TABLE_NAME, values, STORE_ID + " = ?", new String[]{String.valueOf(store.getId())});
    }

    public int delete(Store store) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, STORE_ID + " = ?", new String[]{String.valueOf(store.getId())});
    }
}
