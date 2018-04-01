package com.example.harisweitani.timesheets.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by haris.weitani on 3/22/2018.
 */
//https://www.androidtutorialpoint.com/storage/android-sqlite-database-tutorial/
public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "timesheet.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "timesheets";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_PROJECT_NAME = "projectName";
    public static final String COLUMN_ACTIVITIES = "activities";
    public static final String COLUMN_DETAIL_ACT = "detailAct";
    public static final String COLUMN_SPRINT_ID = "sprintId";
    public static final String COLUMN_STATUS = "status";
    public static final String COLUMN_DURASI = "durasi";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME +
                    " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_DAYS + " TEXT, " +
                    COLUMN_PROJECT_NAME + " TEXT, " +
                    COLUMN_ACTIVITIES + " TEXT, " +
                    COLUMN_DETAIL_ACT + " TEXT, " +
                    COLUMN_SPRINT_ID + " TEXT, " +
                    COLUMN_STATUS + " TEXT, " +
                    COLUMN_DURASI + " TEXT " +
                    ")";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        db.execSQL(TABLE_CREATE);
    }
}
