package com.example.taskapp;

import com.example.taskapp.models.Task;

import java.util.ArrayList;

public interface Taskable {

    ArrayList<Task> getAllTasks();

    Task getTaskById(long id);

    Task insertTask(Task t);

    Task updateTask(Task t);

    int deleteTask(Task t);
}
