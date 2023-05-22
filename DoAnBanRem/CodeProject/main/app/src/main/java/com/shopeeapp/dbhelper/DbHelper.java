package com.shopeeapp.dbhelper;

public class DbHelper {
    public static final String DATABASE_NAME = "shoppe.db";
    public static Integer DATABASE_VERSION = 1;

    public static void setDatabaseVersion(Integer databaseVersion) {
        if (databaseVersion > DATABASE_VERSION)
            DATABASE_VERSION = databaseVersion;
    }

    public static void upgradeDatabaseVersion() {
        DATABASE_VERSION += 1;
    }
}
