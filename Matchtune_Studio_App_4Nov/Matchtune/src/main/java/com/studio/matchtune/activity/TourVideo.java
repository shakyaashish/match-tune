package com.studio.matchtune.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.VideoView;

public class TourVideo extends AppCompatActivity {


     VideoView  mVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_tour_video);

        mVideoView =(VideoView)findViewById(R.id.tourvideoview);
        VideoView simpleVideoView = (VideoView) findViewById(R.id.tourvideoview);
        simpleVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.tour));
//        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
//                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(false);
                    mediaPlayer.start();

                    if(!mediaPlayer.isPlaying()){
                        Intent intent = new Intent(TourVideo.this, MainActivity.class);
                        startActivity(intent);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                                Intent intent = new Intent(TourVideo.this, MainActivity.class);
                               startActivity(intent);
            }
        });


    }
}