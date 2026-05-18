package com.example.disasterguide;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.FriendModel;
import com.example.disasterguide.model.ResponseModel;
import com.example.disasterguide.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ListViewAdapterFriendRequest<listViewItemListFriendRequests> extends BaseAdapter {

    private ArrayList<ListViewItemFriendRequest> listViewItemListFriendRequests = new ArrayList<ListViewItemFriendRequest>();

    public ListViewAdapterFriendRequest(){
    }
    @Override
    public int getCount() {
        return listViewItemListFriendRequests.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemListFriendRequests.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        RecyclerView.ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_listview_friend_request, parent, false);
        }

        TextView friendIDTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView friendNameTextView = (TextView) convertView.findViewById(R.id.textView2);
        TextView acceptTextView = (TextView) convertView.findViewById(R.id.btn1);
        TextView rejectTextView = (TextView) convertView.findViewById(R.id.btn2);

        ListViewItemFriendRequest listViewItemFriendRequest = listViewItemListFriendRequests.get(position);

        friendIDTextView.setText(listViewItemFriendRequest.getRequestFriendID());
        friendNameTextView.setText(listViewItemFriendRequest.getRequestFriendName());

        acceptTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "friend/accept?user1="+listViewItemFriendRequest.getRequestFriendID()+"&user2="+Login.userID;
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        Toast.makeText(acceptTextView.getContext(),"수락 성공!", Toast.LENGTH_SHORT).show();
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(acceptTextView.getContext(),"수락 실패.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(acceptTextView.getContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        rejectTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "friend/delete?user1="+listViewItemFriendRequest.getRequestFriendID()+"&user2="+Login.userID;
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        Toast.makeText(rejectTextView.getContext(),"거절 성공!", Toast.LENGTH_SHORT).show();
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(rejectTextView.getContext(),"거절 실패.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(rejectTextView.getContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return convertView;
    }
    public void addItem(String id, String name){
        ListViewItemFriendRequest item = new ListViewItemFriendRequest();

        item.setRequestFriendID(id);
        item.setRequestFriendName(name);

        listViewItemListFriendRequests.add(item);
    }
}
