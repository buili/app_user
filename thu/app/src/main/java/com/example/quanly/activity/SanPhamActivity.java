package com.example.quanly.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.Abstract.BottomNavigationActivity;
import com.example.quanly.R;
import com.example.quanly.adapter.SanPhamAdapter;
import com.example.quanly.model.SanPham;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.CheckConnection;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SanPhamActivity extends BottomNavigationActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;

    List<SanPham> mangsp;
    SanPhamAdapter sanPhamAdapter;
    int idloai = 0;
    int page = 1;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    LinearLayoutManager linearLayoutManager;

    Handler handler = new Handler();
    boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_san_pham);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        idloai = getIntent().getIntExtra("idloai", 2);
        Log.d("Laydulieu", "Loi: " + idloai);
        InitView();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolBar();
            GetData(page);
            addEventLoad();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Vui lòng kiểm tra wifi");
            finish();
        }


    }

    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading && linearLayoutManager.findLastCompletelyVisibleItemPosition() == mangsp.size() - 1) {
                    isLoading = true;
                    loadMore();
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mangsp.add(null);
                sanPhamAdapter.notifyItemInserted(mangsp.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mangsp.remove(mangsp.size() - 1);
                sanPhamAdapter.notifyItemRemoved(mangsp.size());
                page = page + 1;
                GetData(page);
                sanPhamAdapter.notifyDataSetChanged();
                //  isLoading = false;
            }
        }, 2000);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_san_pham;
    }

    @Override
    protected int getSelectedItemId() {
        return R.id.home_bottom;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Chung.createMenu(this, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Chung.itemMenuSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    private void GetData(int page) {
        compositeDisposable.add(apiBanHang.getsp(page, idloai)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                sanPhamModel -> {
                                    if (sanPhamModel.isSuccess()) {
                                        if (sanPhamAdapter == null) {
                                            mangsp = sanPhamModel.getResult();
                                            sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), mangsp);
                                            recyclerView.setAdapter(sanPhamAdapter);
                                        } else {
//                                    int vitri = mangsp.size()-1;
//                                    int soluongadd = sanPhamModel.getResult().size();
//                                    for(int i = 0; i < soluongadd; i++){
//                                        mangsp.add(sanPhamModel.getResult().get(i));
//                                    }
                                            int vitri = mangsp.size();
                                            int soluongadd = sanPhamModel.getResult().size();

                                            // Thêm dữ liệu mới vào danh sách
                                            mangsp.addAll(sanPhamModel.getResult());
                                            sanPhamAdapter.notifyItemRangeInserted(vitri, soluongadd);
                                        }
                                        isLoading = false;
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Hết dữ liệu", Toast.LENGTH_SHORT).show();
                                        isLoading = true;
                                    }
                                },
                                throwable -> {
                                    Log.e("Laydulieu", "Loi", throwable);
                                    Toast.makeText(getApplicationContext(), "Không lấy được dữ liệu sản phẩm", Toast.LENGTH_SHORT).show();
                                    isLoading = false;
                                }
                        )
        );
    }


    private void ActionToolBar() {
        Chung.ActionToolBar(this, toolbar);
    }

    private void InitView() {
        toolbar = findViewById(R.id.toolbarsp);
        recyclerView = findViewById(R.id.recycleviewsanpham);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mangsp = new ArrayList<>();

        compositeDisposable.add(apiBanHang.giohang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            //gioHangAdapter = new GioHangAdapter(getApplicationContext(), gioHangModel.getResult());
                            Utils.manggiohang = gioHangModel.getResult();
                            invalidateOptionsMenu();
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}