package com.example.icarus.bmobtest.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icarus.bmobtest.Bean.Person;
import com.example.icarus.bmobtest.R;
import com.example.icarus.bmobtest.listener.MyItemClickListener;
import com.example.icarus.bmobtest.listener.MyItemLongClickListener;

import java.util.List;

/**
 * Created by icarus9527 on 2017/3/19.
 */

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private List<Person> resource;

    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    public MyAdapter(List<Person> resource){
        this.resource = resource;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view,mItemClickListener,mItemLongClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Person p = resource.get(position);
        if (p.getHeadImg()!=null){
            holder.headImg.setImageBitmap(BitmapFactory.decodeFile(p.getHeadImg().toString()));
        }
        holder.tvName.setText(p.getName());
        holder.tvAge.setText(String.valueOf(p.getAge()));
    }

    @Override
    public int getItemCount() {
        return resource.size();
    }


    public void setOnItemClickListener(MyItemClickListener listener){
        this.mItemClickListener = listener;
    }

    public void setOnItemLongClickListener(MyItemLongClickListener listener){
        this.mItemLongClickListener = listener;
    }

    public void changeDate(List<Person> resource) {
        this.resource = resource;
        notifyDataSetChanged();
    }
}
