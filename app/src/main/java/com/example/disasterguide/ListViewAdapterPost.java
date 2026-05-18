package com.example.disasterguide;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.CommentModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class ListViewAdapterPost extends BaseAdapter {

    private ArrayList<ListViewItemPost> listViewItemListPost = new ArrayList<ListViewItemPost>();
    ArrayList<Integer> commentCountList;
    @Override
    public int getCount() {
        return listViewItemListPost.size();
    }

    @Override
    public Object getItem(int i) {
        return listViewItemListPost.get(i);
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
            convertView = inflater.inflate(R.layout.item_listview_post, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.textView1);
        TextView contentTextView = (TextView) convertView.findViewById(R.id.textView2);
        ImageView img_reply = (ImageView) convertView.findViewById(R.id.img_reply);
        TextView numTextView = (TextView) convertView.findViewById(R.id.tv_replyNum);
        TextView timeTextView = (TextView) convertView.findViewById(R.id.textView3);

        ListViewItemPost listViewItemPost = listViewItemListPost.get(position);
        try{
            String url = "comment?post_no=" + listViewItemPost.getPost_num();//여기가 잘못된듯
            String result = new HttpResult().execute(url).get();
            Log.d("ServerResponse", listViewItemPost.getPost_num()+"");
            ArrayList<CommentModel> c = new ObjectMapper().readValue(result, new TypeReference<ArrayList<CommentModel>>(){});
            numTextView.setText(String.valueOf(c.size()));
        }catch (Exception e){
            e.printStackTrace();
        }

        nameTextView.setText(listViewItemPost.getPostName());
        timeTextView.setText(listViewItemPost.getTime());
        if(listViewItemPost.getContent().length() > 20){
            String shortedText = listViewItemPost.getContent().substring(0, 20) + "...";
            contentTextView.setText(shortedText);
        }else{
            contentTextView.setText(listViewItemPost.getContent());
        }

        ListView list = (ListView) convertView.findViewById(R.id.listPost);

        /*
        만약 댓글이 있다면 이미지 setVisibility + numTextView.setText(listViewItemPost.getReply_num() + " | ");
        없으면 이미지 x + numTextView.setText("");
         */
//        boolean hasComments = (listViewItemPost.getReply_num() > 0);
//        if(hasComments){
//            img_reply.setVisibility(View.VISIBLE);
//            numTextView.setText(listViewItemPost.getReply_num() + " | ");
//        }else{
//            img_reply.setVisibility(View.GONE);
//            numTextView.setText("");
//        }
//        ListViewAdapterComment adapterComment = new ListViewAdapterComment();
//        for(position = 0 ; position < adapterComment.getCount(); position++){
//            ArrayList<ListViewItemComment> listViewItemListComment = new ArrayList<ListViewItemComment>();
//            ListViewItemComment listViewItemComment = listViewItemListComment.get(position);
//            listViewItemComment.getComment();
//        }


        return convertView;
    }
    public void addItem(String name, String content, String time, int num){
        ListViewItemPost item = new ListViewItemPost();
        item.setPostName(name);
        item.setContent(content);
        item.setTime(time);
        item.setPost_num(num);

        listViewItemListPost.add(item);

    }

}