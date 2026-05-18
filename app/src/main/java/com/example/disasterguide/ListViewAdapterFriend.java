package com.example.disasterguide;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.FriendModel;
import com.example.disasterguide.model.ResponseModel;
import com.example.disasterguide.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ListViewAdapterFriend extends BaseAdapter {

    String friendUser1, friendUser2;
    TextView deleteTextView;
    private ArrayList<ListViewItemFriend> listViewItemListFriends = new ArrayList<ListViewItemFriend>();

    public ListViewAdapterFriend(){

    }
    @Override
    public int getCount() {
        return listViewItemListFriends.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemListFriends.get(i);
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
            convertView = inflater.inflate(R.layout.item_listview_friend, parent, false);
        }

        TextView friendIDTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView friendNameTextView = (TextView) convertView.findViewById(R.id.textView2);
        deleteTextView = (TextView) convertView.findViewById(R.id.btn1);

        ListViewItemFriend listViewItemFriend = listViewItemListFriends.get(position);

        friendIDTextView.setText(listViewItemFriend.getFriendID());
        friendNameTextView.setText(listViewItemFriend.getFriendName());

        friendUser1 = listViewItemFriend.getFriendID();
        friendUser2 = Login.userID;

        deleteTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFriend(friendUser1,friendUser2);
            }
        });

        return convertView;
    }
    public void addItem(String id, String name){
        ListViewItemFriend item = new ListViewItemFriend();

        item.setFriendID(id);
        item.setFriendName(name);

        listViewItemListFriends.add(item);
    }

    public void deleteFriend(String u1, String u2){
        try {
            String deleteUrl = "friend/delete?user1="+u1+"&user2="+u2;
            String deleteResult = new HttpResult().execute(deleteUrl).get();
            ResponseModel r = new ObjectMapper().readValue(deleteResult, ResponseModel.class);
            if(r.getResponsecode() == 1){
                Toast.makeText(deleteTextView.getContext(),"삭제 성공!", Toast.LENGTH_SHORT).show();
            }else if(r.getResponsecode() == 0){
                deleteFriend(u2, u1);
                Toast.makeText(deleteTextView.getContext(),"삭제 성공!", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(deleteTextView.getContext(), "오류", Toast.LENGTH_SHORT).show();
        }
    }
}
