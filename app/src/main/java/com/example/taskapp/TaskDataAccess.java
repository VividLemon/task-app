package com.example.taskapp;

import android.content.Context;

import com.example.taskapp.models.Task;

import java.util.ArrayList;
import java.util.Date;

public class TaskDataAccess {

    private Context context;


    public static ArrayList<Task> allTasks = new ArrayList(){{
        add(new Task(1, "Mow the lawn", new Date(118, 8, 22), false));
        add(new Task(2, "Take out the trash", new Date(118, 9, 15), true));
        add(new Task(3, "Pay rent", new Date(118, 10, 1), false));
    }};

    public TaskDataAccess(Context context){
        this.context = context;
    }

    public Task insertTask(Task t){
        allTasks.add(t);
        t.setId(allTasks.size());
        return t;
    }

    public ArrayList<Task> getAllTasks(){
        return allTasks;
    }

    public Task getTaskById(long id){
        for (Task t: allTasks) {
            if(id == t.getId()){
                return t;
            }
        }
        return null;
    }

    public Task updateTask(Task t){
        return t;
    }

    public int deletedTask(Task t){
        if(allTasks.contains(t)){
            allTasks.remove(t);
            return 1;
        }
        return 0;
    }

}
