package com.studio.matchtune.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.VideoEditionActivity;


public class RestoreSubscriptionFragment extends DialogFragment {

      Context context;
    ImageView arrowcirclerestore;
    public static RestoreSubscriptionFragment newInstance(String title) {
        RestoreSubscriptionFragment frag = new RestoreSubscriptionFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restore_subscription, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrowcirclerestore = (ImageView) view.findViewById(R.id.arrowcirclerestore);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view = inflater.inflate(R.layout.restore_popup_layout, null);


                TextView okButton = (TextView) view.findViewById(R.id.okButton);
                TextView   msg = (TextView) view.findViewById(R.id.msg);
                TextView   resposestatus = (TextView) view.findViewById(R.id.resposestatus);

                final Dialog dialog = new Dialog(getActivity());

                dialog.setCancelable(false);
                dialog.setContentView(view);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

//                msg.setText(""+"message");
                resposestatus.setText("                   An Error occured                    ");


                resposestatus.setTextColor(getActivity().getResources().getColor(R.color.gray_001));



                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();



                    }
                });



                dialog.show();




            }
        }, 5000);





        arrowcirclerestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               dismiss();






            }
        });

    }
}