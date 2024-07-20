package com.example.quanly.Abstract;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.quanly.R;
import com.example.quanly.activity.MainActivity;
import com.example.quanly.activity.TaiKhoanActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public abstract class BottomNavigationActivity extends AppCompatActivity {

    protected BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        initControl();

        FrameLayout frameLayout = findViewById(R.id.frame_container);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(getLayoutResourceId(), frameLayout, false);
        frameLayout.addView(view);
    }

    protected abstract int getLayoutResourceId();

    private void initControl() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home_bottom) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return true;
                }else if(item.getItemId() == R.id.taikhoan_bottom){
                    Intent intent = new Intent(getApplicationContext(), TaiKhoanActivity.class);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bottomNavigationView.getMenu().findItem(getSelectedItemId()).setChecked(true);
    }

    protected abstract int getSelectedItemId();
}