package com.example.quanly.ultil;

import android.content.Intent;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.quanly.R;
import com.example.quanly.activity.ChatActivity;
import com.example.quanly.activity.GioHangActivity;
import com.example.quanly.activity.TimKiemActivity;
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


    public static void createMenu(AppCompatActivity activity, Menu menu) {
        activity.getMenuInflater().inflate(R.menu.menu, menu);

        MenuItem menuItem = menu.findItem(R.id.menugiohang);
        View actionView = menuItem.getActionView();

        ImageView cartIcon = actionView.findViewById(R.id.cart_icon);
        TextView cartBadge = actionView.findViewById(R.id.cart_badge);
        updateCartBadge(cartBadge);

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onOptionsItemSelected(menuItem);
            }
        });
       // return true;
    }
    private static void updateCartBadge(TextView cartBadge) {
        int cartItemCount = getCartItemCount();
        if (cartItemCount > 0) {
            cartBadge.setText(String.valueOf(cartItemCount));
            cartBadge.setVisibility(View.VISIBLE);
        } else {
            //cartBadge.setVisibility(View.GONE);
        }
    }

    private static int getCartItemCount() {
        return Utils.manggiohang != null ? Utils.manggiohang.size() : 0;
    }


    public static void itemMenuSelected(AppCompatActivity activity, @NonNull MenuItem item) {
        if (item.getItemId() == R.id.menugiohang) {
            Intent intent = new Intent(activity.getApplicationContext(), GioHangActivity.class);
            activity.startActivity(intent);
           // return true;
        }
        if (item.getItemId() == R.id.menutimkiem) {
            Intent intent = new Intent(activity.getApplicationContext(), TimKiemActivity.class);
            activity.startActivity(intent);
           // return true;
        }
        if (item.getItemId() == R.id.image_mess) {
            Intent intent = new Intent(activity.getApplicationContext(), ChatActivity.class);
            activity.startActivity(intent);
          //  return true;
        }

       // return activity.onOptionsItemSelected(item);
    }

    public static void showCustomToast(AppCompatActivity activity, String message) {
        Toast toast = new Toast(activity.getApplicationContext());
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast, null);

        TextView txt_ten = view.findViewById(R.id.txttoast);
        txt_ten.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);


        toast.show();
    }



}
