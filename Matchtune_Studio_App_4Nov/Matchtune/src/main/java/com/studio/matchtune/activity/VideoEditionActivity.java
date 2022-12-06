package com.studio.matchtune.activity;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.studio.matchtune.utility.Common.isConnected;
import static com.studio.matchtune.utility.Common.showToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFmpegExecution;
import com.google.gson.Gson;
import com.studio.matchtune.Api.ApiInterface;
import com.studio.matchtune.ResponceModel.Attributedata;
import com.studio.matchtune.ResponceModel.MusicResponce;
import com.studio.matchtune.adapter.MusicTileAdapter;
import com.studio.matchtune.adapter.VideoAdapter;
import com.studio.matchtune.fragment.ExportFragment;
import com.studio.matchtune.fragment.FilterFragment;
import com.studio.matchtune.fragment.FilterViewFragment;
import com.studio.matchtune.fragment.MusicDetailsFragment;
import com.studio.matchtune.fragment.VolumeControllerFragment;
import com.studio.matchtune.inputModel.AttributeModel;
import com.studio.matchtune.inputModel.DataModel;
import com.studio.matchtune.inputModel.MusicAttributeModel;
import com.studio.matchtune.inputModel.MusicDataModel;
import com.studio.matchtune.inputModel.MusicResponcedata;
import com.studio.matchtune.inputModel.responcedata;
import com.studio.matchtune.model.videoData;
import com.studio.matchtune.utility.ApplicationConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import pl.droidsonroids.gif.GifImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;


public class VideoEditionActivity extends AppCompatActivity implements VideoAdapter.OnclickLike {

