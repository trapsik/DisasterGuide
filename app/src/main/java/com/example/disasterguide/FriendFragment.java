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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.FriendModel;
import com.example.disasterguide.model.UserModel;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FriendFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendFragment extends Fragment {

    BottomNavi activity;
    private View view;

    //EditText edtFriendID;
    //TextView tvFindButton, tvAddButton;
    ListView friendList, friendRequestList;
    String friendName, friendID ,friendUser1, friendUser2;
    ImageView search_user;

    private Handler handler;
    ArrayList<FriendModel> f;
    ListViewAdapterFriendRequest lvafr;
    ListViewAdapterFriend lvaf;
    //private boolean isFocused = false;
    boolean isRefreshing = false;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FriendFragment() {
        // Required empty public constructor
    }

    public void refreshList(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                    if(!isRefreshing){
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().detach(FriendFragment.this).commit();
                                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction().attach(FriendFragment.this).commit();

                            }
                        });
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }).start();
    }

    public void viewFriendRequestList(){
        try {
            String url = "friend/list?id="+Login.userID;//최근 100개
            String result = new HttpResult().execute(url).get();
            f = new ObjectMapper().readValue(result, new TypeReference<ArrayList<FriendModel>>(){});

            lvafr = new ListViewAdapterFriendRequest();
            friendRequestList.setAdapter(lvafr);

            int num;
            for(num = 0; num < f.size(); num++){
                if(!f.get(num).isState()){
                    friendUser1 = f.get(num).getUser1();
                    friendUser2 = f.get(num).getUser2();
                    if(friendUser2.equals(Login.userID)){
                        friendID = friendUser1;
                        String userUrl = "user?id=" + friendID;
                        String userResult = new HttpResult().execute(userUrl).get();
                        UserModel u = new ObjectMapper().readValue(userResult, UserModel.class);
                        friendName = u.getName();
                        lvafr.addItem(friendID, friendName);
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "오류", Toast.LENGTH_SHORT).show();
        }
    }

    public void viewFriendList(){//리스트에 나오게 하는 코드
        try {
            String url = "friend/list?id="+Login.userID;//최근 100개
            String result = new HttpResult().execute(url).get();
            f = new ObjectMapper().readValue(result, new TypeReference<ArrayList<FriendModel>>(){});

            lvaf = new ListViewAdapterFriend();
            friendList.setAdapter(lvaf);

            int num;
            for(num = 0; num < f.size(); num++){
                if(f.get(num).isState()){
                    friendUser1 = f.get(num).getUser1();
                    friendUser2 = f.get(num).getUser2();
                    if(friendUser1.equals(Login.userID)){
                        friendID = friendUser2;
                        String userUrl = "user?id=" + friendID;
                        String userResult = new HttpResult().execute(userUrl).get();
                        UserModel u = new ObjectMapper().readValue(userResult, UserModel.class);
                        friendName = u.getName();
                    }else{
                        friendID = friendUser1;
                        String userUrl = "user?id=" + friendID;
                        String userResult = new HttpResult().execute(userUrl).get();
                        UserModel u = new ObjectMapper().readValue(userResult, UserModel.class);
                        friendName = u.getName();
                    }
                    lvaf.addItem(friendID, friendName);
                }
            }

        } catch (Exception e) {
            Toast.makeText(getActivity(), "오류", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof BottomNavi)
            activity = (BottomNavi) context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FriendFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FriendFragment newInstance(String param1, String param2) {
        FriendFragment fragment = new FriendFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_friend, container, false);
        friendList = (ListView) view.findViewById(R.id.listFriend);
        friendRequestList = (ListView) view.findViewById(R.id.listFriendRequest);
        //edtFriendID = (EditText) view.findViewById(R.id.edtFriendID);
        //tvAddButton = (TextView) view.findViewById(R.id.addFriend);
        search_user = (ImageView) view.findViewById(R.id.search_userID);

        viewFriendList();
        viewFriendRequestList();
        refreshList();//5초마다 새로고침

        ActivityResultLauncher<Intent> resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            isRefreshing = false;
                            refreshList();
                        }
                    }
                });


        search_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRefreshing = true;
                Intent intent = new Intent(getActivity(), SendFriendRequest.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                resultLauncher.launch(intent);
            }
        });

        friendList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                isRefreshing = true;
                Intent intent = new Intent(getActivity(), CheckFriendLocation.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                intent.putExtra("friendID", friendID);
                intent.putExtra("friendName", friendName);
                resultLauncher.launch(intent);
            }
        });



        return view;
    }

}