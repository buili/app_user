package com.example.quanly.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DonHang {
    private int id;
    private int iduser;
    private String sdt, email, diachi;
    private int tongtien, trangthai;


    private List<Item> item;

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }
    public List<Item> getItemDonHangList() {
        return item;
    }

    public void setItemDonHangList(List<Item> itemDonHangList) {
        this.item = itemDonHangList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
