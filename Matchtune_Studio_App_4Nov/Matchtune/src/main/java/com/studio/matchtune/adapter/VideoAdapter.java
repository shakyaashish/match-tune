package com.studio.matchtune.adapter;
import static android.content.Context.DOWNLOAD_SERVICE;
import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import static com.studio.matchtune.utility.Common.isConnected;
import static com.studio.matchtune.utility.Common.showToast;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFmpegExecution;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.squareup.picasso.Picasso;
import com.studio.matchtune.ResponceModel.Attributedata;
import com.studio.matchtune.activity.ClickListiner;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.VideoEditionActivity;
import com.studio.matchtune.model.videoData;
import com.studio.matchtune.holders.videoViewHolder;
import com.studio.matchtune.utility.ApplicationConstant;

import com.studio.matchtune.utility.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VideoAdapter extends RecyclerView.Adapter<videoViewHolder> {

    List<videoData> list = Collections.emptyList();
    private static final long DOWNLOAD_LIMIT = 1024 * 1024; // you can change this
    Context context;
    ClickListiner listiner;
    Boolean isEditVideo = false;
    utility util = new utility();
    String musicid;
    String audiomusic,fileName,ifexist;
    DownloadManager manager;
    int islike=0,downloadcompleted=0;
    private int row_index;
    DownloadManager dm;
    Activity activity;
    int ismediadownload =1;
    private Fragment mFragment;
   String   ismusicdownloaded ="/storage/emulated/0/StudioApp/ddddd+.m4a";
    private boolean mAreCheckboxesVisible = true;

   public   MediaPlayer player = new MediaPlayer() ;

    OnclickLike mlikeclick;
    public interface OnclickLike {
        void   onLikeclick(View v, int position,int islike);
    }

    public void setOnlikeClick(final OnclickLike mlikeclick) {
        this.mlikeclick = mlikeclick;

    }



    public VideoAdapter(List<videoData> list,
                        Context context, ClickListiner listiner, Boolean isEditVideo) {
        this.list = list;
        this.context = context;
        this.listiner = listiner;
        this.isEditVideo = isEditVideo;

    }



    public  void release(){
        player.reset();
//        player.release();
    }


    public  void playerplay(){

         String play = player.toString();
        Log.d("playerstatus","playerplay : " +play);

        Log.d("playerstatus","" +player);
        if(!player.isPlaying()){
         player.start();
            Log.d("playerstatus","Player is  Start");
        }else{
            Log.d("playerstatus","Player is  playing");
        }


    }

    public  void playerpause(){

        Log.d("playerstatus","player is not null" );
        Log.d("playerstatus","" +player);
        if(player.isPlaying()){
        player.pause();
            Log.d("playerstatus","Player Paused");
        }else{
            Log.d("playerstatus","Player is not playing");
        }

    }


    @Override
    public videoViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);


        // Inflate the layout

        View photoView = inflater.inflate(isEditVideo == true ? R.layout.video_list_item : R.drawable.musicloader,
                parent, false);

        videoViewHolder viewHolder = new videoViewHolder(photoView);
        return viewHolder;
    }

    public VideoAdapter(Fragment fragment) {
        mFragment = fragment;
    }

    @Override
    public void
    onBindViewHolder(final videoViewHolder viewHolder,final int position) {

        final int index = viewHolder.getAdapterPosition();
        viewHolder.txtTittle.setBackgroundColor(util.getRandomColor());
        String ss = list.get(position).title;

       Log.d("testhashadapter", " " + ss);
        Picasso.with(context)
                .load(list.get(position).image)
                .into(viewHolder.thumbnail);

        if (row_index == position) {
            Log.d("testrowindex", "position is:  "+ position );
            mAreCheckboxesVisible = true;
            viewHolder.mainlayout.setPadding(5,5,5,5);
            viewHolder.mainlayout.setBackground(ContextCompat.getDrawable(context,R.drawable.music_bg));

            viewHolder.thumbnail.setColorFilter(context.getResources().getColor(R.color.bluess));
            viewHolder.txtTittle.setText("CURRENT");
            viewHolder.musicloder.setVisibility(View.VISIBLE);
            viewHolder.horizontalloder.setVisibility(View.VISIBLE);
        //    viewHolder.musicloder.setVisibility(mAreCheckboxesVisible ? View.VISIBLE : View.GONE);

            player.reset();






            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.musicloder.setVisibility(View.GONE);
                }
            }, 15000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewHolder.horizontalloder.setVisibility(View.GONE);
                }
            }, 1500);


        } else {
//            Log.d("testrowindex", "position else ");
            viewHolder.mainlayout.setPadding(5,5,5,5);
            viewHolder.mainlayout.setBackground(ContextCompat.getDrawable(context,R.drawable.no_bg));
            viewHolder.txtTittle.setText("");
            viewHolder.musicloder.setVisibility(View.GONE);
            viewHolder.horizontalloder.setVisibility(View.GONE);
//            viewHolder.mainlayout1.setPadding(5,5,5,5);
            viewHolder.thumbnail.setColorFilter(context.getResources().getColor(R.color.tranpacolor));


        }

        viewHolder.imageFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String   finalhash = list.get(position).title;
                GetMusicRating(finalhash,viewHolder);
              Log.d("likefinalhash","final hash : "+ finalhash);

            }
        });


        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicid = list.get(position).title;
                Log.d("testmusicidthumb", "id : " + musicid);
                Log.d("testcurrentposition" ,"position is : " + list.get(position));
                row_index = position;

                player.reset();
               DownloadMusicReq(musicid,viewHolder);
                viewHolder.musicloder.setVisibility(View.GONE);
                viewHolder.horizontalloder.setVisibility(View.GONE);
               notifyDataSetChanged();
              Log.d("testingdownload","is done:");



