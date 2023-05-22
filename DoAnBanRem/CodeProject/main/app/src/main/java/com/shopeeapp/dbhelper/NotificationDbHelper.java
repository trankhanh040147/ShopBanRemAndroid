package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import com.shopeeapp.entity.Notification;

public class NotificationDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Notification";
    private static final String NOTIFY_ID = "id";
    private static final String NOTIFY_USER_ID = "userId";
    private static final String NOTIFY_TYPE = "type";
    private static final String NOTIFY_MESSAGE = "message";
    private static final String NOTIFY_STATUS = "status";

    public NotificationDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Notification ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    userId    INTEGER NOT NULL, " +
                        "    type    TEXT NOT NULL, " +
                        "    message    TEXT NOT NULL, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id), " +
                        "    FOREIGN KEY(userId) REFERENCES User(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private Notification cursorToNotification(Cursor cursor) {
        return new Notification(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4)
        );
    }

    public ArrayList<Notification> getAllNotifications(Integer userId) {
        ArrayList<Notification> notifications = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE userId = ? AND " + NOTIFY_STATUS + " = ?",
                new String[]{userId.toString(), Notification.NOTIFY_UNREAD});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            notifications.add(cursorToNotification(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        return notifications;
    }

    public int update(Notification notification) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFY_STATUS, notification.getStatus());
        return db.update(TABLE_NAME, contentValues, NOTIFY_ID + " = ?",
                new String[]{notification.getId().toString()});
    }

    private ContentValues createContentValues(Notification notification) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NOTIFY_USER_ID, notification.getUserId());
        contentValues.put(NOTIFY_TYPE, notification.getType());
        contentValues.put(NOTIFY_MESSAGE, notification.getMessage());
        contentValues.put(NOTIFY_STATUS, notification.getStatus());
        return contentValues;
    }

    public long insert(Notification notification) {
        SQLiteDatabase db = getReadableDatabase();
        ContentValues contentValues = createContentValues(notification);
        return db.insert(TABLE_NAME, null, contentValues);
    }
}
