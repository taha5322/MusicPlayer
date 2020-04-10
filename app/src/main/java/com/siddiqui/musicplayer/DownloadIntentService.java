package com.siddiqui.musicplayer;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

// Created by Taha Siddiqui
// 
public class DownloadIntentService extends IntentService {

    private static final String TAG = IntentService.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadIntentService() {
        super("download intent service");
        //be default an intent will usually START_STICKY
        //this makes it to redeliver the intent
        setIntentRedelivery(true);

    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        downloadSong(song);
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

}
