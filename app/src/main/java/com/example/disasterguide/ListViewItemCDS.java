package com.example.disasterguide;

import android.widget.Button;

public class ListViewItemCDS {
    private String name;
    private String address;
    private String capacity;

    public void setName(String name){
        this.name = name;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public void setCapacity(String capacity){
        this.capacity = capacity;
    }

    public String getName(){
        return this.name;
    }
    public String getAddress(){
        return this.address;
    }
    public String getCapacity(){
        return this.capacity;
    }
}
