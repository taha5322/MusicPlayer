package com.siddiqui.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.Nullable;

// Created by Taha Siddiqui
// 2020-04-09
public class DownloadService extends Service {

    private static final String TAG = DownloadService.class.getSimpleName();
    private DownloadHandler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        DownloadThread thread = new DownloadThread();
        thread.setName("DownloadThread");
        thread.start();
        //main thread jumps from onCreate to onStart before handler
        //gets initialised. So you get NullPointer when u try
        //to access mHandler object from onStart
        while (thread.mHandler==null){

        }
        mHandler= thread.mHandler;
        //passing a reference of our service to the handler
        //helps with stopping the service
        mHandler.setService(this);
    }


    @Override//int start id
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
//        return super.onStartCommand(intent, flags, startId);
        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        mHandler.sendMessage(message);

        //return value can be used if service is killed
        //before finishing
        return Service.START_REDELIVER_INTENT;
    }


    @Nullable
    @Override
    //services usually stop themselves after finishing
    //or continue until the device getsa powered off if
    //not finished
    public IBinder onBind(Intent intent) {
        return null;
    }
}
