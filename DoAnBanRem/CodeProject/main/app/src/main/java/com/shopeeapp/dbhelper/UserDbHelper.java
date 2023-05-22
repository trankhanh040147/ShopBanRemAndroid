package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.shopeeapp.entity.Store;
import com.shopeeapp.entity.User;
import com.shopeeapp.utilities.ImageConverter;

import java.util.ArrayList;

public class UserDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "User";
    private static final String USER_ID = "id";
    private static final String USER_ACCOUNT_ID = "accountId";
    private static final String USER_FULL_NAME = "fullname";
    private static final String USER_SEX = "sex";
    private static final String USER_PHONE = "phone";
    private static final String USER_ADDRESS = "address";
    private static final String USER_AVATAR = "avatar";
    private static final String USER_FACEBOOK = "facebook";
    private static final String USER_ZALO = "zalo";
    private static final String USER_STATUS = "status";

    public UserDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS User ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    accountId    INTEGER NOT NULL, " +
                        "    fullname    TEXT NOT NULL, " +
                        "    sex    TEXT NOT NULL, " +
                        "    phone    TEXT NOT NULL, " +
                        "    address    TEXT NOT NULL, " +
                        "    avatar    TEXT, " +
                        "    facebook    TEXT, " +
                        "    zalo    TEXT, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id), " +
                        "    FOREIGN KEY(accountId) REFERENCES Account(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @NotNull
    @Contract("_ -> new")
    private User cursorToUser(@NotNull Cursor cursor) {
        return new User(
                cursor.getInt(0),
                cursor.getInt(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9)
        );
    }

    public User getUser(@NotNull Integer id) {
        User user = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull User user) {
        ContentValues values = new ContentValues();
        values.put(USER_FULL_NAME, user.getFullname());
        values.put(USER_ACCOUNT_ID, user.getAccountId());
        values.put(USER_SEX, user.getSex());
        values.put(USER_PHONE, user.getPhone());
        values.put(USER_ADDRESS, user.getAddress());
        values.put(USER_AVATAR, user.getAvatar());
        values.put(USER_FACEBOOK, user.getFacebook());
        values.put(USER_ZALO, user.getZalo());
        values.put(USER_STATUS, user.getStatus());
        return values;
    }

    public long insert(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(user);
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(User user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(user);
        return db.update(TABLE_NAME, values, USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public int delete(@NotNull User user) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, USER_ID + " = ?", new String[]{String.valueOf(user.getId())});
    }

    public User getUserByAccountId(Integer accountId) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE " + USER_ACCOUNT_ID + " = ?",
                new String[]{String.valueOf(accountId)});
        User user = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            user = cursorToUser(cursor);
        }
        cursor.close();
        return user;
    }

    public ArrayList<User> getAllUser() {
        ArrayList<User> users = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            users.add(
                    cursorToUser(cursor)
            );
            cursor.moveToNext();
        }
        cursor.close();
        return users;
    }
}
