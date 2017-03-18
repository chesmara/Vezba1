package com.example.sninkovic_ns.vezba1.async;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.example.sninkovic_ns.vezba1.tools.ReviewerTools;

/**
 * Created by SNinkovic_ns on 18.3.2017.
 */

public class SimpleService extends Service{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int status = ReviewerTools.getConnectivityStatus(getApplicationContext());

        if (status==ReviewerTools.TYPE_MOBILE){
            new SimpleSyncTask(getApplicationContext()).execute(status);
        }
        if (status==ReviewerTools.TYPE_WIFI){
            new SimpleSyncTask(getApplicationContext()).execute(status);
        }
        stopSelf();

        return START_NOT_STICKY;
    }
}
