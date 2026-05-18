package com.example.disasterguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Login extends AppCompatActivity {

    EditText etID;
    EditText etPW;
    TextView login;
    TextView join;
    static String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        etID = (EditText) findViewById(R.id.login_id);
        etPW = (EditText) findViewById(R.id.login_password);
        login = (TextView) findViewById(R.id.tv_login);
        join = (TextView) findViewById(R.id.tv_join);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "login?id="+etID.getText().toString()+"&pw="+etPW.getText().toString();
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        userID = etID.getText().toString();
                        Toast.makeText(getApplicationContext(),"로그인 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), BottomNavi.class);
                        startActivity(intent);
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(getApplicationContext(),"아이디나 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                }

            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });
    }
}