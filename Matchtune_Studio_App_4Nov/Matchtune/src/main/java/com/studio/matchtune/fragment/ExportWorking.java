package com.studio.matchtune.fragment;

import static android.content.Context.MODE_PRIVATE;
import static com.arthenica.mobileffmpeg.Config.getPackageName;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFmpegExecution;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.VideoEditionActivity;
import com.studio.matchtune.utility.ApplicationConstant;

import java.io.File;


public class ExportWorking extends DialogFragment {

            ImageView arrowcircleexpo;
    BottomSheetDialog sheetDialog;
    Context  context;
      String shareothers;

    String  strtext,finaluri,latestmusic,realurl,musicid;
    String imagePath,videoOutPath,videofinalPath,useremail,issub,issubpersonal,issubpre;
    TextView  exportmsg;
    public static ExportWorking newInstance(String title) {
        ExportWorking frag = new ExportWorking();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


//        shareothers = getArguments().getString("shareothers");
        finaluri = getArguments().getString("finaluri");
        Log.d("testsharevalue",""+ shareothers);
        return inflater.inflate(R.layout.fragment_export_working, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrowcircleexpo = (ImageView)view.findViewById(R.id.arrowcircleexpo);


//        savevideo = sheetDialog.findViewById(R.id.savevideo);


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        SharedPreferences shrd =getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        latestmusic =   shrd.getString("latestmusic","");
        musicid =   shrd.getString("musicid","");
        useremail =   shrd.getString("useremail","");
        issub =   shrd.getString("issub","");
        issubpre =   shrd.getString("issubpre","");
        issubpersonal =   shrd.getString("issubpersonal","");

        Log.d("testlatestmusic",latestmusic);
        realurl = "/storage/emulated/0/StudioApp/"+latestmusic;
        Log.d("testlatestmusicreal",realurl);
        imagePath= "https://codingsansar.com/wp-content/uploads/2022/10/watermark.png";

        videoOutPath =Environment.getExternalStorageDirectory().getPath()
                + "/StudioApp/"+musicid+".mp4";



    //    textanimation();
        final Handler handler = new Handler();
        final int delay = 16160; // 1000 milliseconds == 1 second

        handler.postDelayed(new Runnable() {
            public void run() {

             //   textanimation();
                System.out.println("myHandler: here!"); // Do your work here
                handler.postDelayed(this, delay);
            }
        }, delay);






        if(issub.length()>0|| issubpre.length()>0 || issubpersonal.length()>0){
            String[] ca ={"-i", finaluri, videoOutPath };
            MergeVideo(ca);
            Log.d("waterlogin","user logined   no watermark");

        }else{
            String cmdd[]={ "-i" ,finaluri, "-i",imagePath, "-filter_complex","overlay=5:main_h-overlay_h", "-codec:a","copy",videoOutPath};
            MergeVideo(cmdd);
            Log.d("waterlogin","user no login watermarked");
        }






        arrowcircleexpo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();


            }
        });



    }











    public void shareVideoWhatsApp(String filepath) {
        Intent shareintent=new Intent("android.intent.action.SEND");
        shareintent.setType("*/*");
        shareintent.setPackage("com.whatsapp");
        shareintent.putExtra("android.intent.extra.STREAM",
                Uri.parse(filepath));
        shareintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(shareintent,"share"));

    }



    public void Merge(){

        videofinalPath =Environment.getExternalStorageDirectory().getPath()
                + "/StudioApp/"+musicid+"match"+".mp4";

        Log.d("testenvi",""+Environment.getExternalStorageDirectory().getPath() );
        Log.d("teststring",""+strtext );
        String  b="/storage/9C33-6BBD/DCIM/Camera/VID_20220921_124419.mp4";
        String  cd="storage/emulated/999/Telegram/Telegram Documents/5_4967840056824824377.mp4";
        String  a="/storage/emulated/0/document/video:920412";
        String[] c = {"-i",videoOutPath
                , "-i",realurl
                , "-c:v", "copy", "-c:a", "aac", "-map", "0:v:0", "-map", "1:a:0", "-shortest",
                videofinalPath };

        shareothers=  videofinalPath;
        Log.d("testsharefrom",""+ videofinalPath);

        MergeVideoFinal(c);
    }



    private void MergeVideoFinal(String[] co){
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

    private void MergeVideo(String[] co){
        FFmpeg.executeAsync(co, new ExecuteCallback() {
            @Override
            public void apply( long executionId, int returnCode ) {
                Log.d("hello" , "return  " + returnCode);
                Log.d("hello" , "executionID  " + executionId);
                Log.d("hello" , "FFMPEG  " +  new FFmpegExecution(executionId,co));

                interval(String.valueOf(executionId));



            }
        });
    }


    void interval(String Tx) {

        int interval = 1000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                Merge();

            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }


           // Open BottamSheet after merge to share
    void intervalfinal(String Tx) {

        int interval = 1000; // 1 Second
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                dismiss();
                sheetDialog = new BottomSheetDialog(getActivity(), R.style.BottomSheetStyle);
                sheetDialog.setContentView(R.layout.bottomsheetdialog);
                sheetDialog.setCanceledOnTouchOutside(true);
                RecyclerView recyclerView = sheetDialog.findViewById(R.id.recorderbottom);
                ImageView savevideo = sheetDialog.findViewById(R.id.savevideo);
                ImageView copyvideo = sheetDialog.findViewById(R.id.copyvideo);
                ImageView sheetcross = sheetDialog.findViewById(R.id.sheetcross);

                ImageView sharewhatsapp = sheetDialog.findViewById(R.id.sharewhatsapp);
                ImageView shareinsta = sheetDialog.findViewById(R.id.shareinsta);
                ImageView sharedrive = sheetDialog.findViewById(R.id.sharedrive);
                ImageView sharemore = sheetDialog.findViewById(R.id.sharemore);



                sharewhatsapp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareintent=new Intent("android.intent.action.SEND");
                        shareintent.setType("*/*");
                        shareintent.setPackage("com.whatsapp");
                        shareintent.putExtra("android.intent.extra.STREAM",
                                Uri.parse(shareothers));
                        shareintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sheetDialog.getContext().startActivity(Intent.createChooser(shareintent,"share"));

                    }
                });

                shareinsta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent shareintent=new Intent("android.intent.action.SEND");
                        shareintent.setType("*/*");
                        shareintent.setPackage("com.instagram.android");
                        shareintent.putExtra("android.intent.extra.STREAM",
                                Uri.parse(shareothers));
                        shareintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sheetDialog.getContext().startActivity(Intent.createChooser(shareintent,"share"));

                    }
                });

                sharedrive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent shareintent=new Intent("android.intent.action.SEND");
                        shareintent.setType("*/*");
                        shareintent.setPackage("com.google.android.apps.docs");
                        shareintent.putExtra("android.intent.extra.STREAM",
                                Uri.parse(shareothers));
                        shareintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        sheetDialog.getContext().startActivity(Intent.createChooser(shareintent,"share"));

                    }
                });

                sharemore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent shareintent=new Intent("android.intent.action.SEND");
                        shareintent.setType("video/mp4");
                        shareintent.putExtra("android.intent.extra.STREAM",
                                Uri.parse(shareothers));
                        sheetDialog.getContext().startActivity(Intent.createChooser(shareintent,"share"));

                    }
                });




                savevideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("testclick","video exported");
                        VideoEditionActivity mActivity=  new VideoEditionActivity();

                        sheetDialog.dismiss();
                    }
                });

                copyvideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("testclick","video exported");

                    }
                });

                sheetcross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sheetDialog.dismiss();
                    }
                });



                sheetDialog.show();

            }
        };
        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);
    }



}