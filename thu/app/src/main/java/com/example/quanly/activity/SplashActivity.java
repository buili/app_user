package com.example.quanly.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.quanly.R;
import com.example.quanly.model.User;

import io.paperdb.Paper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Paper.init(this);
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(1500);
                }catch (Exception ex){

                }finally {
                    User user = Paper.book().read("user");
                    if(user == null){
                        Log.d("DangNhap", "Không user" );
                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Log.d("DangNhap", "Có user: " + user.getEmail() + " id = " + user.getId());
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        };
        thread.start();
    }
}