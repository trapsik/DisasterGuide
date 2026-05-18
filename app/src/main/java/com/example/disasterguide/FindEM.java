package com.example.disasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.MessageModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class FindEM extends AppCompatActivity {
    ImageView back;
    TextView tvFind;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_em);

        ListView listView = findViewById(R.id.listEm);
        back = (ImageView) findViewById(R.id.back);
        tvFind = (TextView) findViewById(R.id.em_find);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), BottomNavi.class);
                startActivity(intent);
            }
        });

        tvFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "message/latest";
                    String result = new HttpResult().execute(url).get();
                    ArrayList<MessageModel> e = new ObjectMapper().readValue(result, new TypeReference<ArrayList<MessageModel>>() {
                    });

                    ListViewAdapterMsg lvam = new ListViewAdapterMsg();
                    listView.setAdapter(lvam);
                    int num;

                    for (num = 0; num < e.size(); num++) {
                        String em_location = e.get(num).getLocation();
                        String em_content = e.get(num).getContent();
                        String em_date = e.get(num).getDate();

                        lvam.addItem(em_date, em_location, em_content);
                    }

                    Toast.makeText(getApplicationContext(), "재난문자 최근 100개 조회 성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });

    }
}
