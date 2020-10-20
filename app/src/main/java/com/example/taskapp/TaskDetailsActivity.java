package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.taskapp.models.Task;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailsActivity";
    public static final String EXTRA_TASK_ID = "taskId";
    Task task;
    TaskDataAccess tda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        tda = new TaskDataAccess(this);
        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_TASK_ID, 0);
        if(id > 0){

            task = tda.getTaskById(id);
            Log.d(TAG,task.toString());
        }else{
            Log.d(TAG, "Creating a new task");
        }
    }
}