package com.example.memoryoptimization;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

public class BaseApplication extends Application {

    private static Application application;

    @Override
    public void onCreate() {
        super.onCreate();

        application = this;
    }

    public static Application getApplication() {
        return application;
    }
}
