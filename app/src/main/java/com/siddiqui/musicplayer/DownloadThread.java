package com.siddiqui.musicplayer;

import android.os.Looper;
import android.util.Log;

// Created by Taha Siddiqui
// 2020-04-09
public class DownloadThread extends Thread {
    private static final String TAG = DownloadThread.class.getSimpleName();
    public DownloadHandler mHandler;

    @Override
    public void run() {
        Looper.prepare();
        //creates looper and message queue
        mHandler = new DownloadHandler();
        //by default a handler is associated with the looper of the
        //current thread

        Looper.loop();
        //starts looping over msg queue
    }


}
