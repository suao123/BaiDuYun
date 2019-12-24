package com.example.baiduyun;


import android.content.Context;
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

import com.example.baiduyun.utils.FileIO;
import com.example.baiduyun.utils.HttpURL;
import com.example.baiduyun.utils.Toasttip;

import org.json.JSONObject;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;

public class CloudAdapter extends RecyclerView.Adapter<CloudAdapter.ViewHolder>{

    private List<Cloud> myCloudList;
    private Context parentContext;

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
        parentContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_recyclerview_item,parent,false);
        final ViewHolder holder = new ViewHolder(view);
        holder.cloudView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                Cloud cloud = myCloudList.get(position);
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
                   if(item.getItemId()==R.id.remove) {

                       final int position = holder.getAdapterPosition();
                       new Thread(new Runnable() {
                           final FileIO fileIO = new FileIO(parentContext);
                           HttpURL url = new HttpURL();
                           Toasttip tip = new Toasttip(parentContext);
                           @Override
                           public void run() {
                               try{
                                   HashMap<String, String> cookie = new HashMap<>();
                                   HashMap<String, String> requestResource = new HashMap<>();
                                   cookie.put("token", fileIO.readCookie());
                                   requestResource.put("username", fileIO.readUsername());

                                   Cloud cloud = myCloudList.get(position);
                                   requestResource.put("filename", cloud.getName());
                                   JSONObject result = url.getURLResource("delete", "GET", requestResource, cookie);
                                   if(result.get("status").equals("success")){

                                       ((HomeActivity) parentContext).runOnUiThread(new Runnable() {
                                           @Override
                                           public void run() {
                                               removeData(position);
                                               Toast.makeText(parentContext, "删除成功", Toast.LENGTH_SHORT).show();
                                           }
                                       });
                                   } else {
                                       tip.showTip("删除不成功，请重试");
                                   }
                               } catch (Exception e){
                                   e.printStackTrace();
                               }
                           }
                       }).start();

                   }else if(item.getItemId() == R.id.add){
                        new Thread(new Runnable() {
                            Toasttip tip = new Toasttip(parentContext);
                            FileIO fileIO = new FileIO(parentContext);
                            HttpURL url = new HttpURL();
                            @Override
                            public void run() {
                                int position = holder.getAdapterPosition();
                                Cloud cloud = myCloudList.get(position);
                                String filename = cloud.getName();
                                try {
                                    InputStream input = url.getFile(fileIO.readUsername(), filename);
                                    if(input != null){
                                        fileIO.downloadFile(input, filename);
                                        tip.showTip("下载成功");
                                    } else {
                                        tip.showTip("下载失败");
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
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