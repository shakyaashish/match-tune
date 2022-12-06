package com.studio.matchtune.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.matchtune.activity.MainActivity;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.utility.ApplicationConstant;


public class fragment_logout extends DialogFragment {

     TextView Email;
     Context context;
     String userEmail;
    AppCompatButton logout;
     ImageView arrowcirclelogout;

    public static fragment_logout newInstance(String title) {
        fragment_logout frag = new fragment_logout();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_logout, container);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Email = (TextView) view.findViewById(R.id.txtUserEmail);
        logout = (AppCompatButton) view.findViewById(R.id.logout);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrowcirclelogout = (ImageView) view.findViewById(R.id.arrowcirclelogout);

        SharedPreferences shrd = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        userEmail =   shrd.getString("useremail","");
        Log.d("testprintmail",userEmail);
        Email.setText(userEmail);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor = prefs.edit();
//                prefsEditor.clear();
                prefsEditor.remove("usertoken");
                prefsEditor.remove("useremail");
                prefsEditor.apply();

                Intent intent =new Intent(getActivity(), MainActivity.class);
                startActivity(intent);


            }
        });

        arrowcirclelogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


    }
}