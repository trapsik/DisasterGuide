package com.example.disasterguide;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.CommentModel;
import com.example.disasterguide.model.PostModel;
import com.example.disasterguide.model.ResponseModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class PostView extends AppCompatActivity {//post는 글, comment는 댓글
    ImageView back;
    TextView postUserID, postTime, postTitle, postContent;
    ListView listComment;
    EditText edtComment;
    TextView write;
    String commentUserID, commentContent, commentTime;
    int getPostNum;
    String getID,getContent, getTitle, getTime;



    @Override
    public void onBackPressed() {
        getSupportFragmentManager().popBackStack();
        super.onBackPressed();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_post);

        back = (ImageView) findViewById(R.id.back);
        postUserID = (TextView) findViewById(R.id.member_id);
        postTime = (TextView) findViewById(R.id.postWrittenTime);
        postTitle = (TextView) findViewById(R.id.postWrittenTitle);
        postContent = (TextView) findViewById(R.id.postWrittenContent);
        listComment = (ListView) findViewById(R.id.listComments);
        edtComment = (EditText) findViewById(R.id.edtComment);
        write = (TextView) findViewById(R.id.writeComment);

        Intent intent = getIntent();
        getID = intent.getStringExtra("id");
        getContent = intent.getStringExtra("content");
        getTime = intent.getStringExtra("time");
        getTitle = intent.getStringExtra("title");
        getPostNum = intent.getIntExtra("postNum", 0);

        postUserID.setText(getID);
        postTime.setText(getTime);
        postContent.setText(getContent);
        postTitle.setText(getTitle);

        viewComment();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                writeComment();
                viewComment();
                edtComment.setText("");
                edtComment.requestFocus();
            }
        });



    }
    public void viewComment(){
        try {
            String url = "comment?post_no=" + getPostNum;
            String resultComment = new HttpResult().execute(url).get();
            ArrayList<CommentModel> c = new ObjectMapper().readValue(resultComment, new TypeReference<ArrayList<CommentModel>>(){});

            ListViewAdapterComment lvac = new ListViewAdapterComment();
            listComment.setAdapter(lvac);

            int num;
            for(num = 0; num < c.size(); num++){
                commentUserID = c.get(num).getUser_id();
                commentContent = c.get(num).getContent();
                commentTime = c.get(num).getDate();

                lvac.addItem(commentUserID, commentContent, commentTime);
            }

        } catch (Exception e) {

        }
    }

    public void writeComment(){
        try {
            String url = "comment/upload?post_no="+getPostNum+"&id="+Login.userID+"&content="+edtComment.getText().toString();
            String resultComment = new HttpResult().execute(url).get();
            ResponseModel r = new ObjectMapper().readValue(resultComment, ResponseModel.class);

            if(r.getResponsecode() == 1){
                Toast.makeText(getApplicationContext(),"성공!", Toast.LENGTH_SHORT).show();
            }else if(r.getResponsecode() == 0){
                Toast.makeText(getApplicationContext(),"오류.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {

        }
    }
}
