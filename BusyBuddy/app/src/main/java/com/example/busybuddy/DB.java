package com.example.busybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "USER";
    public static final String TABLE_NAME2 = "TASK";
    public static final String TABLE_NAME3 = "CALENDAR";
    public static final String TABLE_NAME4 = "FOLDER";

    // Table columns
    public static final String USER_ID = "_id";
    public static final String USERNAME = "username";
    public static final String DOB = "birth_date";
    public static final String PASS = "pass";
    public static final String EMAIL = "email";
    public static final String TASK_ID = "taskID";
    public static final String TASK = "taskName";
    public static final String DUE_DATE = "due";
    public static final String DIFFICULTY = "diff";
    public static final String DIFFICULTY_NO="diff_no";
    public static final String LIST_POSITION= "list_position";
    public static final String NEW_POSITION= "new_position";
    public static final String FOLDER = "folder";
    public static final String NOTE = "note";
    public static final String STATUS = "status";
    public static final String EVENT = "event";
    public static final String DATE = "date";
    public static final String TIME = "time";
    public static final String FOLDER_NAME = "foldername";

    // Database Information
    static final String DB_NAME = "BUSY-BUDDY.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private static final String CREATE_TABLE = "create table " + TABLE_NAME + "(" + USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT NOT NULL, " +
            DOB + " TEXT NOT NULL, " + PASS + " PASSWORD NOT NULL, " + EMAIL + " TEXT NOT NULL);";
    private static final String CREATE_TABLE2 = "CREATE TABLE " + TABLE_NAME2 + " (" +
            TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TASK + " TEXT NOT NULL, " +
            DUE_DATE + " TEXT NOT NULL, " +
            DIFFICULTY + " TEXT NOT NULL, " +
            DIFFICULTY_NO + " INTEGER NOT NULL,"+
            LIST_POSITION + " INTEGER NOT NULL,"+
            NEW_POSITION+ " INTEGER, "+
            FOLDER + " TEXT , " +
            USER_ID + " INTEGER NOT NULL, " +
            NOTE + " TEXT, " +
            STATUS + " TEXT NOT NULL, " +
            "FOREIGN KEY (" + USER_ID + ") REFERENCES USER (" + USER_ID + "));";

    private static final String CREATE_TABLE3 = "CREATE TABLE " + TABLE_NAME3 + " (" +
            EVENT + " TEXT NOT NULL, " +
            DATE + " TEXT NOT NULL, " +
            TIME + " TEXT, " +
            USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + USER_ID + ") REFERENCES " + "USER(" + USER_ID + "));";
    private static final String CREATE_TABLE4 = "create table " + TABLE_NAME4 + "(" + FOLDER_NAME
            + " TEXT NOT NULL, " + USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + USER_ID + ") REFERENCES USER(" + USER_ID + "));";

    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        //db.execSQL(CREATE_TABLE5);


    }

    // upgrade on database(currently not used)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

