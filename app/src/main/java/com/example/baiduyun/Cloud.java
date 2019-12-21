package com.example.baiduyun;

public class Cloud {

    private String name ;
    private int imageId;
    private String size;

    public  Cloud(String name,int imageId, String size){
        this.name = name ;
        this.imageId = imageId;
        this.size = size;
    }
    public String getName(){
        return name;
    }

    public String getSize(){
        return size;
    }

    public int getImageId(){
        return imageId;
    }
}
