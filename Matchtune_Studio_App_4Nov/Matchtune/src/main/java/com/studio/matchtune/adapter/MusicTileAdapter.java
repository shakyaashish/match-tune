package com.studio.matchtune.adapter;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.studio.matchtune.ResponceModel.Attributedata;
import com.studio.matchtune.activity.R;

import java.util.List;

public class MusicTileAdapter extends RecyclerView.Adapter<MusicTileAdapter.holder> {

    Context context;

    private List<Attributedata> attributedata;

    public MusicTileAdapter(Context context, List<Attributedata> attributedata) {
        this.context = context;
        this.attributedata = attributedata;
    }


    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_list_item, parent, false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull holder holder, int position) {
        Attributedata model =attributedata.get(position);

        Glide.with(context)
                .load(model.getMetadata().getCover().getSmall())
                .placeholder(R.drawable.reload)
                .into(holder.item_image);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount(){ return  attributedata.size();
    }

    public static class holder extends RecyclerView.ViewHolder {

        ImageView item_image;
        TextView item_title;
        public holder(@NonNull View itemView) {
            super(itemView);

            item_image = itemView.findViewById(R.id.item_image);
            item_title = itemView.findViewById(R.id.item_title);

        }
    }
}
