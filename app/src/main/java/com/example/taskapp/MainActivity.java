package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.taskapp.models.Task;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        TaskDataAccess tda = new TaskDataAccess(this);
        Task t = new Task("Haircut", new Date(), false);
        tda.insertTask(t);
        showAllTasks();
        t.setDescription("DO HOMEWORK");
        tda.updateTask(t);
        tda.deletedTask(t);
        showAllTasks();
    }

    private void showAllTasks(){
        for(Task t: TaskDataAccess.allTasks){
            Log.d(TAG, t.toString());
        }
    }
}