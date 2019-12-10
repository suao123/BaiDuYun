package com.example.baiduyun;

public class Cloud {

    private  String name ;
    private  int imageId;

    public  Cloud(String name,int imageId){
        this.name = name ;
        this.imageId = imageId ;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
