package com.example.quanly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.R;
import com.example.quanly.adapter.GioHangAdapter;
import com.example.quanly.model.EventBus.TinhTongEvent;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView giohangtrong, tongtien;
    CheckBox checkBox;
    RecyclerView recyclerView;
    AppCompatButton btnmuahang;
    GioHangAdapter adapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    LinearLayoutManager linearLayoutManager;

    long tongTienSpMua = 0;

    MenuItem menuSua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        InitView();
        ActionToolBar();
        InitControl();

        tinhTongTien();

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (adapter != null) {
                    adapter.selectAll(isChecked);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Chung.createMenu(this, menu);
        MenuItem menuGioHang = menu.findItem(R.id.menugiohang);
        menuSua = menu.findItem(R.id.menusua);
        menuGioHang.setVisible(false);
        menuSua.setVisible(true);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menusua) {
            if (Utils.mangmuahang.size() > 0) {
                int iduuser = Utils.user_current.getId();
                for (int i = 0; i < Utils.mangmuahang.size(); i++) {
                    xoasp(iduuser, Utils.mangmuahang.get(i).getIdsp());
                }
            } else {
                Toast.makeText(getApplicationContext(), "Vui lòng chọn ít nhất một sản phẩm", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        Chung.itemMenuSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    private void xoasp(int idUser, int idSp) {
        compositeDisposable.add(apiBanHang.xoaspgiohang(idUser, idSp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            Utils.mangmuahang.clear();
                            Utils.manggiohang.removeIf(item -> item.getIdsp() == idSp);
                            adapter.notifyDataSetChanged();
                            updateUI();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Xóa giỏ hàng lỗi", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void updateUI() {
        if (Utils.manggiohang.size() == 0) {
            giohangtrong.setVisibility(View.VISIBLE);
            //btnmuahang.setVisibility(View.INVISIBLE);
        } else {
            giohangtrong.setVisibility(View.GONE);
            btnmuahang.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        }
        tinhTongTien();
    }

    private void tinhTongTien() {
        tongTienSpMua = 0;
        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
            tongTienSpMua += Utils.mangmuahang.get(i).getSanPham().getGia() * Utils.mangmuahang.get(i).getSoluong();
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongtien.setText(decimalFormat.format(tongTienSpMua) + " Đ");
    }

    private void InitControl() {
        if (Utils.manggiohang.size() == 0) {
            giohangtrong.setVisibility(View.VISIBLE);
            //btnmuahang.setVisibility(View.INVISIBLE);
        } else {
            adapter = new GioHangAdapter(getApplicationContext(), Utils.manggiohang);
            recyclerView.setAdapter(adapter);
        }


        btnmuahang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.mangmuahang.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Vui lòng chọn ít nhất một sản phẩm", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ThanhToanActivity.class);
                    intent.putExtra("tongtien", tongTienSpMua);
                    Utils.manggiohang.clear();
                    startActivity(intent);
                }
            }
        });


    }


    private void ActionToolBar() {
        Chung.ActionToolBar(this, toolbar);
    }

    private void InitView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbargiohang);
        recyclerView = findViewById(R.id.recyclerviewgiohang);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        giohangtrong = findViewById(R.id.txtgiohangtrong);
        tongtien = findViewById(R.id.txttongtiengiohang);

        checkBox = findViewById(R.id.checkboxtong);
        checkBox.setChecked(false);

        btnmuahang = findViewById(R.id.btnmuahang);
    }

    @Override
    protected void onStart() {
        super.onStart();

        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventTinhTien(TinhTongEvent event) {
        if (event != null) {
            tinhTongTien();
        }
    }
}