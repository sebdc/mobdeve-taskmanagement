package com.mobdeve.s13.g4.taskmanagement.database;

import android.content.ContentValues;
import android.content.Context;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.mobdeve.s13.g4.taskmanagement.models.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    /*|*******************************************************
                        Category Methods
    *********************************************************/
    // - Category Table
    private static final String CATEGORY_TABLE = "categories";
    private static final String CATEGORY_ID = "id";
    private static final String CATEGORY_NAME = "name";
    private static final String CATEGORY_DATE_CREATED = "dateCreated";
    private static final String CATEGORY_ONGOING_TASK_COUNT = "ongoingTaskCount";
    private static final String CATEGORY_COMPLETED_TASK_COUNT = "completedTaskCount";
    private static final String CATEGORY_MAIN_COLOR = "mainColor";
    private static final String CATEGORY_SUB_COLOR = "subColor";

    public void insertCategory( Category category ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CATEGORY_ID, category.getId());
        values.put(CATEGORY_NAME, category.getName());
        values.put(CATEGORY_DATE_CREATED, category.getDateCreated().getTime());
        values.put(CATEGORY_ONGOING_TASK_COUNT, category.getOngoingTaskCount());
        values.put(CATEGORY_COMPLETED_TASK_COUNT, category.getCompletedTaskCount());
        values.put(CATEGORY_MAIN_COLOR, category.getMainColor());
        values.put(CATEGORY_SUB_COLOR, category.getSubColor());
        db.insert(CATEGORY_TABLE, null, values);
        db.close();
    }

    public List<Category> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor.moveToFirst() ) {
            int categoryNameIndex = cursor.getColumnIndex(CATEGORY_NAME);
            int categoryIdIndex = cursor.getColumnIndex(CATEGORY_ID);
            int categoryDateCreatedIndex = cursor.getColumnIndex(CATEGORY_DATE_CREATED);
            int categoryOngoingTaskCountIndex = cursor.getColumnIndex(CATEGORY_ONGOING_TASK_COUNT);
            int categoryCompletedTaskCountIndex = cursor.getColumnIndex(CATEGORY_COMPLETED_TASK_COUNT);
            int categoryMainColorIndex = cursor.getColumnIndex(CATEGORY_MAIN_COLOR);
            int categorySubColorIndex = cursor.getColumnIndex(CATEGORY_SUB_COLOR);

            do {
                Category category = new Category( cursor.getString(categoryNameIndex) );
                category.setId( cursor.getString(categoryIdIndex) );
                category.setDateCreated( new Date(cursor.getLong(categoryDateCreatedIndex)) );
                category.setOngoingTaskCount( cursor.getInt(categoryOngoingTaskCountIndex) );
                category.setCompletedTaskCount( cursor.getInt(categoryCompletedTaskCountIndex) );
                category.setMainColor( cursor.getString(categoryMainColorIndex) );
                category.setSubColor( cursor.getString(categorySubColorIndex) );
                categoryList.add(category);
            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();
        return categoryList;
    }

    public Category getCategoryById( String categoryId ) {
        if( categoryId == null || categoryId.isEmpty() ) {
            return null;
        }

        String selectQuery = "SELECT * FROM " + CATEGORY_TABLE + " WHERE " + CATEGORY_ID + " = ?";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{categoryId});

        if( cursor.moveToFirst() ) {
            int categoryNameIndex = cursor.getColumnIndex(CATEGORY_NAME);
            int categoryIdIndex = cursor.getColumnIndex(CATEGORY_ID);
            int categoryDateCreatedIndex = cursor.getColumnIndex(CATEGORY_DATE_CREATED);
            int categoryOngoingTaskCountIndex = cursor.getColumnIndex(CATEGORY_ONGOING_TASK_COUNT);
            int categoryCompletedTaskCountIndex = cursor.getColumnIndex(CATEGORY_COMPLETED_TASK_COUNT);
            int categoryMainColorIndex = cursor.getColumnIndex(CATEGORY_MAIN_COLOR);
            int categorySubColorIndex = cursor.getColumnIndex(CATEGORY_SUB_COLOR);

            Category category = new Category(cursor.getString(categoryNameIndex));
            category.setId(cursor.getString(categoryIdIndex));
            category.setDateCreated(new Date(cursor.getLong(categoryDateCreatedIndex)));
            category.setOngoingTaskCount(cursor.getInt(categoryOngoingTaskCountIndex));
            category.setCompletedTaskCount(cursor.getInt(categoryCompletedTaskCountIndex));
            category.setMainColor(cursor.getString(categoryMainColorIndex));
            category.setSubColor(cursor.getString(categorySubColorIndex));

            cursor.close();
            db.close();
            return category;
        }

        cursor.close();
        db.close();
        return null;
    }

    /*|*******************************************************
                        Task Methods
    *********************************************************/
    private static final String TASK_TABLE = "tasks";
    private static final String TASK_ID = "id";
    private static final String TASK_USER_ID = "userId";
    private static final String TASK_CATEGORY_ID = "categoryId";
    private static final String TASK_TITLE = "title";
    private static final String TASK_DESCRIPTION = "description";
    private static final String TASK_DATE_CREATED = "dateCreated";
    private static final String TASK_DUE_DATE = "dueDate";
    private static final String TASK_DUE_TIME = "dueTime";
    private static final String TASK_IS_COMPLETED = "isCompleted";
    private static final String TASK_PRIORITY_LEVEL = "priorityLevel";

    public void insertTask( Task task ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TASK_ID, task.getId());
        values.put(TASK_TITLE, task.getTitle());

        if( task.getDescription() != null ) {
            values.put(TASK_DESCRIPTION, task.getDescription());
        }

        if( task.getDateCreated() != null ) {
            values.put(TASK_DATE_CREATED, task.getDateCreated());
        }

        if( task.getCategory() != null && task.getCategory().getId() != null ) {
            values.put(TASK_CATEGORY_ID, task.getCategory().getId());
        }

        if( task.getPriorityLevel() != null ) {
            values.put(TASK_PRIORITY_LEVEL, task.getPriorityLevel());
        }

        if( task.getDueDate() != null ) {
            values.put(TASK_DUE_DATE, task.getDueDate());
        }

        if( task.getDueTime() != null ) {
            values.put(TASK_DUE_TIME, task.getDueTime());
        }

        values.put(TASK_IS_COMPLETED, task.isCompleted() ? 1 : 0);

        db.insert(TASK_TABLE, null, values);
        db.close();
    }

    public List<Task> getAllTasks() {
        List<Task> taskList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TASK_TABLE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if( cursor.moveToFirst() ) {
            int taskTitleIndex = cursor.getColumnIndex(TASK_TITLE);
            int taskDescriptionIndex = cursor.getColumnIndex(TASK_DESCRIPTION);
            int taskDateCreatedIndex = cursor.getColumnIndex(TASK_DATE_CREATED);
            int taskIdIndex = cursor.getColumnIndex(TASK_ID);
            int taskCategoryIdIndex = cursor.getColumnIndex(TASK_CATEGORY_ID);
            int taskPriorityLevelIndex = cursor.getColumnIndex(TASK_PRIORITY_LEVEL);
            int taskDueDateIndex = cursor.getColumnIndex(TASK_DUE_DATE);
            int taskDueTimeIndex = cursor.getColumnIndex(TASK_DUE_TIME);
            int taskIsCompletedIndex = cursor.getColumnIndex(TASK_IS_COMPLETED);

            do {
                String taskId = cursor.getString(taskIdIndex);
                String taskTitle = cursor.getString(taskTitleIndex);
                String taskDescription = cursor.getString(taskDescriptionIndex);
                String taskDateCreated = cursor.getString(taskDateCreatedIndex);
                String taskDueDate = cursor.getString(taskDueDateIndex);
                String taskDueTime = cursor.getString(taskDueTimeIndex);
                String taskCategoryId = cursor.getString(taskCategoryIdIndex);
                String taskPriorityLevel = cursor.getString(taskPriorityLevelIndex);
                boolean taskIsCompleted = cursor.getInt(taskIsCompletedIndex) == 1;

                Task task = new Task(taskTitle, taskId);
                task.setDescription(taskDescription);
                task.setCategory(getCategoryById(taskCategoryId));
                task.setPriorityLevel(taskPriorityLevel);
                task.setDateCreated(taskDateCreated);
                task.setDueDate(taskDueDate);
                task.setDueTime(taskDueTime);
                task.setCompleted(taskIsCompleted);
                taskList.add(task);

            } while( cursor.moveToNext() );
        }

        cursor.close();
        db.close();
        return taskList;
    }
}
