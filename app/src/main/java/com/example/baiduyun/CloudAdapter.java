package com.example.baiduyun;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder>{

    private List<Cloud> mFruitList;

    //定义一个内部类ViewHolder，继承自RecyclerView.ViewHolder，用来缓存子项的各个实例，提高效率
    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View view){
            super(view);

            fruitImage = (ImageView)view.findViewById(R.id.file_icon);
            fruitName=(TextView)view.findViewById(R.id.file_name);
        }
    }

    //绑定传入的数据源
    public CloudAdapter(List<Cloud> fruitList){
        mFruitList = fruitList;
    }

    //实现onCreateViewHolder方法，返回给recyclerView使用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_recyclerview_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    //实现onBindViewHolder方法，设置子Item上各个实例
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cloud fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());
    }

    //返回子项个数
    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}