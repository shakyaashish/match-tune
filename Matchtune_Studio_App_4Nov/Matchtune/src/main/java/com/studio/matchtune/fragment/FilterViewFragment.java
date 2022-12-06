package com.studio.matchtune.fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.activity.VideoEditionActivity;
import com.studio.matchtune.adapter.FilterAdapter;
import com.studio.matchtune.adapter.ViewpagerAdapter;
import com.studio.matchtune.database.DBHelper;
import com.studio.matchtune.database.DBManager;
import com.studio.matchtune.model.Child;
import com.studio.matchtune.model.videoData;
import com.studio.matchtune.utility.ApplicationConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import co.lujun.androidtagview.TagContainerLayout;
import me.relex.circleindicator.CircleIndicator;

public class FilterViewFragment extends DialogFragment {

    private ImageView arrowcircle,imgPrevious,imgNext,arrowcircle1,trashcircle;
    ViewPager viewpager;
    PagerAdapter adapter;
    private Context context;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CircleIndicator indicator;
    ImageButton dotBtn_first,dotBtn_second,dotBtn_third,dotBtn_four;
    Map<String, ArrayList<String>> dataList = new HashMap<String, ArrayList<String>>();
    AppCompatButton btnClear;

    List<String> tags = new ArrayList<String>();

    JSONArray genres = new JSONArray();

    JSONArray videoFor = new JSONArray();

    private DBManager dbManager;

    public static FilterViewFragment newInstance(String title) {
        FilterViewFragment frag = new FilterViewFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_filter_view, container);

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = getActivity();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ArrayList<String> aList = new ArrayList<String>();
        dataList.put("tags",aList);
        ArrayList<String> quickTuneList = new ArrayList<String>();
        quickTuneList.add("Calm energy");
        quickTuneList.add("Medium energy");
        quickTuneList.add("High energy");
        quickTuneList.add("Contains Vocals");
        quickTuneList.add("Instrumental");
        dataList.put("quickTune",quickTuneList);
        makeJsonObjectRequest();
        arrowcircle = (ImageView) view.findViewById(R.id.arrowcircle);
        trashcircle = (ImageView) view.findViewById(R.id.trashcircle);
        ImageView  arrowcircle1 = (ImageView) view.findViewById(R.id.arrowcircle1);

        imgPrevious  = (ImageView) view.findViewById(R.id.imgPrevious);
        imgNext  = (ImageView) view.findViewById(R.id.imgNext);

        viewpager = (ViewPager) view.findViewById(R.id.pager);
        dotBtn_first = (ImageButton) view.findViewById(R.id.dotBtn_first);
        dotBtn_second =(ImageButton) view.findViewById(R.id.dotBtn_second);
        dotBtn_third =(ImageButton) view.findViewById(R.id.dotBtn_third);
        dotBtn_four= (ImageButton) view.findViewById(R.id.dotBtn_four);
        btnClear= (AppCompatButton) view.findViewById(R.id.btnClear);
        setActiveIndex(currentPage);
        arrowcircle.bringToFront();
        imgPrevious.bringToFront();
        imgNext.bringToFront();


        viewpager.setCurrentItem(2, true);

