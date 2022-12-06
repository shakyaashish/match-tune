package com.studio.matchtune.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.studio.matchtune.activity.R;


public class SubscriptionProcessFragment  extends DialogFragment {


    ImageView arrowcircleplan;

    public static SubscriptionProcessFragment newInstance(String title) {
        SubscriptionProcessFragment frag = new SubscriptionProcessFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_subscription_process, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        arrowcircleplan = (ImageView) view.findViewById(R.id.arrowcircleprosub);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        arrowcircleplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               dismiss();
            }
        }, 10000);


    }
}