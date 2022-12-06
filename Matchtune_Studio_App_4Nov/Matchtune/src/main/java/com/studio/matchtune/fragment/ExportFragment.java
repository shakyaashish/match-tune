package com.studio.matchtune.fragment;

import static android.content.Context.MODE_PRIVATE;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.content.ContextWrapper.*;
import android.widget.TextView;


import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.anjlab.android.iab.v3.BillingProcessor;
import com.anjlab.android.iab.v3.TransactionDetails;
import com.arthenica.mobileffmpeg.ExecuteCallback;
import com.arthenica.mobileffmpeg.FFmpeg;
import com.arthenica.mobileffmpeg.FFmpegExecution;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.utility.ApplicationConstant;

import org.jetbrains.annotations.Nullable;

public class ExportFragment extends DialogFragment  implements BillingProcessor.IBillingHandler  {
    Context context;
    private ImageView arrowcircle;
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    private static final int MY_CAMERA_REQUEST_CODE1 = 101;
    AppCompatButton export,subscribepage;
    TextView  restoresubs;
     String filesaveadd ,strtext,finaluri,latestmusic,realurl,musicid,shareothers;
     String imagePath,videoOutPath,videofinalPath;
     CardView item_imagecard;
    private BillingProcessor bp;
    private TransactionDetails purchaseTransactionDetails = null;
    private TransactionDetails purchaseTransactionDetailsPersonal = null;
    private TransactionDetails purchaseTransactionDetailsPremium = null;


    public static ExportFragment newInstance(String title) {
        ExportFragment frag = new ExportFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         strtext = getArguments().getString("edttext");
        Log.d("testfragment","dddd   :" + strtext);
//         finaluri = strtext.substring(5);
        finaluri = strtext;
        Log.d("testfragmentfinal","is   :" + finaluri);
        return inflater.inflate(R.layout.fragment_export, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrowcircle = (ImageView) view.findViewById(R.id.arrowcircle);
        export = (AppCompatButton) view.findViewById(R.id.submitj);
        subscribepage = (AppCompatButton) view.findViewById(R.id.subscribepage);
        restoresubs = (TextView) view.findViewById(R.id.restoresubs);
        item_imagecard = (CardView) view.findViewById(R.id.item_imagecard);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        item_imagecard.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        bp = new BillingProcessor(getActivity(), getResources().getString(R.string.play_console_license), this);
        bp.initialize();

        SharedPreferences shrd =getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
          latestmusic =   shrd.getString("latestmusic","");
        musicid =   shrd.getString("musicid","");
        Log.d("testlatestmusic",latestmusic);
        realurl = "/storage/emulated/0/StudioApp/"+latestmusic;
        Log.d("testlatestmusicreal",realurl);
        imagePath= "https://codingsansar.com/wp-content/uploads/2022/10/watermark.png";

        videoOutPath =Environment.getExternalStorageDirectory().getPath()
                + "/StudioApp/"+musicid+".mp4";
        String cmdd[]={ "-i" ,finaluri, "-i",imagePath, "-filter_complex","overlay=5:main_h-overlay_h", "-codec:a","copy",videoOutPath};




        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });






        restoresubs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();

                FragmentManager fm =getActivity().getSupportFragmentManager();
                RestoreSubscriptionFragment fragment = RestoreSubscriptionFragment.newInstance("Some Title");
//                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");
            }
        });
        export.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("testddcheckclicked"," clicked");
                SharedPreferences shrd = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                  filesaveadd =   shrd.getString("filesave","");

                if (checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MY_CAMERA_REQUEST_CODE);
                }

                if (checkSelfPermission(getContext(),Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            MY_CAMERA_REQUEST_CODE1);
                }

//                Merge();
//                MergeVideo(cmdd);
               dismiss();

                   Bundle bundle=new Bundle();
                bundle.putString("shareothers",shareothers);
                bundle.putString("finaluri",finaluri);
                Log.d("hellocheck",""+ shareothers);
                FragmentManager fm =getActivity().getSupportFragmentManager();
                ExportWorking fragment = ExportWorking.newInstance("Some Title");
                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");

            }
        });


        subscribepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.d("testsharefromfrom11",""+ shareothers);
                Bundle bundle=new Bundle();
                bundle.putString("shareothers",shareothers);
                FragmentManager fm =getActivity().getSupportFragmentManager();
                SubscriptionFragment fragment = SubscriptionFragment.newInstance("Some Title");
                fragment.setArguments(bundle);
                fragment.show(fm, "fragment_edit_name");
            }
        });


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

    private boolean hasSubscription() {
        if (purchaseTransactionDetails != null) {
            return purchaseTransactionDetails.purchaseInfo != null;
        }
        return false;
    }

    private boolean hasSubscriptionpersonal() {
        if (purchaseTransactionDetailsPersonal != null) {
            return purchaseTransactionDetailsPersonal.purchaseInfo != null;
        }
        return false;
    }

    private boolean hasSubscriptionpremium() {
        if (purchaseTransactionDetailsPremium != null) {
            return purchaseTransactionDetailsPremium.purchaseInfo != null;
        }
        return false;
    }
    @Override
    public void onProductPurchased(String productId, TransactionDetails details) {

    }

    @Override
    public void onPurchaseHistoryRestored() {

    }

    @Override
    public void onBillingError(int errorCode, Throwable error) {

    }

    @Override
    public void onBillingInitialized() {

        String personal = getResources().getString(R.string.personal);
        String premium = getResources().getString(R.string.premium);
        String testing = getResources().getString(R.string.testing);

        purchaseTransactionDetails = bp.getSubscriptionTransactionDetails(testing);
        purchaseTransactionDetailsPersonal = bp.getSubscriptionTransactionDetails(personal);
        purchaseTransactionDetailsPremium = bp.getSubscriptionTransactionDetails(premium);
        // testing checking premium subscription
        bp.loadOwnedPurchasesFromGoogle();

        if(hasSubscription()==true){
            Log.d("testddcheck", ": " +hasSubscription() );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issub","subscriptiondone");
            editor.apply();

        }else{
            Log.d("testddcheckfalse", ": " +hasSubscription()  );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issub","");
            editor.apply();
        }

        // checking premium subscription

        if(hasSubscriptionpremium()==true){
            Log.d("testddcheckpre", ": " +hasSubscriptionpremium() );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issubpre","subscriptionpremium");
            editor.apply();

        }else{
            Log.d("testddcheckprefalse", ": " +hasSubscriptionpremium()  );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issubpre","");
            editor.apply();
        }

        // checking personal subscription

        if(hasSubscriptionpersonal()==true){
            Log.d("testddcheckper", ": " +hasSubscriptionpersonal() );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issubpersonal","subscriptionpersonal");
            editor.apply();

        }else{
            Log.d("testddcheckperfalse", ": " +hasSubscriptionpersonal()  );
            SharedPreferences.Editor editor = getContext().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE).edit();
            editor.putString("issubpersonal","");
            editor.apply();
        }


    }
}