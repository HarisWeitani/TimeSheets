package com.example.harisweitani.timesheets.Fragment;

import android.app.DatePickerDialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.harisweitani.timesheets.Database.DBOperations;
import com.example.harisweitani.timesheets.Model.TimeSheet;
import com.example.harisweitani.timesheets.R;

/**
 * Created by haris.weitani on 3/22/2018.
 */

//https://stackoverflow.com/questions/37943474/pass-data-from-one-design-tab-to-another-tab
public class AddNewFragment extends Fragment  {

    EditText dateET, daysET, detailActET, sprintBulkIdET, durationET;
    Spinner projectNameSPN, activitiesSPN, statusSPN;
    Button inputActBTN;

    DBOperations dbOperations;

    private TimeSheet newTimeSheet;
    private double duration;
    private boolean valid;

    int i;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dbOperations = new DBOperations(getActivity());
        newTimeSheet = new TimeSheet();

        bindViews(view);

        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "mantab", Toast.LENGTH_SHORT).show();
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(),"Date Picker");

            }
        });

        inputActBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
                dbOperations.open();
                if( valid == true ) {
//                toastCheckData();

                    newTimeSheet.setDate(dateET.getText().toString());
                    newTimeSheet.setDays(daysET.getText().toString());
                    newTimeSheet.setDetailAct(detailActET.getText().toString());
                    newTimeSheet.setSprintId(sprintBulkIdET.getText().toString());
                    newTimeSheet.setDurasi(duration);
                    newTimeSheet.setProjectName(String.valueOf(projectNameSPN.getSelectedItem()));
                    newTimeSheet.setStatus(String.valueOf(statusSPN.getSelectedItem()));
                    newTimeSheet.setActivities(String.valueOf(activitiesSPN.getSelectedItem()));
                    dbOperations.addTimesSheet(newTimeSheet);
                    Toast.makeText(getActivity(), "Time Sheet " +
                            newTimeSheet.getProjectName() + " Success" +
                            newTimeSheet.getId()
                            , Toast.LENGTH_SHORT).show();
                }
                dbOperations.close();
                clearViews();
            }
        });

    }

    private void bindViews(View v){
        dateET = (EditText) v.findViewById(R.id.dateET);
        daysET = (EditText) v.findViewById(R.id.daysET);
        detailActET = (EditText) v.findViewById(R.id.detailActET);
        sprintBulkIdET = (EditText) v.findViewById(R.id.sprintIdET);
        durationET = (EditText) v.findViewById(R.id.durationET);

        projectNameSPN = (Spinner) v.findViewById(R.id.projectNameSPN);
        activitiesSPN = (Spinner) v.findViewById(R.id.activitiesSPN);
        statusSPN = (Spinner) v.findViewById(R.id.statusSPN);

        inputActBTN = (Button) v.findViewById(R.id.input_actBTN);
    }

    private void clearViews(){
        dateET.setText("");
        daysET.setText("");
        detailActET.setText("");
        sprintBulkIdET.setText("");
        durationET.setText("");
        projectNameSPN.setSelection(0);
        activitiesSPN.setSelection(0);
        statusSPN.setSelection(0);

    }

    private void validation(){

        if( dateET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter a Valid Date", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( daysET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter a Valid Day", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( detailActET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Detail Activity", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( sprintBulkIdET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Sprint Bulk ID", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }
        if( durationET.getText().toString().matches("") ){
            Toast.makeText(getActivity(), "Enter Duration", Toast.LENGTH_SHORT).show();
            valid = false;
            return;
        }

        if( String.valueOf(statusSPN.getSelectedItem()).equals("Plan") ){
            duration = Math.round(Double.parseDouble(durationET.getText().toString()));
            durationET.setText(duration+"");
            valid = true;
        }
        if( String.valueOf(statusSPN.getSelectedItem()).equals("Real") ){
            duration = Double.parseDouble(durationET.getText().toString());
            durationET.setText(duration+"");
            valid = true;
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            i++;
            Log.i("FRAGMENT TWO", "setUserVisibleHint: " + i);
        }
    }

    private void toastCheckData(){
        Toast.makeText(getActivity(),
                "HASIL : " +
                        "\nDate : "+ dateET.getText().toString() +
                        "\nDays : "+ daysET.getText().toString() +
                        "\nDetail Act : "+ detailActET.getText().toString() +
                        "\nSprint ID : "+ sprintBulkIdET.getText().toString() +
                        "\nDuration : "+ durationET.getText().toString() +
                        "\nProject : "+ String.valueOf(projectNameSPN.getSelectedItem()) +
                        "\nStatus : "+ String.valueOf(statusSPN.getSelectedItem()) +
                        "\nActivities : "+ String.valueOf(activitiesSPN.getSelectedItem()) ,
                Toast.LENGTH_SHORT).show();
    }

}
