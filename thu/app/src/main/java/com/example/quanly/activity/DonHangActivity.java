package com.example.quanly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.quanly.Interface.ItemClickDeleteListener;
import com.example.quanly.R;
import com.example.quanly.adapter.DonHangAdapter;
import com.example.quanly.model.DonHang;
import com.example.quanly.model.Item;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DonHangActivity extends AppCompatActivity {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);
        inintView();
        actionToolbar();
        donHang();
    }

    private void donHang() {
        compositeDisposable.add(apiBanHang.donhang(Utils.user_current.getId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                donHangModel -> {

                                    Log.d("DonHangActivity", "DonHangModel: " + donHangModel.getResult());
                                    List<DonHang> donHangList = donHangModel.getResult();
//                            if (donHangList == null) {
//                                donHangList = new ArrayList<>();
//                            }

//                            for (DonHang donHang : donHangList) {
//                                List<Item> itemDonHangList = donHang.getItemDonHangList();
//                                int size = (itemDonHangList != null) ? itemDonHangList.size() : 0;
//                                Log.d("DonHangActivity", "DonHang ID: " + donHang.getId() + ", ItemDonHangList size: " + size);
//                            }
                                    DonHangAdapter donHangAdapter = new DonHangAdapter(getApplicationContext(), donHangList, new ItemClickDeleteListener() {

                                        //                                @Override
//                                public void onClickDelete(int iddonhang) {
//                                    xoaDonHang(iddonhang);
//                                }
                                        @Override
                                        public void onClickDelete(View view, int iddonhang) {
                                            xoaDonHang(view, iddonhang);
                                        }

                                    });
                                    recyclerView.setAdapter(donHangAdapter);
                                    donHangAdapter.notifyDataSetChanged();
                                },
                                throwable -> {
                                    Log.e("DonHangActivity", "Lỗi: " + throwable);
                                    Toast.makeText(getApplicationContext(), "Lỗi " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                        )
        );
    }

    //    private void xoaDonHang(int iddonhang) {
//        PopupMenu popupMenu = new PopupMenu(this, recyclerView.findViewById(R.id.trangthaidon));
//        popupMenu.inflate(R.menu.menu_delete);
//        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                if(item.getItemId() == R.id.xoadonhang){
//                    xoa(iddonhang);
//                }
//                return false;
//            }
//        });
//        popupMenu.show();
//    }
    private void xoaDonHang(View view, int iddonhang) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.menu_delete);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.xoadonhang) {
                    xoa(iddonhang);
                }
                return false;
            }
        });
        popupMenu.show();
    }


    private void xoa(int iddonhang) {
        compositeDisposable.add(apiBanHang.xodonhang(iddonhang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        messageModel -> {
                            if (messageModel.isSuccess()) {
                                donHang();
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Xóa lỗi", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void actionToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void inintView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        recyclerView = findViewById(R.id.recyclerview_donhang);
        toolbar = findViewById(R.id.toolbardonhang);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}