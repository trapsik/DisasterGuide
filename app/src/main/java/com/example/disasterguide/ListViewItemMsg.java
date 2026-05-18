package com.example.disasterguide;

public class ListViewItemMsg {
    private String date;
    private String location;
    private String content;

    public void setDate(String date){
        this.date = date;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getDate(){
        return this.date;
    }
    public String getLocation(){
        return this.location;
    }
    public String getContent(){
        return this.content;
    }
}
