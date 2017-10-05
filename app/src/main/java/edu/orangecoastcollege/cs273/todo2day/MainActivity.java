package edu.orangecoastcollege.cs273.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Task> allTasksList = new ArrayList<>();

    //Reference to the database
    private DBHelper mDB;

    //reference to the widgets needed
    private EditText mDescriptionEditText;
    private ListView mTaskListView;

    //reference to the custom list adapter
    TaskListAdapter mTaskListAdapter;

    /**
     * establishes connections from view to controller
     * from model to controller
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDescriptionEditText = (EditText) findViewById(R.id.taskEditText);
        mTaskListView = (ListView) findViewById(R.id.taskListView);
        mDB = new DBHelper(this);

    }

    /**
     * re-populate the to-do list
     */
    @Override
    protected void onResume() {
        super.onResume();
        //database-related stuff
        // 1) populate a list using db helper
        allTasksList = mDB.getAllTasks();
        // 2) connect the listview with the custom list
        mTaskListAdapter = new TaskListAdapter(this,R.layout.task_item,allTasksList);
        mTaskListView.setAdapter(mTaskListAdapter);
    }

    /**
     * add a new task
     * @param v the view that triggers the event
     */
    public void addTask(View v) {
        //WE SHOULD USE THE ID FROM THE DATABASE AFTER ADDING A NEW TASK TO THE DATABASE
        //OTHERWISE, IF WE WE CREATE A NEW TASK, THE FIRST TIME WE TOGGLE THE TASK STATUS,
        //IT WONT APPLY

        //For the time being, we will just do a short way (Demonstrate right below)

        //check to see if the description is empty or not
        String description = mDescriptionEditText.getText().toString();
        if (TextUtils.isEmpty(description))
            Toast.makeText(this, "Please enter a description.", Toast.LENGTH_SHORT).show();
        else
        {
            //create a task
            Task newTask = new Task(description,false);
            //Add it to the database
            mDB.addTask(newTask);
            //Add it to the list
            allTasksList.add(newTask);
            //Notify the list adapter that it's been changed
            mTaskListAdapter.notifyDataSetChanged();
            //Clear out the EditText
            mDescriptionEditText.setText("");
        }
    }

    /**
     * clear all tasks
     * @param v the view that triggers the event
     */
    public void clearAllTasks(View v)
    {
        allTasksList.clear();
        mDB.deleteAllTasks();
        mTaskListAdapter.notifyDataSetChanged();
    }

    /**
     * change the "done" status
     * @param v the view that triggers the event
     */
    public void changeTaskStatus(View v)
    {
        CheckBox selectedCheckBox = (CheckBox)v;
        Task selectedTask = (Task) selectedCheckBox.getTag();
        //update the task
        selectedTask.setIsDone(selectedCheckBox.isChecked());
        //update the database
        mDB.updateTask(selectedTask);
    }
}

