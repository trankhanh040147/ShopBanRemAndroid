package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.entity.ProductType;
import com.shopeeapp.entity.ProductType;
import com.shopeeapp.entity.Store;

import org.jetbrains.annotations.NotNull;

public class ProductTypeDbHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "ProductType";

    public ProductTypeDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS ProductType ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    name    TEXT NOT NULL, " +
                        "    image    TEXT, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id) " +
                        ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public ArrayList<ProductType> getAllProductTypes() {
        ArrayList<ProductType> productTypes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            productTypes.add(
                    new ProductType(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();

        return productTypes;
    }


    @NotNull
    private ContentValues createContentValues(@NotNull ProductType productType) {
        ContentValues values = new ContentValues();
        values.put("id", productType.getId());
        values.put("name", productType.getName());
        values.put("image", productType.getImage());
        values.put("status", productType.getStatus());
        return values;
    }

    public long insert(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productType);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public int update(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productType);
        return db.update(TABLE_NAME, values,   " id = ?", new String[]{String.valueOf(productType.getId())});
    }

    public int delete(ProductType productType) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,  "id = ?", new String[]{String.valueOf(productType.getId())});
    }

    public ProductType getProductTypeById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        ProductType productType = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            productType =   new ProductType(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3)
            );
        }
        cursor.close();
        return productType;
    }


}
