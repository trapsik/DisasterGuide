package com.example.disasterguide;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.disasterguide.api.HttpResult;
import com.example.disasterguide.model.ResponseModel;
import com.example.disasterguide.model.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    BottomNavi activity;
    private View view;
    TextView tv_id, tv_name, tv_birthDate, tv_phoneNum, tv_writtenPost, tv_writtenComment, tv_unregister;
    String id, name, birthDate, phoneNumber, writtenPost, writtenComment;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OptionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_setting, container, false);
        tv_id = (TextView) view.findViewById(R.id.myPage_id);
        tv_name = (TextView) view.findViewById(R.id.myPage_name);
        tv_birthDate = (TextView) view.findViewById(R.id.myPage_birthDate);
        tv_phoneNum = (TextView) view.findViewById(R.id.myPage_phoneNumber);
        tv_writtenPost = (TextView) view.findViewById(R.id.myPage_writtenPost);
        tv_writtenComment = (TextView) view.findViewById(R.id.myPage_writtenComment);
        tv_unregister = (TextView) view.findViewById(R.id.myPage_unregister);

        id = Login.userID;
        try{
            String Url = "user?id=" + id;
            String userResult = new HttpResult().execute(Url).get();
            UserModel u = new ObjectMapper().readValue(userResult, UserModel.class);
            String Url2 = "post/count?id=" + id;
            String userResult2 = new HttpResult().execute(Url2).get();
            ResponseModel r = new ObjectMapper().readValue(userResult2, ResponseModel.class);
            String Url3 = "comment/count?id=" + id;
            String userResult3 = new HttpResult().execute(Url3).get();
            ResponseModel r2 = new ObjectMapper().readValue(userResult3, ResponseModel.class);
            name = u.getName();
            birthDate = u.getBirth();
            phoneNumber = u.getPhone();
            writtenPost = r.getMessage();
            writtenComment = r2.getMessage();
        }catch (Exception e){
            Toast.makeText(getActivity(), "오류", Toast.LENGTH_SHORT).show();
        }

        tv_id.append(id);
        tv_name.append(name);
        tv_birthDate.append(birthDate);
        tv_phoneNum.append(phoneNumber);
        tv_writtenPost.append(writtenPost);
        tv_writtenComment.append(writtenComment);

        tv_unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("회원탈퇴");
                builder.setMessage("정말로 탈퇴하시겠어요?");
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                });
                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            String Url = "user/delete?id=" + id;
                            String result = new HttpResult().execute(Url).get();
                            ResponseModel r = new ObjectMapper().readValue(result, ResponseModel.class);
                            if(r.getResponsecode() == 1){
                                Toast.makeText(getActivity(), "회원 정보가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(), Login.class);
                                startActivity(intent);
                            }else if(r.getResponsecode() == 0){
                                Toast.makeText(getActivity(),"회원 정보가 삭제되지 않았습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }catch (Exception e){
                            Toast.makeText(getActivity(), "오류", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();

            }
        });


        return view;
    }
}