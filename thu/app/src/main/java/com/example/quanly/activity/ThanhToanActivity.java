package com.example.quanly.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quanly.R;
import com.example.quanly.model.CreateOrder;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txttongtien;
    EditText edtsdt, edtemail, edtdiachi;
    AppCompatButton btndathang, btnthanhtoan;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;
    long tongiten;
    int soluong;

    int iddonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        //zalopay
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(2553, Environment.SANDBOX);

        InitView();
        ActionToolBar();
        initControl();
        countItem();
    }

    private void countItem() {
        soluong = 0;
        for(int i = 0; i < Utils.mangmuahang.size(); i++){
            soluong += Utils.mangmuahang.get(i).getSoluong();
        }
    }
    private void initControl() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongiten = getIntent().getLongExtra("tongtien", 0);
        txttongtien.setText(decimalFormat.format(tongiten));
        edtemail.setText(Utils.user_current.getEmail());



        btndathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = edtemail.getText().toString().trim();
                String str_sdt = edtsdt.getText().toString().trim();
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_sdt)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập sdt", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    int iduuser = Utils.user_current.getId();

                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.themdonhang(iduuser, str_sdt, str_email, str_diachi, soluong, tongiten, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    donHangModel -> {
                                        if(donHangModel.isSuccess()){
                                            Toast.makeText(getApplicationContext(), "Dặt hàng thành công", Toast.LENGTH_SHORT).show();
                                            for(int i = 0; i < Utils.mangmuahang.size(); i++){
                                                xoagiohang(iduuser, Utils.mangmuahang.get(i).getIdsp());
                                            }

                                            Utils.mangmuahang.clear();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "dat hang loi", Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }


            }
        });

        btnthanhtoan.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String str_email = edtemail.getText().toString().trim();
                String str_sdt = edtsdt.getText().toString().trim();
                String str_diachi = edtdiachi.getText().toString().trim();
                if(TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập email", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_sdt)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập sdt", Toast.LENGTH_SHORT).show();
                }else if(TextUtils.isEmpty(str_diachi)){
                    Toast.makeText(getApplicationContext(), "Bạn chưa nhập địa chỉ", Toast.LENGTH_SHORT).show();
                }else{
                    int iduuser;
                    iduuser = Utils.user_current.getId();
                    Log.d("test", new Gson().toJson(Utils.mangmuahang));
                    compositeDisposable.add(apiBanHang.themdonhang(iduuser, str_sdt, str_email, str_diachi, soluong, tongiten, new Gson().toJson(Utils.mangmuahang))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    messageModel -> {
                                        if(messageModel.isSuccess()){
                                           // Toast.makeText(getApplicationContext(), "Dặt hàng thành công", Toast.LENGTH_SHORT).show();
                                            for(int i = 0; i < Utils.mangmuahang.size(); i++){
                                                xoagiohang(iduuser, Utils.mangmuahang.get(i).getIdsp());
                                            }

                                            Utils.mangmuahang.clear();
                                            iddonhang = messageModel.getIddonhang();
                                            requestZalo();
//                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                            startActivity(intent);
//                                            finish();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), "dat hang loi", Toast.LENGTH_SHORT).show();
                                    }
                            )
                    );
                }
            }
        });
    }

    private void requestZalo() {
        CreateOrder orderApi = new CreateOrder();

        try {
//            JSONObject data = orderApi.createOrder("100000");
            JSONObject data = orderApi.createOrder(String.valueOf(tongiten));
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");



                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener(){

                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        compositeDisposable.add(apiBanHang.updatezalo(iddonhang, token)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        messageModel -> {
                                            if (messageModel.isSuccess()) {
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        },
                                        throwable -> {
                                            Log.e("lỗi thanh toán", throwable.getMessage());
                                            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                ));
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {

                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {

                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void xoagiohang(int idUser, int idSp){
        compositeDisposable.add(apiBanHang.xoaspgiohang(idUser, idSp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {

                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "Xóa giỏ hàng lỗi", Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void InitView() {
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        toolbar = findViewById(R.id.toolbarthanhtoan);
        txttongtien = findViewById(R.id.txttongtiendathang);
        edtsdt = findViewById(R.id.txtstddathang);
        edtemail = findViewById(R.id.txtemaildathang);
        edtdiachi = findViewById(R.id.txtdiachidathang);
        btndathang = findViewById(R.id.btndathang);
        btnthanhtoan = findViewById(R.id.btnthanhtoan);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}