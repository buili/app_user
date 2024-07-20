package com.example.quanly.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.quanly.Interface.ImageClickListener;
import com.example.quanly.R;
import com.example.quanly.model.EventBus.TinhTongEvent;
import com.example.quanly.model.GioHang;
import com.example.quanly.retrofit.ApiBanHang;
import com.example.quanly.retrofit.RetrofitClient;
import com.example.quanly.ultil.Utils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.MyViewHolder> {
    Context context;
    List<GioHang> manggiohang;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiBanHang apiBanHang;

    private boolean isSelectAll = false;

    public GioHangAdapter(Context context, List<GioHang> manggiohang) {
        this.context = context;
        this.manggiohang = manggiohang;
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_gio_hang, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        GioHang gioHang = manggiohang.get(position);
        if(gioHang.getSoluong() > gioHang.getSanPham().getSltonkho()){

            int soLuongMoi = gioHang.getSanPham().getSltonkho();
            manggiohang.get(position).setSoluong(soLuongMoi);

            holder.giohang_soluong.setText(gioHang.getSoluong() + " ");
            long gia = gioHang.getSoluong() * gioHang.getSanPham().getGia();

            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            holder.giohang_giasp2.setText(decimalFormat.format(gia));

            capNhatGioHang(Utils.user_current.getId(), gioHang.getIdsp(), soLuongMoi);
        }else{
            holder.giohang_soluong.setText(gioHang.getSoluong() + " ");
        }
        holder.giohang_tensp.setText(gioHang.getSanPham().getTen());

//        Glide.with(context).load(gioHang.getSanPham().getHinhanh())
//                .placeholder(R.drawable.noanh)
//                .error(R.drawable.error)
//                .into(holder.img_giohang);
        if(gioHang.getSanPham().getHinhanh().contains("https")){
            Glide.with(context)
                    .load(gioHang.getSanPham().getHinhanh())
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(holder.img_giohang);
        }else{
            String hinh = Utils.BASE_URL + "images/" + gioHang.getSanPham().getHinhanh();
            Glide.with(context).load(hinh)
                    .placeholder(R.drawable.noanh)
                    .error(R.drawable.error)
                    .into(holder.img_giohang);
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.giohang_giasp.setText(decimalFormat.format(gioHang.getSanPham().getGia()));
        long gia = gioHang.getSoluong() * gioHang.getSanPham().getGia();
        holder.giohang_giasp2.setText(decimalFormat.format(gia));

        holder.setListener(new ImageClickListener() {

            @Override
            public void onImageClick(View view, int pos, int giatri) {
                if (giatri == 1) {
                    if (manggiohang.get(pos).getSoluong() > 1) {
                        int soLuongMoi = manggiohang.get(pos).getSoluong() - 1;
                        manggiohang.get(pos).setSoluong(soLuongMoi);

                        holder.giohang_soluong.setText(manggiohang.get(pos).getSoluong() + " ");
                        long gia = manggiohang.get(pos).getSoluong() * manggiohang.get(pos).getSanPham().getGia();
                        holder.giohang_giasp2.setText(decimalFormat.format(gia));

                        capNhatGioHang(Utils.user_current.getId(), manggiohang.get(pos).getIdsp(), soLuongMoi);

                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    } else if (manggiohang.get(pos).getSoluong() == 1) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
                        builder.setTitle("Thông báo")
                                .setIcon(R.drawable.baseline_announcement_24)
                                .setMessage("Bạn có muốn xóa sản phẩm khỏi giỏ hàng không")
                                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        xoaspgiohang(Utils.user_current.getId(), manggiohang.get(pos).getIdsp());
                                        Utils.manggiohang.remove(pos);
                                        notifyDataSetChanged();

                                        EventBus.getDefault().postSticky(new TinhTongEvent());
                                    }
                                })

                                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show();
                    }
                }else if(giatri == 2){
                    if(manggiohang.get(pos).getSoluong() < manggiohang.get(pos).getSanPham().getSltonkho()){
                        int soLuongMoi = manggiohang.get(pos).getSoluong() + 1;

                        manggiohang.get(pos).setSoluong(soLuongMoi);

                        holder.giohang_soluong.setText(manggiohang.get(pos).getSoluong() + "");
                        long gia = manggiohang.get(pos).getSoluong() * manggiohang.get(pos).getSanPham().getGia();
                        holder.giohang_giasp2.setText(decimalFormat.format(gia));

                        capNhatGioHang(Utils.user_current.getId(), manggiohang.get(pos).getIdsp(), soLuongMoi);

                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    }
                }
            }
        });

        if(manggiohang.get(position).getSanPham().getSltonkho() > 0) {


            holder.checkBox.setChecked(isSelectAll);
            holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        Utils.mangmuahang.add(gioHang);
                        EventBus.getDefault().postSticky(new TinhTongEvent());
                    } else {
                        for (int i = 0; i < Utils.mangmuahang.size(); i++) {
                            if (Utils.mangmuahang.get(i).getIdsp() == gioHang.getIdsp()) {
                                Utils.mangmuahang.remove(i);
                                EventBus.getDefault().postSticky(new TinhTongEvent());
                            }
                        }
                    }
                }
            });
        }else{
            holder.hethang.setVisibility(View.VISIBLE);
            holder.checkBox.setVisibility(View.INVISIBLE);

        }


    }

    @Override
    public int getItemCount() {
        return manggiohang.size();
    }

    public void selectAll(boolean selectAll) {
        isSelectAll = selectAll;
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_giohang, imgtru, imgcong;
        TextView giohang_tensp, giohang_giasp, giohang_soluong, giohang_giasp2, hethang;
        ImageClickListener imageClickListener;
        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_giohang = itemView.findViewById(R.id.imgspgiohang);
            imgtru = itemView.findViewById(R.id.imgtrusl);
            imgcong = itemView.findViewById(R.id.imgthemsl);
            giohang_tensp = itemView.findViewById(R.id.txttenspgiohang);
            giohang_giasp = itemView.findViewById(R.id.txtgiaspgiohang);
            giohang_soluong = itemView.findViewById(R.id.txtslspgiohang);
            giohang_giasp2 = itemView.findViewById(R.id.txtgiasp2);
            checkBox = itemView.findViewById(R.id.checkbox);
            hethang = itemView.findViewById(R.id.txthethang);

            imgcong.setOnClickListener(this);
            imgtru.setOnClickListener(this);
        }

        public void setListener(ImageClickListener imageClickListener) {
            this.imageClickListener = imageClickListener;
        }

        @Override
        public void onClick(View v) {
            if (v == imgtru) {
                imageClickListener.onImageClick(v, getAdapterPosition(), 1);
            } else if (v == imgcong) {
                imageClickListener.onImageClick(v, getAdapterPosition(), 2);
            }
        }
    }

    private void capNhatGioHang(int idUser, int idSp, int soluong){
      compositeDisposable.add(apiBanHang.updategiohang(idUser, idSp, soluong)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        themGioHangModel -> {
                            if (themGioHangModel.isSuccess()) {
                                Toast.makeText(context, "Đã cập nhật giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void xoaspgiohang(int idUser, int idSp){
        compositeDisposable.add(apiBanHang.xoaspgiohang(idUser, idSp)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        gioHangModel -> {
                            if(gioHangModel.isSuccess()){
                                Toast.makeText(context, "Đã xóa sản phẩm khỏi giỏ hàng", Toast.LENGTH_SHORT).show();
                            }
                        },
                        throwable -> {
                            Toast.makeText(context, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }
}




