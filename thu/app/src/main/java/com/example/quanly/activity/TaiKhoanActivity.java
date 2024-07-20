package com.example.quanly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quanly.Abstract.BottomNavigationActivity;
import com.example.quanly.R;
import com.example.quanly.adapter.DonHangAdapter;
import com.example.quanly.adapter.TienIchAdapter;
import com.example.quanly.model.TienIch;
import com.example.quanly.ultil.Chung;

import java.util.ArrayList;
import java.util.List;

public class TaiKhoanActivity extends BottomNavigationActivity {

    String ten[] = {"Khách hàng thaanh thiết", "Mua lại", "Kênh người sáng tạo", "Số dư TK", "Săn Thưởng", "Ví"};
    int img[] = {R.drawable.baseline_playlist_add_check_circle_24, R.drawable.baseline_local_car_wash_24
    , R.drawable.baseline_email_24, R.drawable.baseline_home_24,
    R.drawable.baseline_stars_24, R.drawable.baseline_person_24};

    ArrayList<TienIch> tienIchList;
    TienIchAdapter tienIchAdapter;
    GridView gridView;
    TextView txt_lichsu;
    ImageView  img_choxacnhan;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_tai_khoan);
        initView();
        getData();
        initControl();
        actionToolBar();
    }

    private void actionToolBar() {
            Chung.ActionToolBar(this, toolbar);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_tai_khoan;
    }
    @Override
    protected int getSelectedItemId() {
         return R.id.taikhoan_bottom;
    }
    private void initControl() {
        txt_lichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DonHangActivity.class);
                startActivity(intent);
            }
        });

        img_choxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donmua = new Intent(getApplicationContext(), DonMuaActivity.class);
                startActivity(donmua);
            }
        });

    }



    private void initView() {
        gridView = findViewById(R.id.gridview);
        txt_lichsu = findViewById(R.id.txtlichsu);
        img_choxacnhan = findViewById(R.id.imgchoxacnhan);
        toolbar = findViewById(R.id.toolbar_taikhoan);
        tienIchList = new ArrayList<>();
    }

    private void getData() {
        for (int i =0; i < 6; i++){
            tienIchList.add(new TienIch(img[i], ten[i]));
        }
        tienIchAdapter = new TienIchAdapter(TaiKhoanActivity.this, R.layout.item_tienich, tienIchList);
        gridView.setAdapter(tienIchAdapter);
    }
}