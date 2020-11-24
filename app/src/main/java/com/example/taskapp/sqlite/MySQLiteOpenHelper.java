package com.example.taskapp.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.taskapp.SQLTaskDataAccess;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "SQLiteOpenHelper";
    private static final String DATA_BASE_NAME = "tasks.sqlite";
    private static final int DATA_BASE_VERSION = 1;

    public MySQLiteOpenHelper(Context context){
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String sql = "CREATE TABLE  blah (_id INTEGER PRIMARY KEY AUTOINCREMENT, description TEXT, due TEXT, done INTEGER)";
        String sql = SQLTaskDataAccess.TABLE_CREATE;
        db.execSQL(sql);
        Log.d(TAG, sql);

//        String insertSql = "INSERT INTO blah (description, due, done) VALUES ('foo', 'bar', 7)";
//        db.execSQL(insertSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "UPGRADING DATABASE");
        Log.d(TAG, "OLD VERSION: " + oldVersion);
        Log.d(TAG, "NEW VERSION: " + newVersion);

        switch(oldVersion){
            case 1:
                Log.d(TAG, "Upgrading from version 1");
                String sql = "ALTER TABLE blah ADD new_column TEXT;";
                db.execSQL(sql);


        }
    }
}
