package com.smsreaderapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SMSWorker extends Worker{
    public SMSWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
      }
    
      @NonNull
      @Override
      public Result doWork() {
        // Define the work to be done here
        return Result.success();
      }
}

