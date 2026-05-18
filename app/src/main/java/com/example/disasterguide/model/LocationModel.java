package com.example.disasterguide.model;

public class LocationModel {
    private int location_no;
    private String name;
    private String address;
    private String info;
    
    public LocationModel() {}
    
    public LocationModel(int location_no, String name, String address, String info) {
        this.location_no = location_no;
        this.name = name;
        this.address = address;
        this.info = info;
    }

    public int getLocation_no() {
        return location_no;
    }

    public void setLocation_no(int location_no) {
        this.location_no = location_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
