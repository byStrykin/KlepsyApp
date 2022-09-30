package com.example.klepsy.data;

public class Constans {
    public static final String TABLE_NAME = "tasks_list";
    public static final String _ID = "_id";
    public static final String NAME_TASK = "name";
    public static final String DESCRIPTION_TASK = "description";
    public static final String COMPLETE_TASK = "complete";
    public static final String CREATION_DATE = "creation_date";
    public static final String COMPLETE_DATE = "complete_date";
    public static final String DB_NAME = "task_db.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_REQ = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + NAME_TASK +
            " TEXT," + DESCRIPTION_TASK + " TEXT," + COMPLETE_TASK + " TEXT,"
            + CREATION_DATE + " INTEGER," + COMPLETE_DATE + " INTEGER)";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}
