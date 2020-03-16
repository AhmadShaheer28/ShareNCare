package com.example.sharecare.API;

import android.app.Application;
import android.content.Context;



public class Global extends Application {
    private static Context applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
    }


    public static Context getAppContext() {
        return applicationContext;
    }



}
