package com.example.quanly.adapter;

import android.content.Context;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.quanly.R;
import com.example.quanly.model.Menu_1;

import java.util.List;

public class MenuAdapter extends BaseAdapter {
    List<Menu_1> mangmenu;
    Context context;

    public MenuAdapter( Context context, List<Menu_1> mangmenu) {
        this.mangmenu = mangmenu;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mangmenu.size();
    }

    @Override
    public Object getItem(int position) {
        return mangmenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public static  class  ViewHolder{
        TextView txtten;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.loai_sp,  parent, false);
            viewHolder.txtten = convertView.findViewById(R.id.texttenloaisp);
            viewHolder.img = convertView.findViewById(R.id.imageviewloaisp);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // Lấy dữ liệu cho hàng tại vị trí position
        Menu_1 menu = mangmenu.get(position);

        //gán dữ liệu cho view
        viewHolder.txtten.setText(menu.getTen());
        Glide.with(context)
                .load(menu.getAnh())
                .placeholder(R.drawable.noanh)
                .error(R.drawable.error)
                .into(viewHolder.img);
        return convertView;
    }
}
