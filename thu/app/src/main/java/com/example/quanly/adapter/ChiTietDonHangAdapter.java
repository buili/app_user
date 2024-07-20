package com.example.quanly.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly.R;
import com.example.quanly.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ChiTietDonHangAdapter extends RecyclerView.Adapter<ChiTietDonHangAdapter.MyViewHolder> {

    Context context;
    List<Item> itemDonHangList;

    public ChiTietDonHangAdapter(Context context, List<Item> itemDonHangList) {
        this.context = context;
        this.itemDonHangList = itemDonHangList != null ? itemDonHangList : new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chitietdonhang, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Item itemDonHang = itemDonHangList.get(position);
        Log.d("DonHang", "Item: " + itemDonHang.getTensanpham());
        holder.txtten.setText(itemDonHang.getTensanpham() + " ");
        holder.txtsoluong.setText("Số lượng: " + itemDonHang.getSoluong());
        Glide.with(context).load(itemDonHang.getHinhanhsanpham())
                .error(R.drawable.error)
                .placeholder(R.drawable.noanh)
                .into(holder.imagechitiet);
    }

    @Override
    public int getItemCount() {
        return itemDonHangList.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder{
        ImageView imagechitiet;
        TextView txtten, txtsoluong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imagechitiet = itemView.findViewById(R.id.item_imgchitiet);
            txtten = itemView.findViewById(R.id.item_tenspchitiet);
            txtsoluong = itemView.findViewById(R.id.item_soluongchitiet);
        }
    }
}
