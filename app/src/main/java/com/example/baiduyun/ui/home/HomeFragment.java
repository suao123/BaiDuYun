package com.example.baiduyun.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baiduyun.Cloud;
import com.example.baiduyun.CloudAdapter;
import com.example.baiduyun.R;
import com.example.baiduyun.utils.FileIO;
import com.example.baiduyun.utils.HttpURL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private  List<Cloud> cloudList = new ArrayList<>();
    private boolean flag = false;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        flag = false;
        initCloud();
        while(!flag){System.out.println("");}
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycle_view_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CloudAdapter adapter = new CloudAdapter(cloudList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        return root;
    }


    private void initCloud(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                JSONObject result = null;
                HashMap<String, String> requestResource = new HashMap<>();
                HashMap<String, String> cookie = new HashMap<>();
                HttpURL url = new HttpURL();
                FileIO file=  new FileIO(getContext());
                try {
                    cookie.put("token", file.readCookie());
                    requestResource.put("username", file.readUsername());
                    result = url.getURLResource("files", "GET", requestResource, cookie);
                    System.out.println(result);
                    if(result.get("status").equals("success")){
                        JSONArray files = result.getJSONArray("files");

                        for(int i = 0; i < files.length(); i++){
                            JSONObject json = (JSONObject) files.get(i);
                            if((Integer)json.get("size") < 1024 * 1024){
                                int tmp = (Integer) json.get("size") / 1024;
                                cloudList.add(new Cloud(json.getString("name"), R.drawable.file_logo, tmp + "KB"));
                            } else {
                                int tmp = (Integer) json.get("size") / (1024*1024);
                                cloudList.add(new Cloud(json.getString("name"), R.drawable.file_logo, tmp + "MB"));
                            }
                        }
                        System.out.println(cloudList);
                        System.out.println("");
                        flag = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}