package com.example.disasterguide.model;

public class ResponseModel {
    private int responsecode;
    private String message;

    public ResponseModel() {}

    public ResponseModel(int responsecode, String message) {
        this.responsecode = responsecode;
        this.message = message;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
