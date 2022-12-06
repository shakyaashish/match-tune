package com.studio.matchtune.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.VideoView;

import com.studio.matchtune.utility.ApplicationConstant;

public class DemoVideoActivity extends AppCompatActivity {

    VideoView cityVideoView,seaLifeVideoView,sportVideoView,animalVideoView,holidaysVideoView,travelVideoView,flyVideoView,discoverVideoView,surfVideoView,spaceVideoView,zenVideoView;
    Uri cityUri = Uri.parse(ApplicationConstant.INSTANCE.cityUri);
    Uri seaLifeUri = Uri.parse(ApplicationConstant.INSTANCE.seaLifeUri);
    Uri sportLifeUri = Uri.parse(ApplicationConstant.INSTANCE.sportLifeUri);
    Uri animalUri = Uri.parse(ApplicationConstant.INSTANCE.animalUri);
    //  Uri holidaysUri = Uri.parse("https://video.wixstatic.com/video/2e0338_a5f3c89881a445b0bab9f4d788219f44/720p/mp4/file.mp4");
    Uri holidaysUri = Uri.parse(ApplicationConstant.INSTANCE.holidaysUri);
    Uri travelUri = Uri.parse(ApplicationConstant.INSTANCE.travelUri);
    Uri flyUri = Uri.parse(ApplicationConstant.INSTANCE.flyUri);
    Uri spaceUri = Uri.parse(ApplicationConstant.INSTANCE.spaceUri);
    Uri discoverUri = Uri.parse(ApplicationConstant.INSTANCE.discoverUri);
    Uri surfUri = Uri.parse(ApplicationConstant.INSTANCE.surfUri);
    Uri zenUri = Uri.parse(ApplicationConstant.INSTANCE.zenUri);

    ImageView arrowcircle;
RelativeLayout cityRL,seeLifeRL,sportRL,flyRL,travelRL,discoverRL,surfRL,spacefRL;
    RelativeLayout animalRL,holidaysRL;
    RelativeLayout  zenRL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_demo_video);
        arrowcircle = (ImageView) findViewById(R.id.arrowcircle);

        seaLifeVideoView = (VideoView) findViewById(R.id.seaLifeVideoView);
        cityVideoView = (VideoView) findViewById(R.id.cityVideoView);
        sportVideoView = (VideoView) findViewById(R.id.sportVideoView);
        animalVideoView  = (VideoView) findViewById(R.id.animalVideoView);
        holidaysVideoView  = (VideoView) findViewById(R.id.holidaysVideoView);
        travelVideoView  = (VideoView) findViewById(R.id.travelVideoView);
        flyVideoView  = (VideoView) findViewById(R.id.flyVideoView);
        discoverVideoView  = (VideoView) findViewById(R.id.discoverVideoView);
        surfVideoView  = (VideoView) findViewById(R.id.surfVideoView);
        spaceVideoView  = (VideoView) findViewById(R.id.spaceVideoView);
        zenVideoView  = (VideoView) findViewById(R.id.zenVideoView);
        cityRL = (RelativeLayout) findViewById(R.id.cityRL);
        seeLifeRL = (RelativeLayout) findViewById(R.id.seeLifeRL);
        sportRL = (RelativeLayout) findViewById(R.id.sportRL);
        animalRL = (RelativeLayout) findViewById(R.id.animalRL);
        holidaysRL = (RelativeLayout) findViewById(R.id.holidaysRL);
        flyRL = (RelativeLayout) findViewById(R.id.flyRL);
        travelRL = (RelativeLayout) findViewById(R.id.travelRL);
        discoverRL = (RelativeLayout) findViewById(R.id.discoverRL);
        surfRL = (RelativeLayout) findViewById(R.id.surfRL);
        spacefRL = (RelativeLayout) findViewById(R.id.spacefRL);
        zenRL = (RelativeLayout) findViewById(R.id.zenRL);

        seaLifeVideoView.setVideoURI(seaLifeUri);
        cityVideoView.setVideoURI(cityUri);
       sportVideoView.setVideoURI(sportLifeUri);
        animalVideoView.setVideoURI(animalUri);
        flyVideoView.setVideoURI(flyUri);
        holidaysVideoView.setVideoURI(holidaysUri);
        travelVideoView.setVideoURI(travelUri);
        discoverVideoView.setVideoURI(discoverUri);
                surfVideoView.setVideoURI(surfUri);
                spaceVideoView.setVideoURI(spaceUri);
                zenVideoView.setVideoURI(zenUri);
        discoverRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.discoverUri);
                startActivity(intent);

            }
        });
        seeLifeRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.seaLifeUri);
                startActivity(intent);
            }
        });
        cityRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.cityUri);
                startActivity(intent);

            }
        });

        sportRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.sportLifeUri);
                startActivity(intent);

            }
        });
        animalRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.animalUri);
                startActivity(intent);

            }
        });
        holidaysRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.holidaysUri);
                startActivity(intent);

            }
        });
        flyRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.flyUri);
                startActivity(intent);

            }
        });

        travelRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.travelUri);
                startActivity(intent);

            }
        });

        zenRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.zenUri);
                startActivity(intent);

            }
        });
        spacefRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.spaceUri);
                startActivity(intent);

            }
        });
        surfRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DemoVideoActivity.this, VideoEditionActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.surfUri);
                startActivity(intent);
            }
        });
        zenVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {


                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        spaceVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        surfVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        discoverVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        travelVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        holidaysVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        animalVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        flyVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        sportVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        cityVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        seaLifeVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final ScrollView scrollview = ((ScrollView) findViewById(R.id.scrollView));
        scrollview.post(new Runnable() {
            @Override
            public void run() {
                scrollview.fullScroll(ScrollView.FOCUS_UP);
            }
        });
    }
}