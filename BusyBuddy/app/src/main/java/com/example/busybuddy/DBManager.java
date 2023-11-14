package com.example.busybuddy;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


public class DBManager {
    private DB db;
    private Context context;
    private SQLiteDatabase database;

    public DBManager(Context c) {

        context = c;
    }

    public DBManager open() throws SQLException {
        db = new DB(context);
        database = db.getWritableDatabase();


        return this;
    }

    public void close() {
        db.close();
    }

    public boolean nameCheck(String user){
        Cursor cursor = database.rawQuery("Select * from " + DB.TABLE_NAME + " where " +
                DB.USERNAME + " = ?", new String[]{user});
        if (cursor.getCount() > 0){
            return true;
        }
        else{
            return false;
        }
    }

    // new user sign up
    public void register(String username, String mail, String password, String dob, String phrase) {
        ContentValues registerNew = new ContentValues();
        registerNew.put(DB.USERNAME, username);
        registerNew.put(DB.DOB, dob);
        registerNew.put(DB.PASS, password);
        registerNew.put(DB.EMAIL, mail);
        registerNew.put(DB.PHRASE, phrase);
        database.insert(DB.TABLE_NAME, null, registerNew);
    }

    // user login
    public boolean login(String username, String password) {
        String[] columns = {DB.USER_ID};
        String selection = DB.USERNAME + " = ?";
        String[] selectionArgs = {username};
        String storedPassword="";
        Cursor cursor = database.query(DB.TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            // if the username exists, now check if the password matches
            int userIdIndex = cursor.getColumnIndex(DB.USER_ID);
            if (userIdIndex != -1) {
                int userId = cursor.getInt(userIdIndex);
                cursor.close();

                // check if the provided password matches the one in the database
                String[] passwordColumns = {DB.PASS};
                String passwordSelection = DB.USER_ID + " = ?";

                String[] passwordSelectionArgs = {String.valueOf(userId)};

                Cursor passwordCursor = database.query(DB.TABLE_NAME, passwordColumns, passwordSelection, passwordSelectionArgs, null, null, null);

                if (passwordCursor != null && passwordCursor.moveToFirst()) {
                    int passwordIndex = passwordCursor.getColumnIndex(DB.PASS);
                    if (passwordIndex != -1) {
                        storedPassword = passwordCursor.getString(passwordIndex);
                        passwordCursor.close();

                        if (password.equals(storedPassword)) {
                            // if password matches, login successful
                            return true;
                        }
                    }
                    passwordCursor.close();
                }
            }
            cursor.close();
        }


        // username doesn't exist or password doesn't match
        return false;
    }

    // get user ID based on current logged-in username
    public int getUserID(String username) {
        Cursor cursor = database.rawQuery("SELECT " + DB.USER_ID + " FROM " + DB.TABLE_NAME +
                " WHERE " + DB.USERNAME + " = ? ", new String[]{username});

        int userId = 0;

        if (cursor != null && cursor.moveToFirst()) {
            int userIdIndex = cursor.getColumnIndex(DB.USER_ID);
            if (userIdIndex != -1) {
                userId = cursor.getInt(userIdIndex);
            }
            cursor.close();
        } else {
            userId = -1;
        }

        return userId;
    }


    // fetch current logged-in user for display user information
    public Cursor fetchUser(int userID) {
        String[] columns = {DB.USERNAME, DB.DOB, DB.PASS, DB.EMAIL, DB.PHRASE};
        String selection = DB.USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        return database.query(DB.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
    }

    // validate whether user is in the database by finding user with username and phrase
    public boolean validateUser(String user, String phrase) {
        String selection = DB.USERNAME + " = ? AND " + DB.PHRASE + "= ?";
        String[] selectionArgs = {String.valueOf(user), String.valueOf(phrase)};
        Cursor cursor = database.query(DB.TABLE_NAME, null, selection, selectionArgs, null, null, null);
        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    // insert notification message
    public void ActivityLog(String message, int userid, String date) {
        ContentValues createNew = new ContentValues();
        createNew.put(DB.USER_ID, userid);
        createNew.put(DB.MESSAGE, message);
        createNew.put(DB.MESSAGE_DATE, date);
        database.insert(DB.TABLE_NAME5, null, createNew);
    }

    // fetch all the unread notification message
    public Cursor fetchUnreadActivity(int userID) {
        String selection = DB.USER_ID + " = ? AND " + DB.READ + "= 'NO'";
        String[] selectionArgs = {String.valueOf(userID)};
        return database.query(DB.TABLE_NAME5, null, selection, selectionArgs, null, null, null);
    }

    // update the read notification message
    public void updateRead(int messageID) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.READ, "YES");
        String whereClause = DB.MESSAGE_ID + " = ?";
        String[] whereArgs = {String.valueOf(messageID)};
        database.update(DB.TABLE_NAME5, contentValues, whereClause, whereArgs);
    }

