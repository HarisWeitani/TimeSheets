package com.example.harisweitani.timesheets.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harisweitani.timesheets.Database.DBOperations;
import com.example.harisweitani.timesheets.Model.TimeSheet;
import com.example.harisweitani.timesheets.R;

import java.util.zip.Inflater;

/**
 * Created by haris.weitani on 3/23/2018.
 */
//https://www.youtube.com/watch?v=ARezg1D9Zd0
//    https://stackoverflow.com/questions/15459209/passing-argument-to-dialogfragment
public class DetailDialogFragment extends AppCompatDialogFragment {

    TextView dateTv, dayTv, projNameTv, activiTv;
    EditText detActET, sprintET, durET;
    Spinner statusSPN;

    String date, day, projName, activ, detAct, sprint, dura, status;
    int itemId;

    DBOperations dbOperations;
    private TimeSheet oldTimeSheet;
    private double duration;
    private boolean valid;

    private DetailDialogListner listner;
    DataFragment df;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        dbOperations = new DBOperations(getActivity());
        oldTimeSheet = new TimeSheet();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_detail_dialog,null);

        bindViews(view);

        bindText();

        builder.setView(view)
                .setCancelable(true)
                .setTitle("Task Detail")
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        warning();
//                        listner.applyTexts(detailAct.getText().toString()+" GAGAL");
                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if( String.valueOf(statusSPN.getSelectedItem()).equals(status) ) {
                            updateData();
                        }
                        else if( String.valueOf(statusSPN.getSelectedItem()).equals("Real")){
                            insertData();
                        }

//                        String text = detailAct.getText().toString();
//                        listner.applyTexts(text);
                    }
                }).setNeutralButton("Go Real", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if( String.valueOf(statusSPN.getSelectedItem()).equals("Real")){
                            Toast.makeText(getActivity(), "This is Reality", Toast.LENGTH_SHORT).show();
                        }
                        else if ( String.valueOf(statusSPN.getSelectedItem()).equals("Plan") ){
                            goReal();
                        }
                    }
                });

        return builder.create();
    }

    private void warning(){
        AlertDialog.Builder warning = new AlertDialog.Builder(getActivity());
        warning.setMessage("You sure to delete this?" +
                "\nDate : " + day + "   " + date +
                "\nProject Name : " + projName);
        warning.setCancelable(true);

        warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deleteData(itemId);
            }
        });

        warning.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = warning.create();
        alertDialog.show();

    }

    private void bindViews(View v){
        dateTv = (TextView) v.findViewById(R.id.detdog_dateTv);
        dayTv = (TextView) v.findViewById(R.id.detdog_dayTv);
        projNameTv = (TextView) v.findViewById(R.id.detdog_projectNameTV);
        activiTv = (TextView) v.findViewById(R.id.detdog_activitiesTV);

        detActET = (EditText) v.findViewById(R.id.detdog_detailActET);
        sprintET = (EditText) v.findViewById(R.id.detdog_sprintIdET);
        durET = (EditText) v.findViewById(R.id.detdog_durationET);

        statusSPN = (Spinner) v.findViewById(R.id.detdog_statusSPN);
    }

    private void bindText(){
        Bundle mArgs = getArguments();

        itemId = mArgs.getInt("itemId");
        date = mArgs.getString("date");
        day = mArgs.getString("day");
        projName = mArgs.getString("projName");
        activ = mArgs.getString("activ");
        detAct = mArgs.getString("detAct");
        sprint = mArgs.getString("sprint");
        dura = mArgs.getString("dura");
        status = mArgs.getString("status");

        dateTv.setText(date);
        dayTv.setText(day);
        projNameTv.setText(projName);
        activiTv.setText(activ);

        detActET.setText(detAct);
        sprintET.setText(sprint);
        durET.setText(dura);

        if( status.equals("Plan") ) {
            statusSPN.setSelection(0);
        }
        else if( status.equals("Real") ){
            statusSPN.setSelection(1);
        }

    }

    public void deleteData(int id){
        dbOperations.open();
        dbOperations.deleteTimeSheet(id);
        dbOperations.close();
    }

    private void updateData(){
        validation();
        dbOperations.open();
        if(valid == true){
            oldTimeSheet.setDate(date);
            oldTimeSheet.setDays(day);
            oldTimeSheet.setDetailAct(detActET.getText().toString());
            oldTimeSheet.setSprintId(sprintET.getText().toString());
            oldTimeSheet.setDurasi(duration);
            oldTimeSheet.setProjectName(projName);
            oldTimeSheet.setStatus(String.valueOf(statusSPN.getSelectedItem()));
            oldTimeSheet.setActivities(activ);
            dbOperations.updateTimeSheet(oldTimeSheet, itemId);
            Toast.makeText(getActivity(), "Time Sheet " +
                            oldTimeSheet.getProjectName() + " Success" +
                            itemId + "\nDura : " + duration
                    , Toast.LENGTH_SHORT).show();
        }

        dbOperations.close();
    }

    private void insertData(){
        validation();
        dbOperations.open();
        if(valid == true){
            oldTimeSheet.setDate(date);
            oldTimeSheet.setDays(day);
            oldTimeSheet.setDetailAct(detActET.getText().toString());
            oldTimeSheet.setSprintId(sprintET.getText().toString());
            oldTimeSheet.setDurasi(duration);
            oldTimeSheet.setProjectName(projName);
            oldTimeSheet.setStatus(String.valueOf(statusSPN.getSelectedItem()));
            oldTimeSheet.setActivities(activ);
            dbOperations.addTimesSheet(oldTimeSheet);
            Toast.makeText(getActivity(), "Time Sheet " +
                            oldTimeSheet.getProjectName() + " Success" +
                            itemId + "\nDura : " + duration
                    , Toast.LENGTH_SHORT).show();
        }

        dbOperations.close();
    }
    private void goReal(){
        statusSPN.setSelection(1);
        validation();
        dbOperations.open();
        if(valid == true){
            oldTimeSheet.setDate(date);
            oldTimeSheet.setDays(day);
            oldTimeSheet.setDetailAct(detActET.getText().toString());
            oldTimeSheet.setSprintId(sprintET.getText().toString());
            oldTimeSheet.setDurasi(duration);
            oldTimeSheet.setProjectName(projName);
            oldTimeSheet.setStatus("Real");
            oldTimeSheet.setActivities(activ);
            dbOperations.addTimesSheet(oldTimeSheet);
            Toast.makeText(getActivity(), "Time Sheet " +
                            oldTimeSheet.getProjectName() + " Success" +
                            itemId + "\nDura : " + duration
                    , Toast.LENGTH_SHORT).show();
        }

        dbOperations.close();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listner = (DetailDialogListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Must implement ExampleDialogListner");
        }

    }

    public interface DetailDialogListner{
        void applyTexts(String text);
    }


    private void validation(){

        if( detActET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Detail Activity", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( sprintET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Sprint Bulk ID", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( durET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Duration", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }

        if( String.valueOf(statusSPN.getSelectedItem()).equals("Plan") ){
            duration = Math.round(Double.parseDouble(durET.getText().toString()));
            valid = true;
        }
        if( String.valueOf(statusSPN.getSelectedItem()).equals("Real") ){
            duration = Double.parseDouble(durET.getText().toString());
            valid = true;
        }

    }

}
