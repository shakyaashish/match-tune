package com.studio.matchtune.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.matchtune.activity.R;

import pl.droidsonroids.gif.GifImageView;

public class videoViewHolder extends RecyclerView.ViewHolder {
    public ImageView thumbnail;
    public TextView txtTittle;
    public ImageView imageFav;
    public View view;
    public RelativeLayout mainlayout;
    public com.google.android.material.card.MaterialCardView mainlayout1;
    public GifImageView musicloder,horizontalloder;



    public videoViewHolder(View itemView)
    {
        super(itemView);
        thumbnail
                = (ImageView)itemView
                .findViewById(R.id.item_image);
        txtTittle
                = (TextView)itemView
                .findViewById(R.id.item_title);
        view  = itemView;
        imageFav =  (ImageView)itemView
            .findViewById(R.id.imageFav);
        mainlayout =  (RelativeLayout)itemView
                .findViewById(R.id.mainlayout);
        mainlayout1 =  (com.google.android.material.card.MaterialCardView)itemView
                .findViewById(R.id.mainlayout1);
        musicloder =  (GifImageView)itemView
                .findViewById(R.id.musicloder);
        horizontalloder =  (GifImageView)itemView
                .findViewById(R.id.horizontalloder);
    }


}