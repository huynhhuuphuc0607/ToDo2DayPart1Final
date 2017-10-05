package edu.orangecoastcollege.cs273.todo2day;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.util.List;

/**
 * Created by phuynh101 on 10/3/2017.
 */

/**
 * An adapter. Inflate the rows of the list with data
 */
public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context mContext;
    private int mResourceId;
    private List<Task> mTaskList;

    /**
     * constructor
     * @param context context where the list should be populated
     * @param resource resource id layout for the row
     * @param objects a container of objects
     */
    public TaskListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResourceId = resource;
        mTaskList = objects;
    }

    /**
     * inflate the row layout with data
     * @param position position
     * @param convertView
     * @param parent
     * @return view after being inflated with data
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //retireve the selected task
        Task mSelectedTask = mTaskList.get(position);

        //Used LayoutInflater to inflate the view for this specific task
        LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResourceId,null);

        //Get a reference to the checkbox
        CheckBox selectedCheckBox = (CheckBox)  view.findViewById(R.id.isDoneCheckBox);
        selectedCheckBox.setText(mSelectedTask.getDescription());
        selectedCheckBox.setChecked(mSelectedTask.getIsDone());


        //Tag = invisible locker behind each view (store anything there)
        selectedCheckBox.setTag(mSelectedTask);
        return view;
    }
}
