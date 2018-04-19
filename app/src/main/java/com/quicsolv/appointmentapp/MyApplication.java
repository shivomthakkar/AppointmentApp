package com.quicsolv.appointmentapp;

import android.app.Application;
import android.content.Context;

//import android.support.multidex.MultiDex;

/**********************************************************************
 * Created by   -  Tushar Patil
 * Organization -  QuicSolv Technologies Pvt.Ltd
 * Date         -  08 Feb 2018
 ***********************************************************************/

public class MyApplication extends Application {

    private static MyApplication _instance;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        _instance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
//        MultiDex.install(this);
    }

    public static MyApplication getInstance() {
        return _instance;
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }

}