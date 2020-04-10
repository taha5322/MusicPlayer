package com.siddiqui.musicplayer;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

// Created by Taha Siddiqui
// 
public class PlayerService extends Service {
    private static final String TAG = PlayerService.class.getSimpleName();
    private MediaPlayer mPlayer;
    private IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG, "On create");
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();//stops the service immediately
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "On Bind");
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "OnUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        mPlayer.release();//frees up system resources.
        super.onDestroy();
    }

    public class LocalBinder extends Binder{
        public PlayerService getService(){
            return PlayerService.this;
        }
    }

    //client methods
    public void play(){
        mPlayer.start();
    }
    public void pause(){
        mPlayer.pause();
    }
    public boolean isPlaying(){
        return mPlayer.isPlaying();
    }

}
