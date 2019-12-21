package com.example.baiduyun;


import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder>{

    private List<Cloud> myCloudList;

    //定义一个内部类ViewHolder，继承自RecyclerView.ViewHolder，用来缓存子项的各个实例，提高效率
    static class ViewHolder extends RecyclerView.ViewHolder{
        View cloudView;
        ImageView cloudImage;
        TextView cloudName;
        TextView cloudSize;

        public ViewHolder(View view){
            super(view);
            cloudView = (Button)view.findViewById(R.id.btn_eidt);
            cloudImage = (ImageView)view.findViewById(R.id.file_icon);
            cloudName=(TextView)view.findViewById(R.id.file_name);
            cloudSize = (TextView)view.findViewById(R.id.file_num);
        }
    }

    //绑定传入的数据源
    public CloudAdapter(List<Cloud> fruitList){
        myCloudList = fruitList;
    }

    //实现onCreateViewHolder方法，返回给recyclerView使用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_recyclerview_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cloudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Cloud cloud = myCloudList.get(position);
//                Toast.makeText(v.getContext(),"ojbk",Toast.LENGTH_SHORT).show();
                showPopupMenu(holder,holder.cloudView);
            }
        });
        return holder;
    }

    private void showPopupMenu(final ViewHolder holder, final View view) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_d, popupMenu.getMenu());
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(view.getContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
                   if(item.getItemId()==R.id.remove) {
                       int position = holder.getAdapterPosition();
                       removeData(position);
                   }
                return false;
            }
        });
        popupMenu.show();
    }


    //  删除数据
    public void removeData(int position) {
        myCloudList.remove(position);
        //删除动画
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }
    //实现onBindViewHolder方法，设置子Item上各个实例
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Cloud cloud = myCloudList.get(position);
        holder.cloudImage.setImageResource(cloud.getImageId());
        holder.cloudName.setText(cloud.getName());
        holder.cloudSize.setText(cloud.getSize());
    }

    //返回子项个数
    @Override
    public int getItemCount() {
        return myCloudList.size();
    }


}