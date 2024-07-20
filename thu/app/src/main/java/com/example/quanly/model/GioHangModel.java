package com.example.quanly.model;

import java.util.List;

public class GioHangModel {
    private boolean success;
    private String message;
    private List<GioHang> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<GioHang> getResult() {
        return result;
    }

    public void setResult(List<GioHang> result) {
        this.result = result;
    }
}
