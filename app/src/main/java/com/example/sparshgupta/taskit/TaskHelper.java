package com.example.sparshgupta.taskit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by sparshgupta on 26/02/17.
 */


public class TaskHelper extends SQLiteOpenHelper {

    public static final String TASK_TABLE_NAME = "Task";
    public static final String TASK_TITLE = "title";
    public static final String TASK_DATE = "date";
    public static final String TASK_TIME = "time";
    public static final String TASK_DESCRIPTION = "description";
    public static final String TASK_REMINDER = "reminder";
    public static final String TASK_COLUMN_ID = "_id";
    public static final String TASK_ALARM_TIME = "taskAlarmTime";
    public static final String IS_ALARM = "isAlarm";

    public TaskHelper(Context context) {
        super(context, "TaskITdatabasedb", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_Query = "CREATE TABLE " + TASK_TABLE_NAME +
                "( " + TASK_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TASK_DATE + " TEXT, " +
                TASK_TITLE + " TEXT, " +
                TASK_DESCRIPTION + " TEXT, " +
                TASK_REMINDER + " INTEGER, " +
                TASK_TIME + " TEXT, " +
                IS_ALARM + " REAL, " +
                TASK_ALARM_TIME + " REAL);";
        db.execSQL(sql_Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
