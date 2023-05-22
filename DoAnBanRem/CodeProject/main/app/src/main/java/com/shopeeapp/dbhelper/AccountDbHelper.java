package com.shopeeapp.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import com.shopeeapp.entity.Account;

public class AccountDbHelper extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "Account";
    private static final String ACCOUNT_ID = "id";
    private static final String ACCOUNT_USERNAME = "username";
    private static final String ACCOUNT_EMAIL = "email";
    private static final String ACCOUNT_PASSWORD = "password";
    private static final String ACCOUNT_ROLE_ID = "roleId";
    private static final String ACCOUNT_STATUS = "status";

    public AccountDbHelper(@Nullable Context context) {
        super(context, DbHelper.DATABASE_NAME, null, DbHelper.DATABASE_VERSION);
    }

    @Override
    public void onCreate(@NotNull SQLiteDatabase db) {
        String query =
                "CREATE TABLE IF NOT EXISTS Account ( " +
                        "    id    INTEGER NOT NULL, " +
                        "    username    TEXT NOT NULL, " +
                        "    email    TEXT NOT NULL, " +
                        "    password    TEXT NOT NULL, " +
                        "    roleId    INTEGER NOT NULL, " +
                        "    status    TEXT, " +
                        "    PRIMARY KEY(id) " +
                        ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(@NotNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    @NotNull
    @Contract("_ -> new")
    private Account cursorToAccount(@NotNull Cursor cursor) {
        return new Account(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getInt(4),
                cursor.getString(5)
        );
    }

    public Account getAccount(@NotNull Integer id) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id = ?", new String[]{id.toString()});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }

    public Account login(String email, String password) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE email = ? AND password = ?",
                new String[]{email, password});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }

    @NotNull
    private ContentValues createContentValues(@NotNull Account account) {
        ContentValues values = new ContentValues();
        values.put(ACCOUNT_USERNAME, account.getUsername());
        values.put(ACCOUNT_EMAIL, account.getEmail());
        values.put(ACCOUNT_PASSWORD, account.getPassword());
        values.put(ACCOUNT_ROLE_ID, account.getRoleID());
        values.put(ACCOUNT_STATUS, account.getStatus());
        return values;
    }

    public long insert(Account account) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(account);
        return db.insert(TABLE_NAME, null, values);
    }

    public int update(Account account) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = createContentValues(account);
        return db.update(TABLE_NAME, values, ACCOUNT_ID + " = ?", new String[]{String.valueOf(account.getId())});
    }

    public int delete(@NotNull Account account) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, ACCOUNT_ID + " = ?", new String[]{String.valueOf(account.getId())});
    }

    public Account getAccountByRowId(long rowID) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE rowid = ?",
                new String[]{String.valueOf(rowID)});
        Account account = null;
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }

    public Integer getAccountIdByRowId(long rowId) {
        return getAccountByRowId(rowId).getId();
    }

    public Account getAccountByEmail(String email) {
        Account account = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_NAME + " WHERE email = ?",
                new String[]{email});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            account = cursorToAccount(cursor);
        }
        cursor.close();
        return account;
    }
}
