package com.example.disasterguide.model;

public class CommentModel {
    int comment_no;
    int post_no;
    String user_id;
    String content;
    String date;

    public CommentModel() {}

    public CommentModel(int comment_no, int post_no, String user_id, String content, String date) {
        this.comment_no = comment_no;
        this.post_no = post_no;
        this.user_id = user_id;
        this.content = content;
        this.date = date;
    }

    public int getComment_no() {
        return comment_no;
    }

    public void setComment_no(int comment_no) {
        this.comment_no = comment_no;
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
