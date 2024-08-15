package com.example.quanly.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.quanly.R;
import com.example.quanly.model.TienIch;

import java.util.ArrayList;
import java.util.List;

public class TienIchAdapter extends ArrayAdapter<TienIch> {
    Activity context;
    int Idlayout;
    ArrayList<TienIch> tienIchList;

    public TienIchAdapter(Activity context, int resource, ArrayList<TienIch> tienIchList) {
        super(context, resource, tienIchList);
        this.context = context;
        Idlayout = resource;
        this.tienIchList = tienIchList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        TienIch tienIch = tienIchList.get(position);
        LayoutInflater layoutInflater = context.getLayoutInflater();
        convertView = layoutInflater.inflate(Idlayout, null);
        TextView txt_ten = convertView.findViewById(R.id.txt_ten_tienich);
        ImageView imageView = convertView.findViewById(R.id.img_tienich);
        txt_ten.setText(tienIch.getTen());
        imageView.setImageResource(tienIch.getImageView());



        return convertView;
    }
}
