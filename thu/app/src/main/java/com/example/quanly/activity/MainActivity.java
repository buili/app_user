package com.example.quanly.activity;

import androidx.annotation.NonNull;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.quanly.Abstract.BottomNavigationActivity;
import com.example.quanly.R;
import com.example.quanly.adapter.MenuAdapter;
import com.example.quanly.adapter.SanPhamMoiAdapter;
import com.example.quanly.model.Menu_1;
import com.example.quanly.model.SanPham;
import com.example.quanly.model.User;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.CheckConnection;
import com.example.quanly.ultil.Chung;
import com.example.quanly.ultil.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainActivity extends BottomNavigationActivity {
    Toolbar toolbar;
    RecyclerView recyclerView, recyclerView_bc;
    // ViewFlipper viewFlipper;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ListView listViewMain;

    List<Menu_1> mangmenu;
    MenuAdapter menuAdapter;

    List<SanPham> mangSp;
    SanPhamMoiAdapter sanPhamAdapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    ImageSlider imageSlider;

    long backpressTime;

    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        if (Paper.book().read("user") != null) {
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }
        getToken();
        intiView();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetMenu();
            GetSpMoiNhat();
            CatchOnItemListView();
            refresh();
        } else {
            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối wifi");
            finish();
        }

    }



    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            compositeDisposable.add(apiBanHang.updateToken(Utils.user_current.getId(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            },
                                            throwable -> {
                                                Log.d("log", throwable.getMessage());
                                            }
                                    )
                            );
                        }
                    }
                });

        compositeDisposable.add(apiBanHang.gettoken(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userModel -> {
                            if (userModel.isSuccess()) {
                                Utils.ID_RECEIVED = String.valueOf(userModel.getResult().get(0).getId());
                            }
                        },
                        throwable -> {

                        }
                )
        );

    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getSelectedItemId() {
        return R.id.home_bottom;
    }

    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.menugiohang);
