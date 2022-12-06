package com.studio.matchtune.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.studio.matchtune.activity.R;

public class MusicDetailsFragment extends DialogFragment {

    private EditText mEditText;
    private ImageView imageClose,imgTrack,imgShare;
    String musicimage;
    public static MusicDetailsFragment newInstance(String title) {
        MusicDetailsFragment musicFragment = new MusicDetailsFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        musicFragment.setArguments(args);
        return musicFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_details, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        imageClose = (ImageView) view.findViewById(R.id.imageClose);
        imgTrack = (ImageView) view.findViewById(R.id.imgTrack);
        musicimage = getArguments().getString("musicimage");

        Picasso.with(getActivity().getApplicationContext()
                )
//                .load("https://assets.matchtune.com/covers/FMA0280.jpg")
                .load(musicimage)
                .into(imgTrack);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        imageClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}