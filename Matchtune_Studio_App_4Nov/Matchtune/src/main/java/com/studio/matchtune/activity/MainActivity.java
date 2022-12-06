package com.studio.matchtune.activity;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;
import com.google.gson.Gson;
import com.ramotion.circlemenu.CircleMenuView;
import com.studio.matchtune.Api.ApiInterface;
import com.studio.matchtune.ResponceModel.LoginRoot;
import com.studio.matchtune.fragment.AboutFragment;
import com.studio.matchtune.fragment.ResetAppStorage;
import com.studio.matchtune.fragment.TagsFragment;
import com.studio.matchtune.fragment.fragment_login;
import com.studio.matchtune.fragment.fragment_logout;
import com.studio.matchtune.inputModel.AttributeModel;
import com.studio.matchtune.inputModel.DataModel;
import com.studio.matchtune.inputModel.responcedata;
import com.studio.matchtune.model.AttributeModelLogin;
import com.studio.matchtune.model.DataModelLogin;
import com.studio.matchtune.model.RegisterModel;
import com.studio.matchtune.model.responcedatalogin;
import com.studio.matchtune.utility.ApplicationConstant;
import com.studio.matchtune.utility.Constants;
import com.studio.matchtune.utility.RealPathUtil;
import com.studio.matchtune.utility.TrimmerUtils;
import com.studio.matchtune.utility.utility;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    ApiInterface apiInterface;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton about_menu, menuWatchTutorial, floating_action_menu_privacy, floating_action_menu_about_us, action_menu_rate_app, loating_action_menu_matchTune_AppCredit, loating_action_menu_matchTune_Login, loating_action_menu_matchTune_Logout, loating_action_menu_resetapp, loating_action_menu_terms;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_WRITE_REQUEST_CODE = 101;
    private static final int MY_READ_REQUEST_CODE = 102;
    public final String APP_TAG = "MyCustomApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    private VideoView mVideoView;
    TextView txtStory, txtNoInterNet, txtSelectVideo, txtUserlogged;
    ImageView ivLogo;
    GifImageView loader;
    final int SELECT_IMAGE = 1234;
    final int CAPTURE_VIDEO = 123344;
    private static final int pic_id = 123;
    FrameLayout frmSettingBg;
    utility util = new utility();
    public List<Integer> videoList;
    int count = 2;
    Uri uri = Uri.parse(ApplicationConstant.INSTANCE.VideosSlideShow + count + ".MP4");
    CircleMenuView menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        Retrofit retrofit = com.studio.matchtune.Api.ApiClient.getclient();
        apiInterface = retrofit.create(com.studio.matchtune.Api.ApiInterface.class);


        try {
            UserLogin();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        mVideoView = (VideoView) findViewById(R.id.videoview);
        txtStory = (TextView) findViewById(R.id.txtStory);
        txtUserlogged = (TextView) findViewById(R.id.txtUserlogged);
        txtNoInterNet = (TextView) findViewById(R.id.txtNoInterNet);
        txtSelectVideo = (TextView) findViewById(R.id.txtSelectVideo);
        ivLogo = (ImageView) findViewById(R.id.ivLogo);
        loader = (GifImageView) findViewById(R.id.loader);
        menu = (CircleMenuView) findViewById(R.id.circle_menu);
        loating_action_menu_matchTune_Login = (FloatingActionButton) findViewById(R.id.loating_action_menu_matchTune_Login);
        loating_action_menu_matchTune_Logout = (FloatingActionButton) findViewById(R.id.loating_action_menu_matchTune_Logout);
        loating_action_menu_resetapp = (FloatingActionButton) findViewById(R.id.loating_action_menu_resetapp);
        loating_action_menu_terms = (FloatingActionButton) findViewById(R.id.loating_action_menu_terms);
        videoList = util.getRandomVideo();

        // Permission to write External Storage

        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_WRITE_REQUEST_CODE);
        }

        // If User Not Logged In


        // Checking internet connected or not

        if (!internetIsConnected(MainActivity.this)) {

            count = 0;
            uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video_a);
            loader.setVisibility(View.VISIBLE);
            txtNoInterNet.setVisibility(View.VISIBLE);
            menu.setVisibility(View.INVISIBLE);
            ivLogo.setVisibility(View.INVISIBLE);
            txtSelectVideo.setVisibility(View.INVISIBLE);
        } else {
            uri = Uri.parse(ApplicationConstant.INSTANCE.VideosSlideShow+videoList.get(count) + ".MP4");
            txtNoInterNet.setText("Initializing...");
            loader.setVisibility(View.VISIBLE);
            txtNoInterNet.setVisibility(View.VISIBLE);
            txtSelectVideo.setVisibility(View.INVISIBLE);
            menu.setVisibility(View.INVISIBLE);
            ivLogo.setVisibility(View.INVISIBLE);


            new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {

                    loader.animate().alpha(0f).setDuration(2000);
                    txtNoInterNet.animate().alpha(0f).setDuration(2000);
                    loader.animate().alpha(0f).setDuration(2000);
                    txtNoInterNet.animate().alpha(0f).setDuration(2000);

                    loader.setVisibility(View.INVISIBLE);
                    txtNoInterNet.setVisibility(View.INVISIBLE);
                    txtSelectVideo.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.VISIBLE);
                    ivLogo.setVisibility(View.VISIBLE);

                }
            }.start();
        }
        mVideoView.setVideoURI(uri);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        mediaPlayer = new MediaPlayer();
                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(false);
                    mediaPlayer.start();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // Changing icon if user logged in or logout

        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        if (prefs.contains("useremail")) {
            loating_action_menu_matchTune_Logout.setVisibility(View.VISIBLE);
            loating_action_menu_matchTune_Login.setVisibility(View.GONE);
        } else {
            loating_action_menu_matchTune_Login.setVisibility(View.VISIBLE);
            loating_action_menu_matchTune_Logout.setVisibility(View.GONE);

        }

        SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String userEmail = shrd.getString("useremail", "");

        if (userEmail == "") {
            txtUserlogged.setText("");
        } else {
            txtUserlogged.setText("Logged as " + userEmail);
        }


        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                if (internetIsConnected(MainActivity.this)) {
                    if (count <= 91) {
                        count = count + 1;
                    } else {
                        count = 2;
                    }
                    uri = Uri.parse(ApplicationConstant.INSTANCE.VideosSlideShow + videoList.get(count) + ".MP4");

                } else {
                    if (count == 0) {
                        count = count + 1;
                        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.pexels);
                    } else if (count == 1) {
                        count = count + 1;
                        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_sea);
                    } else {
                        count = 0;
                        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.bg_video_a
                        );
                    }
                }
                mVideoView.setVideoURI(uri);
                // mVideoView.start();
            }
        });

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(8000); //time in milliseconds

        AnimationSet animation = new AnimationSet(false); //change to false
        animation.addAnimation(fadeIn);
        txtStory.setAnimation(animation);


        new CountDownTimer(10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                // txtStory.animate().alpha(1f).setDuration(20000);
            }

            @Override
            public void onFinish() {

                txtStory.animate().alpha(0f).setDuration(2000);

            }
        }.start();

        materialDesignFAM = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floating_action_menu_privacy = (FloatingActionButton) findViewById(R.id.floating_action_menu_privacy);
        about_menu = (FloatingActionButton) findViewById(R.id.about_menu);
        menuWatchTutorial = (FloatingActionButton) findViewById(R.id.menuWatchTutorial);
        floating_action_menu_about_us = (FloatingActionButton) findViewById(R.id.floating_action_menu_about_us);
        action_menu_rate_app = (FloatingActionButton) findViewById(R.id.action_menu_rate_app);
        loating_action_menu_matchTune_AppCredit = (FloatingActionButton) findViewById(R.id.floating_action_menu_matchTune_AppCredit);

        frmSettingBg = (FrameLayout) findViewById(R.id.frmSettingBg);
        materialDesignFAM.bringToFront();

        materialDesignFAM.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (materialDesignFAM.isOpened()) {

                    materialDesignFAM.close(true);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            frmSettingBg.setVisibility(View.INVISIBLE);
                        }
                    }, 500);

                } else {
                    materialDesignFAM.open(true);
                    frmSettingBg.setVisibility(View.VISIBLE);
                }
            }
        });
        about_menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                AboutFragment frg = AboutFragment.newInstance("");
                frg.show(fm, "fragment_edit_name");
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        loating_action_menu_matchTune_AppCredit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.AppCredit);
                startActivity(intent);
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });


        loating_action_menu_terms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.Termofuse);
                startActivity(intent);
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        menuWatchTutorial.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TourVideo.class);
                startActivity(intent);
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        frmSettingBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        floating_action_menu_privacy.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra("urlString", ApplicationConstant.INSTANCE.PrivacyPolicy);
                startActivity(intent);
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        floating_action_menu_about_us.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);

                intent.putExtra("urlString", ApplicationConstant.INSTANCE.ContactUs);
                startActivity(intent);
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        action_menu_rate_app.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rateApp();
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });
        loating_action_menu_matchTune_Login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                fragment_login fragment = fragment_login.newInstance("Some Title");
                // TagsFragment fragment = TagsFragment.newInstance("Some Title");
                fragment.show(fm, "fragment_edit_name");
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });


        loating_action_menu_resetapp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                ResetAppStorage fragment = ResetAppStorage.newInstance("Some Title");
                // TagsFragment fragment = TagsFragment.newInstance("Some Title");
                fragment.show(fm, "fragment_edit_name");
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });

        loating_action_menu_matchTune_Logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                fragment_logout fragment = fragment_logout.newInstance("Some Title");
                // TagsFragment fragment = TagsFragment.newInstance("Some Title");
                fragment.show(fm, "fragment_edit_name");
                materialDesignFAM.close(true);
                frmSettingBg.setVisibility(View.INVISIBLE);
            }
        });


        // menu.setIconMenu(R.drawable.);
        // menu.setIconClose(R.drawable.persons);

        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {

                Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bounce);
                ivLogo.startAnimation(animation);
                ivLogo.animate().rotation(45).start();
                materialDesignFAM.setVisibility(View.INVISIBLE);

                if (checkSelfPermission(Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            MY_CAMERA_REQUEST_CODE);
                }

            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {


            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {
                ivLogo.animate().rotation(90).start();
                materialDesignFAM.setVisibility(View.VISIBLE);

            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {


            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {
//                Intent intent = new Intent(MainActivity.this,TourActivity.class);
//                startActivity(intent);
                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_WRITE_REQUEST_CODE);
                }

            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                Intent intent = new Intent(MainActivity.this, TourVideo.class);
                ivLogo.animate().rotation(90).start();
                materialDesignFAM.setVisibility(View.VISIBLE);

                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_READ_REQUEST_CODE);
                }

                if (index == 0) {
                    // Select Video from Album
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, 0);

                } else if (index == 1) {
                    //  Select Video from folder
                    Intent intenta = new Intent();
                    intenta.setType("video/*");
                    intenta.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intenta, 1);

                } else if (index == 2) {
                    //Select Video from camera


                    if (checkSelfPermission(Manifest.permission.CAMERA)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA},
                                MY_CAMERA_REQUEST_CODE);
                    }


                    intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                    startActivityForResult(intent, 2);


                } else if (index == 4) {
                    // go to Tour Video
                    intent = new Intent(MainActivity.this, TourVideo.class);
                    startActivity(intent);
                } else if (index == 3) {
                //  Demo Video

                    // Permission to write during demo video
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MY_WRITE_REQUEST_CODE);
                    }


                    intent = new Intent(MainActivity.this, DemoVideoActivity.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri video = data.getData();
            String videopath = data.getData().getPath();
            Log.d("testvideopath", "" + videopath);
            Log.d("testvideop", "" + video);
//            videoField.setVideoURI(video);
//            videoField.start();
//            File file = new File(String.valueOf(video));
//            file = new File(file.getAbsolutePath());


            String aa = RealPathUtil.getRealPath(MainActivity.this, video).toString();

            Log.d("testvideoabsolute", "" + aa);
            Intent intentw = new Intent(MainActivity.this, VideoEditionActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("filepath", String.valueOf(video));
            bundle.putString("filepathvideo", aa);
            bundle.putInt("testing", 2);
            intentw.putExtras(bundle);
            startActivity(intentw);

        }
    }


    ActivityResultLauncher<Intent> takeOrSelectVideoResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        String filepath = String.valueOf(data);
                        Log.d("testvideodata", "video" + filepath);
                        Log.d("testvideodataaaa", "video" + data);
                        Intent intent = new Intent(MainActivity.this, VideoEditionActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("filepath", filepath);
                        bundle.putInt("testing", 2);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        if (TrimmerUtils.getDuration(MainActivity.this, data.getData()) < Constants.MIN_TRIM_TIME) {
                            Toast.makeText(MainActivity.this, getString(R.string.video_must_be_larger_then_second), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (data.getData() != null) {
//                            openTrimActivity(String.valueOf(data.getData()));
                        }
                    }
                }
            });


    public void rateApp() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl(ApplicationConstant.INSTANCE.RateUs);
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21) {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        } else {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    @Override
    protected void onResume() {
        mVideoView.resume();
        super.onResume();
    }

    @Override
    protected void onRestart() {
        mVideoView.start();
        super.onRestart();
    }

    @Override
    protected void onPause() {
        mVideoView.suspend();
        super.onPause();
    }



    public boolean internetIsConnected1() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public static Boolean internetIsConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

       // If user not Logged In
    public void UserLogin() throws InvalidKeyException {



        long unixTime = System.currentTimeMillis() / 1000L;
        String unitTimestring = String.valueOf(unixTime);
        Log.d("loginunixtime",""+unixTime );



         String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Log.d("loginandroid_id",""+android_id );

        // hmac to find signature
        String dataa = unitTimestring + android_id+ ApplicationConstant.INSTANCE.appid;
        String secret = "fee79c6356e5a25ad2a699a9b5cba549e9b0d0dc0c485a0b5a1cfeb006f923e9";


        Mac sha256_HMAC = null;
        try {
            sha256_HMAC = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(),"HmacSHA256");
        sha256_HMAC.init(secret_key);

        String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(dataa.getBytes()));

        Log.d("loginsignature",""+hash );
        // responce json object that pass in request
        responcedatalogin responcedatalogin = new responcedatalogin();
        DataModelLogin data = new DataModelLogin();
//        ArrayList<String>  arrayList=new ArrayList<>();
//        arrayList.add("Blues");
        data.setType("tokens");
        AttributeModelLogin attributedata=new AttributeModelLogin();
        data.setAttributes(attributedata);
        responcedatalogin.setData(data);

        // Set Login data if user Not Logged In

        attributedata.setUNIXTIME(unitTimestring);
        attributedata.setUUID(android_id);
        attributedata.setAPPId(ApplicationConstant.INSTANCE.appid);
        attributedata.setTos(true);
        attributedata.setSignature(hash);
        Log.d("testing","dfsfadsfl"+ new Gson().toJson(responcedatalogin));
        Log.d("apihitsssd","api responce");

        apiInterface.getUserNotLogin(responcedatalogin).enqueue(new Callback<LoginRoot>() {
            @Override
            public void onResponse(Call<LoginRoot> call, retrofit2.Response<LoginRoot> response) {

                try {
                    if (response != null & response.code()<201) {
//                        Log.d("apihitres","responce is"+ new Gson().toJson(response));
                        Log.e("apihitdatarescode","api responce is: "+ response.code());
                        Log.d("apihitsssdres","api responce: "+ response);
                        Log.d("apihitsssdres","api responce: "+ response.body().getData().getAttributes().getValue());
                        String token  = response.body().getData().getAttributes().getValue();
                        SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                        editor.putString("usertoken",token);
                        Log.d("your token is","token is :  " +token );
                        editor.apply();



//                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                        startActivity(intent);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Authentication error", Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    Log.e("exp", e.getLocalizedMessage());
                    loader.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "Authentication error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginRoot> call, Throwable t) {
                Log.e("failure", t.getLocalizedMessage());
            }
        });

//
    }

}