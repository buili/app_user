package com.example.quanly.model;

public class Item {

    private int idsp;
    private String ten;
    private int soluong;
    private String hinhanh;

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

    public String getTensanpham() {
        return ten;
    }

    public void setTensanpham(String tensanpham) {
        this.ten = tensanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public String getHinhanhsanpham() {
        return hinhanh;
    }

    public void setHinhanhsanpham(String hinhanhsanpham) {
        this.hinhanh = hinhanhsanpham;
    }
}
