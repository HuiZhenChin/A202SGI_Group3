package com.example.busybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DB extends SQLiteOpenHelper {

    // table name
    // USER: store user information
    public static final String TABLE_NAME = "USER";
    // TASK: store task information
    public static final String TABLE_NAME2 = "TASK";
    // CALENDAR: store task date and title information
    public static final String TABLE_NAME3 = "CALENDAR";
    // FOLDER: store task folder information
    public static final String TABLE_NAME4 = "FOLDER";
    public static final String TABLE_NAME5 = "NOTIFICATION";

    // table columns
    // USER table
    public static final String USER_ID = "_id";     // unique identification
    public static final String USERNAME = "username";   // username
    public static final String DOB = "birth_date";  // date of birth
    public static final String PASS = "pass";  // password
    public static final String EMAIL = "email";  // email
    public static final String PHRASE = "phrase";  // special phrase for reset password purpose
    // TASK table
    public static final String TASK_ID = "taskID";   // unique identification of task
    public static final String TASK = "taskName";  //task title
    public static final String DUE_DATE = "due";  // task due date
    public static final String DIFFICULTY = "diff";  // task difficulty/ priority
    public static final String DIFFICULTY_NO="diff_no";  // identification number of different priority (used for task display purpose)
    public static final String LIST_POSITION= "list_position"; // task initial position in the list (before drag and drop/ change position)
    public static final String NEW_POSITION= "new_position"; // task latest/ new position in the list (after drag and drop/ change position)
    public static final String FOLDER = "folder"; // task folder name
    public static final String NOTE = "note"; // task important/ extra notes
    public static final String STATUS = "status"; // task status (in progress/ completed)
    // CALENDAR table
    public static final String EVENT = "event"; // calendar event (task information)
    public static final String DATE = "date"; // task/ event date
    public static final String TIME = "time"; // task/ event created time based on device
    // FOLDER table
    public static final String FOLDER_NAME = "foldername"; // folder name
    // NOTIFICATION table
    public static final String MESSAGE_ID = "messageID"; // message ID
    public static final String MESSAGE = "message"; // message content
    public static final String MESSAGE_DATE = "message_date"; // message created date

    // database name
    static final String DB_NAME = "BUSY-BUDDY.DB";

    // database version
    static final int DB_VERSION = 1;

    // create table query
    // USER table (TABLE_NAME)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + USER_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + USERNAME + " TEXT NOT NULL, " +
            DOB + " TEXT NOT NULL, " + PASS + " PASSWORD NOT NULL, " + EMAIL + " TEXT NOT NULL, " +
            PHRASE + " TEXT NOT NULL);";

    // TASK table (TABLE_NAME2)
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

    // CALENDAR table (TABLE_NAME3)
    private static final String CREATE_TABLE3 = "CREATE TABLE " + TABLE_NAME3 + " (" +
            EVENT + " TEXT NOT NULL, " +
            DATE + " TEXT NOT NULL, " +
            TIME + " TEXT, " +
            USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + USER_ID + ") REFERENCES " + "USER(" + USER_ID + "));";

    // FOLDER table (TABLE_NAME4)
    private static final String CREATE_TABLE4 = "create table " + TABLE_NAME4 + "(" + FOLDER_NAME
            + " TEXT NOT NULL, " + USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + USER_ID + ") REFERENCES USER(" + USER_ID + "));";

    // NOTIFICATION table (TABLE_NAME5)
    private static final String CREATE_TABLE5 = "CREATE TABLE " + TABLE_NAME5 + " (" +
            MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            MESSAGE + " TEXT NOT NULL, " +
            MESSAGE_DATE + " TEXT NOT NULL, " +
            USER_ID + " INTEGER NOT NULL, " +
            "FOREIGN KEY(" + USER_ID + ") REFERENCES USER(" + USER_ID + "));";


    public DB(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // execute the tables
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE);
        db.execSQL(CREATE_TABLE2);
        db.execSQL(CREATE_TABLE3);
        db.execSQL(CREATE_TABLE4);
        db.execSQL(CREATE_TABLE5);


    }

    // upgrade on database(currently not used)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


     // delete task function (swipe to delete)
    public void deletetask(int _id) {
        SQLiteDatabase database = this.getWritableDatabase();  // write the changes on the database

        database.beginTransaction();
        try {
            database.delete(DB.TABLE_NAME2, DB.TASK_ID + " = ?", new String[]{String.valueOf(_id)});
            database.setTransactionSuccessful();
        } catch (Exception e) {
            // Handle the exception, log it, or throw it if necessary
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    // get task current status (In Progress/ Completed)
    public String getSelectedStatus() {

        String selectedStatus = "";

        SQLiteDatabase db = this.getReadableDatabase();  // read from database
        Cursor cursor = db.rawQuery("SELECT status FROM TASK", null);
        if (cursor.moveToFirst()) {
            selectedStatus = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return selectedStatus;
    }

    // get task recent folder
    public String getSelectedFolder() {
        String selectedFolder = "";

        SQLiteDatabase db = this.getReadableDatabase();  // read from database
        String query = "SELECT folder FROM TASK ";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            selectedFolder = cursor.getString(0);
        }

        cursor.close();
        db.close();

        return selectedFolder;
    }

    // update new task status from In Progress to Completed or from Completed to In Progress (in case user feels to change)
    public void updateTaskStatus(int taskId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();  // write to database
        ContentValues values = new ContentValues();
        values.put(DB.STATUS, status);
        db.update(DB.TABLE_NAME2, values, DB.TASK_ID + " = " + taskId, null);
        db.close();
    }

    // update new task folder (after user change the task folder)
    public void updateTaskFolder(int taskID, String folder) {
        SQLiteDatabase db = this.getWritableDatabase(); // write to database
        ContentValues values = new ContentValues();
        values.put(FOLDER, folder);
        db.update(TABLE_NAME2, values, "taskID=?", new String[]{String.valueOf(taskID)});
        db.close();
    }

     // update the position order of the task after drag and drop
    public void updateOrder(long taskID, int newPos) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NEW_POSITION, newPos);
        String whereClause = TASK_ID + " = ?";
        String[] whereArgs = { String.valueOf(taskID) };
        db.update(TABLE_NAME2, values, whereClause, whereArgs);
        db.close();
    }

    // get the task ID based on title and due date (choose due date as another criteria because sometimes the task title will be the same)
    public int getTaskIdByTitleAndDate(String title, String date) {
        SQLiteDatabase db = this.getReadableDatabase(); // read from database

        String[] columns = {DB.TASK_ID};
        String table = DB.TABLE_NAME2;
        String whereClause = DB.TASK + " = ? AND " + DB.DUE_DATE + " = ?";
        String[] whereArgs = {title, date};

        Cursor cursor = null;
        int taskId = -1; // task ID = -1 if the task is not found

        try {
            cursor = db.query(table, columns, whereClause, whereArgs, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                int taskIdIndex = cursor.getColumnIndex(DB.TASK_ID);
                if (taskIdIndex != -1) {
                    taskId = cursor.getInt(taskIdIndex);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            db.close();
        }

        return taskId;
    }

     // check if "Not required folder" already exist
    public boolean isFolderExist(String folderName, int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;

        try {
            String query = "SELECT COUNT(*) FROM " + DB.TABLE_NAME4 +
                    " WHERE " + DB.FOLDER_NAME + " = ? AND " + DB.USER_ID + " = ?";
            cursor = db.rawQuery(query, new String[]{folderName, String.valueOf(userID)});

            // move the cursor to the first row
            if (cursor.moveToFirst()) {
                // get the count from the query result
                int count = cursor.getInt(0);
                return count > 0; // if count > 0, the folder exists; otherwise, it does not exist
            }
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            // close the database connection
            db.close();
        }

        return false; // if something went wrong, assume the folder does not exist
    }

    // get list of tasks (title and due date) 
    public List<TaskItem> getTasksByUser(int userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        List<TaskItem> taskItems = new ArrayList<>();

    // check if notifications message already exist
    public boolean isMessageExist(String messageContent, int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
