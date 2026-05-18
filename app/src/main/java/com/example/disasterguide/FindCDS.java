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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.LocationModel;
import com.example.disasterguide.model.MarkerModel;
import com.example.disasterguide.model.XYModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class FindCDS extends AppCompatActivity implements OnMapReadyCallback {

    EditText edtSearchLocation;
    TextView tvSearchBtn;
    TextView tvFindNearLocation;
    TextView tvFindNearestLocation;
    ImageView back;

    static double cur_lat = 36.85118;//위도
    static double cur_lon = 127.15113;//경도
    LocationManager locationManager; //위치관리자
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String provider;

    static GoogleMap map;
    ArrayList<LocationModel> locationList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_cds);

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

        ListView listView = findViewById(R.id.listCds);
        edtSearchLocation = (EditText) findViewById(R.id.cds_location);
        tvSearchBtn = (TextView) findViewById(R.id.cds_searchLocation);
        tvFindNearLocation = (TextView) findViewById(R.id.cds_findNearLocation);
        tvFindNearestLocation = (TextView) findViewById(R.id.cds_findNearestLocation);
        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNavi.class);
                startActivity(intent);
            }
        });

        tvSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "location/civildefense?address=" + edtSearchLocation.getText().toString();
                    String result = new HttpResult().execute(url).get();
                    ArrayList<LocationModel> e = new ObjectMapper().readValue(result, new TypeReference<ArrayList<LocationModel>>() {
                    });

                    ListViewAdapterCDS lva = new ListViewAdapterCDS("수용인원(명)");
                    listView.setAdapter(lva);

                    int num;

                    for (num = 0; num < e.size(); num++) {
                        String cds_name = e.get(num).getName();
                        String cds_address = e.get(num).getAddress();
                        String cds_capacity = e.get(num).getInfo();

                        lva.addItem(cds_name, cds_address, cds_capacity);
                    }

                    locationList = e;
                    onMapReady(map);

                    Toast.makeText(getApplicationContext(), "총 " + num + " 개 검색 성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });

        tvFindNearLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "location/civildefense/near?x=" + cur_lon + "&y=" + cur_lat;
                    String result = new HttpResult().execute(url).get();
                    ArrayList<LocationModel> e = new ObjectMapper().readValue(result, new TypeReference<ArrayList<LocationModel>>(){});

                    ListViewAdapterCDS lva = new ListViewAdapterCDS("수용인원(명)");
                    listView.setAdapter(lva);

                    int num;

                    for(num = 0; num < e.size(); num++){
                        String cds_name = e.get(num).getName();
                        String cds_address = e.get(num).getAddress();
                        String cds_capacity = e.get(num).getInfo();

                        lva.addItem(cds_name, cds_address, cds_capacity);

                    }

                    locationList = e;
                    onMapReady(map);

                    Toast.makeText(getApplicationContext(),"총 " + num +" 개 검색 성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });

        tvFindNearestLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "location/civildefense/nearest?x=" + cur_lon + "&y=" + cur_lat;
                    String result = new HttpResult().execute(url).get();
                    LocationModel e = new ObjectMapper().readValue(result, LocationModel.class);

                    ListViewAdapterCDS lva = new ListViewAdapterCDS("수용인원(명)");
                    listView.setAdapter(lva);

                    String cds_name = e.getName();
                    String cds_address = e.getAddress();
                    String cds_capacity = e.getInfo();

                    lva.addItem(cds_name, cds_address, cds_capacity);

                    locationList = new ArrayList<>();
                    locationList.add(e);
                    onMapReady(map);

                    Toast.makeText(getApplicationContext(),"검색 성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });

        try {
            String url = "location/civildefense/near?x=" + cur_lon + "&y=" + cur_lat;
            String result = new HttpResult().execute(url).get();
            ArrayList<LocationModel> e = new ObjectMapper().readValue(result, new TypeReference<ArrayList<LocationModel>>(){});

            ListViewAdapterCDS lva = new ListViewAdapterCDS("수용인원(명)");
            listView.setAdapter(lva);

            int num;

            for(num = 0; num < e.size(); num++){
                String cds_name = e.get(num).getName();
                String cds_address = e.get(num).getAddress();
                String cds_capacity = e.get(num).getInfo();

                lva.addItem(cds_name, cds_address, cds_capacity);

            }

            locationList = e;
            onMapReady(map);

            Toast.makeText(getApplicationContext(),"총 " + num +" 개 검색 성공!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
