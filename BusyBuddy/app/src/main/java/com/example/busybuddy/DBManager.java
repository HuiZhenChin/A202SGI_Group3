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
