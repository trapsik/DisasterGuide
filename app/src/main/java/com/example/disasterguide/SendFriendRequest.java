package com.example.disasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.ResponseModel;
import com.example.disasterguide.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SendFriendRequest extends AppCompatActivity {
    ImageView back;
    EditText id;
    TextView add;
    TextView search;
    TextView info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_friend_request);

        back=(ImageView) findViewById(R.id.back);
        id = (EditText) findViewById(R.id.edtFriendID);
        search = (TextView) findViewById(R.id.searchFriend);
        add = (TextView) findViewById(R.id.addFriend);
        info = (TextView) findViewById(R.id.tv_searchPeopleInfo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent = new Intent();
                outIntent.putExtra("result","");
                setResult(RESULT_OK, outIntent);
                finish();
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addFriendID = id.getText().toString();
                try{
                    String Url = "user?id=" + addFriendID;
                    String userResult = new HttpResult().execute(Url).get();
                    UserModel u = new ObjectMapper().readValue(userResult, UserModel.class);
                    String searchID = ""; String searchName = ""; String searchInfo="";
                    if(u.getId().equals("데이터베이스 오류")){
                        Toast.makeText(getApplicationContext(), "없는 ID 입니다.", Toast.LENGTH_SHORT).show();
                    }else {
                        searchID = u.getId();
                        searchName = u.getName();
                        searchInfo = "아이디 : " + searchID + "\n이름 : " + searchName;
                        info.setText(searchInfo);
                    }
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addFriendID = id.getText().toString();
                try {
                    String url = "friend/request?user1="+Login.userID+"&user2="+addFriendID;
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        Toast.makeText(getApplicationContext(), "친구 요청 전송 완료!", Toast.LENGTH_SHORT).show();
                        Intent outIntent = new Intent();
                        outIntent.putExtra("result","");
                        setResult(RESULT_OK, outIntent);
                        finish();
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(getApplicationContext(), "해당 ID가 존재하지 않거나 이미 보낸 요청입니다.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
