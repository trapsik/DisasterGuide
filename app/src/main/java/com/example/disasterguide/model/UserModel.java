package com.example.disasterguide.model;

public class UserModel {
    private String id;
    private String pw;
    private String name;
    private String birth;
    private String phone;

    public UserModel() {}

    public UserModel(String id, String pw, String name, String birth, String phone) {
        this.id = id;
        this.pw = pw;
        this.name = name;
        this.birth = birth;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
