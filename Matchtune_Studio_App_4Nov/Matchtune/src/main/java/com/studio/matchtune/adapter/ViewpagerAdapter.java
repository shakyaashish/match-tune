package com.studio.matchtune.adapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.studio.matchtune.activity.R;
import com.studio.matchtune.database.DBHelper;
import com.studio.matchtune.database.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class ViewpagerAdapter extends PagerAdapter {
    Map<String, ArrayList<String>> dataList;
    LayoutInflater inflater;
    Context context;
    FilterAdapter adapter;
    String categories;
    private DBManager dbManager;
    TagContainerLayout mTagContainerLayout;

    public ViewpagerAdapter(Context context, Map<String,ArrayList<String>> aList) {
        this.context = context ;
        this.dataList = aList;
    }

       // Delete Tag that user select
    public  void deletetag(){
        Log.d("methodcall","called");
      //  mTagContainerLayout.removeAllTags();
//        player.release();

        dbManager = new DBManager(context);
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

            dbManager.deleteUserTag();


        //  mTagContainerLayout.removeAllTags();
    }

    @Override
    public int getCount() {

        return dataList.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ListView listView;
        TextView txtStudio,soundtrack;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(position == 0) {
            List<String> tags = new ArrayList<String>();
            View itemview = inflater.inflate(R.layout.fragment_tags, container, false);
             mTagContainerLayout = (TagContainerLayout) itemview.findViewById(R.id.tagContainer);


            txtStudio = (TextView) itemview.findViewById(R.id.txtStudio);
            txtStudio.setText("Overall search criterias");
            categories =txtStudio.getText().toString();
            Log.d("testcattest0",""+categories);
            dbManager = new DBManager(context);
            try {
                dbManager.open();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Cursor cursor = dbManager.fetch();
            if(cursor.moveToFirst()){
                while (! cursor.isAfterLast()) {
                    tags.add(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
                    cursor.moveToNext();
                }
            }
            mTagContainerLayout.setTags(tags);



           // deletetag();
         //   mTagContainerLayout.removeAllTags();

            mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

                @Override
                public void onTagClick(int position, String text) {

                    dbManager.delete(text);
                    tags.remove(position);
                    mTagContainerLayout.setTags(tags);
                }

                @Override
                public void onTagLongClick(final int position, String text) {
                    dbManager.delete(text);
                    tags.remove(position);
                    mTagContainerLayout.setTags(tags);

                }

                @Override
                public void onSelectedTagDrag(int position, String text){
                    // ...
                }

                @Override
                public void onTagCrossClick(int position) {
                    dbManager.delete(tags.get(position).toString());
                    tags.remove(position);
                    mTagContainerLayout.setTags(tags);

                }
            });
            ((ViewPager) container).addView(itemview);
            return itemview;
        }
        else {
            View itemview = inflater.inflate(R.layout.fragment_filter, container, false);


            listView = (ListView) itemview.findViewById(R.id.filterList);
            txtStudio = (TextView) itemview.findViewById(R.id.txtStudio);
            soundtrack = (TextView) itemview.findViewById(R.id.soundtrack);

            // Toast.makeText(context, position, Toast.LENGTH_LONG).show();
            ArrayList<String> aList = new ArrayList<String>();

             // Show tags of filters
            if (position == 1) {
                aList = dataList.get("quickTune");
                txtStudio.setText("Quick Search");
                soundtrack.setText("Tell us what energy and content you are looking for and we will find the best music for you");
                categories =txtStudio.getText().toString();
                Log.d("testcattest1",""+categories);
            } else if (position == 2) {
                aList = dataList.get("videoFor");
                txtStudio.setText("Video Contents");
                soundtrack.setText("Tell us what your video clips are about and we will find the best music for you");
                categories ="videoFor";
                Log.d("testcattest2",""+categories);
            }else if (position == 3) {
                aList = dataList.get("genres");
                txtStudio.setText("Music Genres");
                soundtrack.setText("Tell us what genre of music you are looking for and we will find the best music for you ");
                categories ="genres";
                Log.d("testcattest3",""+categories);
            } else if (position == 4) {
                aList = dataList.get("moods");
                txtStudio.setText("Music moods");
                soundtrack.setText("Tell us what mood you are looking for and we will find the best music for you");
                categories ="moods";
                Log.d("testcattest4",""+categories);
            } else if (position == 5) {
                aList = dataList.get("instruments");
                soundtrack.setText("Tell us what favourite instruments you are looking for and we will find the best music for you");
                txtStudio.setText("Music Instruments");
                categories ="instruments";
                Log.d("testcattest5",""+categories);
            } else if (position == 6) {
                aList = dataList.get("musicFor");
                txtStudio.setText("Quick Tune");
                categories ="musicFor";
                Log.d("testcattest6",""+categories);
            }



            adapter = new FilterAdapter(context, aList,categories);
            listView.setAdapter(adapter);
            ((ViewPager) container).addView(itemview);
            return itemview;
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((LinearLayout) object);
    }

//    @Override
//    public float getPageWidth(int position) {
//        return .20f;   //it is used for set page widht of view pager
//    }
}


