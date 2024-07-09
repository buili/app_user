package com.example.quanly.activity;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.quanly.R;
import com.example.quanly.adapter.GioHangAdapter;
import com.example.quanly.model.GioHang;
import com.example.quanly.model.SanPham;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ChiTietActivity extends AppCompatActivity {
    ImageView imghinhanh;
    TextView tensp, giasp, mota;
    Spinner spinner;
    Toolbar toolbar;
    AppCompatButton btnhthem;

    SanPham sanPham;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    GioHangAdapter gioHangAdapter;

    GioHang gioHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet);
        InitView();
        ActionBar();
        InitData();
        InitControl();
    }



    private void InitControl() {
        btnhthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themgiohang();
            }
        });
    }

    //    private void themgiohang() {
//        if(Utils.manggiohang.size() > 0){
//            boolean flag = false;
//            int soluong =Integer.parseInt(spinner.getSelectedItem().toString());
//            for(int i = 0; i < Utils.manggiohang.size(); i++){
//                if(Utils.manggiohang.get(i).getIdsp() == sanPham.getId()){
//                    Utils.manggiohang.get(i).setSoluongsp(soluong + Utils.manggiohang.get(i).getSoluongsp());
//                    flag = false;
//                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                    break;
//
//                }
//            }
//            if(!flag){
//                GioHang gioHang = new GioHang();
//                gioHang.setSoluongsp(soluong);
//                gioHang.setIdsp(sanPham.getId());
//                gioHang.setTensp(sanPham.getTen());
//                gioHang.setHinhanhsp(sanPham.getHinhanh());
//                Utils.manggiohang.add(gioHang);
//                Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                invalidateOptionsMenu();
//            }
//        }else{
//            int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
//            GioHang gioHang = new GioHang();
//            gioHang.setSoluongsp(soluong);
//            gioHang.setIdsp(sanPham.getId());
//            gioHang.setTensp(sanPham.getTen());
//            gioHang.setHinhanhsp(sanPham.getHinhanh());
//            Utils.manggiohang.add(gioHang);
//            Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//            invalidateOptionsMenu();
//        }
//        int totalItem = 0;
//        if (Utils.manggiohang != null) {
//            for (int i = 0; i < Utils.manggiohang.size(); i++) {
//                totalItem += Utils.manggiohang.get(i).getSoluongsp();
//            }
//        }
    // invalidateOptionsMenu();
//
    //   }
    private void themgiohang() {
        //boolean isNewProduct = true;
        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());

//        if (Utils.manggiohang.size() > 0) {
//            for (int i = 0; i < Utils.manggiohang.size(); i++) {
//                if (Utils.manggiohang.get(i).getIdsp() == sanPham.getId()) {
//                    Utils.manggiohang.get(i).setSoluongsp(soluong + Utils.manggiohang.get(i).getSoluongsp());
//                    isNewProduct = false;
//                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng 2", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//            }
//        }

//        compositeDisposable.add(apiBanHang.giohang(1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        gioHangModel -> {
//                                //gioHangAdapter = new GioHangAdapter(getApplicationContext(), gioHangModel.getResult());
//                                manggiohang = gioHangModel.getResult();

                            // Kiểm tra và thêm sản phẩm vào giỏ hàng
                            boolean isNewProduct = true;
                            if (Utils.manggiohang.size() > 0) {
                                for (int i = 0; i < Utils.manggiohang.size(); i++) {
                                    if (Utils.manggiohang.get(i).getIdsp() == sanPham.getId()) {
                                        int sl_moi = soluong + Utils.manggiohang.get(i).getSoluong();
                                        Utils.manggiohang.get(i).setSoluong(sl_moi);

                                        int iduser = Utils.user_current.getId();
                                        int idsp = sanPham.getId();
                                        int soluong2 = sl_moi;
                                        if(soluong2 >= sanPham.getSltonkho()){
                                            soluong2 = sanPham.getSltonkho();
                                        }
                                        capNhatGioHang(iduser, idsp, soluong2);
                                        isNewProduct = false;
                                      //  Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                        break;
                                    }
                                }
                            }

                            if (isNewProduct) {
                                int iduser = Utils.user_current.getId();
                                int idsp = sanPham.getId();
                                int soluong2 = soluong;
                                if(soluong2 >= sanPham.getSltonkho()){
                                    soluong2 = sanPham.getSltonkho();
                                }
                                themNhatGioHang(iduser, idsp, soluong2);
                            }
