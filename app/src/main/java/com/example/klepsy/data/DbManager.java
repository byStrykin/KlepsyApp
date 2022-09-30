package com.example.klepsy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.klepsy.Task;

import java.util.ArrayList;
import java.util.Date;

public class DbManager {
    private Context context;
    private DbHelper myDbHelper;
    private SQLiteDatabase db;

    public DbManager(Context context) {
        this.context = context;
        myDbHelper = new DbHelper(context);
    }
    public void openDb() {
        db = myDbHelper.getWritableDatabase();
    }
    public void insertToDb(String name_task, String desc_task, long create_date) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.NAME_TASK, name_task);
        contentValues.put(Constans.DESCRIPTION_TASK, desc_task);
        contentValues.put(Constans.COMPLETE_TASK, "false");
        contentValues.put(Constans.CREATION_DATE, create_date);
        db.insert(
                Constans.TABLE_NAME,
                null,
                contentValues);
    }
    public void updateToDb(String name_task, String desc_task) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constans.NAME_TASK, name_task);
        contentValues.put(Constans.DESCRIPTION_TASK, desc_task);
        db.update(Constans.TABLE_NAME, contentValues, "name = ?", new String[]{ name_task });
    }
    public ArrayList<String> getNameTaskFromDb(String cond){
        ArrayList<String> name_task_list = new ArrayList<>();
        Cursor cursor = db.query(Constans.TABLE_NAME, null, "complete = ?",
                new String[] { cond }, null, null, null);

        while (cursor.moveToNext()){
            String name_task = cursor.getString(cursor.getColumnIndexOrThrow(Constans.NAME_TASK));
            name_task_list.add(name_task);
        }
        cursor.close();
        return name_task_list;
    }
    public ArrayList<String> getDescTaskFromDb(String cond) {
        ArrayList<String> desc_task_list = new ArrayList<>();
        Cursor cursor = db.query(Constans.TABLE_NAME, null, "complete = ?",
                new String[] { cond }, null, null, null);

        while (cursor.moveToNext()) {
            String desc_task = cursor.getString(cursor.getColumnIndexOrThrow(Constans.DESCRIPTION_TASK));
            desc_task_list.add(desc_task);
        }
        cursor.close();
        return desc_task_list;
    }
    public ArrayList<String> getCreDateFromDb(String cond) {
        ArrayList<String> credate_task_list = new ArrayList<>();
        Cursor cursor = db.query(Constans.TABLE_NAME, null, "complete = ?",
                new String[] { cond }, null, null, null);

        while (cursor.moveToNext()) {
            String credate_task = cursor.getString(cursor.getColumnIndexOrThrow(Constans.CREATION_DATE));
            credate_task_list.add(credate_task);
        }
        cursor.close();
        return credate_task_list;
    }
    public void closeDb() {
        myDbHelper.close();
    }
    public String getDesc(String nameTask) {
        String[] fdaf = new String[1];
        Cursor cursor = db.query(Constans.TABLE_NAME, null, "name = ? AND complete = ?",
                new String[] { nameTask, "false" }, null, null, null);
        while (cursor.moveToNext()) {
            String desc_task = cursor.getString(cursor.getColumnIndexOrThrow(Constans.DESCRIPTION_TASK));
            fdaf[0] = desc_task;
        }
        return fdaf[0];

    }
    public void removeAll() {
        db.delete(Constans.TABLE_NAME, null, null);
    }
    public void removeOne(Task task){
        db.delete(Constans.TABLE_NAME, "creation_date = ?", new String[]{Long.toString(task.getCreationDate())});
    }
    public void setCompleteTask(Task task, String cond) {
        ContentValues contentValues = new ContentValues();
        if (cond.equals("true")) {
            contentValues.put(Constans.COMPLETE_TASK, cond);
            contentValues.put(Constans.COMPLETE_DATE, task.getCompleteDate());
            db.update(Constans.TABLE_NAME, contentValues, "name = ?", new String[]{ task.getName() });
        } else {
            contentValues.put(Constans.COMPLETE_TASK, cond);
            contentValues.put(Constans.COMPLETE_DATE, 0);
            db.update(Constans.TABLE_NAME, contentValues, "creation_date = ?",
                    new String[]{Long.toString(task.getCreationDate())});
        }

    }
}
