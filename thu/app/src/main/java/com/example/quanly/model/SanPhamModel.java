package com.example.quanly.model;

import java.util.List;

public class SanPhamModel {
    boolean success;
    String massage;
    List<SanPham> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }

    public List<SanPham> getResult() {
        return result;
    }

    public void setResult(List<SanPham> result) {
        this.result = result;
    }
}
