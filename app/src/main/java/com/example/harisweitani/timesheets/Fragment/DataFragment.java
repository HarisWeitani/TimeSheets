package com.example.harisweitani.timesheets.Fragment;

import android.app.AlertDialog;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.harisweitani.timesheets.Database.DBHelper;
import com.example.harisweitani.timesheets.Database.DBOperations;
import com.example.harisweitani.timesheets.Model.SettingsVariable;
import com.example.harisweitani.timesheets.Model.TimeSheet;
import com.example.harisweitani.timesheets.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by haris.weitani on 3/22/2018.
 */
//https://gist.github.com/VladSumtsov/ad4e13511a9b73ff3b13
public class DataFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    String date, day, projName, activ, detAct, sprint, dura , status;
    int itemId;

    SwipeRefreshLayout swipeRefreshLayout;

    private DBOperations dbOps;
    private List<TimeSheet> timeSheets;
    private List<String> list;
    private ListView listView;
    private TimeSheet ts;

    int i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) view.findViewById(R.id.listView);
        ts = new TimeSheet();
        dbOps = new DBOperations(getActivity());

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(),android.R.color.holo_green_dark),
                ContextCompat.getColor(getActivity(),android.R.color.holo_red_dark),
                ContextCompat.getColor(getActivity(),android.R.color.holo_blue_dark),
                ContextCompat.getColor(getActivity(),android.R.color.holo_orange_dark));

        showData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int idModel = timeSheets.get(position).getId();

                getSelectedItem(idModel);

                openDialog();

            }
        });

    }

    public void openDialog(){
        DetailDialogFragment detailDialogFragment = new DetailDialogFragment();
        Bundle args = new Bundle();

        args.putInt("itemId",itemId);
        args.putString("detAct", detAct);
        args.putString("date",date);
        args.putString("day",day);
        args.putString("dura",dura);
        args.putString("projName",projName);
        args.putString("activ",activ);
        args.putString("sprint",sprint);
        args.putString("status",status);

        detailDialogFragment.setArguments(args);
        detailDialogFragment.show(getFragmentManager(),"Example Dialog");

    }

    public void getSelectedItem(int id){
        dbOps.open();

        Cursor rs = dbOps.getData(id);
        rs.moveToFirst();

        itemId = id;
        date = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DATE));
        day = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DAYS));
        dura = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DURASI));
        projName = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_PROJECT_NAME));
        detAct = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_DETAIL_ACT));
        activ = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_ACTIVITIES));
        sprint = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_SPRINT_ID));
        status = rs.getString(rs.getColumnIndex(DBHelper.COLUMN_STATUS));

        if (!rs.isClosed())  {
            rs.close();
        }

        Toast.makeText(getActivity(), "Item Id :"+itemId + "\nDura : " + dura, Toast.LENGTH_SHORT).show();

        dbOps.close();
    }

    public void showData(){
        dbOps.open();
        timeSheets = dbOps.getAllTimeSheet();
        dbOps.close();

        list = ts.getListDateTimeSheet(timeSheets);

        ArrayAdapter<String> adapter = new CustomListAdapter(getActivity(),list);
        listView.setAdapter(adapter);

        dbOps.close();
    }

    @Override
    public void onRefresh() {
        showData();

        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            i++;
            Log.i("FRAGMENT ONE", "user visible : " + i);
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
}