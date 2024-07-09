package com.example.quanly.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly.Interface.ItemClickListener;
import com.example.quanly.R;
import com.example.quanly.activity.ChiTietActivity;
import com.example.quanly.model.SanPham;

import java.text.DecimalFormat;
import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<SanPham> mangsp;
    private  static final int VIEW_TYPE_DATA = 0;
    private static  final  int VIEW_TYPE_LOADING = 1;

    public SanPhamAdapter(Context context, List<SanPham> mangsp) {
        this.context = context;
        this.mangsp = mangsp;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_DATA){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham, parent, false);
            return new MyViewHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar, parent, false);
            return new loadingViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            SanPham sanPham = mangsp.get(position);
            myViewHolder.tensp.setText(sanPham.getTen());
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            myViewHolder.giasp.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + " Đ");
            myViewHolder.mota.setMaxLines(2);
            myViewHolder.mota.setEllipsize(TextUtils.TruncateAt.END);
            myViewHolder.mota.setText(sanPham.getMota());

            Glide.with(context)
                    .load(sanPham.getHinhanh())
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(myViewHolder.hinhanh);

            myViewHolder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onClick(View view, int pos, boolean isLongClick) {
                    if (!isLongClick) {
                        Intent intent = new Intent(context, ChiTietActivity.class);
                        intent.putExtra("chitiet", sanPham);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                    }
                }


            });
        }else{
            loadingViewHolder loadingViewHolder = (loadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mangsp.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_DATA;
    }

    @Override
    public int getItemCount() {
        return mangsp.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tensp, giasp, mota;
        ImageView hinhanh;
        private ItemClickListener itemClickListener;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tensp = itemView.findViewById(R.id.textviewtensp);
            giasp = itemView.findViewById(R.id.textviewgiasp);
            mota = itemView.findViewById(R.id.textviewmotasp);
            hinhanh = itemView.findViewById(R.id.imageviewsp);
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

    private class loadingViewHolder extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        public loadingViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressbar);
        }
    }
}


//public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.MyViewHolder> {
//    private Context context;
//    private List<SanPham> mangsp;
//
//    public SanPhamAdapter(Context context, List<SanPham> mangsp) {
//        this.context = context;
//        this.mangsp = mangsp;
//    }
//
//    @NonNull
//    @Override
//    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanpham, parent, false);
//        return new MyViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
//        SanPham sanPham = mangsp.get(position);
//        holder.tensp.setText(sanPham.getTen());
//        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
//        holder.giasp.setText("Giá: " + decimalFormat.format(sanPham.getGia()) + " Đ");
//        holder.mota.setMaxLines(2);
//        holder.mota.setEllipsize(TextUtils.TruncateAt.END);
//        holder.mota.setText(sanPham.getMota());
//
//        Glide.with(context)
//                .load(sanPham.getHinhanh())
//                .placeholder(R.drawable.noanh)
//                .error(R.drawable.error)
//                .into(holder.hinhanh);
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ChiTietActivity.class);
//                intent.putExtra("chitiet", sanPham);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(intent);
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mangsp.size();
//    }
//
//    public class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tensp, giasp, mota;
//        ImageView hinhanh;
//
//        public MyViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tensp = itemView.findViewById(R.id.textviewtensp);
//            giasp = itemView.findViewById(R.id.textviewgiasp);
//            mota = itemView.findViewById(R.id.textviewmotasp);
//            hinhanh = itemView.findViewById(R.id.imageviewsp);
//        }
//    }
//}
