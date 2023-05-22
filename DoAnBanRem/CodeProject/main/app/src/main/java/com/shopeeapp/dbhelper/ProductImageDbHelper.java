package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.ProductImage;
import com.shopeeapp.entity.ProductImage;
import com.shopeeapp.utilities.ImageConverter;

import org.jetbrains.annotations.NotNull;

public class ProductImageDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "ProductImage";

    public ProductImageDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS ProductImage ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    productId    INTEGER NOT NULL, " +
                        "    image    TEXT, " +
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

    public ArrayList<String> getAllImageByProduct(Integer productId) {
        ArrayList<String> images = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE productId = ?", new String[]{productId.toString()});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            images.add(cursor.getString(2));
            cursor.moveToNext();
        }
        cursor.close();
        return images;
    }

    public static ProductImage cursorToProductImage(@NotNull Cursor cursor) {
        return new ProductImage(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3)
        );
    }

    public ArrayList<ProductImage> getAllProductImages() {
        ArrayList<ProductImage> productImages = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            productImages.add(
                    cursorToProductImage(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return productImages;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull ProductImage productImage) {
        ContentValues values = new ContentValues();
        values.put("id", productImage.getId());
        values.put("productId", productImage.getProductId());
        values.put("image", productImage.getImage());
        values.put("status", productImage.getStatus());
        return values;
    }

    public long insert(ProductImage productImage) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productImage);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }


    public int update(ProductImage productImage) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(productImage);
        return db.update(TABLE_NAME, values,  "id = ?", new String[]{String.valueOf(productImage.getId())});
    }

    public int delete(ProductImage productImage) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME,   "id = ?", new String[]{String.valueOf(productImage.getId())});
    }

    public ProductImage getProductImageById(Integer id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        ProductImage productImage = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            productImage = cursorToProductImage(cursor);
        }
        cursor.close();
        return productImage;
    }


}
