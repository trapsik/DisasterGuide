package com.example.disasterguide;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.XYModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ListViewAdapterFriendLocation extends BaseAdapter {

    private ArrayList<ListViewItemFriendLocation> listViewItemList = new ArrayList<ListViewItemFriendLocation>();
    private Context c;
    String info;
    public ListViewAdapterFriendLocation(){
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_friend_location, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView latitudeTextView = (TextView) convertView.findViewById(R.id.textView2);
        TextView longitudeTextView = (TextView) convertView.findViewById(R.id.textView3);

        ListViewItemFriendLocation listViewItem = listViewItemList.get(position);

//        dateTextView.setText("시간: "+ listViewItem.getDate());
//        latitudeTextView.setText("위도: "+String.valueOf(listViewItem.getLatitude()));
//        longitudeTextView.setText("경도: "+String.valueOf(listViewItem.getLongitude()));
        dateTextView.setText(listViewItem.getDate());
        latitudeTextView.setText(String.valueOf(listViewItem.getLatitude())+"/");
        longitudeTextView.setText(String.valueOf(listViewItem.getLongitude()));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    public void addItem(String date, double lat, double lon){
        ListViewItemFriendLocation item = new ListViewItemFriendLocation();

        item.setDate(date);
        item.setLatitude(lat);
        item.setLongitude(lon);

        listViewItemList.add(item);
    }
}
