package com.example.taskapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.taskapp.fileio.CSVTaskDataAccess;
import com.example.taskapp.models.Task;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TaskDetailsActivity";
    public static final String EXTRA_TASK_ID = "taskId";
    Task task;
    Taskable tda;
    SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
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


        tda = new CSVTaskDataAccess(this);
        Intent i = getIntent();
        long id = i.getLongExtra(EXTRA_TASK_ID, 0);
        if(id > 0){
            task = tda.getTaskById(id);
            Log.d(TAG,task.toString());
            putDataIntoUI();
        }else{
            Log.d(TAG, "Creating a new task");
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    getdataFromUI();
                    Log.d(TAG, "Passed validation");
                    if(task.getId() > 0){
                        tda.updateTask(task);
                    }else{
                        tda.insertTask(task);
                    }

                    Intent i = new Intent(TaskDetailsActivity.this, TaskListActivity.class);
                    startActivity(i);
                }
            }
        });

    }

    private void putDataIntoUI(){
        if(task != null){

            try{

                String dateStr = sdf.format(task.getDue());
                txtDescription.setText(task.getDescription());
                txtDueDate.setText(dateStr);
                chkDone.setChecked(task.isDone());
            }
            catch (Exception e){
                Log.e(TAG, "Error: " + e.toString());
            }
        }
    }

    private boolean validate(){
        boolean isValid = true;
        if(txtDescription.getText().toString().isEmpty()){
            txtDescription.setError("You must enter a description");
            isValid = false;
        }
        Date dueDate = null;
        if(txtDueDate.getText().toString().isEmpty()){
            txtDueDate.setError("You must enter a date");
            isValid = false;
        }
        else{
            try{
                dueDate = sdf.parse(txtDueDate.getText().toString());
            }
            catch (Exception e){
                txtDueDate.setError(("Enter a valid date in M/d/yyyy"));
                isValid = false;
                Log.e(TAG, "Unable to convert string to date" + e.toString());
            }
        }
        return isValid;
    }

    private void getdataFromUI(){
        String desc = txtDescription.getText().toString();
        String dueDateString = txtDueDate.getText().toString();
        boolean done = chkDone.isChecked();

        Date dueDate = null;
        try{
            dueDate = sdf.parse(dueDateString);
        }
        catch (Exception e){
            Log.e(TAG, "Unable to parse date string " + e.toString());
        }
        if(task != null){
            task.setDescription(desc);
            task.setDue(dueDate);
            task.setDone(done);
        }
        else{
            task = new Task(desc,dueDate,done);
        }
    }
}