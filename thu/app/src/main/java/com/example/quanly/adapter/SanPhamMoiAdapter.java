package com.example.quanly.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly.Interface.ItemClickListener;
import com.example.quanly.R;
import com.example.quanly.activity.ChiTietActivity;
import com.example.quanly.model.SanPham;
import com.example.quanly.ultil.Utils;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamMoiAdapter extends RecyclerView.Adapter<SanPhamMoiAdapter.ItemHolder> {

    Context context;
    List<SanPham> mangsanpham;

    public SanPhamMoiAdapter(Context context, List<SanPham> mangsanpham) {
        this.context = context;
        this.mangsanpham = mangsanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sp, parent, false);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        SanPham sanPham = mangsanpham.get(position);
        holder.txttensanpham.setText(sanPham.getTen());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + " Đ");
        if(sanPham.getHinhanh().contains("https")){
            Glide.with(context)
                    .load(sanPham.getHinhanh())
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(holder.imghinhanhsp);
        }else{
            String hinh = Utils.BASE_URL + "images/" + sanPham.getHinhanh();
            Glide.with(context).load(hinh)
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(holder.imghinhanhsp);
        }

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int pos, boolean isLongClick) {
                if(!isLongClick){
                    Intent intent = new Intent(context, ChiTietActivity.class);
                    intent.putExtra("chitiet", sanPham);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            }
        });

        
    }

    @Override
    public int getItemCount() {
        return mangsanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imghinhanhsp;
        public TextView txttensanpham, txtgiasanpham;
        private ItemClickListener itemClickListener;
        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imghinhanhsp = itemView.findViewById(R.id.itemsp_image);
            txttensanpham = itemView.findViewById(R.id.itemsp_ten);
            txtgiasanpham = itemView.findViewById(R.id.itemsp_gia);
            itemView.setOnClickListener(this);
        }
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onClick(v, getAdapterPosition(), false);
        }

    }
}
