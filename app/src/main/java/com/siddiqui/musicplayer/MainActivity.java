package com.siddiqui.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String KEY_SONG = "song";
    private boolean mBound = false;
    private PlayerService mPlayerService;
    Button mDownloadButton;
    Button mPlayButton;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        //when connection is established to service
        @Override
        //Ibinder in the parameter is the one returned from onBind
        public void onServiceConnected(ComponentName name, IBinder binder) {
            mBound = true;
            PlayerService.LocalBinder localBinder = (PlayerService.LocalBinder) binder;
            mPlayerService = localBinder.getService();

            if(mPlayerService.isPlaying()){
                mPlayButton.setText("Pause");
            }
        }
        //when connection to service gets disconnected unexpectedly.
        //THis doesn't get called when unbind service gets called
        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDownloadButton = findViewById(R.id.doanloadButton);
        mPlayButton = findViewById(R.id.playButton);


        //thread is the highway
        //Runnable is the traffic that flows on that highway
//        final DownloadThread thread = new DownloadThread();
//        thread.setName("Download thread");
//
//        thread.start();


        //calls the run method of Thread

        mDownloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "downloading", Toast.LENGTH_SHORT).show();

//                //Send messaages to the handler for processing
//                //For every song per click
//                    //something to send to the handler
//                    Message message = Message.obtain();
//                    //.obj lets us add any sort of object
//                    //to our message. We want to tell our handler abt a song
//                    message.obj = song;
//                    //sending msg to handler to add to the msg queue
//                    thread.mHandler.sendMessage(message);

                for(String song: Playlist.songs) {
                    //how to start a service
                    Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                    //to pass msg's
                    intent.putExtra(KEY_SONG, song);
                    //starting a servuce
                    startService(intent);
                }

            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mBound){
                    if(mPlayerService.isPlaying()){
                        mPlayerService.pause();
                        mPlayButton.setText("Play");
                    } else {
                        Intent intent = new Intent(MainActivity.this, PlayerService.class);
                        startService(intent);
                        mPlayerService.play();
                        mPlayButton.setText("Pause");
                    }
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection , Context.BIND_AUTO_CREATE);//third param
        //automatically binds to service
        //service connection is what we use to know if we're successfuly
        //connected or disconnected
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mBound) {
            //called when we want to disconnect from service
            unbindService(mServiceConnection);
            mBound= false;
        }
    }

    //    private void downloadSong() {
//        //makes it so that end time is 10 seconds more than the current time
//        long endTime = System.currentTimeMillis()+ (10*1000);
//        while (System.currentTimeMillis() < endTime){
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        Log.d(TAG, "Song downloaded");
//
//    }
}
