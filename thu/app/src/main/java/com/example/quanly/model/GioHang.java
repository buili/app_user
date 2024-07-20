package com.example.quanly.model;

public class GioHang {
    private int id;
    private int iduser;
    private int idsp;
    //     String tensp;
//     long giasp;
//     String hinhanhsp;
    private int soluong;


    private SanPham sanPham;

    public GioHang() {
    }

    public GioHang(int idsp, int soluong, SanPham sanPham) {
        this.idsp = idsp;
        this.soluong = soluong;
        this.sanPham = sanPham;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public int getIdsp() {
        return idsp;
    }

    public void setIdsp(int idsp) {
        this.idsp = idsp;
    }

//    public String getTensp() {
//        return tensp;
//    }
//
//    public void setTensp(String tensp) {
//        this.tensp = tensp;
//    }
//
//    public long getGiasp() {
//        return giasp;
//    }
//
//    public void setGiasp(long giasp) {
//        this.giasp = giasp;
//    }
//
//    public String getHinhanhsp() {
//        return hinhanhsp;
//    }
//
//    public void setHinhanhsp(String hinhanhsp) {
//        this.hinhanhsp = hinhanhsp;
//    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
