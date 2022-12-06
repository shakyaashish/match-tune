package com.studio.matchtune.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studio.matchtune.activity.R;

import java.io.File;


public class ResetAppStorage extends DialogFragment {

    private ImageView arrowcircle;
    AppCompatCheckBox usefastmusic,adaptmusic,showcover;
    AppCompatCheckBox downloadallmusic;
    AppCompatButton resetappstorage;
    public static ResetAppStorage newInstance(String title) {
        ResetAppStorage frag = new ResetAppStorage();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reset_app_storage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        downloadallmusic = (AppCompatCheckBox) view.findViewById(R.id.downloadallmusic);
        usefastmusic = (AppCompatCheckBox) view.findViewById(R.id.usefastmusic);
        adaptmusic = (AppCompatCheckBox) view.findViewById(R.id.adaptmusic);
        showcover = (AppCompatCheckBox) view.findViewById(R.id.showcover);
        arrowcircle = (ImageView) view.findViewById(R.id.arrowcirclereset);
        resetappstorage = (AppCompatButton) view.findViewById(R.id.resetappstorage);


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        resetappstorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                File myDirectory = new File(Environment.getExternalStorageDirectory(), "StudioApps");
                if(!myDirectory.exists()) {
                    myDirectory.mkdirs();

                    Log.d("checkdir","directory created");
                }else{
                      myDirectory.delete();

                    Log.d("checkdir","directory  Already created" + myDirectory.getAbsolutePath());
                }


            }
        });

        downloadallmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (downloadallmusic.isActivated() == true) {
                    downloadallmusic.setActivated(false);
                    downloadallmusic.setChecked(false);
                    downloadallmusic.setSelected(false);
                   // finder = "";
                    Log.d("remembercheck","rem");
                } else {
                    downloadallmusic.setChecked(true);
                    downloadallmusic.setSelected(true);
                    downloadallmusic.setActivated(true);
                   // finder = "rememberme";

                  //  Log.d("remembercheck","rem not " + finder);
                }


            }
        });

        usefastmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (usefastmusic.isActivated() == true) {
                    usefastmusic.setActivated(false);
                    usefastmusic.setChecked(false);
                    usefastmusic.setSelected(false);
                    // finder = "";
                    Log.d("remembercheck","rem");
                } else {
                    usefastmusic.setChecked(true);
                    usefastmusic.setSelected(true);
                    usefastmusic.setActivated(true);
                    // finder = "rememberme";

                    //  Log.d("remembercheck","rem not " + finder);
                }


            }
        });



        adaptmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (adaptmusic.isActivated() == true) {
                    adaptmusic.setActivated(false);
                    adaptmusic.setChecked(false);
                    adaptmusic.setSelected(false);
                    // finder = "";
                    Log.d("remembercheck","rem");
                } else {
                    adaptmusic.setChecked(true);
                    adaptmusic.setSelected(true);
                    adaptmusic.setActivated(true);
                    // finder = "rememberme";

                    //  Log.d("remembercheck","rem not " + finder);
                }


            }
        });



        showcover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showcover.isActivated() == true) {
                    showcover.setActivated(false);
                    showcover.setChecked(false);
                    showcover.setSelected(false);
                    // finder = "";
                    Log.d("remembercheck","rem");
                } else {
                    showcover.setChecked(true);
                    showcover.setSelected(true);
                    showcover.setActivated(true);
                    // finder = "rememberme";

                    //  Log.d("remembercheck","rem not " + finder);
                }


            }
        });



    }
}