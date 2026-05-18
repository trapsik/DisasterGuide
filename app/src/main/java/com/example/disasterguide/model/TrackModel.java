package com.example.disasterguide.model;

public class TrackModel {
    int track_no;
    String user_id;
    double lat;
    double lng;
    String upload_date;

    public TrackModel() {}

    public TrackModel(int track_no, String user_id, double lat, double lng, String upload_date) {
        this.track_no = track_no;
        this.user_id = user_id;
        this.lat = lat;
        this.lng = lng;
        this.upload_date = upload_date;
    }

    public int getTrack_no() {
        return track_no;
    }

    public void setTrack_no(int track_no) {
        this.track_no = track_no;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }
}
