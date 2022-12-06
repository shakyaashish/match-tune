package com.studio.matchtune.fragment;

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.studio.matchtune.activity.R;
import com.studio.matchtune.database.DBHelper;
import com.studio.matchtune.database.DBManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class TagsFragment extends DialogFragment {

    private EditText mEditText;
    private ImageView arrowcircle;
    List<String> tags = new ArrayList<String>();
    private DBManager dbManager;

    public static TagsFragment newInstance(String title) {
        TagsFragment frag = new TagsFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("testtagon", " tags :" + tags);
        return inflater.inflate(R.layout.fragment_tags, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        TagContainerLayout mTagContainerLayout = (TagContainerLayout) view.findViewById(R.id.tagContainer);

        dbManager = new DBManager(getActivity().getApplicationContext());
        try {
            dbManager.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Cursor cursor = dbManager.fetch();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                tags.add(cursor.getString(cursor.getColumnIndex(DBHelper.TAGS)));
                cursor.moveToNext();
                Log.d("testtag", " tags :" + tags);
            }
        }
        Log.d("testtaga", " tags :" + tags);
        mTagContainerLayout.setTags(tags);


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        mTagContainerLayout.setOnTagClickListener(new TagView.OnTagClickListener() {

            @Override
            public void onTagClick(int position, String text) {
                tags.add("shoaib");
                mTagContainerLayout.setTags(tags);
            }

            @Override
            public void onTagLongClick(final int position, String text) {
                tags.remove(position);
                mTagContainerLayout.setTags(tags);
            }

            @Override
            public void onSelectedTagDrag(int position, String text) {
                // ...
            }

            @Override
            public void onTagCrossClick(int position) {
                tags.remove(position);
                mTagContainerLayout.setTags(tags);
            }
        });
    }
}