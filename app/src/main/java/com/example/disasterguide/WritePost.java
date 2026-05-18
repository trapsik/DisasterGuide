package com.example.disasterguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.LocationModel;
import com.example.disasterguide.model.PostModel;
import com.example.disasterguide.model.ResponseModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class WritePost extends AppCompatActivity {
    ImageView back;
    TextView success;
    EditText title, content;
    CommunityFragment communityFragment;

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.write_post);

        back = (ImageView) findViewById(R.id.back);
        success = (TextView) findViewById(R.id.success);
        title = (EditText) findViewById(R.id.post_title);
        content = (EditText) findViewById(R.id.post_content);
        communityFragment = new CommunityFragment();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        success.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "post/upload?id="+Login.userID+"&title="+title.getText().toString()+"&content="+content.getText().toString();
                    String result = new HttpResult().execute(url).get();
                    ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                    if(r.getResponsecode() == 1){
                        Toast.makeText(getApplicationContext(),"게시글 업로드 성공!", Toast.LENGTH_SHORT).show();
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("result","");
                        setResult(RESULT_OK, resultIntent);
                        finish();
                    }else if(r.getResponsecode() == 0){
                        Toast.makeText(getApplicationContext(),"게시글 등록에 실패했습니다.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "오류", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
