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

    public void ActivityLog(String message, int userid, String date) {
        ContentValues createNew = new ContentValues();
        createNew.put(DB.USER_ID, userid);
        createNew.put(DB.MESSAGE, message);
        createNew.put(DB.MESSAGE_DATE, date);
        database.insert(DB.TABLE_NAME5, null, createNew);
    }

    public Cursor fetchActivity(int userID) {
        String[] columns = {DB.MESSAGE};
        String selection = DB.USER_ID + " = ?";
        String[] selectionArgs = {String.valueOf(userID)};
        return database.query(DB.TABLE_NAME5, columns, selection, selectionArgs, null, null, null);
    }

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
