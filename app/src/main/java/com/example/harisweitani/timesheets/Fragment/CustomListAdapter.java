package com.example.harisweitani.timesheets.Fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.harisweitani.timesheets.Model.TimeSheet;
import com.example.harisweitani.timesheets.R;

import java.util.List;

/**
 * Created by Sir.WarFox on 25/03/2018.
 */

public class CustomListAdapter extends ArrayAdapter<String> {

    List<String> customData;

    public CustomListAdapter(@NonNull Context context, List<String> data) {
        super(context ,android.R.layout.simple_list_item_1,data);
        this.customData = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = super.getView(position, convertView, parent);

        if(customData.get(position).contains("Real")){
            view.setBackgroundColor(Color.parseColor("#85deff"));
        }
        
        return view;
    }
}
