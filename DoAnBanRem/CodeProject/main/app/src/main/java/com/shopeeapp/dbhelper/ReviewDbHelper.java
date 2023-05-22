package com.shopeeapp.dbhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.entity.Review;

public class ReviewDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Review";

    public ReviewDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Review ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    userID    INTEGER NOT NULL, " +
                        "    productID    INTEGER NOT NULL, " +
                        "    content    TEXT, " +
                        "    time    TEXT, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id), " +
                        "    FOREIGN KEY(userID) REFERENCES User(id), " +
                        "    FOREIGN KEY(productID) REFERENCES Product(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public ArrayList<Review> getAllReviews(Integer productId) {
        ArrayList<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE productId = ?", new String[]{productId.toString()});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            reviews.add(
                    new Review(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    )
            );
            cursor.moveToNext();
        }
        cursor.close();
        return reviews;
    }
}
