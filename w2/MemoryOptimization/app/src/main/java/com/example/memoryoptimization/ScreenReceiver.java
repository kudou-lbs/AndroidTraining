package com.example.memoryoptimization;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ScreenReceiver extends BroadcastReceiver {

    private final String TAG = ScreenReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent != null) {
            Log.v(TAG, "onReceive() 收到广播：" + intent.getAction());
        }
    }
}
