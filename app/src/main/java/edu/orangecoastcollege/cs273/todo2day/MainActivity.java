package edu.orangecoastcollege.cs273.todo2day;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private List<Task> allTasksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deleteDatabase(DBHelper.DATABASE_NAME);

        allTasksList.add(new Task("Study for CS A273 Midterm", false));
        allTasksList.add(new Task("Get that laundry done!", false));
        allTasksList.add(new Task("Master explicit intents", true));
        allTasksList.add(new Task("Play League of Legends", true));

        DBHelper db = new DBHelper(this);
        for (Task t : allTasksList)
            db.addTask(t);

        allTasksList = db.getAllTasks();
        Log.i(TAG, "Displaying all Tasks after adding:");
        for (Task t : allTasksList)
            Log.i(TAG, t.toString());

        db.deleteTask(allTasksList.get(2));
        db.deleteTask(allTasksList.get(3));
        allTasksList = db.getAllTasks();
        Log.i(TAG, "Displaying all Tasks after deleting T3 and T4:");
        for (Task t : allTasksList)
            Log.i(TAG, t.toString());


        Task updatedTask = allTasksList.get(0);
        updatedTask.setDescription("Pretend to study for CS A273 Midterm");
        updatedTask.setIsDone(true);
        db.updateTask(updatedTask);

        allTasksList = db.getAllTasks();
        Log.i(TAG, "Displaying all Tasks after updating T1:");
        for (Task t : allTasksList)
            Log.i(TAG, t.toString());


        Task t2 = db.getSingleTask(2);
        Log.i(TAG, "Displaying Task T2:");
        Log.i(TAG, t2.toString());

        db.close();
    }
}
