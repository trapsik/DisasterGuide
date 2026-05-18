package com.example.disasterguide;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WeatherFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WeatherFragment extends Fragment {

    BottomNavi activity;
    private View view;
    TextView tvWeatherInfo;
    String weatherInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof BottomNavi)
            activity = (BottomNavi) context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WeatherFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WeatherFragment newInstance(String param1, String param2) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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

        view = inflater.inflate(R.layout.fragment_weather, container, false);
        tvWeatherInfo = (TextView) view.findViewById(R.id.tv_weatherInfo);
        weatherInfo="";
        try {
            String url = "weather";
            String resultComment = new HttpResult().execute(url).get();
            ResponseModel r = new ObjectMapper().readValue(resultComment, ResponseModel.class);

            if(r.getResponsecode() == 1){
                weatherInfo = r.getMessage();
                tvWeatherInfo.setText(weatherInfo);
                Toast.makeText(getActivity(),"성공!", Toast.LENGTH_SHORT).show();
            }else if(r.getResponsecode() == 0){
                Toast.makeText(getActivity(),"오류.", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {

        }
        // Inflate the layout for this fragment
        return view;
    }

//    public void viewWeatherInfo(){
//        try {
//            String url = "weather";
//            String resultComment = new HttpResult().execute(url).get();
//            ResponseModel r = new ObjectMapper().readValue(resultComment, ResponseModel.class);
//
//            if(r.getResponsecode() == 1){
//                weatherInfo = r.getMessage();
//                Toast.makeText(getActivity(),"성공!", Toast.LENGTH_SHORT).show();
//            }else if(r.getResponsecode() == 0){
//                Toast.makeText(getActivity(),"오류.", Toast.LENGTH_SHORT).show();
//            }
//        } catch (Exception e) {
//
//        }
//    }
}