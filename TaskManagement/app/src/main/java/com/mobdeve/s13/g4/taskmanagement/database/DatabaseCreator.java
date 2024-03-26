package com.mobdeve.s13.g4.taskmanagement.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseCreator {
    
    // - User Table
    private static final String USER_TABLE = "users";
    private static final String USER_ID = "id";
    private static final String USER_EMAIL = "email";
    private static final String USER_USERNAME = "username";
    private static final String USER_FIRSTNAME = "firstName";
    private static final String USER_LASTNAME = "lastName";

    // - Category Table 
    private static final String CATEGORY_TABLE = "categories";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_DATE_CREATED = "dateCreated";
    private static final String CATEGORY_ONGOING_TASK_COUNT = "ongoingTaskCount";
    private static final String CATEGORY_COMPLETED_TASK_COUNT = "completedTaskCount";

    // - Task Table
    private static final String TASK_TABLE = "tasks";
    private static final String TASK_ID = "id";
    private static final String TASK_USER_ID = "userId";
    private static final String TASK_CATEGORY_ID = "categoryId";
    private static final String TASK_TITLE = "title";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_DATE_CREATED = "dateCreated";
    private static final String TASK_DUE_DATE = "dueDate";
    private static final String TASK_IS_COMPLETED = "isCompleted";
    private static final String TASK_PRIORITY_LEVEL = "priorityLevel";

    // - Subtask Table
    private static final String SUBTASK_TABLE = "subtasks";
    private static final String SUBTASK_ID = "id";
    private static final String SUBTASK_TASK_ID = "taskId";
    private static final String SUBTASK_DESCRIPTION = "description";
    private static final String SUBTASK_IS_COMPLETED = "isCompleted";

    /*|*******************************************************
                        Constructor Methods
    *********************************************************/
    public DatabaseCreator() {}

    /*|*******************************************************
                   Database Tables Initialization
    *********************************************************/
    public void initializeTables( SQLiteDatabase database ) {
        createUserTable( database );
        createCategoryTable( database );
        createTaskTable( database );
        createSubtaskTable( database );
    }

    private void createUserTable( SQLiteDatabase database ) {
        String CREATE_USER_TABLE = 
            "CREATE TABLE " + USER_TABLE + "("
                + USER_ID + " TEXT PRIMARY KEY,"
                + USER_EMAIL + " TEXT,"
                + USER_USERNAME + " TEXT,"
                + USER_FIRSTNAME + " TEXT,"
                + USER_LASTNAME + " TEXT"
                + ")";
        database.execSQL(CREATE_USER_TABLE);
    }

    private void createCategoryTable( SQLiteDatabase database ) {
        String CREATE_CATEGORY_TABLE =
            "CREATE TABLE " + CATEGORY_TABLE + "("
                + CATEGORY_ID + " TEXT PRIMARY KEY,"
                + CATEGORY_NAME + " TEXT NOT NULL,"
                + CATEGORY_DATE_CREATED + " TEXT NOT NULL,"
                + CATEGORY_ONGOING_TASK_COUNT + " INTEGER NOT NULL,"
                + CATEGORY_COMPLETED_TASK_COUNT + " INTEGER NOT NULL"
                + ")";
        database.execSQL(CREATE_CATEGORY_TABLE);
    }

    private void createTaskTable( SQLiteDatabase database ) {
        String CREATE_TASK_TABLE =
            "CREATE TABLE " + TASK_TABLE + "("
                + TASK_ID + " TEXT PRIMARY KEY,"
                + TASK_USER_ID + " TEXT NOT NULL," 
                + TASK_CATEGORY_ID + " TEXT NOT NULL,"
                + TASK_TITLE + " TEXT NOT NULL,"
                + TASK_DESCRIPTION + " TEXT,"
                + TASK_DATE_CREATED + " TEXT NOT NULL,"
                + TASK_DUE_DATE + " TEXT,"
                + TASK_IS_COMPLETED + " INTEGER NOT NULL,"
                + TASK_PRIORITY_LEVEL + " TEXT,"
                + "FOREIGN KEY(" + TASK_USER_ID + ") REFERENCES " + USER_TABLE + "(" + USER_ID + ")"
                + "FOREIGN KEY(" + TASK_CATEGORY_ID + ") REFERENCES " + CATEGORY_TABLE + "(" + CATEGORY_ID + ")"
                + ")";
        database.execSQL(CREATE_TASK_TABLE);
    }

    private void createSubtaskTable( SQLiteDatabase database ) {
        String CREATE_SUBTASK_TABLE =
            "CREATE TABLE " + SUBTASK_TABLE + "("
                + SUBTASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + SUBTASK_TASK_ID + " TEXT NOT NULL,"
                + SUBTASK_DESCRIPTION + " TEXT NOT NULL,"
                + SUBTASK_IS_COMPLETED + " INTEGER NOT NULL,"
                + "FOREIGN KEY(" + SUBTASK_TASK_ID + ") REFERENCES " + TASK_TABLE + "(" + TASK_ID + ")"
                + ")";
        database.execSQL(CREATE_SUBTASK_TABLE);
    }


    /*|*******************************************************
                    Database Table Removal
    *********************************************************/
    public void dropAllTables( SQLiteDatabase database ) {
        database.execSQL( "DROP TABLE IF EXISTS " + USER_TABLE );
        database.execSQL( "DROP TABLE IF EXISTS " + CATEGORY_TABLE );
        database.execSQL( "DROP TABLE IF EXISTS " + TASK_TABLE );
        database.execSQL( "DROP TABLE IF EXISTS " + SUBTASK_TABLE );
    }

}