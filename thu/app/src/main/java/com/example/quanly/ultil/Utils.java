package com.example.quanly.ultil;

import com.example.quanly.model.GioHang;
import com.example.quanly.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    //public static  final String BASE_URL = "http://192.168.1.32/thu/";
   public static final String BASE_URL = "http://appbanhangw.000webhostapp.com/thu/";
    public static List<GioHang> manggiohang;
    public static User user_current = new User();

    public static List<GioHang> mangmuahang = new ArrayList<>();

    public static String tokenSend;
    public static String ID_RECEIVED;
    public static String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";
    public static final  String PATH_CHAT2 = "chats";

    public static String statusOrder(int status) {
        String result = "";
        switch (status) {
            case 0:
                result = "Chờ xác nhận";
                break;
            case 1:
                result = "Chờ lấy hàng";
                break;
            case 2:
                result = "Chờ giao hàng";
                break;
            case 3:
                result = "Đã giao";
                break;
            case 4:
                result = "Đã hủy";
                break;
            default:
                result = "....";
        }
        return result;
    }

}
