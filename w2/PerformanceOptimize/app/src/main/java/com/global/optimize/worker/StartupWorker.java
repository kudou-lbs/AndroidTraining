package com.global.optimize.worker;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class StartupWorker extends Worker {

    public StartupWorker(Context context, WorkerParameters workerParams) {
        super(context, workerParams);
    }


    @NonNull
    @Override
    public Result doWork() {
        Log.d("StartupWorker","--------doWork");
        return Result.success();
    }
}
