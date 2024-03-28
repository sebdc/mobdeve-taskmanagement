package com.mobdeve.s13.g4.taskmanagement.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // - Database Details
    private static final String DATABASE_NAME = "TaskManagementDB";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase database;

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public DatabaseHandler( Context context ) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate( SQLiteDatabase database ) {
        this.database = database;
        DatabaseCreator dbCreator = new DatabaseCreator();
        dbCreator.initializeTables(this.database);
    }

    @Override
    public void onUpgrade( SQLiteDatabase database, int oldVersion, int newVersion ) {
        DatabaseCreator dbCreator = new DatabaseCreator();
        dbCreator.dropAllTables(this.database);
        onCreate(database);
    }
}