//
//        View actionView = menuItem.getActionView();
//
//        ImageView cartIcon = actionView.findViewById(R.id.cart_icon);
//        TextView cartBadge = actionView.findViewById(R.id.cart_badge);
//        updateCartBadge(cartBadge);
//        actionView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onOptionsItemSelected(menuItem);
//            }
//        });
//        return true;
//    }
//
//    private void updateCartBadge(TextView cartBadge) {
//        int cartItemCount = getCartItemCount();
//        if (cartItemCount > 0) {
//            cartBadge.setText(String.valueOf(cartItemCount));
//            cartBadge.setVisibility(View.VISIBLE);
//        } else {
//            //cartBadge.setVisibility(View.GONE);
//        }
//    }
//
//    private int getCartItemCount() {
//        return Utils.manggiohang != null ? Utils.manggiohang.size() : 0;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == R.id.menugiohang) {
//            Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
//            startActivity(intent);
//            return true;
//        }
//        if (item.getItemId() == R.id.menutimkiem) {
//            Intent intent = new Intent(getApplicationContext(), TimKiemActivity.class);
//            startActivity(intent);
//            return true;
//        }
//        if (item.getItemId() == R.id.image_mess) {
//            Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
//            startActivity(intent);
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
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

    private void CatchOnItemListView() {
        listViewMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Vui long kiem tra lai ket noi internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            int idloai = mangmenu.get(position).getId();
                            Log.d("MainActivity", "idloai trước khi truyền: " + idloai);
                            Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
                            intent.putExtra("idloai", mangmenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                            int idloai = mangmenu.get(position).getId();
                            Log.d("MainActivity", "idloai trước khi truyền: " + idloai);
                            Intent intent = new Intent(MainActivity.this, SanPhamActivity.class);
                            intent.putExtra("idloai", mangmenu.get(position).getId());
                            startActivity(intent);
                        } else {
                            CheckConnection.showToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối mạng");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        Intent chat = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(chat);
                        break;
                    case 8:
                        Paper.book().delete("user");
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getApplicationContext(), DangNhapActivity.class);
                        startActivity(intent);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetSpMoiNhat() {
        compositeDisposable.add(apiBanHang.getspmoinhat()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        sanPhamModel -> {
                            if (sanPhamModel.isSuccess()) {
                                mangSp = sanPhamModel.getResult();
                                sanPhamAdapter = new SanPhamMoiAdapter(getApplicationContext(), mangSp);
                                recyclerView.setAdapter(sanPhamAdapter);
                                recyclerView_bc.setAdapter(sanPhamAdapter);
                            }
                        },
                        throwable -> {
                            Toast.makeText(getApplicationContext(), "lỗi " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }


    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }


    private void ActionViewFlipper() {
        List<SlideModel> imagelist = new ArrayList<>();
        imagelist.add(new SlideModel("https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg", null));
        imagelist.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS31qZj9VmsHL0-dTRbu_uAXHl5sD-vqVl7lg&s", null));
        imagelist.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQflpbFu6iB31LDfzn4SqY9DPSY3-td6SxUYQ&s", null));
        imagelist.add(new SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvjx3jlhsXma8HSCTnQqrBuNItz-Kmba-8Cg&s", null));
        imageSlider.setImageList(imagelist, ScaleTypes.CENTER_CROP);
        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {
                String imageUrl = imagelist.get(i).getImageUrl();
                Intent myintent = new Intent(Intent.ACTION_VIEW, Uri.parse(imageUrl));
                startActivity(myintent);
            }

            @Override
            public void doubleClick(int i) {

            }
        });
    }
//    private void ActionViewFlipper() {
//        ArrayList<String> mangquangcao = new ArrayList<>();
//        mangquangcao.add("https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS31qZj9VmsHL0-dTRbu_uAXHl5sD-vqVl7lg&s");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQflpbFu6iB31LDfzn4SqY9DPSY3-td6SxUYQ&s");
//        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSvjx3jlhsXma8HSCTnQqrBuNItz-Kmba-8Cg&s");
//        for (int i = 0; i < mangquangcao.size(); i++) {
//            ImageView imageView = new ImageView(getApplicationContext());
//            Glide.with(getApplicationContext())
//                    .load(mangquangcao.get(i))
//                    .into(imageView);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//
//
//
//            viewFlipper.addView(imageView);
//        }
//        viewFlipper.setFlipInterval(5000);
//        viewFlipper.setAutoStart(true);
//
//        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
//        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
//        viewFlipper.setInAnimation(animation_slide_in);
//        viewFlipper.setOutAnimation(animation_slide_out);
//
//
//    }

    private void GetMenu() {
        compositeDisposable.add(apiBanHang.getmenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        menuModel -> {
                            if (menuModel.isSuccess()) {
                                mangmenu = menuModel.getResult();
                                menuAdapter = new MenuAdapter(getApplicationContext(), mangmenu);
                                listViewMain.setAdapter(menuAdapter);
                            }
                        }, throwable -> {
                            Log.e("GetSpError", "Error while getting product types", throwable);
                            Toast.makeText(getApplicationContext(), "khong ket noi duoc" + throwable.getMessage(), Toast.LENGTH_LONG).show();
                        }
                )
        );
    }

    private void intiView() {

        toolbar = findViewById(R.id.toolbarmain);
        recyclerView = findViewById(R.id.recycleviewmain);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView_bc = findViewById(R.id.recyclerview_banchay);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_bc.setLayoutManager(layoutManager2);

        // viewFlipper = findViewById(R.id.viewflipper);
        imageSlider = findViewById(R.id.image_slider);
        drawerLayout = findViewById(R.id.drawerlayoutmain);
        navigationView = findViewById(R.id.navigationview);
        listViewMain = findViewById(R.id.listviewmain);

        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        mangmenu = new ArrayList<>();

        mangSp = new ArrayList<>();

        if (Utils.manggiohang == null) {
            Utils.manggiohang = new ArrayList<>();
        } else {
            getgiohang();
        }

    }

    private void getgiohang() {
        compositeDisposable.add(apiBanHang.giohang(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {

                            //gioHangAdapter = new GioHangAdapter(getApplicationContext(), gioHangModel.getResult());
                            if (gioHangModel.getResult() == null || gioHangModel.getResult().isEmpty()) {
                                Utils.manggiohang = new ArrayList<>();
                            } else {
                                Utils.manggiohang = gioHangModel.getResult();
                            }
                            invalidateOptionsMenu();
                        },
                        throwable -> {
                            Log.e("DangNhap", "Lỗi main" + throwable);
                            Toast.makeText(getApplicationContext(), "lôi user" + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void refresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
    }

    private void refreshData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                GetSpMoiNhat();
                sanPhamAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);

            }
        }, 2000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getgiohang();
        invalidateOptionsMenu();

    }

    @Override
    public void onBackPressed() {
        if (backpressTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            //Toast.makeText(this, "Ấn lần nữa để thoát", Toast.LENGTH_SHORT).show();
            Chung.showCustomToast(this, "Ấn lần nữa để thoát");
        }
        backpressTime = System.currentTimeMillis();

    }
}