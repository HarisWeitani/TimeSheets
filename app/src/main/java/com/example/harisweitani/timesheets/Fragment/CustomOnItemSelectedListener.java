package com.example.harisweitani.timesheets.Fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by haris.weitani on 3/22/2018.
 */
public class CustomOnItemSelectedListener implements AdapterView.OnItemSelectedListener {


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
