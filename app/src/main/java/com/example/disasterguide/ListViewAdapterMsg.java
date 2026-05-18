package com.example.disasterguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapterMsg extends BaseAdapter {
    private ArrayList<ListViewItemMsg> listViewItemListMsg = new ArrayList<ListViewItemMsg>();

    public ListViewAdapterMsg(){

    }
    @Override
    public int getCount() {
        return listViewItemListMsg.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemListMsg.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_msg, parent, false);
        }

        TextView dateTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView locationTextView = (TextView) convertView.findViewById(R.id.textView2);
        TextView contentTextView = (TextView) convertView.findViewById(R.id.textView3);

        ListViewItemMsg listViewItemMsg = listViewItemListMsg.get(position);

        dateTextView.setText(listViewItemMsg.getDate());
        locationTextView.setText(listViewItemMsg.getLocation());
        contentTextView.setText(listViewItemMsg.getContent());

        return convertView;
    }
    public void addItem(String date, String location, String content){
        ListViewItemMsg item = new ListViewItemMsg();

        item.setDate(date);
        item.setLocation(location);
        item.setContent(content);

        listViewItemListMsg.add(item);
    }
}
