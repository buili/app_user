package com.example.quanly.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class DonMuaAdapter extends RecyclerView.Adapter<DonMuaAdapter.MyViewHolder> {
    List<DonHang> donHangList;
    ItemClickDeleteListener itemClickDeleteListener;

    RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public DonMuaAdapter(List<DonHang> donHangList, ItemClickDeleteListener itemClickDeleteListener, RecyclerView.RecycledViewPool viewPool) {
        this.donHangList = donHangList;
        this.itemClickDeleteListener = itemClickDeleteListener;
        this.viewPool = viewPool;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_donhang, parent, false);
        return  new DonMuaAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DonHang donHang = donHangList.get(position);
        holder.txtdonhang.setText("Đơn Hàng: " + donHang.getId());
        holder.txttrangthai.setText(Utils.statusOrder(donHang.getTrangthai()));

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                itemClickDeleteListener.onClickDelete(donHang.getId());
//                return false;
                itemClickDeleteListener.onClickDelete(v, donHang.getId());
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
     //   ChiTietDonHangAdapter chiTietDonHangAdapter = new ChiTietDonHangAdapter(ge, donHang.getItemDonHangList());
        holder.recyclerView.setLayoutManager(layoutManager);
     //   holder.recyclerView.setAdapter(chiTietDonHangAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return donHangList.size();
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
}
