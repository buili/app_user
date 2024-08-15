package com.example.quanly.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.quanly.Abstract.BottomNavigationActivity;
import com.example.quanly.R;
import com.example.quanly.adapter.DonHangAdapter;
import com.example.quanly.adapter.TienIchAdapter;
import com.example.quanly.model.Message;
import com.example.quanly.model.MessageData;
import com.example.quanly.model.Notification;
import com.example.quanly.model.TienIch;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.ApiPushNofication;
import com.example.quanly.retrofit.AuthorizationInterceptor;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.retrofit.RetrofitClientNoti;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.OkHttpClient;

public class TaiKhoanActivity extends BottomNavigationActivity {

    String ten[] = {"Khách hàng thaanh thiết", "Mua lại", "Kênh người sáng tạo", "Số dư TK", "Săn Thưởng", "Ví"};
    int img[] = {R.drawable.baseline_playlist_add_check_circle_24, R.drawable.baseline_local_car_wash_24
            , R.drawable.baseline_email_24, R.drawable.baseline_home_24,
            R.drawable.baseline_stars_24, R.drawable.baseline_person_24};

    ArrayList<TienIch> tienIchList;
    TienIchAdapter tienIchAdapter;
    GridView gridView;
    TextView txt_lichsu;
    ImageView img_choxacnhan;
    Toolbar toolbar;

    LinearLayout linearChoLayHang;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    OkHttpClient client;

    private static final int REQUEST_CODE_POST_NOTIFICATIONS = 1;
    private static final int REQUEST_CODE_NOTIFICATION_SETTINGS = 2;


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

        linearChoLayHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS)
                            != PackageManager.PERMISSION_GRANTED) {
                        // Yêu cầu quyền thông báo
                        ActivityCompat.requestPermissions(TaiKhoanActivity.this,
                                new String[]{Manifest.permission.POST_NOTIFICATIONS},
                                REQUEST_CODE_POST_NOTIFICATIONS);
                    } else {
                        // Quyền đã được cấp, gửi thông báo
                        pushNotiUser();
                    }
                } else {
                    // Không cần yêu cầu quyền, gửi thông báo
                    pushNotiUser();
                }


        }
    });

}

    private void pushNotiUser() {

        if (Utils.tokenSend != null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthorizationInterceptor(Utils.tokenSend))
                    .build();
        }
        Log.d("Notification", "Starting pushNotiUser method");
        compositeDisposable.add(apiBanHang.gettoken(1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    Log.d("Notification", "Received user token response");
                                    if (userModel.isSuccess()) {
                                        for (int i = 0; i < userModel.getResult().size(); i++) {
                                            Notification notification = new Notification("Thông báo", "Bạn có đơn hàng mới");
                                            //  Message message = new Message(userModel.getResult().get(i).getToken(), notification);
                                            Message message = new Message(userModel.getResult().get(i).getToken(),
                                                    notification);
                                            Log.d("Notification", "Token: " + userModel.getResult().get(i).getToken());
                                            MessageData messageData = new MessageData(message);
                                            Gson gson = new Gson();
                                            String json = gson.toJson(messageData);
                                            Log.d("Notification JSON", json);
                                            ApiPushNofication apiPushNofication = RetrofitClientNoti.getInstance(client).create(ApiPushNofication.class);
                                            compositeDisposable.add(apiPushNofication.sendNofitication(messageData)
                                                            .subscribeOn(Schedulers.io())
                                                            .observeOn(AndroidSchedulers.mainThread())
                                                            .subscribe(
                                                                    notiResponse -> {
                                                                        if (notiResponse.getName().isEmpty()) {
                                                                            Log.d("Notification", "Thành công");
                                                                        } else {
                                                                            Log.d("Notification", "Lỗi");
                                                                        }
                                                                        Log.d("Notification", "không viết");
//                                                        Log.d("Notification", "Success: " + notiResponse.getSuccess());
//                                                        Log.d("Notification", "Failure: " + notiResponse.getFailure());
                                                                    },
                                                                    throwable -> {
                                                                        Log.d("Notification Error", "Error sending notification: " + throwable.getMessage());
                                                                        throwable.printStackTrace();
                                                                    }
                                                            )
                                            );
                                        }
                                    }
                                },
                                throwable -> {
                                    Log.d("Notification Error2", "Error receiving token: " + throwable.getMessage());
                                }
                        )
        );
    }


    private void initView() {
        gridView = findViewById(R.id.gridview);
        txt_lichsu = findViewById(R.id.txtlichsu);
        img_choxacnhan = findViewById(R.id.imgchoxacnhan);
        toolbar = findViewById(R.id.toolbar_taikhoan);
        linearChoLayHang = findViewById(R.id.linear_cholayhang);
        tienIchList = new ArrayList<>();

        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }

    private void getData() {
        for (int i = 0; i < 6; i++) {
            tienIchList.add(new TienIch(img[i], ten[i]));
        }
        tienIchAdapter = new TienIchAdapter(TaiKhoanActivity.this, R.layout.item_tienich, tienIchList);
        gridView.setAdapter(tienIchAdapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Quyền thông báo đã được cấp

                pushNotiUser();
            } else {
                // Quyền thông báo bị từ chối

                // Hiển thị thông báo yêu cầu người dùng bật quyền thông báo
                showNotificationPermissionDeniedDialog();
            }
        }
    }
    private void showNotificationPermissionDeniedDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Quyền Thông Báo Cần Thiết")
                .setMessage("Ứng dụng cần quyền thông báo để hoạt động. Vui lòng bật quyền trong cài đặt.")
                .setPositiveButton("Cài Đặt", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Mở cài đặt quyền thông báo
                        openNotificationSettings();
                    }
                })
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void openNotificationSettings() {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
        } else {
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
        }
        startActivityForResult(intent, REQUEST_CODE_NOTIFICATION_SETTINGS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_NOTIFICATION_SETTINGS) {
            // Kiểm tra lại quyền sau khi người dùng quay lại từ cài đặt
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    == PackageManager.PERMISSION_GRANTED) {
                pushNotiUser();
            }
        }
    }
}