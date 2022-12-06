package com.studio.matchtune.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;

import com.studio.matchtune.activity.ClickListiner;
import com.studio.matchtune.activity.R;
import com.studio.matchtune.database.DBHelper;
import com.studio.matchtune.database.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FilterAdapter extends ArrayAdapter<String> {
    private DBManager dbManager;
    String  category;
    List<String> tags = new ArrayList<String>();

    public FilterAdapter(Context context, ArrayList<String> users, String categories) {
        super(context, 0, users);
         //  changes
        Log.d("testcat",""+users.get(0).toString());
    }

    @SuppressLint("Range")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        Log.d("testcatddd",""+position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.button_list_item, parent, false);
        }

        dbManager = new DBManager(getContext());
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
        AppCompatButton itemButton = (AppCompatButton) convertView.findViewById(R.id.btnItem);
        itemButton.setText(getItem(position).toString());
        if(tags.contains(getItem(position).toString())){
            itemButton.setBackgroundResource(R.drawable.rounded_fill_orange_btn);
        }
        else{
            itemButton.setBackgroundResource(R.drawable.rounded_fill_btn);

        }

        itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               String tag = getItem(position).toString();
                if(tags.contains(tag)) {
                    dbManager.delete(tag);
                    Log.d("hastag","hash");
                    itemButton.setBackgroundResource(R.drawable.rounded_fill_btn);
                }else {
                    dbManager.insert(tag);
                    itemButton.setBackgroundResource(R.drawable.rounded_fill_orange_btn);
                    Log.d("hastagb","hashnot");
                }
                Log.d("hastagb","hashnotall");

                Cursor cursor = dbManager.fetch();
                if(cursor.moveToFirst()){
                    while (! cursor.isAfterLast()) {
                        tags.add(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
                        cursor.moveToNext();
                    }
                }

            }
        });

        // Return the completed view to render on screen
        return convertView;
    }
}

