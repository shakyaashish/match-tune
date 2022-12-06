package com.studio.matchtune.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;

import com.studio.matchtune.adapter.VideoAdapter;
import com.studio.matchtune.fragment.FilterFragment;
import com.studio.matchtune.model.videoData;

import java.util.ArrayList;
import java.util.List;

public class VideoListActivity extends AppCompatActivity {

    VideoAdapter adapter;
    RecyclerView recyclerView;
    ClickListiner listiner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        List<videoData> list = new ArrayList<>();
        list = getData();

        recyclerView
                = (RecyclerView)findViewById(
                R.id.recycler_view);
        listiner = new ClickListiner() {
            @Override
            public void click(String index){
                //Toast.makeTexT(this,"clicked item index is "+index,Toast.LENGTH_LONG).show();
            }
        };
        adapter
                = new VideoAdapter(
                list, getApplication(),listiner,false);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(
                new LinearLayoutManager(VideoListActivity.this));
       // showEditDialog();
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
       // fragment_login fragmentLogin = fragment_login.newInstance("Some Title");
        //ExportFragment fragmentLogin = ExportFragment.newInstance("Some Title");
       // VolumeControllerFragment fragmentLogin = VolumeControllerFragment.newInstance("Some Title");
        FilterFragment fragmentLogin = FilterFragment.newInstance("Some Title");
        fragmentLogin.show(fm, "fragment_edit_name");
    }


    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
    }

    // Sample data for RecyclerView
    private List<videoData> getData()
    {
        List<videoData> list = new ArrayList<>();
         list.add(new videoData("Sports",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview5.jpg"));
        list.add(new videoData("My Test Exam",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview2.jpg"));
        list.add(new videoData("See Line",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview1.jpg"));
        list.add(new videoData("See Line",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview3.jpg"));
        list.add(new videoData("Sports",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview4.jpg"));
        list.add(new videoData("My Test Exam",
                "https://assets.matchtune.com/Studio/General/VideosOfTheWeek/votwPreview6.jpg"));
        return list;
    }
}