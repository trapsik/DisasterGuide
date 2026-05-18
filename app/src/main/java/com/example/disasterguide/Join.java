package com.example.disasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.ResponseModel;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Join extends AppCompatActivity {

    EditText id;
    EditText pw;
    EditText phone_number;
    EditText name;
    EditText birthDate;
    Button enter;
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_member);

        id = (EditText) findViewById(R.id.join_id);
        pw = (EditText) findViewById(R.id.join_pw);
        phone_number = (EditText) findViewById(R.id.join_phoneNumber);
        name = (EditText) findViewById(R.id.join_name);
        birthDate = (EditText) findViewById(R.id.join_birthDate);
        enter = (Button) findViewById(R.id.btnJoin);
        back = (ImageView) findViewById(R.id.back);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userID = id.getText().toString();
                String userPassword = pw.getText().toString();
                String userName = name.getText().toString();
                String userPhone = phone_number.getText().toString();
                String userBirth = birthDate.getText().toString();
                try {
                    String url = "signup?id="+userID+"&pw="+userPassword+"&name="+userName+
                            "&birth="+userBirth+"&phone="+userPhone;
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        Toast.makeText(getApplicationContext(),"회원가입 성공!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        startActivity(intent);
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(getApplicationContext(),"중복된 아이디 입니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
        });


    }
}
