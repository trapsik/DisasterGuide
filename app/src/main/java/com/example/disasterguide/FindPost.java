package com.example.disasterguide;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.LocationModel;
import com.example.disasterguide.model.PostModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class FindPost extends AppCompatActivity {
    ImageView back;
    TextView find;
    EditText title;
    ListView listView;

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_post);

        back = (ImageView) findViewById(R.id.back);
        find = (TextView) findViewById(R.id.find_post);
        title = (EditText) findViewById(R.id.post_title);
        listView = (ListView) findViewById(R.id.listPost);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String url = "post/search?title=" + title.getText().toString();
                    String result = new HttpResult().execute(url).get();
                    ArrayList<PostModel> p = new ObjectMapper().readValue(result, new TypeReference<ArrayList<PostModel>>() {
                    });

                    ListViewAdapterPost lvap = new ListViewAdapterPost();
                    listView.setAdapter(lvap);

                    int num;

                    for (num = 0; num < p.size(); num++) {
                        String post_title = p.get(num).getTitle();
                        String post_content = p.get(num).getContent();
                        String post_time = p.get(num).getDate();
                        int post_no = p.get(num).getPost_no();

                        lvap.addItem(post_title, post_content, post_time, post_no);
                    }
                    Toast.makeText(getApplicationContext(), "총 " + num + " 개 검색 성공!", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {

                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


    }
}
