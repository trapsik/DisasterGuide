package com.example.disasterguide;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.LocationModel;
import com.example.disasterguide.model.PostModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommunityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommunityFragment extends Fragment {

    BottomNavi activity;
    private View view;
    ListView listView;
    ImageView search_post;
    Button write;
    String postTitle, postContent, postTime, userID;
    int postNum;
    ArrayList<PostModel> p;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommunityFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommunityFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CommunityFragment newInstance(String param1, String param2) {
        CommunityFragment fragment = new CommunityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public void viewPostList(){//리스트에 나오게 하는 코드
        try {
            String url = "post/list?page=1";//최근 100개
            String result = new HttpResult().execute(url).get();
            p = new ObjectMapper().readValue(result, new TypeReference<ArrayList<PostModel>>(){});

            ListViewAdapterPost lvap = new ListViewAdapterPost();
            listView.setAdapter(lvap);

            int num;
            for(num = 0; num < p.size(); num++){
                postTitle = p.get(num).getTitle();
                postContent = p.get(num).getContent();
                postTime = p.get(num).getDate();
                userID = p.get(num).getUser_id();
                postNum = p.get(num).getPost_no();

                lvap.addItem(postTitle, postContent, postTime, postNum);
            }

//            Toast.makeText(getApplicationContext(),"검색 성공!", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BottomNavi)
            activity = (BottomNavi) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_community, container, false);
        listView = (ListView) view.findViewById(R.id.listPost);
        search_post = (ImageView) view.findViewById(R.id.search_post);
        write = (Button) view.findViewById(R.id.writeButton);

        viewPostList();

        /*글 작성 후 새로고침*/
        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            viewPostList();
                        }
                    }
                });

        write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WritePost.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                resultLauncher.launch(intent);
            }
        });

        search_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FindPost.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), PostView.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("title", p.get(i).getTitle());
                intent.putExtra("content", p.get(i).getContent());
                intent.putExtra("time", p.get(i).getDate());
                intent.putExtra("id", p.get(i).getUser_id());
                intent.putExtra("postNum", p.get(i).getPost_no());
                startActivity(intent);
            }
        });

        return view;
    }
}