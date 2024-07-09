package com.example.quanly.model;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int id;
    String ten;
    int gia;
    String hinhanh;
    String mota;
    int idloai;

    int sltonkho;

    public SanPham(int id, String ten, int gia, String hinhanh, String mota, int idloai, int sltonkho) {
        this.id = id;
        this.ten = ten;
        this.gia = gia;
        this.hinhanh = hinhanh;
        this.mota = mota;
        this.idloai = idloai;
        this.sltonkho = sltonkho;
    }

    public SanPham() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getIdloai() {
        return idloai;
    }

    public void setIdloai(int idloai) {
        this.idloai = idloai;
    }

    public int getSltonkho() {
        return sltonkho;
    }

    public void setSltonkho(int sltonkho) {
        this.sltonkho = sltonkho;
    }
}
