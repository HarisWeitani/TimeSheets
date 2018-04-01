package com.example.harisweitani.timesheets.Model;

/**
 * Created by Sir.WarFox on 25/03/2018.
 */

public class SettingsVariable {

    private static SettingsVariable mInstance = null;

    public int settingsId;

    protected SettingsVariable() {
    }

    public static synchronized SettingsVariable getInstance(){
        if(null == mInstance){
            mInstance = new SettingsVariable();
        }
        return mInstance;
    }

}