//                ((VideoEditionActivity)context).ItemClick(position);


            }
        });

        viewHolder.thumbnail.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d("testcoverimage","image url :" + list.get(position).image +", "+list.get(position).title);
                  String image= list.get(position).image;
                listiner.click(image);




                return true;
            }
        });



    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    private void GetMusicRating(String finalhash,videoViewHolder viewHolder) {

        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        String url = ApplicationConstant.INSTANCE.musiclistUridownload + finalhash+"/rating";

        Log.d("testurlhash", "url is : " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JSONArray jsonArray = new JSONArray();


                        try {
                            jsonArray=jsonObject.getJSONObject("data").getJSONArray("attributes");
                            jsonArray.length();
                           if(jsonArray.length()>0){

                               audiomusic = jsonObject.getJSONObject("data").getJSONArray("attributes").getJSONObject(0).getJSONObject("data").getString("rating");
                               viewHolder.imageFav.setImageResource(R.drawable.heart_fill);
                           }else {
                              // Toast.makeText(context, "testing", Toast.LENGTH_SHORT).show();
                               LikemusicRequest(finalhash,viewHolder);
                           }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("getlikemusicarraylengh", "l : " + jsonArray.length());
                        Log.d("getlikemusic", "music is : " + audiomusic);


                            Log.d("getlikemusicfilename"," s :" + new Gson().toJson(response));







                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences shrd =context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String  usertoken = shrd.getString("usertoken", "");

                Log.d("retrievetoken","token is :  " +usertoken );
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +usertoken);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(stringRequest);


    }

    public void LikemusicRequest(String finalhash,videoViewHolder viewHolder) throws JSONException {

        String url = ApplicationConstant.INSTANCE.musiclistUridownload + finalhash+"/rating";
     //   Log.d("testresbefore", String.valueOf(obj));
        //{"data": {"type": "musics","attributes": {"genre":"Blues"}}}
        if (isConnected(context)) {
            JSONObject attObj = new JSONObject();
            // attObj.put("genre", "Blues");
            attObj.put("rating", 5);
            JSONObject dataObj = new JSONObject();
            dataObj.put("type", "rating");
            dataObj.put("attributes", attObj);

            JSONObject jobj = new JSONObject();
            jobj.put("data", dataObj);
            Log.d("likemusicschema", String.valueOf(jobj));


            final JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, url, jobj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {

                    Log.d("musicsearchurl",""+ ApplicationConstant.INSTANCE.musicSearchUri);
                    if (jsonObject.isNull("data")) {

                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
                    } else {


                    }

                    try {
                        JSONObject attribute = jsonObject.getJSONObject("data").getJSONObject("attributes");
                        Log.d("likemusicattribute", "" + attribute);

                        String rating = attribute.getString("rating");
                        Log.d("likemusicrating", ":"+ rating );
                        if(rating.equals("5")){
                            Log.d("likemusicrating555", ":"+ rating );
                            viewHolder.imageFav.setImageResource(R.drawable.heart_fill);
                        }else{
                            Log.d("likemusicratingnotliked", ":"+ rating );
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    Log.d("testrescoverd", "" );


                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //  Toast.makeText(VideoEditionActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(context, "Check your internet connection", Toast.LENGTH_LONG).show();
                    Log.d("testerror", "error :" + error.toString());

                }
            }) {

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    SharedPreferences shrd = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String  usertoken = shrd.getString("usertoken", "");

                    Log.d("retrievetoken","token is :  " +usertoken );

                    Map<String, String> headers = new HashMap<String, String>();
                    headers.put("Authorization", "Bearer " + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyIjp7ImxvZ2luIjoiOGNhY2Y0YzctNjBlNS00YjUwLWE1NzEtMjc2MWQ5MmJmNGM0QGNvbS5hbmRyb2lkLlN0dWRpb0J5TWF0Y2hUdW5lIiwiQVBQSWQiOiJjb20uYW5kcm9pZC5TdHVkaW9CeU1hdGNoVHVuZSIsImFjY291bnQiOiJjb20uYW5kcm9pZC5TdHVkaW9CeU1hdGNoVHVuZSIsImNvdW50cnkiOiJJTiIsImN1cnJlbmN5IjoidXNkIiwiaXNFdXJvcGVhbiI6ZmFsc2V9LCJpYXQiOjE2NjgwNjg4NzAsIm5iZiI6MTY2ODA2ODg3MCwiZXhwIjoxNjc2NzA4ODcwfQ.dkrSsqUlXdqZX8zXeOxRV_yC3_KT9x6lUY6OB0trmyg");
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(stringRequest);
        } else {
            showToast(context, "Please check your internet connection");
        }
    }

    private void DownloadMusicReq(String musicid,videoViewHolder viewHolder) {
        viewHolder.musicloder.setVisibility(View.GONE);
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
       String url = ApplicationConstant.INSTANCE.musiclistUridownload + musicid;

        Log.d("testurlhash", "url is : " + url);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> aList = new ArrayList<String>();
                            JSONObject jsonObject = new JSONObject(response);

                             audiomusic = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONObject("urls").getString("PROTECTED");

                            Log.d("testaudiomusic", "music is : " + audiomusic);


                            startDownload(viewHolder);

                            SharedPreferences.Editor editor = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
                            editor.putString("latestmusic",fileName);
                            editor.putString("musicid",musicid);
                            editor.apply();
                            Log.d("testmusicfilename"," s :" + fileName);

                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    Log.d("testmusicwill"," s :" + "1sec");
//                                    playmusic();
                                }
                            }, 1);



                        } catch (JSONException | IOException e) {
                            Log.e("MYAPP", "unexpected JSON exception", e);
                        }
                        if (ismediadownload==2){
                            final Handler handler = new Handler(Looper.getMainLooper());
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    //Do something after 100ms
                                    Log.d("testmusicnotdownloadwait"," s :" + "15000");
                                }
                            }, 15000);
                        }else {
                            Log.d("testmusicdownlaodedplay"," s :" + "000000");
                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context.getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                SharedPreferences shrd =context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String  usertoken = shrd.getString("usertoken", "");

                Log.d("retrievetoken","token is :  " +usertoken );
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer " +usertoken);
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        requestQueue.add(stringRequest);


    }

                  // Music Downloading Start

    private void startDownload(videoViewHolder viewHolder) throws IOException {

        player.reset();
        Log.d("testmusicname","should : " +audiomusic);
       fileName = musicid+".m4a";
         ifexist = "/storage/emulated/0/StudioApp"+musicid+".m4a";
              ismusicdownloaded ="/storage/emulated/0/StudioApp/"+musicid+".m4a";

        File file = new File(ismusicdownloaded);
        if(file.exists()){
            viewHolder.musicloder.setVisibility(View.GONE);
            Log.d("testmusick","already downloaded : " +ismusicdownloaded);
            Log.d("testingplayeralready","is : " +fileName);
            playmusic(fileName);

        }else{
            viewHolder.musicloder.setVisibility(View.GONE);
            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(audiomusic + ""));
            request.setTitle(fileName);
            request.setMimeType("application/.m4a");
            request.allowScanningByMediaScanner();
            request.setAllowedOverMetered(true);
          //  request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
          //  request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);
            request.setDestinationInExternalPublicDir("/StudioApp/", fileName);
            dm = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
            dm.enqueue(request);


            Log.d("iscompleted","dddf  :  " );


            new DownloadProgressCounter(dm.enqueue(request),viewHolder).start();
            Log.d("testmusicdownloading","should : " +fileName);
            Log.d("testmusick","not exist now downloading : " +ismusicdownloaded);
        }


