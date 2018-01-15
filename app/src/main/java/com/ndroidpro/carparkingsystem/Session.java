package com.ndroidpro.carparkingsystem;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setIsAdmin(boolean admin) {
        prefs.edit().putBoolean("admin", admin).apply();
    }

    public boolean getIsAdmin() {
        return prefs.getBoolean("admin",false);
    }
}