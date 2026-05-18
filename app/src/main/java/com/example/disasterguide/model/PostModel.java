package com.example.disasterguide.model;

public class PostModel {
    int post_no;
    String user_id;
    String title;
    String content;
    String date;

    public PostModel() {}

    public PostModel(int post_no, String user_id, String title, String content, String date) {
        this.post_no = post_no;
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public int getPost_no() {
        return post_no;
    }

    public void setPost_no(int post_no) {
        this.post_no = post_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
