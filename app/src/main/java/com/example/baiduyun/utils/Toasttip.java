package com.example.baiduyun.utils;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

public class Toasttip {

    private Context mContext;

    public Toasttip(){}

    public Toasttip(Context context){
        super();
        mContext = context;
    }

    public void showTip(String content){
        Looper.myLooper();
        Looper.prepare();
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
        Looper.loop();
    }
}