//                        },
//                        throwable -> {
//                            Log.e("Themgiohang", "Loi", throwable);
//                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
//                        }
//                )
//        );

//        if (manggiohang.size() > 0) {
//            for (int i = 0; i < manggiohang.size(); i++) {
//                if (manggiohang.get(i).getIdsp() == sanPham.getId()) {
//                    manggiohang.get(i).setSoluongsp(soluong + manggiohang.get(i).getSoluongsp());
//                    isNewProduct = false;
//                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng 2", Toast.LENGTH_SHORT).show();
//                    break;
//                }
//            }
//        }
//
//        if (isNewProduct) {
////            GioHang gioHang = new GioHang();
////            gioHang.setSoluongsp(soluong);
////            gioHang.setIdsp(sanPham.getId());
////            gioHang.setSanPham(sanPham.getTen());
////            gioHang.setHinhanhsp(sanPham.getHinhanh());
////            gioHang.setGiasp(sanPham.getGia());
////            Utils.manggiohang.add(gioHang);
////            Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
////            invalidateOptionsMenu(); // Only invalidate if a new product is added
//            int iduser = 1;
//            int idsp = sanPham.getId();
//            int soluong2 = soluong;
//            compositeDisposable.add(apiBanHang.themgiohang(iduser, idsp, soluong2)
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(
//                            gioHangModel -> {
//                                if(gioHangModel.isSuccess()){
//                                    Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                                    invalidateOptionsMenu(); // Only invalidate if a new product is added
//                                }
//                            },
//                            throwable -> {
//                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//                    )
//            );
//
//
//        }
    }

   private void capNhatGioHang(int idUser, int idSp, int soluong){
       compositeDisposable.add(apiBanHang.updategiohang(idUser, idSp, soluong)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(
                       themGioHangModel -> {
                           if (themGioHangModel.isSuccess()) {
                               Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                               refreshCart();
                               invalidateOptionsMenu(); // Only invalidate if a new product is added
                           }
                       },
                       throwable -> {
                           Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                       }
               )
       );
   }

    private void themNhatGioHang(int idUser, int idSp, int soluong){
        compositeDisposable.add(apiBanHang.themgiohang(idUser, idSp, soluong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        themGioHangModel -> {
                            if (themGioHangModel.isSuccess()) {
                                Toast.makeText(getApplicationContext(), "Đã thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                                refreshCart();
                                invalidateOptionsMenu(); // Only invalidate if a new product is added
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }
    private void InitData() {
        sanPham = (SanPham) getIntent().getSerializableExtra("chitiet");
        tensp.setText(sanPham.getTen());
        mota.setText(sanPham.getMota());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        giasp.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + " Đ");
//        Glide.with(getApplicationContext()).load(sanPham.getHinhanh())
//                .placeholder(R.drawable.noanh)
//                .error(R.drawable.error)
//                .into(imghinhanh);

        if(sanPham.getHinhanh().contains("https")){
            Glide.with(getApplicationContext())
                    .load(sanPham.getHinhanh())
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(imghinhanh);
        }else{
            String hinh = Utils.BASE_URL + "images/" + sanPham.getHinhanh();
            Glide.with(getApplicationContext()).load(hinh)
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(imghinhanh);
        }
      //  Integer[] so = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        List<Integer> so = new ArrayList<>();
        for (int i = 1; i < sanPham.getSltonkho()+1; i++) {
            so.add(i);
        }
        ArrayAdapter<Integer> adapterspinner = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, so);
        spinner.setAdapter(adapterspinner);
    }

//    private void ActionBar() {
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//    }

    private void ActionBar() {
        Chung.ActionToolBar(this, toolbar);
    }

    private void InitView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        imghinhanh = findViewById(R.id.imagespchitiet);
        tensp = findViewById(R.id.txttenspchitiet);
        giasp = findViewById(R.id.txtgiaspchitiet);
        mota = findViewById(R.id.txtmotachitiet);
        spinner = findViewById(R.id.spinnersoluong);
        toolbar = findViewById(R.id.toolbarchitiet);
        btnhthem = findViewById(R.id.btnthemgiohang);

        gioHang = new GioHang();
    }

    private void refreshCart() {

        compositeDisposable.add(apiBanHang.giohang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            Utils.manggiohang = gioHangModel.getResult();
                            invalidateOptionsMenu(); // Update the menu to refresh the cart badge
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
}