package com.example.taskapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.taskapp.models.Task;
import com.example.taskapp.sqlite.MySQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class SQLTaskDataAccess implements Taskable{

    private static final String TAG = "SQLTaskDataAccess";
    private ArrayList<Task> allTasks;
    private Context context;
    private MySQLiteOpenHelper dbHelper;
    private SQLiteDatabase database;

    public static final String TABLE_NAME = "tasks";
    public static final String COLUMN_TASK_ID = "_id"; // it needs to be named _id in db
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_DONE = "done";
    public static final String COLUMN_DUE = "due";
    public static final String TABLE_CREATE = String.format("create table %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER )",
            TABLE_NAME,
            COLUMN_TASK_ID,
            COLUMN_DESCRIPTION,
            COLUMN_DUE,
            COLUMN_DONE
    );
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);



    public SQLTaskDataAccess(Context context){
        Log.d(TAG, "Instantiating SQLDataAccess");
        this.context = context;
        this.dbHelper = new MySQLiteOpenHelper(context);
        this.database = dbHelper.getWritableDatabase();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        String query = String.format("SELECT %s, %s, %s, %s from %s", COLUMN_TASK_ID, COLUMN_DESCRIPTION, COLUMN_DUE, COLUMN_DONE, TABLE_NAME);
        Cursor c = database.rawQuery(query, null);
        if(c != null && c.getCount() > 0){
            c.moveToFirst();
            while(!c.isAfterLast()){
                long id = c.getLong(0);
                String description = c.getString(1);
                String due = c.getString(2);
                Boolean done = c.getLong(3) == 1 ? true : false;
                Date dueDate = null;
                try{

                    dueDate = simpleDateFormat.parse(due);
                }
                catch (Exception e){
                    Log.d(TAG, e.toString());
                }
                Task task = new Task(id,description,dueDate,done);
                tasks.add(task);
                c.moveToNext(); //never forget this!
            }
            c.close();
        }
        return tasks;
    }

    @Override
    public Task getTaskById(long id) {
        String query = String.format("SELECT %s, %s, %s, %s FROM %s WHERE %s = %d", COLUMN_TASK_ID, COLUMN_DESCRIPTION, COLUMN_DUE, COLUMN_DONE, TABLE_NAME, COLUMN_TASK_ID, id);
        Cursor c = database.rawQuery(query, null);
        if(c != null && c.getCount() == 1) {
            c.moveToFirst();
            String description = c.getString(1);
            String due = c.getString(2);
            boolean done = c.getLong(3) == 1 ? true : false;
            Date dueDate = null;
            try {
                dueDate = simpleDateFormat.parse(due);
                Log.d(TAG, dueDate.toString());
            } catch (Exception e) {
                Log.d(TAG, e.toString());
            }
            c.close();
            return new Task(id, description, dueDate, done);
        }
        else{
            return null;
        }
    }

    @Override
    public Task insertTask(Task t) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_DESCRIPTION, t.getDescription());
        contentValues.put(COLUMN_DUE,  simpleDateFormat.format(t.getDue()));
        contentValues.put(COLUMN_DONE, t.isDone() ? 1 : 0);
        long insertId = database.insert(TABLE_NAME, null, contentValues);
        t.setId(insertId);
        return t;
    }

    @Override
    public Task updateTask(Task t) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_DESCRIPTION, t.getDescription());
        values.put(COLUMN_DUE, simpleDateFormat.format(t.getDue()));
        values.put(COLUMN_DONE, t.isDone() ? 1 : 0);
        int rowsUpdated = database.update(TABLE_NAME, values, COLUMN_TASK_ID + " = " + t.getId(), null);
        return t;
    }

    @Override
    public int deleteTask(Task t) {
        int rowsDeleted = database.delete(TABLE_NAME, COLUMN_TASK_ID + " = " + t.getId(), null);
        return rowsDeleted;
    }
}
