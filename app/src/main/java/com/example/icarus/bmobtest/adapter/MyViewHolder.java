package com.example.icarus.bmobtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.icarus.bmobtest.R;
import com.example.icarus.bmobtest.listener.MyItemClickListener;
import com.example.icarus.bmobtest.listener.MyItemLongClickListener;

/**
 * Created by icarus9527 on 2017/3/19.
 */

public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

    public ImageView headImg;
    public TextView tvName,tvAge;

    private MyItemClickListener listener;
    private MyItemLongClickListener longClickListener;


    public MyViewHolder(View itemView, MyItemClickListener listener, MyItemLongClickListener longClickListener) {
        super(itemView);
        headImg = (ImageView) itemView.findViewById(R.id.item_iv_headImg);
        tvName = (TextView) itemView.findViewById(R.id.item_tv_name);
        tvAge = (TextView) itemView.findViewById(R.id.item_tv_age);

        this.listener = listener;
        this.longClickListener = longClickListener;

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (listener != null){
            listener.onItemClick(v,getLayoutPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null){
            longClickListener.onItemLongClick(v,getLayoutPosition());
        }
        return true;
    }
}
