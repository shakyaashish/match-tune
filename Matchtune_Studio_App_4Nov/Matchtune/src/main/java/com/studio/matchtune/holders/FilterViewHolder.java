package com.studio.matchtune.holders;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.studio.matchtune.activity.R;

public class FilterViewHolder extends RecyclerView.ViewHolder {
    public AppCompatButton itemButton;
    public View view;
    public FilterViewHolder(View itemView)
    {
        super(itemView);
        itemButton
                = (AppCompatButton)itemView
                .findViewById(R.id.btnItem);
        view  = itemView;
    }
}



