package com.example.baiduyun;

public class Cloud {

    private  String name ;
    private  int imageId;
    private  String time ;

    public  Cloud(String name,int imageId ,String  time){
        this.name = name ;
        this.imageId = imageId ;
        this.time =  time;
    }
    public String getName(){
        return name;
    }

    public int getImageId(){
        return imageId;
    }
}
