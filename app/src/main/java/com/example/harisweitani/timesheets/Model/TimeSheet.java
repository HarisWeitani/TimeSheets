package com.example.harisweitani.timesheets.Model;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by haris.weitani on 3/22/2018.
 */

public class TimeSheet{

    private int id;
    private String date;
    private String days;
    private String projectName;
    private String activities;
    private String detailAct;
    private String sprintId;
    private String status;
    private Double durasi;

    public TimeSheet() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public String getDetailAct() {
        return detailAct;
    }

    public void setDetailAct(String detailAct) {
        this.detailAct = detailAct;
    }

    public String getSprintId() {
        return sprintId;
    }

    public void setSprintId(String sprintId) {
        this.sprintId = sprintId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDurasi() {
        return durasi;
    }

    public void setDurasi(Double durasi) {
        this.durasi = durasi;
    }

    public List<String> getListDateTimeSheet (List<TimeSheet> timeSheets){
        List<String> dateList = new ArrayList<>();
        for ( TimeSheet timeSheet : timeSheets) {
            dateList.add(timeSheet.getDate() + " " + timeSheet.getProjectName() + " "+timeSheet.getActivities() +
                    " "+ timeSheet.getDurasi() + " " + timeSheet.getStatus());
        }
        return dateList;
    }
}