    private static String df;
    private static final String TAG = df;
    ApiInterface apiInterface;
    VideoAdapter adapter;
    RecyclerView recyclerView;
    ClickListiner listiner;
    ImageView btnVolue, btnMusic, btnMusicPlay, btnUpload;
    VideoView videoView;
    ImageView arrowcircle, Logo, playbtn;
    String userselected, filepathvideo, fileName, uridata;
    LinearLayout beforevideo;
    int testing, testingc, sharevideo;
    Uri uri;
    Uri uris;
    List<videoData> list = new ArrayList<>();
    List<String> videoforlist = new ArrayList<>();
    MusicTileAdapter musicTileAdapter;
    String thumbnail;
    GifImageView loader;
    JSONObject jobj = new JSONObject();
    String fromgallery, uricheck, fromdevice;
    int a;
    RelativeLayout playbtns;
    public RelativeLayout likegif;
    String isvideodownloaded = "/storage/emulated/0/StudioApp/" + "asme" + ".mp4";
    String imagePath, videoOutPath, fileNameUri;
    SeekBar seekProg;
    RelativeLayout musicsection, playandpause;
    String key,duration;
    int intValue;
    public MediaPlayer videoplayer = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_edition);
        Retrofit retrofit = com.studio.matchtune.Api.ApiClient.getclient();
        apiInterface = retrofit.create(com.studio.matchtune.Api.ApiInterface.class);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        videoView = (VideoView) findViewById(R.id.videoView);
        arrowcircle = (ImageView) findViewById(R.id.arrowcircle);
        loader = (GifImageView) findViewById(R.id.loader);
        playbtn = (ImageView) findViewById(R.id.btnplay);
        playbtns = (RelativeLayout) findViewById(R.id.playandpause);
        likegif = (RelativeLayout) findViewById(R.id.likegif);
        seekProg = (SeekBar) findViewById(R.id.seekProg);
        beforevideo = (LinearLayout) findViewById(R.id.beforevideo);
        recyclerView
                = (RecyclerView) findViewById(
                R.id.recycler_view);
        Logo = (ImageView) findViewById(R.id.Logo);
        musicsection = (RelativeLayout) findViewById(R.id.musicsection);
        playandpause = (RelativeLayout) findViewById(R.id.playandpause);


        beforevideo.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        musicsection.setVisibility(View.GONE);
        playandpause.setVisibility(View.GONE);



        File myDirectory = new File(Environment.getExternalStorageDirectory(), "StudioApp");

        if(!myDirectory.exists()) {
            myDirectory.mkdirs();
            Log.d("checkdir","directory created");
        }else{
//            myDirectory.delete();
            Log.d("checkdir","directory  Already created");
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                beforevideo.setVisibility(View.GONE);
                videoView.setVisibility(View.VISIBLE);
                musicsection.setVisibility(View.VISIBLE);

            }
        }, 2000);


        SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        uridata = shrd.getString("urlString", "");
        Log.d("testprinturishares", uridata);
        uri = Uri.parse(uridata);

        // On the basic of selected index get video location

        Bundle extras = getIntent().getExtras();
        if (extras != null) {

            sharevideo = (extras.getInt("sharevideo"));
            userselected = (extras.getString("filterobj"));
            uri = Uri.parse(extras.getString("urlString", ""));
            Log.d("uritestinguri", "" + uri);
            testing = (extras.getInt("testing"));
            testingc = (extras.getInt("testingc"));
            uricheck = String.valueOf(uri);


            // If User Select Demo Video

            if (uricheck.length() > 5 && testing != 1) {
                Log.d("uritesting", "" + uri);
                downloadvideo(String.valueOf(uri));
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.remove("filesave");
                prefsEditor.apply();

                try {
                    demovideoRequest(String.valueOf(uri));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            // If User Select Demo Video Search     gallery Search

            if (uricheck.length() == 0 && testing != 2 && !uridata.equals("")) {
                SharedPreferences shrds = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String uridatas = shrds.getString("urlString", "");
                Log.d("testdemovideoseach", "" + uridatas);
          //      downloadvideo(String.valueOf(uridatas));
                SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
                prefsEditor.remove("filesave");
                prefsEditor.apply();




            }

//            testing =(extras.getInt("testing"));
            Log.d("testprinttesting", "" + testing);
            Log.d("testprinttestinguser", "" + userselected);
            Log.d("testprintdownloaduri", "" + uri);


            // Search if user select from gallery/folder/camera  or search demo video

            if (testing == 1) {
                SharedPreferences shrds = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String uridatas = shrds.getString("urlString", "");
                fromdevice = shrds.getString("filesave", "");
                Log.d("testprinturishareblanck", uridatas);
                Log.d("testprinturifromdevice", fromdevice);
                uri = Uri.parse(uridatas);
                if(uridatas.isEmpty()|| uridatas.length()<70){
                    // Search from device
                    Log.d("seachfromdevice", fromdevice);
                    try {
                        uploadVideoRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    // Search from demo video
                    Log.d("seachfromdemovideo", fromdevice);
                    try {
                        demovideoRequest(uridatas);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (fromdevice.isEmpty()) {
                    Log.d("testprinturiisempty", fromdevice);

                    // search from demo video

                } else {
                    uri = Uri.parse(fromdevice);
                    filepathvideo = fromdevice;
                    Log.d("testprinturiis has avle", fromdevice);




                }




                try {
                    JSONObject jsonObj = new JSONObject(userselected);
                    Log.d("testprintfinal", "" + jsonObj);

                    makeMusicsSearchRequest(jsonObj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {

            }

            SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("urlString", String.valueOf(uri));
            editor.apply();
            Log.d("testprinturiisds", String.valueOf(uri));
            Log.d("testprintuserselected", "" + userselected);


        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            fromgallery = bundle.getString("filepath");
            filepathvideo = bundle.getString("filepathvideo", "");
            a = bundle.getInt("testing");
            Log.d("testfromgallery11", "fromgal :" + fromgallery);
            Log.d("testprinturiis  in bundle", "fromgal :" + filepathvideo);

            if (filepathvideo.isEmpty()) {
                filepathvideo = fromdevice;
                Log.d("testprinturiis  in null", "fromgal :" + filepathvideo);


            }

        }

        // If user Select Video from gallery/ folder/ camera

        if (a == 2) {
            uri = Uri.parse(fromgallery);
            Log.d("testfromgallery", "fromgal :" + uri);
            String uriif = String.valueOf(uri);
            SharedPreferences.Editor editor = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("filesave", filepathvideo);
            editor.apply();

            try {
                uploadVideoRequest();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if(uriif.isEmpty()){
                Log.d("testfromgalleryif", "fromgal :" + uri);
            }else{
                Log.d("testfromgalleryelse", "fromgal :" + uri);

            }

            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor = prefs.edit();
            prefsEditor.remove("urlString");
            prefsEditor.apply();


            File myfile = new File(uri.getPath());
            String b = myfile.getAbsolutePath();
            Log.d("testactualpath", "fromgal :" + b);



        } else {

            Log.d("testfromdemovideoseach", "yeee :" + "dd");
        }
        String sdd = "content://com.android.providers.media.documents/document/video:897944";

      // Playing Selected video in videoview

        videoView.setVideoURI(uri);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                try {
                    if (mediaPlayer.isPlaying()) {

                        mediaPlayer.stop();
                        mediaPlayer.release();
//                        mediaPlayer = new MediaPlayer();

                    }
                    mediaPlayer.setVolume(0f, 0f);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                    Logo.setVisibility(View.INVISIBLE);
                    arrowcircle.setVisibility(View.INVISIBLE);
                    seekProg.setVisibility(View.GONE);
                    ImageView image = (ImageView) findViewById(R.id.btnplay);
                    image.setImageResource(R.drawable.transpa);

                    seekProg.setMax(mediaPlayer.getDuration());

                    try {
                        new Timer().scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
//                               seekProg.setProgress(mediaPlayer.getCurrentPosition());
                            }
                        }, 1000, 10000);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.d("currentposition", "current : " + mediaPlayer.getCurrentPosition());
                    }


                    seekProg.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            mediaPlayer.seekTo(progress);
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        // Play and pause video

        playbtns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testbtn", "ddddddddd");
//                mediaPlayer.pause();
                ImageView image = (ImageView) findViewById(R.id.btnplay);


                if (videoView.isPlaying()) {
                    videoView.pause();
                    Logo.setVisibility(View.VISIBLE);
                    arrowcircle.setVisibility(View.VISIBLE);
                    image.setImageResource(R.drawable.btnpause);
                    seekProg.setVisibility(View.VISIBLE);
                    adapter.playerpause();
                } else {
                    videoView.start();
                    Logo.setVisibility(View.INVISIBLE);
                    arrowcircle.setVisibility(View.INVISIBLE);
                    seekProg.setVisibility(View.GONE);
                    adapter.playerplay();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageResource(R.drawable.transpa);
                        }
                    }, 500);
                    image.setImageResource(R.drawable.btnplay);

                }
            }

        });

        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        seekProg.setVisibility(View.GONE);

//        try {
//            JSONObject attObj = new JSONObject();
//            // attObj.put("genre", "Blues");
//            attObj.put("count", 7);
//            JSONObject dataObj = new JSONObject();
//            dataObj.put("type", "search");
//            dataObj.put("attributes", attObj);
//
////        JSONObject jobj = new JSONObject();
//            jobj.put("data", dataObj);
//            Log.d("testresbefore", String.valueOf(jobj));
//            Log.d("testprintfinals", "" + jobj);
//            makeMusicsRequest(jobj);
//
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

//        try {
//            makeMusicsRequest(jobj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


        listiner = new ClickListiner() {
            @Override
            public void click(String index) {


                Log.d("testcoverimageinactivity", "image url :" + index);
                bundle.putString("musicimage", index);

                FragmentManager fm = getSupportFragmentManager();
                MusicDetailsFragment fragment = MusicDetailsFragment.newInstance("Some Title");
                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");
            }
        };



        recyclerView.setLayoutManager(
                new LinearLayoutManager(VideoEditionActivity.this, LinearLayoutManager.HORIZONTAL, false));

        btnVolue = (ImageView) findViewById(R.id.btnVolue);
        btnUpload = (ImageView) findViewById(R.id.btnUpload);
        btnMusicPlay = (ImageView) findViewById(R.id.btnMusicPlay);
        btnMusic = (ImageView) findViewById(R.id.btnMusic);

        Log.d("testpageload", "pagelaod");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("lifecycle", "onResume invoked");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        videoView.pause();
//           adapter.release();

        Log.d("testondestroy", "onResume invoked");


    }




    private void downloadvideo(String videoUrl) {


        Log.d("testdemovideouri", "is : " + videoUrl);

        String files = videoUrl.substring(34);
        //   String  fileName = "amitkumar"+".mp4";
        Log.d("testvideofilename", "is : " + files);
        String ff = files.substring(0, files.length() - 18);
        Log.d("testvideofilenamefff", "is : " + ff);
        fileName = ff + ".mp4";
        fileNameUri = videoUrl;
        Log.d("testvideo final", "is : " + fileName);
        isvideodownloaded = "/storage/emulated/0/StudioApp/" + fileName + ".mp4";
        videoOutPath = Environment.getExternalStorageDirectory().getPath() + "/StudioApp/" + fileName;
        imagePath = "https://codingsansar.com/wp-content/uploads/2022/10/watermark.png";
        String cmdd[] = {"-i", videoUrl, "-i", imagePath, "-filter_complex", "overlay=5:main_h-overlay_h", "-codec:a", "copy", videoOutPath};


//        MergeVideo(cmdd);


//        File file = new File(isvideodownloaded);
        if (uricheck.length() == 0 && testing != 2 && !uridata.equals("")) {
            Log.d("testvideok", "already downloaded : " + videoOutPath);
        } else {

        }

    }

    private void MergeVideo(String[] co) {
        FFmpeg.executeAsync(co, new ExecuteCallback() {
            @Override
            public void apply(long executionId, int returnCode) {
                Log.d("hello", "return  " + returnCode);
                Log.d("hello", "executionID  " + executionId);
                Log.d("hello", "FFMPEG  " + new FFmpegExecution(executionId, co));

            }
        });
    }


    private void setadapter(List<Attributedata> attributedata) {
        musicTileAdapter = new MusicTileAdapter(VideoEditionActivity.this, attributedata);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoEditionActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(musicTileAdapter);


    }

    // Go to Export Section

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpload: {
                adapter.release();
                Log.d("testttt", "dds");
                Bundle bundle = new Bundle();
                if (uricheck.length() > 5) {
//                    String   savedvideopath = "/storage/emulated/0/Download/"+fileName;
                    String savedvideopath = fileNameUri;
                    bundle.putString("edttext", savedvideopath);
                    Log.d("testsavediofromdemo", "path is : " + savedvideopath);

                } else if (uricheck.length() == 0 && testing != 2 && !uridata.equals("")) {
//                    String   savedvideopath = "/storage/emulated/0/Download/"+fileName;
                    String savedvideopath = fileNameUri;
                    bundle.putString("edttext", savedvideopath);
                    Log.d("testsavediofromdemosearch", "path is : " + savedvideopath);
                } else {
                    bundle.putString("edttext", filepathvideo);
                    Log.d("testsavecamera", "path is : " + filepathvideo);

                }

                FragmentManager fm = getSupportFragmentManager();
                ExportFragment fragment = ExportFragment.newInstance("Some Title");
                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");
                break;

            }
            case R.id.btnVolue: {
                // Open Volume Fragment
                FragmentManager fm = getSupportFragmentManager();
                VolumeControllerFragment fragmentLogin = VolumeControllerFragment.newInstance("Some Title");
                fragmentLogin.show(fm, "fragment_edit_name");
                break;
            }
            case R.id.btnMusic: {
             // Refresh Music List Api
                try {
                    makeMusicsRequest(jobj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

            }
            case R.id.btnMusicPlay: {
                // Go to Search Filters
                FragmentManager fm = getSupportFragmentManager();
                FilterViewFragment fragment = FilterViewFragment.newInstance("Some Title");
                fragment.show(fm, "fragment_edit_name");
                break;
            }
        }
    }

    private void showEditDialog(String type) {
        FragmentManager fm = getSupportFragmentManager();
        FilterFragment fragmentLogin = FilterFragment.newInstance("Some Title");
        fragmentLogin.show(fm, "fragment_edit_name");
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    private void ValidateUploadedVideo(String fileid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = ApplicationConstant.INSTANCE.validatevideouri+fileid+"/validate";
        Log.d("validatevideorequrl", "" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject attribute = jsonObject.getJSONObject("data").getJSONObject("attributes");
                            Log.d("validatevideoattributes", "" + attribute);
                            String status = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("status");
                             duration = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("duration");

                            Double durationd = Double.parseDouble(duration) *1000;
                            BigDecimal bigDecimal = new BigDecimal(String.valueOf(durationd));
                             intValue = bigDecimal.intValue();

                            Log.d("validatevideostatus",""+ status);
                            Log.d("validatevideoduration",""+ duration);
                            Log.d("validatevideoactualduration",""+ intValue);
                            AnalyseUploadedVideo(fileid);

                        } catch (JSONException e) {
                            Log.e("MYAPP", "unexpected JSON exception", e);
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(VideoEditionActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String  usertoken = shrd.getString("usertoken", "");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + usertoken);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }

    public void  convertFileToBytes(File file,String url)
    {
        int size = (int) file.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
            buf.read(bytes, 0, bytes.length);
            buf.close();
            Log.d("videobyte","videobyte" + bytes);
            uploadImageToServer(bytes,url);
        } catch (FileNotFoundException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();

        }

    }



    public  void uploadImageToServer(byte[] bytes, String url) {
        String uploadImageURL = url;
        Log.d("videotuploadurl",":" +url);
        final StringRequest stringRequest = new StringRequest(Request.Method.PUT,uploadImageURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("s3 response", response);
                        Log.d("videotupload","Video Uploaded to server");
                       //  ValidateUploadedVideo(fileid);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error response", error.toString());
                        Log.d("videotuploadnot","Video Not Uploaded to server");
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<String, String>();
            //    headerMap.put("header1", "header value");
                headerMap.put("Content-Type", "bytes");
                return headerMap;
            }


            @Override
            public byte[] getBody() throws AuthFailureError {
                return bytes;
            }

        };
        {
            int socketTimeout = 30000;
            RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
            stringRequest.setRetryPolicy(policy);
            RequestQueue requestQueue = Volley.newRequestQueue(VideoEditionActivity.this);
            requestQueue.getCache().clear();
            requestQueue.add(stringRequest);
        }
    }


    private void AnalyseUploadedVideo(String fileid) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = ApplicationConstant.INSTANCE.validatevideouri+fileid+"/video-analyzer";
        Log.d("analysevideorequrl", "" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject attribute = jsonObject.getJSONObject("data").getJSONObject("attributes");
                            Log.d("analysevideoattributes", "" + attribute);
                            JSONObject videofor = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONObject("videoFor");

//                            Iterator x = videofor.keys();
//                            JSONArray jsonArray = new JSONArray();
//                            while (x.hasNext()){
//                                String key =(String) x.next();
//                                jsonArray.put((videofor.get(key)));
//                            }
//                              String test =   jsonArray.get(0).toString();

                            Iterator<String> iter = videofor.keys(); //This should be the iterator you want.
                            while(iter.hasNext()){
                                 key = iter.next();
                                Log.d("analysevideokey",""+ key);
                                videoforlist.add(key);
                            }
                            Log.d("analysevideostatus",""+ videofor);
                            Log.d("analysevideolist",""+ videoforlist);



                            try {

                                makeMusicsRequest(jobj);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }



                        } catch (JSONException e) {
                            Log.e("MYAPP", "unexpected JSON exception", e);
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //   Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                Toast.makeText(VideoEditionActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String  usertoken = shrd.getString("usertoken", "");
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " + usertoken);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }

        };
        requestQueue.add(stringRequest);
    }


    public void makeMusicsRequest(JSONObject obj) throws JSONException {
        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Log.d("testresbefore", String.valueOf(obj));
        //{"data": {"type": "musics","attributes": {"genre":"Blues"}}}
        if (isConnected(VideoEditionActivity.this)) {
            JSONArray jsonArrayv = new JSONArray();
            jsonArrayv.put(key);

            JSONObject attObj = new JSONObject();
            // attObj.put("genre", "Blues");
            attObj.put("count", 7);
            attObj.put("duration", intValue);
            attObj.put("usePlaylist", true);
            attObj.put("useFastRender", true);
            attObj.put("enforceDuration", true);
            attObj.put("engine", "tuneblades");
            attObj.put("videoFor", jsonArrayv);
            JSONObject dataObj = new JSONObject();
            dataObj.put("type", "search");
            dataObj.put("attributes", attObj);
            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);
            Log.d("testresbefore", String.valueOf(jobj));

              Log.d("musiclisturi" ,""+ApplicationConstant.INSTANCE.musiclistUri);
            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstant.INSTANCE.musiclistUri, jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    playandpause.setVisibility(View.VISIBLE);
                   Log.d("testrescover",""+ jsonObject);
                    if (jsonObject.isNull("data")) {
                        loader.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        btnUpload.setVisibility(View.GONE);
                        Toast.makeText(VideoEditionActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        loader.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        btnUpload.setVisibility(View.VISIBLE);
                    }

                    try {
                        JSONArray attribute = jsonObject.getJSONObject("data").getJSONArray("attributes");
                        Log.d("testrescoverattri", "" + attribute);
                        ArrayList<Attributedata> temp_lists = new ArrayList<>();
                        for (int i = 0; i < attribute.length(); i++) {
                            JSONObject data = attribute.optJSONObject(i);
                            Gson gson = new Gson();
                            String Json = gson.toJson(data);
                            String test = data.getJSONObject("metadata").getJSONObject("cover").getString("medium");
                            String hash = data.getString("finalHash");

                            Log.d("testrescover", "" + test);
                            Log.d("testhash", "" + hash);
                            list.add(new videoData(hash,
                                    test));
                        }
                        adapter = new VideoAdapter(
                                list, getApplication(), listiner, true);
                        recyclerView.setAdapter(adapter);
                        adapter.setOnlikeClick(VideoEditionActivity.this);

//                        thumbnail = cover;

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errormsg","error: " +  e.getLocalizedMessage());
                    }
                    Log.d("testrescoverd", "" + thumbnail);

                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(VideoEditionActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                    Log.d("testerror", "error :" + error.getLocalizedMessage());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String  usertoken = shrd.getString("usertoken", "");

                    Log.d("retrievetoken","token is :  " +usertoken );
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " +usertoken );
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(VideoEditionActivity.this).add(stringRequest);
        } else {
            showToast(VideoEditionActivity.this, "Please check your internet connection");
        }
    }

    public void makeMusicsSearchRequest(JSONObject obj) throws JSONException {
        loader.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        Log.d("testresbefore", String.valueOf(obj));
        //{"data": {"type": "musics","attributes": {"genre":"Blues"}}}
        if (isConnected(VideoEditionActivity.this)) {
            JSONObject attObj = new JSONObject();
            // attObj.put("genre", "Blues");
            attObj.put("count", 7);
            JSONObject dataObj = new JSONObject();
            dataObj.put("type", "search");
            dataObj.put("attributes", attObj);

            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);
            Log.d("searchmusicschema", String.valueOf(obj));


            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstant.INSTANCE.musicSearchUri, obj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    playandpause.setVisibility(View.VISIBLE);

                    Log.d("musicsearchurl",""+ ApplicationConstant.INSTANCE.musicSearchUri);
                    if (jsonObject.isNull("data")) {
                        loader.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        btnUpload.setVisibility(View.GONE);
                        Toast.makeText(VideoEditionActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {
                        loader.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        btnUpload.setVisibility(View.VISIBLE);

                    }

                    try {
                        JSONArray attribute = jsonObject.getJSONObject("data").getJSONArray("attributes");
                        Log.d("testrescoverattri", "" + attribute);
                        ArrayList<Attributedata> temp_lists = new ArrayList<>();
                        for (int i = 0; i < attribute.length(); i++) {
                            JSONObject data = attribute.optJSONObject(i);
                            Gson gson = new Gson();
                            String Json = gson.toJson(data);
                            String test = data.getJSONObject("metadata").getJSONObject("cover").getString("medium");
                            String hash = data.getString("finalHash");

                            Log.d("testrescover", "" + test);
                            Log.d("testhash", "" + hash);
                            list.add(new videoData(hash,
                                    test));

//                              String  aa =  attributedata.getMetadata().getCover().getMedium();

//                                    temp_lists.add(attribute);
                        }


                        adapter = new VideoAdapter(
                                list, getApplication(), listiner, true);
                        recyclerView.setAdapter(adapter);

//                        thumbnail = cover;

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.d("testrescoverd", "" + thumbnail);


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(VideoEditionActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                    Log.d("testerror", "error :" + error.toString());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String  usertoken = shrd.getString("usertoken", "");

                    Log.d("retrievetoken","token is :  " +usertoken );

                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " + usertoken);
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(VideoEditionActivity.this).add(stringRequest);
        } else {
            showToast(VideoEditionActivity.this, "Please check your internet connection");
        }
    }



    public void uploadVideoRequest() throws JSONException {
//        loader.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        //{"data": {"type": "musics","attributes": {"genre":"Blues"}}}
        if (isConnected(VideoEditionActivity.this)) {

            JSONObject metaObj = new JSONObject();
            metaObj.put("name","anyvalue");
            JSONObject attObj = new JSONObject();
            // attObj.put("genre", "Blues");
            attObj.put("metadata", metaObj);
            JSONObject dataObj = new JSONObject();
            dataObj.put("type", "content");
            dataObj.put("attributes", attObj);
            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);
            Log.d("uploadvideoreqschema", String.valueOf(jobj));
            Log.d("uploadvideorequesturi" ,""+ApplicationConstant.INSTANCE.uploadvideorequest);
            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstant.INSTANCE.uploadvideorequest, jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.d("uploadvideorequestjson",""+ jsonObject);

                    try {
                        JSONObject attribute = jsonObject.getJSONObject("data").getJSONObject("attributes");
                        Log.d("uploadvideoattributes", "" + attribute);
                        String fileid = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("fileid");
                        String url = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("url");

                        Log.d("uploadvideofileid",""+ fileid);
                        Log.d("uploadvideourl",""+ url);
                        convertFileToBytes(new File(filepathvideo),url);
                     //   ValidateUploadedVideo(fileid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errormsg","error: " +  e.getLocalizedMessage());
                    }


                    Log.d("testrescoverd", "" + thumbnail);


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(VideoEditionActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("testerror", "error :" + error.getLocalizedMessage());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String  usertoken = shrd.getString("usertoken", "");

                    Log.d("retrievetoken","token is :  " +usertoken );
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " +usertoken );
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(VideoEditionActivity.this).add(stringRequest);
        } else {
            showToast(VideoEditionActivity.this, "Please check your internet connection");
        }
    }

    public void demovideoRequest(String url) throws JSONException {
//        loader.setVisibility(View.VISIBLE);
//        recyclerView.setVisibility(View.GONE);

        //{"data": {"type": "musics","attributes": {"genre":"Blues"}}}
        if (isConnected(VideoEditionActivity.this)) {



            JSONObject attObj = new JSONObject();
            // attObj.put("genre", "Blues");
            attObj.put("name", "anyvalue");
            attObj.put("externalURL",url);
            attObj.put("duration",0);

            JSONObject dataObj = new JSONObject();
            dataObj.put("type", "content");
            dataObj.put("attributes", attObj);

            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);
            Log.d("demovideoreqschema", String.valueOf(jobj));

            Log.d("demovideorequesturi" ,""+ApplicationConstant.INSTANCE.uploadvideorequest);
            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, ApplicationConstant.INSTANCE.uploadvideorequest, jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.d("demovideorequestjson",""+ jsonObject);

                    try {
                        JSONObject attribute = jsonObject.getJSONObject("data").getJSONObject("attributes");
                        Log.d("demovideoattributes", "" + attribute);
                        String fileid = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("fileid");
                        String url = jsonObject.getJSONObject("data").getJSONObject("attributes").getString("externalURL");

                        Log.d("demovideofileid",""+ fileid);
                        Log.d("demovideourl",""+ url);
                        ValidateUploadedVideo(fileid);

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("errormsg","error: " +  e.getLocalizedMessage());
                    }


                    Log.d("testrescoverd", "" + thumbnail);


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //   Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(VideoEditionActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                    Log.d("testerror", "error :" + error.getLocalizedMessage());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences shrd = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String  usertoken = shrd.getString("usertoken", "");

                    Log.d("retrievetoken","token is :  " +usertoken );
                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " +usertoken );
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(VideoEditionActivity.this).add(stringRequest);
        } else {
            showToast(VideoEditionActivity.this, "Please check your internet connection");
        }
    }

    @Override
    public void onLikeclick(View v, int position, int islike) {

        Log.d("islike", "liked");
        likegif.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                likegif.setVisibility(View.GONE);
            }
        }, 3000);


    }


}