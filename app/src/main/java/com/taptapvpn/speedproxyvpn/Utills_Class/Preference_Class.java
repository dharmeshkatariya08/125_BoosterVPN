package com.taptapvpn.speedproxyvpn.Utills_Class;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference_Class {
    public SharedPreferences appSharedPrefs;
    public SharedPreferences.Editor prefsEditor;
    Context context;

    public Preference_Class(Context context) {
        this.context = context;
        this.appSharedPrefs = context.getSharedPreferences("FireVPNPro_pref", Activity.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
    }

    public boolean isBooleenPreference(String key_value) {
        return appSharedPrefs.getBoolean(key_value, false);
    }

    public void setStringpreference(String key_value, String defult_value) {
        this.prefsEditor.putString(key_value, defult_value).commit();
    }


}
