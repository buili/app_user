package com.example.quanly.model;

import android.widget.ImageView;

public class TienIch {
    private int imageView;
    private String ten;

    public TienIch(int imageView, String ten) {
        this.imageView = imageView;
        this.ten = ten;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }
}
