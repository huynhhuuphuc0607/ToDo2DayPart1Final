package edu.orangecoastcollege.cs273.todo2day;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mpaulding on 9/28/2017.
 */

class DBHelper extends SQLiteOpenHelper {

    //TASK 1: DEFINE THE DATABASE VERSION, NAME AND TABLE NAME
    static final String DATABASE_NAME = "ToDo2Day";
    private static final String DATABASE_TABLE = "Tasks";
    private static final int DATABASE_VERSION = 1;


    //TASK 2: DEFINE THE FIELDS (COLUMN NAMES) FOR THE TABLE
    private static final String KEY_FIELD_ID = "_id";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_IS_DONE = "is_done";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        String table = "CREATE TABLE " + DATABASE_TABLE + "("
                + KEY_FIELD_ID + " INTEGER PRIMARY KEY, "
                + FIELD_DESCRIPTION + " TEXT, "
                + FIELD_IS_DONE + " INTEGER" + ")";
        database.execSQL(table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database,
                          int oldVersion,
                          int newVersion) {
        database.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(database);
    }

    public void addTask(Task newTask) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FIELD_DESCRIPTION, newTask.getDescription());
        values.put(FIELD_IS_DONE, newTask.getIsDone() ? 1 : 0);
        db.insert(DATABASE_TABLE, null, values);
        db.close();
    }


    public List<Task> getAllTasks() {
        ArrayList<Task> allTasksList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Task task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1);
                allTasksList.add(task);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return allTasksList;
    }

    public void deleteAllTasks()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, null, null);
        db.close();
    }

    public void deleteTask(Task task)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(DATABASE_TABLE, KEY_FIELD_ID + " = ?", new String[] {String.valueOf(task.getId())});
        db.close();
    }


    public void updateTask(Task task)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIELD_ID, task.getId());
        values.put(FIELD_DESCRIPTION, task.getDescription());
        values.put(FIELD_IS_DONE, task.getIsDone() ? 1 : 0);

        db.update(DATABASE_TABLE, values, KEY_FIELD_ID + "=" + task.getId(), null);
        db.close();
    }

    public Task getSingleTask(int id)
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                DATABASE_TABLE,
                new String[]{KEY_FIELD_ID, FIELD_DESCRIPTION, FIELD_IS_DONE},
                KEY_FIELD_ID + "=" + id,
                null,
                null,
                null,
                null);

        Task task = null;
        if (cursor.moveToFirst())
        {
            task = new Task(cursor.getInt(0), cursor.getString(1), cursor.getInt(2) == 1);
        }
        cursor.close();
        db.close();
        return task;
    }
}
