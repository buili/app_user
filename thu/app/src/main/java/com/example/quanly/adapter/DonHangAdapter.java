package com.example.quanly.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quanly.Interface.ItemClickDeleteListener;
import com.example.quanly.R;
import com.example.quanly.model.DonHang;
import com.example.quanly.model.Item;
import com.example.quanly.ultil.Utils;

import java.util.ArrayList;
import java.util.List;

public class DonHangAdapter extends RecyclerView.Adapter<DonHangAdapter.MyViewHolder> {
    Context context;
    List<DonHang> donHangList;
    ItemClickDeleteListener itemClickDeleteListener;

    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DonHangAdapter(Context context, List<DonHang> donHangList, ItemClickDeleteListener itemClickDeleteListener) {
        this.context = context;
//        this.donHangList = donHangList;
        this.donHangList = donHangList != null ? donHangList : new ArrayList<>();
        this.itemClickDeleteListener = itemClickDeleteListener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtdonhang, txttrangthai;
        RecyclerView recyclerView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtdonhang = itemView.findViewById(R.id.iddonhang);
            txttrangthai = itemView.findViewById(R.id.trangthaidon);
            recyclerView = itemView.findViewById(R.id.recyclerview_chitiet);

        }
    }
    @NonNull
    @Override
    public DonHangAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        holder.txtdonhang.setText("Đơn Hàng: " + donHang.getId());
        holder.txttrangthai.setText(Utils.statusOrder(donHang.getTrangthai()));

                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
            public boolean onLongClick(View v) {
               // itemClickDeleteListener.onClickDelete(donHang.getId());
                //return false;
                itemClickDeleteListener.onClickDelete(holder.txttrangthai, donHang.getId());
                return true;
            }
        });


        List<Item> itemDonHangList = donHang.getItemDonHangList();
        if (itemDonHangList == null) {
            itemDonHangList = new ArrayList<>();
        }
        Log.d("DonHangAdapter", "ItemDonHangList size: " + itemDonHangList.size());
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(), LinearLayoutManager.VERTICAL, false
        );
//        layoutManager.setInitialPrefetchItemCount(donHang.getItemDonHangList().size());

        layoutManager.setInitialPrefetchItemCount(itemDonHangList.size());
        ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(context, donHang.getItemDonHangList());
        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(chiTietDonHangAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);

    }

    @Override
    public int getItemCount() {
        return donHangList.size();
    }




}