    // fetch the notification message
    public Cursor fetchActivity(int userID) {
        String[] columns = {DB.MESSAGE};
        String selection = DB.USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        return database.query(DB.TABLE_NAME5, columns, selection, selectionArgs, null, null, null);
    }

    // reset password function
    public int resetPass(long _id, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.PASS, password);
        int i = database.update(DB.TABLE_NAME, contentValues, DB.USER_ID + " = " + _id, null);
        return i;
    }


    // update user information (Edit My Account)
    public int update(long _id, String username, String mail, String password, String dob, String phrase) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.USERNAME, username);
        contentValues.put(DB.DOB, dob);
        contentValues.put(DB.PASS, password);
        contentValues.put(DB.EMAIL, mail);
        contentValues.put(DB.PHRASE, phrase);
        int i = database.update(DB.TABLE_NAME, contentValues, DB.USER_ID + " = " + _id, null);
        return i;
    }

    
    // task

    // update task priority while dragging
    public void updateTaskPriority(int taskId, String newPriority, int newNo ) {

        ContentValues values = new ContentValues();
        values.put(DB.DIFFICULTY, newPriority);
        values.put(DB.DIFFICULTY_NO, newNo);
        database.update(DB.TABLE_NAME2, values, DB.TASK_ID + " = " + taskId, null);

    }

    // insert the newly created task information
    public void createtask(String tasktitle, String duedate, String difficulty, int difficultyNo, int listPos,String folder, String note, String status, int userid) {
        ContentValues createNew = new ContentValues();
        createNew.put(DB.TASK, tasktitle);
        createNew.put(DB.DUE_DATE, duedate);
        createNew.put(DB.DIFFICULTY, difficulty);
        createNew.put(DB.DIFFICULTY_NO, difficultyNo);
        createNew.put(DB.LIST_POSITION, listPos);
        createNew.put(DB.FOLDER, folder);
        createNew.put(DB.USER_ID, userid);
        createNew.put(DB.NOTE, note);
        createNew.put(DB.STATUS, status);
        database.insert(DB.TABLE_NAME2, null, createNew);
    }

    // display the task from database based on user ID
    public Cursor displayTask(int userID) {
        String[] columns = {DB.TASK_ID, DB.TASK, DB.DUE_DATE, DB.DIFFICULTY, DB.DIFFICULTY_NO, DB.FOLDER, DB.NOTE, DB.STATUS};
        String selection = DB.USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        String orderBy = DB.NEW_POSITION + " ASC"; // arranged and ordered by new position
        return database.query(DB.TABLE_NAME2, columns, selection, selectionArgs, null, null, orderBy);
    }

    // get the task ID based on title and due date (choose due date as another criteria because sometimes the task title will be the same)
    public int getTaskIdByTitleAndDate(String title, String date) {
        db.getWritableDatabase();
        String[] columns = {DB.TASK_ID};

        // define the table and the WHERE clause to find the task by title and date
        String table = DB.TABLE_NAME2;
        String whereClause = DB.TASK + " = ? AND " + DB.DUE_DATE + " = ?";
        String[] whereArgs = {title, date};

        // retrieve the task's ID
        Cursor cursor = database.query(table, columns, whereClause, whereArgs, null, null, null);

        int taskId = 0;
        if (cursor != null && cursor.moveToFirst()) {
            int taskIdIndex = cursor.getColumnIndex(DB.TASK_ID);
            if (taskIdIndex != -1) {
                taskId = cursor.getInt(taskIdIndex);
            }
            cursor.close();
        } else {
            taskId = -1;
        }

        // close database connection
        cursor.close();

        return taskId;
    }
