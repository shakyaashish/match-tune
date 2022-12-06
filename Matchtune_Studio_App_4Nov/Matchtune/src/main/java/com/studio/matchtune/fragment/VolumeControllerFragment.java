package com.studio.matchtune.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;
import com.studio.matchtune.activity.R;

import org.w3c.dom.Text;

import java.math.BigDecimal;

public class VolumeControllerFragment extends DialogFragment {
    private ImageView arrowcircle;
    Slider volumeslider;
    TextView soundtrack,sound2,soundbar,volumebar,txtStudio,btnadvance,btnstartrecord;
    LinearLayout quickmode,startrecording,advancemode;
    AudioManager  audioManager;
    Context context;
    SeekBar slider;
    ImageView deleteicon;
    public static VolumeControllerFragment newInstance(String title) {
        VolumeControllerFragment frag = new VolumeControllerFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volume_controller, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        arrowcircle = (ImageView) view.findViewById(R.id.arrowcircle);
       slider = (SeekBar) view.findViewById(R.id.slider);
       volumeslider = (Slider) view.findViewById(R.id.slider2);
       soundtrack = (TextView) view.findViewById(R.id.sound);
        sound2 = (TextView) view.findViewById(R.id.sound2);
        soundbar = (TextView) view.findViewById(R.id.soundbar);
        volumebar = (TextView) view.findViewById(R.id.volumebar);
        txtStudio = (TextView) view.findViewById(R.id.btnadvance);
        deleteicon = (ImageView) view.findViewById(R.id.deleteicon);
        TextView tv = (TextView) view.findViewById(R.id.soundbar);
        TextView vb = (TextView) view.findViewById(R.id.volumebar);

        quickmode = (LinearLayout) view.findViewById(R.id.quickmode);
        startrecording = (LinearLayout) view.findViewById(R.id.startrecording);
        advancemode = (LinearLayout) view.findViewById(R.id.advancemode);
        btnadvance = (TextView) view.findViewById(R.id.btnadvance);
        btnstartrecord = (TextView) view.findViewById(R.id.btnstartrecord);

        // Audio manager

        audioManager = (AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVol =audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol  =audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

       // SeekBar seekVol = view.findViewById(R.id.slider);
        slider.setMax(maxVol);
//        slider.setProgress(curVol);
        slider.setProgress(4);
        slider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                String string_temps = new Double(slider.getProgress()).toString();


                Log.d("testslider",""+ string_temps);

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                String str = String.valueOf(slider.getProgress());
                double doubleNumber = Double.parseDouble(str);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(doubleNumber));
                int intValue = bigDecimal.intValue();
                BigDecimal  bigDecimal1= BigDecimal.valueOf(6.67*intValue);
                int intValue1 = bigDecimal1.intValue();

                int setValue = 2*intValue1;

                soundtrack.setText(intValue1+"%");
                Log.d("testsliderstop",""+ slider.getProgress());
                LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(1000,setValue);
                vb.setLayoutParams(Params1);

            }
        });



        deleteicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                slider.setProgress(0);
                Log.d("deleteclick","dd");
                soundtrack.setText("0"+"%");
                LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(1000,0);
                vb.setLayoutParams(Params1);
                volumeslider.setValue(0);
                sound2.setText("0"+"%");
                LinearLayout.LayoutParams Params3 = new LinearLayout.LayoutParams(1000,0);
                tv.setLayoutParams(Params3);

            }
        });


        quickmode.setVisibility(View.VISIBLE);
        startrecording.setVisibility(View.GONE);
        advancemode.setVisibility(View.GONE);

        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
          getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        txtStudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(btnadvance.getText().equals("ADVANCED MODE"))
                {
                    quickmode.setVisibility(View.GONE);
                    startrecording.setVisibility(View.VISIBLE);
                    advancemode.setVisibility(View.VISIBLE);
                    btnadvance.setText("QUICK MODE");
                } else{
                    quickmode.setVisibility(View.VISIBLE);
                    startrecording.setVisibility(View.GONE);
                    advancemode.setVisibility(View.GONE);
                    btnadvance.setText("ADVANCED MODE");
                }


            }
        });


        btnstartrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnstartrecord.getText().equals("START RECORDING")){
                    btnstartrecord.setText("STOP RECORDING");
                    btnstartrecord.setBackgroundResource(R.drawable.btn_bg_record);
                    btnadvance.setVisibility(View.INVISIBLE);
                } else{
                    btnstartrecord.setText("START RECORDING");
                    btnstartrecord.setBackgroundResource(R.drawable.btn_bg);
                    btnadvance.setVisibility(View.VISIBLE);
                }

            }
        });
//        slider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
//            @Override
//            public void onStartTrackingTouch(@NonNull Slider slider) {
//
//                String string_temps = new Double(slider.getValue()).toString();
//
//                Log.d("testslider",""+ string_temps);
//            }
//
//            @Override
//            public void onStopTrackingTouch(@NonNull Slider slider) {
//                String str = String.valueOf(slider.getValue());
//                double doubleNumber = Double.parseDouble(str);
//                BigDecimal bigDecimal = new BigDecimal(String.valueOf(doubleNumber));
//                int intValue = bigDecimal.intValue();
//                int setValue =2*intValue;
//
//                soundtrack.setText(intValue+"%");
//                Log.d("testsliderstop",""+ slider.getValue());
//                LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(1000,setValue);
//                vb.setLayoutParams(Params1);
//
//
//
//            }
//        });

        volumeslider.addOnSliderTouchListener(new Slider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull Slider slider) {

                String string_temps = new Double(slider.getValue()).toString();

                Log.d("testslider",""+ string_temps);
            }

            @Override
            public void onStopTrackingTouch(@NonNull Slider slider) {
                String str = String.valueOf(slider.getValue());
                double doubleNumber = Double.parseDouble(str);
                BigDecimal bigDecimal = new BigDecimal(String.valueOf(doubleNumber));
                int intValue = bigDecimal.intValue();
                int setValue =2*intValue;

                sound2.setText(intValue+"%");
                Log.d("testsliderstop",""+ slider.getValue());
                LinearLayout.LayoutParams Params1 = new LinearLayout.LayoutParams(1000,setValue);
                tv.setLayoutParams(Params1);


            }
        });
    }
}