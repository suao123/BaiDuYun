package com.example.baiduyun.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baiduyun.Cloud;
import com.example.baiduyun.CloudAdapter;
import com.example.baiduyun.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    private List<Cloud> cloudList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        initCloud();
        RecyclerView recyclerView = (RecyclerView)root.findViewById(R.id.recycle_view_1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        CloudAdapter adapter = new CloudAdapter(cloudList);
        recyclerView.setAdapter(adapter);

        return root;
    }


    private void initCloud(){
        for(int i=0;i<=20;i++){
            Cloud cloud = new Cloud("wo shi yi duo ke ai de yun",R.drawable.ic_logo);
            cloudList.add(cloud);
        }
    }


}