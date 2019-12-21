package com.example.baiduyun.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baiduyun.Cloud;
import com.example.baiduyun.CloudAdapter;
import com.example.baiduyun.DownLoad;
import com.example.baiduyun.DownLoadAdapter;
import com.example.baiduyun.R;
import com.example.baiduyun.utils.FileIO;
import com.example.baiduyun.utils.HttpURL;
import com.google.gson.stream.JsonWriter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;
    private List<DownLoad> cloudList = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        try {
            initCloud();
        } catch (Exception e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycle_view_2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DownLoadAdapter adapter = new DownLoadAdapter(cloudList);
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
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }
}