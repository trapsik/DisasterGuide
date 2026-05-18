package com.example.disasterguide;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.FriendModel;
import com.example.disasterguide.model.LocationModel;
import com.example.disasterguide.model.MarkerModel;
import com.example.disasterguide.model.ResponseModel;
import com.example.disasterguide.model.TrackModel;
import com.example.disasterguide.model.UserModel;
import com.example.disasterguide.model.XYModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class CheckFriendLocation extends AppCompatActivity implements OnMapReadyCallback {
    LocationManager locationManager; //위치관리자
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String provider;
    static GoogleMap map;
    ArrayList<LocationModel> locationList;
    static double cur_lat = 36.85118;//친구의 위치 정보를 받아옴
    static double cur_lon = 127.15113;


    TextView title_friendID;
    ImageView back;
    ListView listView;
    String getFriendID, getFriendName, date;
    double lat, lon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_friend_location);

        ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        // GPS 프로바이더 사용가능여부
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        Log.d("Main", "isGPSEnabled="+ isGPSEnabled);
        Log.d("Main", "isNetworkEnabled="+ isNetworkEnabled);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                // 현재 위치의 위도 경도 받아오기
                cur_lat = location.getLatitude();
                cur_lon = location.getLongitude();

                Log.d(TAG, "onLocationChanged: latitude =" + cur_lat);
                Log.d(TAG, "onLocationChanged: longitude =" + cur_lon);
            }
        };
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0,0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,0,locationListener);
        provider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = locationManager.getLastKnownLocation(provider);
        if(lastKnownLocation != null) {
            cur_lat = lastKnownLocation.getLatitude();
            cur_lon = lastKnownLocation.getLongitude();
            Log.d(TAG, "GetUserLoction: lastKnownLocation != null");
            Log.d(TAG, "GetUserLoction: latitude =" + cur_lat);
            Log.d(TAG, "GetUserLoction: longitude =" + cur_lon);
        }

        title_friendID = (TextView) findViewById(R.id.tv_friendID);
        back = (ImageView) findViewById(R.id.back);
        listView = (ListView) findViewById(R.id.listFriendLocation);

        Intent intent = getIntent();
        getFriendID = intent.getStringExtra("friendID");
        getFriendName = intent.getStringExtra("friendName");
        title_friendID.setText(getFriendName+"("+ getFriendID + ")님의 위치기록");

        try {
            String url = "track/list?id=js";
            String result = new HttpResult().execute(url).get();
            ArrayList<TrackModel> f = new ObjectMapper().readValue(result, new TypeReference<ArrayList<TrackModel>>(){});

            ListViewAdapterFriendLocation lvafl= new ListViewAdapterFriendLocation();
            listView.setAdapter(lvafl);

            int num;
            for(num = 0; num < f.size(); num++){
                date = f.get(num).getUpload_date();
                lat = f.get(num).getLat();
                lon = f.get(num).getLng();

                lvafl.addItem(date, lat, lon);
            }
            Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent = new Intent();
                outIntent.putExtra("result","");
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.map = googleMap;
        map.clear();
        LatLng first = new LatLng(cur_lat,cur_lon);
        try {
            for (int i = 0; i < locationList.size(); i++) {
                String get = new HttpResult().execute("location/locationtoxy?address="+locationList.get(i).getAddress()).get();
                XYModel xy = new ObjectMapper().readValue(get, XYModel.class);
                map.addMarker(new MarkerModel(xy.y, xy.x, locationList.get(i).getName(), locationList.get(i).getAddress()));
            }
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(first, 13));
    }


}
