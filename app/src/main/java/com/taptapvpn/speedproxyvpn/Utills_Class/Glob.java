package com.taptapvpn.speedproxyvpn.Utills_Class;

import android.content.Context;
import android.content.SharedPreferences;

public class Glob {
    private static final String PREF_NAME = "snow-intro-slider";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;
    public static final String PRIMIUM_STATE = "primium_state";//boolean

    public static final String COUNTRY_DATA = "Country_data";
    public static final String BUNDLE = "Bundle";
    public static final String SELECTED_COUNTRY = "selected_country";


    public Glob(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

}