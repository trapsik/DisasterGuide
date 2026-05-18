package com.example.disasterguide.model;

public class FriendModel {
    int friend_no;
    String user1;
    String user2;
    boolean state;

    public FriendModel() {}

    public FriendModel(int friend_no, String user1, String user2, boolean state) {
        this.friend_no = friend_no;
        this.user1 = user1;
        this.user2 = user2;
        this.state = state;
    }

    public int getFriend_no() {
        return friend_no;
    }

    public void setFriend_no(int friend_no) {
        this.friend_no = friend_no;
    }

    public String getUser1() {
        return user1;
    }

    public void setUser1(String user1) {
        this.user1 = user1;
    }

    public String getUser2() {
        return user2;
    }

    public void setUser2(String user2) {
        this.user2 = user2;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
