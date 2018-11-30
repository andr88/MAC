package com.dora.app.mynetwork2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {
    private Timer timer;
    private int i;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        timer = new Timer();
        timer.schedule(new MyTask(),0,1000);
        Log.v("brad", "onCreate");
    }

    private class MyTask extends TimerTask{
        @Override
        public void run() {
            i++;
            Log.v("brad", "i = " + i);
        }
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int rand = intent.getIntExtra("key", -1);
        Log.v("brad", "onStartCommand" + rand);
        i = rand;
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        Log.v("brad", "onDestroy");
    }
}
