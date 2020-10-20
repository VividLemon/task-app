package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.taskapp.models.Task;

import java.util.ArrayList;

public class TaskListActivity extends AppCompatActivity {

    public static final String TAG = "TaskListActivity";
    ListView lsTasks;
    Button btnAdd;
    TaskDataAccess tda;
    ArrayList<Task> allTasks;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list2);

        lsTasks = findViewById(R.id.lsTasks);
        tda = new TaskDataAccess(this);
        allTasks = tda.getAllTasks();
        btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TaskListActivity.this, TaskDetailsActivity.class);
                startActivity(i);
            }
        });

        ArrayAdapter<Task> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, allTasks);
        lsTasks.setAdapter(adapter);

        lsTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Task selectedTask = allTasks.get(position);
                Log.d(TAG, "SELECTED TASK: " + selectedTask.toString());
                Intent i = new Intent(TaskListActivity.this, TaskDetailsActivity.class);
                i.putExtra(TaskDetailsActivity.EXTRA_TASK_ID, selectedTask.getId());
                startActivity(i);
            }
        });


    }
}