package com.example.quanly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.quanly.R;
import com.example.quanly.adapter.DonMuaPageAdapter;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class DonMuaActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_mua);

        initView();
        actionBar();
        initControl();
    }

    private void actionBar() {
        Chung.ActionToolBar(this, toolbar);
    }

    private void initControl() {
        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                String title = Utils.statusOrder(position);
                tab.setText(title);
            }
        }).attach();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar_donmua);
        tabLayout = findViewById(R.id.tablayout);
        viewPager2 = findViewById(R.id.view_pager);

        DonMuaPageAdapter adapter = new DonMuaPageAdapter(this);
        viewPager2.setAdapter(adapter);
    }
}