package com.example.newdiplwm;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class Session {

    private SharedPreferences prefs;

    public Session(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUserIdSession(int userid) {
        prefs.edit().putInt("USERID", userid).apply();
    }

    public int getUserIdSession() {
        int userid = prefs.getInt("USERID",-1);
        return userid;
    }
    public void setUsernameSession(String username) {
        prefs.edit().putString("USERNAME", username).apply();
    }

    public String getUsernameSession() {
        String username = prefs.getString("USERNAME","none");
        return username;
    }


    public void setGameIdSession(int gameid) {
        prefs.edit().putInt("GAMEID", gameid).apply();
    }

    public int getGameIdSession() {
        int gameid = prefs.getInt("GAMEID",-1);
        return gameid;
    }


    public void setModeSession(String mode) {
        prefs.edit().putString("MODE", mode).apply();
    }

    public String getModeSession() {
        String mode = prefs.getString("MODE","none");
        return mode;
    }


}