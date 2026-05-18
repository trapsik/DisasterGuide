package com.example.disasterguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationBarView;

public class BottomNavi extends AppCompatActivity {

    HomeFragment homeFragment;
    FriendFragment friendFragment;
    CommunityFragment communityFragment;
    WeatherFragment weatherFragment;
    SettingFragment settingFragment;
    NavigationBarView navigationBarView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        homeFragment = new HomeFragment();
        friendFragment = new FriendFragment();
        communityFragment = new CommunityFragment();
        weatherFragment = new WeatherFragment();
        settingFragment = new SettingFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();//초기화면 설정

        navigationBarView = findViewById(R.id.bot_navigation);

        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.homeMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.friendMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, friendFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.communityMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, communityFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.weatherMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, weatherFragment).addToBackStack(null).commit();
                        return true;
                    case R.id.optionMenu:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).addToBackStack(null).commit();
                        return true;
                }
                return false;
            }
        });
    }
}
