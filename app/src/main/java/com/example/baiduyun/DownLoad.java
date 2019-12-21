package com.example.baiduyun;

public class DownLoad {

    private  String name ;
    private  int imageId;
    private  int jinDu;
    private  String time ;

    public  DownLoad(String name,int imageId ,String  time,int num){
        this.name = name ;
        this.imageId = imageId ;
        this.time =  time;
        this.jinDu = num ;
    }
    public  DownLoad(String name,int imageId ,String  time){
        this.name = name ;
        this.imageId = imageId ;
        this.time =  time;
    }
    public String getName(){
        return name;
    }
    public String getTime(){
        return time;
    }
    public int getImageId(){
        return imageId;
    }

    public int getjinDu(){
        return jinDu;
    }
}
