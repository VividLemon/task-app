package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.taskapp.models.Task;

import java.text.SimpleDateFormat;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailsActivity";
    public static final String EXTRA_TASK_ID = "taskId";
    Task task;
    TaskDataAccess tda;

    EditText txtDescription;
    EditText txtDueDate;
    CheckBox chkDone;
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);

        txtDescription = findViewById(R.id.txtDescription);
        txtDueDate = findViewById(R.id.txtDueDate);
        chkDone = findViewById(R.id.chkDone);
        btnSave = findViewById(R.id.btnSave);

        tda = new TaskDataAccess(this);
        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_TASK_ID, 0);
        if(id > 0){
            task = tda.getTaskById(id);
            Log.d(TAG,task.toString());
            putDataIntoUI();
        }else{
            Log.d(TAG, "Creating a new task");
        }


    }

    private void putDataIntoUI(){
        if(task != null){

            txtDescription.setText(task.getDescription());
            txtDueDate.setText(task.getDue().toString());
            chkDone.setChecked(task.isDone());

        }
    }
}