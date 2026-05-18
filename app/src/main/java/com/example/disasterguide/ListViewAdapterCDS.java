package com.example.disasterguide;

import android.app.Activity;
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
import com.example.disasterguide.model.MarkerModel;
import com.example.disasterguide.model.XYModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ListViewAdapterCDS extends BaseAdapter {

    private ArrayList<ListViewItemCDS> listViewItemList = new ArrayList<ListViewItemCDS>();
    private Context c;
    String info;
    public ListViewAdapterCDS(String info){
        this.info = info;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView addressTextView = (TextView) convertView.findViewById(R.id.textView2);
        TextView capacityTextView = (TextView) convertView.findViewById(R.id.textView3);
        Button viewMap = (Button) convertView.findViewById(R.id.btnMap);
        Button findRoad = (Button) convertView.findViewById(R.id.btnFindRoad);

        ListViewItemCDS listViewItem = listViewItemList.get(position);

        nameTextView.setText(listViewItem.getName());
        addressTextView.setText(listViewItem.getAddress());
        capacityTextView.setText(listViewItem.getCapacity());

        viewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String get = new HttpResult().execute("location/locationtoxy?address="+listViewItem.getAddress()).get();
                    XYModel xy = new ObjectMapper().readValue(get, XYModel.class);
                    FindCDS.map.clear();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(xy.y, xy.x))
                            .title(listViewItem.getName())
                            .snippet(listViewItem.getAddress());
                    FindCDS.map.addMarker(markerOptions).showInfoWindow();
                    FindCDS.map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(xy.y, xy.x), 13));

                } catch (Exception e) {
                }
            }
        });

        findRoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String get = new HttpResult().execute("location/locationtoxy?address="+listViewItem.getAddress()).get();
                    XYModel xy = new ObjectMapper().readValue(get, XYModel.class);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("kakaomap://route?sp=" + FindCDS.cur_lat + ","+ FindCDS.cur_lon +"&ep=" + xy.y + "," + xy.x + "&by=CAR"));
                    context.startActivity(intent);
                } catch (Exception e) {
                }
            }
        });

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

    public void addItem(String name, String address, String capacity){
        ListViewItemCDS item = new ListViewItemCDS();

        item.setName(name);
        item.setAddress(address);
        item.setCapacity(info+ " : " + capacity);

        listViewItemList.add(item);
    }
}
