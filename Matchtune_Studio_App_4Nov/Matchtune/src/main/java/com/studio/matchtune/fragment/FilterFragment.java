package com.studio.matchtune.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studio.matchtune.activity.ClickListiner;
import com.studio.matchtune.adapter.FilterAdapter;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.database.DBManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class FilterFragment extends DialogFragment {

    ListView filterList;
    ClickListiner listiner;
    ArrayList<String> aList;
    private ImageView arrowcircle;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    TextView txtStudio;
    ImageView  imagefront,imagefronts;
public static FilterFragment newInstance(String title) {
        FilterFragment frag = new FilterFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter, container);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        filterList = (ListView) view.findViewById(R.id.filterList);
        txtStudio = (TextView) view.findViewById(R.id.txtStudio);
        imagefront = (ImageView) view.findViewById(R.id.imagefront);
        imagefronts = (ImageView) view.findViewById(R.id.imagefronts);


         imagefront.bringToFront();
         imagefront.invalidate();
        imagefronts.bringToFront();
        imagefronts.invalidate();
        ViewCompat.setTranslationZ(imagefront, 0);
        ViewCompat.setTranslationZ(imagefronts, 0);

        filterList.setEnabled(false);
        filterList.setVerticalScrollBarEnabled(false);

        filterList.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                return (event.getAction() == MotionEvent.ACTION_MOVE);
            }
        });

//        listiner = new ClickListiner() {
//            @Override
//            public void click(int index){
//
//            }
//        };

        arrowcircle = (ImageView) view.findViewById(R.id.arrowcircle);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

    }


}