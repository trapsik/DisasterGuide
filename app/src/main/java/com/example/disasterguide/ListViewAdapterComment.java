package com.example.disasterguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapterComment extends BaseAdapter {
    private ArrayList<ListViewItemComment> listViewItemListComment = new ArrayList<ListViewItemComment>();

    public ListViewAdapterComment(){

    }
    @Override
    public int getCount() {
        return listViewItemListComment.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemListComment.get(i);
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
            convertView = inflater.inflate(R.layout.item_listview_comment, parent, false);
        }

        TextView memIDTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView commentTextView = (TextView) convertView.findViewById(R.id.textView2);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.textView3);

        ListViewItemComment listViewItemComment = listViewItemListComment.get(position);

        memIDTextView.setText(listViewItemComment.getMemID());
        timeTextView.setText(listViewItemComment.getTime());
        commentTextView.setText(listViewItemComment.getComment());

        return convertView;
    }
    public void addItem(String userID, String comment, String time){
        ListViewItemComment item = new ListViewItemComment();

        item.setMemID(userID);
        item.setComment(comment);
        item.setTime(time);

        listViewItemListComment.add(item);
    }
}
