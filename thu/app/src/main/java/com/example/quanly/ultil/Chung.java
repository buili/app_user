package com.example.quanly.ultil;

import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanly.retrofit.ApiBanHang;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class Chung {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    public static void ActionToolBar(AppCompatActivity activity, Toolbar toolbar) {
       activity.setSupportActionBar(toolbar);
       activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               activity.finish();
            }
        });
    }


}
