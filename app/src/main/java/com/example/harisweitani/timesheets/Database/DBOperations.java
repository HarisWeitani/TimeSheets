package com.example.harisweitani.timesheets.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.database.Cursor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


import com.example.harisweitani.timesheets.MainActivity;
import com.example.harisweitani.timesheets.Model.SettingsVariable;
import com.example.harisweitani.timesheets.Model.TimeSheet;

/**
 * Created by haris.weitani on 3/22/2018.
 */

public class DBOperations  {

    public static final String TAG = "DBOPERATIONS";

    SQLiteOpenHelper helper;
    SQLiteDatabase database;

    private static final String[] allColumns = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_DATE,
            DBHelper.COLUMN_DAYS,
            DBHelper.COLUMN_PROJECT_NAME,
            DBHelper.COLUMN_ACTIVITIES,
            DBHelper.COLUMN_DETAIL_ACT,
            DBHelper.COLUMN_SPRINT_ID,
            DBHelper.COLUMN_STATUS,
            DBHelper.COLUMN_DURASI
    };

    public DBOperations(Context context) {
        helper = new DBHelper(context);
    }

    public void open(){
        Log.i(TAG,"Database Opened");
        database = helper.getWritableDatabase();
    }
    public void close(){
        Log.i(TAG, "Database Closed");
        helper.close();
    }

    public TimeSheet addTimesSheet(TimeSheet timeSheet){
        ContentValues values  = new ContentValues();
        values.put(DBHelper.COLUMN_DATE,timeSheet.getDate());
        values.put(DBHelper.COLUMN_DAYS,timeSheet.getDays());
        values.put(DBHelper.COLUMN_PROJECT_NAME,timeSheet.getProjectName());
        values.put(DBHelper.COLUMN_ACTIVITIES,timeSheet.getActivities());
        values.put(DBHelper.COLUMN_DETAIL_ACT,timeSheet.getDetailAct().trim());
        values.put(DBHelper.COLUMN_SPRINT_ID,timeSheet.getSprintId().trim());
        values.put(DBHelper.COLUMN_STATUS,timeSheet.getStatus());
        values.put(DBHelper.COLUMN_DURASI,timeSheet.getDurasi());

        long insertid = database.insert(DBHelper.TABLE_NAME,null,values);
        timeSheet.setId((int)insertid);
        return timeSheet;
    }

    public Cursor getData(int id) {
        Cursor res =  database.rawQuery( "select * from timesheets where id="+id+"", null );
        return res;
    }

    public List<TimeSheet> getAllTimeSheet() {
        Log.i(TAG, "getAllTimeSheet: OPEN");

        Cursor cursor = database.query(DBHelper.TABLE_NAME,allColumns,null,null,null, null, null);

        List<TimeSheet> timeSheets = new ArrayList<>();
        if(cursor.getCount() > 0){
            while(cursor.moveToNext()){
                TimeSheet timeSheet= new TimeSheet();
                timeSheet.setId(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID)));
                timeSheet.setDate(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DATE)));
                timeSheet.setDays(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DAYS)));
                timeSheet.setProjectName(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_PROJECT_NAME)));
                timeSheet.setActivities(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_ACTIVITIES)));
                timeSheet.setDetailAct(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_DETAIL_ACT)));
                timeSheet.setSprintId(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_SPRINT_ID)));
                timeSheet.setStatus(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_STATUS)));
                timeSheet.setDurasi(cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_DURASI)));

                timeSheets.add(timeSheet);
            }
        }
        Log.i(TAG, "getAllTimeSheet: CLOSE");


        if( SettingsVariable.getInstance().settingsId == 0 ) {
            Collections.sort(timeSheets, new Comparator<TimeSheet>() {
                @Override
                public int compare(TimeSheet o1, TimeSheet o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    int compareResult = 0;
                    try {
                        Date arg0Date = format.parse(o1.getDate());
                        Date arg1Date = format.parse(o2.getDate());
                        compareResult = arg0Date.compareTo(arg1Date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        compareResult = o1.getDate().compareTo(o2.getDate());
                    }
                    return compareResult;
                }
            });
            Collections.reverse(timeSheets);
        }
        if( SettingsVariable.getInstance().settingsId == 1 ){
            Collections.sort(timeSheets, new Comparator<TimeSheet>() {
                @Override
                public int compare(TimeSheet o1, TimeSheet o2) {
                    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    int compareResult = 0;
                    try {
                        Date arg0Date = format.parse(o1.getDate());
                        Date arg1Date = format.parse(o2.getDate());
                        compareResult = arg0Date.compareTo(arg1Date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                        compareResult = o1.getDate().compareTo(o2.getDate());
                    }
                    return compareResult;
                }
            });
        }
        if( SettingsVariable.getInstance().settingsId == 2 ){
            Collections.sort(timeSheets, new Comparator<TimeSheet>() {
                @Override
                public int compare(TimeSheet o1, TimeSheet o2) {
                    int compareResult = 0;
                    try {
                        compareResult = o1.getDurasi().compareTo(o2.getDurasi());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return compareResult;
                }
            });
        }
        return timeSheets;
    }

    // Updating Employee
    public int updateTimeSheet(TimeSheet timeSheet, int id) {

        ContentValues values  = new ContentValues();
        values.put(DBHelper.COLUMN_DATE,timeSheet.getDate());
        values.put(DBHelper.COLUMN_DAYS,timeSheet.getDays());
        values.put(DBHelper.COLUMN_PROJECT_NAME,timeSheet.getProjectName());
        values.put(DBHelper.COLUMN_ACTIVITIES,timeSheet.getActivities());
        values.put(DBHelper.COLUMN_DETAIL_ACT,timeSheet.getDetailAct().trim());
        values.put(DBHelper.COLUMN_SPRINT_ID,timeSheet.getSprintId().trim());
        values.put(DBHelper.COLUMN_STATUS,timeSheet.getStatus());
        values.put(DBHelper.COLUMN_DURASI,timeSheet.getDurasi());

        // updating row
        return database.update(DBHelper.TABLE_NAME, values,
                 "id = ?",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteTimeSheet (int id) {
        return database.delete("timesheets",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    //FAILURE
//    // Deleting Employee
//    public void delete(TimeSheet timeSheet) {
//
//        database.delete(DBHelper.TABLE_NAME, DBHelper.COLUMN_ID + "=" + timeSheet.getId(), null);
//    }

    // Getting single Employee
//    public TimeSheet getTimeSheet(long id) {
//        Cursor cursor = database.query(DBHelper.TABLE_NAME,allColumns,DBHelper.COLUMN_ID + "=?",new String[]{String.valueOf(id)},null,null, null, null);
//        if (cursor != null)
//            cursor.moveToFirst();
//
//        TimeSheet ts = new TimeSheet(
//                Long.parseLong(cursor.getString(0)),cursor.getString(1),
//                cursor.getString(2),cursor.getString(3),
//                cursor.getString(4),cursor.getString(5),
//                cursor.getString(6),cursor.getString(7),cursor.getFloat(8));
//        // return Employee
//        return ts;
//    }

}
