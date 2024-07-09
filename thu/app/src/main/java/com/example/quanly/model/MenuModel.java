package com.example.quanly.model;

import java.util.List;

public class MenuModel {
    boolean success;
    String message;
    List<Menu_1> result;

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

    public List<Menu_1> getResult() {
        return result;
    }

    public void setResult(List<Menu_1> result) {
        this.result = result;
    }
}
