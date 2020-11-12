package com.example.taskapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.taskapp.models.Task;
import com.example.taskapp.sqlite.MySQLiteOpenHelper;

import java.util.ArrayList;

public class SQLTaskDataAccess implements Taskable{

    private static final String TAG = "SQLTaskDataAccess";
    private ArrayList<Task> allTasks;
    private Context context;
    private MySQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public SQLTaskDataAccess(Context context){
        Log.d(TAG, "Instantiating SQLDataAccess");
        this.context = context;
        this.dbHelper = new MySQLiteOpenHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return null;
    }

    @Override
    public Task getTaskById(long id) {
        return null;
    }

    @Override
    public Task insertTask(Task t) {
        return null;
    }

    @Override
    public Task updateTask(Task t) {
        return null;
    }

    @Override
    public int deleteTask(Task t) {
        return 0;
    }
}
