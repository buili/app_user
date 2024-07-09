package com.example.quanly.model.EventBus;

import com.example.quanly.model.SanPham;

public class SuaXoaEvent {
    SanPham sanPham;

    public SuaXoaEvent(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }
}