//        intervalfinal(String.valueOf(file.exists()));



    }


    private void MergeVideo(String[] co){
        FFmpeg.executeAsync(co, new ExecuteCallback() {
            @Override
            public void apply( long executionId, int returnCode ) {
                Log.d("hello" , "return  " + returnCode);
                Log.d("hello" , "executionID  " + executionId);
                Log.d("hello" , "FFMPEG  " +  new FFmpegExecution(executionId,co));
                intervalfinal(String.valueOf(executionId));
            }
        });
    }


    void intervalfinal(String Tx) {

        int interval = 1000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
//               playmusic();
                Log.d("already","musicdownloadfresh");


            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }


    public class DownloadProgressCounter extends Thread {

        private final long downloadId;
        private final DownloadManager.Query query;
        private Cursor cursor;
        private int lastBytesDownloadedSoFar;
        private int totalBytes;

        Context context;
      //  videoViewHolder viewHolder;

        public DownloadProgressCounter(long downloadId,videoViewHolder viewHolder) {
            this.downloadId = downloadId;
            this.query = new DownloadManager.Query();
            query.setFilterById(this.downloadId);
           // this.viewHolder=viewHolder;
            viewHolder.musicloder.setVisibility(View.GONE);


        }

        @SuppressLint("Range")
        @Override
        public void run() {
            Log.d("testdpercent1","" + downloadId );
                try {
                    boolean downloading = true;
                    while (downloading) {

                    DownloadManager.Query q = new DownloadManager.Query();
                    q.setFilterById(downloadId);

                    Cursor cursor = dm.query(q);
                    cursor.moveToFirst();
                    @SuppressLint("Range") final int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));

                    @SuppressLint("Range") final int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));

                    Log.d("bytedownloaded", "are :" + bytes_downloaded);
                    Log.d("bytedowntotal", "are :" + bytes_total);

                    if (cursor.getInt(cursor
                            .getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL) {
                        downloading = false;
                        mAreCheckboxesVisible = false;
                        downloadcompleted=10;
                        Log.d("bytedownloadcomp", "are :" + bytes_downloaded + "," + bytes_total);
                    }
                    cursor.close();
                }

                    Log.d("testingplayer1st","is : " +fileName);
                    for (int i = 0; i < list.size(); i++) {
                       musicid =    list.get(i).title;
                        Log.d("testingplayer1st2nd","is : " +musicid);

                    }
                    playmusic(fileName);

                   //   viewHolder.musicloder.setVisibility(View.GONE);

                } catch (Exception e) {
                    return;
                }

        }

    }

      public  void playmusic(String fileNames ){
          Log.d("hellosound" , "return  " + "splay");

          try {
//            Uri uri = Uri.parse("https://www.soundhelix.com/examples/mp3/SoundHelix-Song-1.mp3");
              Uri uri = Uri.parse("/storage/emulated/0/StudioApp/"+fileNames);
              Log.d("testplaymusic" , "return  :  " + uri);
//              MediaPlayer player = new MediaPlayer();


              player.setAudioStreamType(AudioManager.STREAM_MUSIC);
              player.setDataSource(context, uri);
              player.prepare();
              player.start();
              player.setLooping(true);
              ismediadownload=3;
          } catch(Exception e) {
              System.out.println(e.toString());
              Log.d("testmusicnotavailable","ss=set==2 :");
              ismediadownload =2;
          }

      }





}
