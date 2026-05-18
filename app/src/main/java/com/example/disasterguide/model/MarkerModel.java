package com.example.disasterguide.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MarkerModel extends MarkerOptions {
    public MarkerModel(double x, double y, String title, String content) {
        position(new LatLng(x, y));
        title(title);
        snippet(content);
    }
}
