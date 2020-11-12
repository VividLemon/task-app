package com.example.taskapp.fileio;



import android.content.Context;
import android.util.Log;

import com.example.taskapp.Taskable;
import com.example.taskapp.models.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class CSVTaskDataAccess implements Taskable {
    Context context;
    public static final String TAG = "CSVTaskDataAccess";
    public static final String DATA_FILE = "tasks.csv";
    private ArrayList<Task> allTasks = new ArrayList<>();

    public CSVTaskDataAccess(Context c){
        this.context = c;
        loadTasks();
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return allTasks;
    }

    @Override
    public Task getTaskById(long id) {
        for(Task t : allTasks){
            if(id == t.getId()){
                return t;
            }
        }
        return null;
    }

    @Override
    public Task insertTask(Task t) {
        t.setId(getMaxId() + 1);
        this.allTasks.add(t);
        saveTasks();
        return t;
    }

    @Override
    public Task updateTask(Task t) {
        Task taskToRemove = getTaskById(t.getId());
        allTasks.remove(allTasks.indexOf(taskToRemove));
        allTasks.add(t);
        saveTasks();
        return t;
    }

    @Override
    public int deleteTask(Task t) {
        return 0;
    }

    private String convertTaskToCSV(Task task){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
        return task.getId() + "," +  task.getDescription() + "," + dateFormat.format(task.getDue()) + "," + task.isDone();
    }

    private Task convertCSVToTask(String string){
        Task task = new Task();
        try {
            String[] split = string.split(",");
            task.setId(Long.parseLong(split[0]));
            task.setDescription(split[1]);
            task.setDue(new SimpleDateFormat("MM/dd/yyyy", Locale.US).parse(split[2]));
            task.setDone(Boolean.parseBoolean(split[3]));
        }
        catch (ParseException parseErr){
            Log.e(TAG, "Parse error: " + parseErr.toString());
        }
        catch (Exception err){
            Log.e(TAG, err.toString());
        }
        return task;
    }

    private void loadTasks(){
        ArrayList<Task> dataList = new ArrayList<>();
        String dataString = FileHelper.readFromFile(DATA_FILE, context);
        if(dataString == null){
            Log.d(TAG, "Error reading from file");
            return;
        }
        String[] lines = dataString.trim().split("\n");
        for(String line : lines){
              Task t = convertCSVToTask(line);
              if(t != null){
                  dataList.add(t);
              }
        }
        this.allTasks = dataList;
    }

    private void saveTasks(){
        String dataString = "";
        StringBuilder stringBuilder = new StringBuilder();
        for (Task t :
                allTasks) {
            stringBuilder.append(convertTaskToCSV(t) + "\n");
        }
        dataString = stringBuilder.toString();
        if(FileHelper.writeToFile(DATA_FILE, dataString, context)){
            Log.d(TAG, "Successfully saved task data");
        }else{
            Log.d(TAG, "Failed to save task data");
        }
    }

    private long getMaxId(){
        long maxId = 0;
        for (Task t :
                allTasks) {
              if(t.getId() > maxId){
                  maxId = t.getId();
              }
        }
        return maxId;
    }

}
