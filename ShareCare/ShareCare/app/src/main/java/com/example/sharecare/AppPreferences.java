package com.example.sharecare;

import android.content.Context;
import android.content.SharedPreferences;


import com.example.sharecare.ApiResponse.LoginResponse.User;
import com.google.gson.Gson;

public class AppPreferences {
    private SharedPreferences sharedPref;
    private Context appContext;

    private static AppPreferences instance;

    public static synchronized AppPreferences getInstance(Context context){
        if(instance == null)
            instance = new AppPreferences(context);
        return instance;
    }

    private AppPreferences(Context applicationContext) {
        appContext = applicationContext;
        sharedPref = appContext.getSharedPreferences(
                "app_preferences", Context.MODE_PRIVATE );
    }


    public void setToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.TOKEN, token);
        editor.commit();
    }

    public String getToken() {
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getString(Constants.TOKEN, null);
    }

    public void setFMToken(String token) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.FM_TOKEN, token);
        editor.commit();
    }

    public String getFMToken() {
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getString(Constants.FM_TOKEN, "");
    }

    public void setDemo(Boolean demo){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(Constants.DEMO, demo);
        editor.commit();
    }

    public boolean getDemo() {
        SharedPreferences.Editor editor = sharedPref.edit();
        return sharedPref.getBoolean(Constants.DEMO, false);
    }

    public void saveUser(User user) {
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString(Constants.USER_DATA, json);
        editor.commit();
    }


    public User getUserData() {
        Gson gson = new Gson();
        String json = sharedPref.getString(Constants.USER_DATA, null);
        User user = gson.fromJson(json, User.class);
        return user;
    }





}
