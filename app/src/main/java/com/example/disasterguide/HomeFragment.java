package com.example.disasterguide;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.LocationModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    BottomNavi activity;
    private View view;
    private ImageButton img_cds;
    private ImageButton img_es;
    private ImageButton img_eh;
    private ImageButton img_em;
    TextView nearestCDS_name, nearestES_name, nearestEH_name;
    Button btnNearestCDS_direction, btnNearestES_direction, btnNearestEH_direction;
    Switch switchView;

    LocationManager locationManager; //위치관리자
    boolean isGPSEnabled;
    boolean isNetworkEnabled;
    String provider;
    double cur_lat, cur_lon;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BottomNavi)
            activity = (BottomNavi) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ContextCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION);
        ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.INTERNET);

        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
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

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        img_cds = (ImageButton) view.findViewById(R.id.civilDefenseShelter);
        img_es = (ImageButton) view.findViewById(R.id.earthquakeShelter);
        img_eh = (ImageButton) view.findViewById(R.id.emergencyHospital);
        img_em = (ImageButton) view.findViewById(R.id.emergencyMessage);

        nearestCDS_name = (TextView) view.findViewById(R.id.tv_nearestCDS);
        nearestES_name = (TextView) view.findViewById(R.id.tv_nearestES);
        nearestEH_name = (TextView) view.findViewById(R.id.tv_nearestEH);
        btnNearestCDS_direction = (Button) view.findViewById(R.id.btnDirectionCDS);
        btnNearestES_direction = (Button) view.findViewById(R.id.btnDirectionES);
        btnNearestEH_direction = (Button) view.findViewById(R.id.btnDirectionEH);
        switchView = (Switch) view.findViewById(R.id.swDisasterMode);

        viewNearestCDS();
        viewNearestES();
        viewNearestEH();

        img_cds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindCDS.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        img_es.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindES.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
//
        img_eh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindEH.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
//
        img_em.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindEM.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        btnNearestCDS_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNearestES_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnNearestEH_direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        switchView.setOnCheckedChangeListener(new Switch.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if(isChecked) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("재난모드 ON!");
                    builder.setMessage("위치정보 공유하겠습니까?");
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    });
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // 본인 위치를 친구들에게 알려주는
                        }
                    });
                    builder.show();
                }
            }
        });

        return view;
    }
    public void viewNearestCDS(){
        try {
            String url = "location/civildefense/nearest?x=" + cur_lon + "&y=" + cur_lat;
            String result = new HttpResult().execute(url).get();
            LocationModel e = new ObjectMapper().readValue(result, LocationModel.class);
            nearestCDS_name.setText(e.getName());
        }catch (Exception e){

        }
    }
    public void viewNearestES(){
        try {
            String url = "location/earthquake/nearest?x=" + cur_lon + "&y=" + cur_lat;
            String result = new HttpResult().execute(url).get();
            LocationModel e = new ObjectMapper().readValue(result, LocationModel.class);
            nearestES_name.setText(e.getName());
        }catch (Exception e){

        }
    }
    public void viewNearestEH(){
        try {
            String url = "location/hospital/nearest?x=" + cur_lon + "&y=" + cur_lat;
            String result = new HttpResult().execute(url).get();
            LocationModel e = new ObjectMapper().readValue(result, LocationModel.class);
            nearestEH_name.setText(e.getName());
        }catch (Exception e){

        }
    }
}