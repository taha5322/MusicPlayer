package com.siddiqui.musicplayer;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

// Created by Taha Siddiqui
// 2020-04-09
public class DownloadHandler extends Handler {
    private static final String TAG = DownloadHandler.class.getSimpleName();
    private DownloadService mService;

    //used to tell handler what to do when given a certain msg
    @Override
    public void handleMessage(@NonNull Message msg) {
        downloadSong(msg.obj.toString());

        //makes sure all arg id's are handled and then services
        //are stopped
        mService.stopSelf(msg.arg1);
    }

    private void downloadSong(String song) {
        //makes it so that end time is 10 seconds more than the current time
        long endTime = System.currentTimeMillis()+ (10*1000);
        while (System.currentTimeMillis() < endTime){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, song+" is downloading");
    }

    public void setService(DownloadService downloadService) {
        mService = downloadService;
    }
}
