package com.example.quanly.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.quanly.fragment.DonMuaFragment;
import com.example.quanly.ultil.Utils;

public class DonMuaPageAdapter extends FragmentStateAdapter {
   // String[] trangthai = {"Chờ xác nhận", "Chờ lấy hàng", "Chờ giao hàng", "Đã giao", "Đã hủy"};
    public DonMuaPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String trangthai = Utils.statusOrder(position);
        return DonMuaFragment.newInstance(trangthai);
    }

    @Override
    public int getItemCount() {
        return 5;
    }


}
