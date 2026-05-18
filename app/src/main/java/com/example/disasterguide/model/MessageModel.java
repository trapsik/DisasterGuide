package com.example.disasterguide.model;

public class MessageModel {
    private int id;
    private  String date;
    private String location;
    private String content;

    public MessageModel() {}

    public MessageModel(int id, String date, String location, String content) {
        this.id = id;
        this.date = date;
        this.location = location;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
