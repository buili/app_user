package com.example.quanly.ultil;

import com.example.quanly.model.GioHang;
import com.example.quanly.model.User;

import java.util.ArrayList;
import java.util.List;

public class Utils {
   //public static  final String BASE_URL = "http://192.168.1.14/thu/";
   public static  final String BASE_URL = "http://appbanhangw.000webhostapp.com/thu/";
    public static List<GioHang> manggiohang;
    public static User user_current = new User();

    public  static  List<GioHang> mangmuahang = new ArrayList<>();

    public  static  String ID_RECEIVED;
    public static String SENDID = "idsend";
    public static final String RECEIVEDID = "idreceived";
    public static final String MESS = "message";
    public static final String DATETIME = "datetime";
    public static final String PATH_CHAT = "chat";

}
