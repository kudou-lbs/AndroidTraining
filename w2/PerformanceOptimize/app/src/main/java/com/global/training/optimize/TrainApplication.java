package com.global.training.optimize;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Trace;

import com.adjust.sdk.Adjust;
import com.adjust.sdk.AdjustConfig;
import com.tencent.bugly.crashreport.CrashReport;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TrainApplication  extends Application {

    public static final String TAG = TrainApplication.class.getSimpleName();

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();


/*  try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //Adjust 数据统计SDK 初始化
        Trace.beginSection("Adjust_init");
        String appToken = "v6stbmt5dwcg";
        String environment = AdjustConfig.ENVIRONMENT_SANDBOX;
        AdjustConfig config = new AdjustConfig(this, appToken, environment);
        Adjust.onCreate(config);
        registerActivityLifecycleCallbacks(new AdjustLifecycleCallbacks());
        Trace.endSection();
        //Tecent bugly SDK初始化
        Trace.beginSection("bugly_init");
        CrashReport.initCrashReport(getApplicationContext(), "4a2a2fa851", false);
        Trace.endSection();
    }


    private static final class AdjustLifecycleCallbacks implements ActivityLifecycleCallbacks {
        @Override
        public void onActivityResumed(Activity activity) {
            Adjust.onResume();
        }

        @Override
        public void onActivityPaused(Activity activity) {
            Adjust.onPause();
        }

        @Override
        public void onActivityStopped(@NonNull Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(@NonNull Activity activity) {

        }

        @Override
        public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

        }

        @Override
        public void onActivityStarted(@NonNull Activity activity) {

        }
    }
}