        btnClear.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {


           dbManager = new DBManager(getActivity().getApplicationContext());
           try {
               dbManager.open();
           } catch (SQLException e) {
               e.printStackTrace();
           }






           Cursor cursor = dbManager.fetch();
           if(cursor.moveToFirst()){
               while (! cursor.isAfterLast()) {
//                   tags.add(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
                   genres.put(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
                   cursor.moveToNext();
//                   Log.d("testtagn"," tags :" + tags);
               }
           }
           Log.d("testtagan"," tags :" + tags);



//          Cursor cursor1 = dbManager.fetchm();
//           if(cursor1.moveToFirst()){
//               while (! cursor1.isAfterLast()) {
////                   tags.add(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
//                   videoFor.put(cursor1.getString(cursor1.getColumnIndex(DBHelper.TAGS)));
//                   cursor1.moveToNext();
////                   Log.d("testtagn"," tags :" + tags);
//               }
//           }
//           Log.d("testtagan"," tags :" + tags);
//
//





           try {
//               List<String> list = new ArrayList<>();
//            list.add("Blues");

//               List<String> cheklist = new ArrayList<String>();
//              JSONArray genres = new JSONArray();
//                    genres.put(tags);

//               genres.put("Blues");
//               genres.put("Classical");


//               if(tags.size() > 0) {
//                   for (int i = 0; i < tags.size(); i++) {
////                       tags.add(String.valueOf(i));
//                       genres.put(i);
//                   }
//               }


               JSONArray instruments = new JSONArray();
               instruments.put("Drums");
               instruments.put("Guitar");

               JSONArray musicFor = new JSONArray();
               musicFor.put("Adult");
               musicFor.put("Animated");

               JSONArray videoFor = new JSONArray();
               videoFor.put("Animals");
               videoFor.put("Arts");

               JSONArray moods = new JSONArray();
               moods.put("Agressive");
               moods.put("Atmospheric");







               JSONObject doattribute = new JSONObject();
               doattribute.put("genres",genres);
               doattribute.put("instruments",instruments);
               doattribute.put("musicFor",musicFor);
               doattribute.put("videoFor",videoFor);
               doattribute.put("moods",moods);



           JSONObject dataObj = new JSONObject();
           dataObj.put("type", "search");
           dataObj.put("attributes", doattribute);


           JSONObject jobj = new JSONObject();
           jobj.put("data", dataObj);
        Log.d("testfindmusic", String.valueOf(jobj));
//               VideoEditionActivity videoEditionActivity =new VideoEditionActivity();
//               videoEditionActivity.makeMusicsRequest(jobj);
               Intent intent =new Intent(getActivity(),VideoEditionActivity.class);
               intent.putExtra("filterobj", jobj.toString());
               intent.putExtra("testing",1);
               intent.putExtra("testingc",5);
               startActivity(intent);


           dismiss();
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
   });
        arrowcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });


        trashcircle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            ViewpagerAdapter adaptera =new ViewpagerAdapter(getActivity().getApplicationContext(),dataList);
            adaptera.deletetag();
                viewpager.setCurrentItem(3, true);
          //      viewpager.setCurrentItem(0, true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewpager.setCurrentItem(0, true);
                    }
                }, 450);


            }
        });
        arrowcircle1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int pageCount = dataList.size();
                viewpager.setCurrentItem(0, true);



            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int pageCount = dataList.size();
                    if (currentPage <= pageCount -1 ) {
                        viewpager.setCurrentItem(currentPage++, true);
                    } else if (currentPage == pageCount - 1) {
                        viewpager.setCurrentItem(0, true);
                    }

            }
        });
        imgPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    int pageCount = dataList.size();
                if (currentPage > 0 ) {
                    viewpager.setCurrentItem(currentPage--, true);

                }
                if (currentPage == 0 ) {
                    viewpager.setCurrentItem(0, true);

                }else if (currentPage == pageCount - 1) {
                    viewpager.setCurrentItem(0, true);

                }
            }
        });

        currentPage=1;
        viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                setActiveIndex(position);
                currentPage = position;
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {

                    int pageCount = dataList.size();
                    if (currentPage == 0 || currentPage < 0 ) {
                        viewpager.setCurrentItem(0, false);
                    } else if (currentPage == pageCount - 1) {
                        viewpager.setCurrentItem(pageCount - 1, false);
                    }else if (currentPage > pageCount) {
                        viewpager.setCurrentItem(0, false);
                    }
                }
            }
        });
    }


    private void makeJsonObjectRequest() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        String url = "https://api.matchtune.com/classifiers";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<String> aList = new ArrayList<String>();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONArray("videoFor");
                            aList.clear();
                            if(jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    aList.add(jsonArray.getString(i));
                                }
                            }
                            dataList.put("videoFor",aList);

                    // added new
                            jsonArray = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONArray("musicFor");
                            aList = new ArrayList<String>();
                            if(jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    aList.add(jsonArray.getString(i));
                                }
                            }
                            dataList.put("musicFor",aList);

                            jsonArray = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONArray("genres");
                               aList = new ArrayList<String>();
                            if(jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    aList.add(jsonArray.getString(i));
                                }
                            }
                            dataList.put("genres",aList);

                            jsonArray = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONArray("moods");
                           aList = new ArrayList<String>();
                           if(jsonArray.length() > 0) {
                               for (int i = 0; i < jsonArray.length(); i++) {

                                   aList.add(jsonArray.getString(i));
                               }
                           }
                            dataList.put("moods",aList);

                            jsonArray = jsonObject.getJSONObject("data").getJSONObject("attributes").getJSONArray("instruments");
                              aList = new ArrayList<String>();
                            if(jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    aList.add(jsonArray.getString(i));
                                }
                            }
                            dataList.put("instruments",aList);
                            adapter = new ViewpagerAdapter(getActivity().getApplicationContext(), dataList);
                            viewpager.setAdapter(adapter);
                            viewpager.setCurrentItem(1, true);
                          //  indicator.setViewPager(viewpager);

                        } catch (JSONException e) {
                            Log.e("MYAPP", "unexpected JSON exception", e);

                        }


                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
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

    public void setActiveIndex(Integer index){

        dotBtn_first.setImageResource(R.drawable.default_dot);
        dotBtn_second.setImageResource(R.drawable.default_dot);
        dotBtn_third.setImageResource(R.drawable.default_dot);
        dotBtn_four.setImageResource(R.drawable.default_dot);
        if(index==0) {
            imgPrevious.setVisibility(View.INVISIBLE);
            dotBtn_first.setImageResource(R.drawable.selected_dot);
        } else  if(index==1) {
            imgPrevious.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
            dotBtn_second.setImageResource(R.drawable.selected_dot);
        } else  if(index==2) {
            dotBtn_third.setImageResource(R.drawable.selected_dot);
            imgPrevious.setVisibility(View.VISIBLE);
            imgNext.setVisibility(View.VISIBLE);
        } else {
            dotBtn_four.setImageResource(R.drawable.selected_dot);
            if(index == dataList.size() - 1) {
                imgNext.setVisibility(View.INVISIBLE);
            }
        }

    }
}