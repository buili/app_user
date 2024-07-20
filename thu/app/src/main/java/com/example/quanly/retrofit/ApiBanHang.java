package com.example.quanly.retrofit;


import com.example.quanly.model.DonHangModel;
import com.example.quanly.model.GioHangModel;
import com.example.quanly.model.MenuModel;
import com.example.quanly.model.MessageModel;
import com.example.quanly.model.SanPhamModel;
import com.example.quanly.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiBanHang {
    @GET("getmenu.php")
    Observable<MenuModel> getmenu();

    @GET("getspmoinhat.php")
    Observable<SanPhamModel> getspmoinhat();

    @POST("getsp.php")
    @FormUrlEncoded
    Observable<SanPhamModel> getsp(
            @Field("page") int page,
            @Field("idloai") int idloai
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangnhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("giohang.php")
    @FormUrlEncoded
    Observable<GioHangModel> giohang(
            @Field("iduser") int iduser
    );

    @POST("themgiohang.php")
    @FormUrlEncoded
    Observable<GioHangModel> themgiohang(
            @Field("iduser") int iduser,
            @Field("idsp") int idsp,
            @Field("soluong") int soluong
    );

    @POST("updategiohang.php")
    @FormUrlEncoded
    Observable<GioHangModel> updategiohang(
            @Field("iduser") int iduser,
            @Field("idsp") int idsp,
            @Field("soluong") int soluong
    );

    @POST("xoaspgiohang.php")
    @FormUrlEncoded
    Observable<GioHangModel> xoaspgiohang(
            @Field("iduser") int iduser,
            @Field("idsp") int idsp


    );

    @POST("themdonhang.php")
    @FormUrlEncoded
    Observable<MessageModel> themdonhang(
      @Field("iduser") int iduser,
      @Field("sdt") String sdt,
      @Field("email") String email,
      @Field("diachi") String diachi,
      @Field("soluong") int soluong,
      @Field("tongtien") long tongtien,
      @Field("chitiet") String chitiet
    );

    @POST("xoagiohang.php")
    @FormUrlEncoded
    Observable<GioHangModel> xoagiohang(
      @Field("iduser") int iduser,
      @Field("idsp") int idsp
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamModel> timkiem(
       @Field("timkiem") String timkiem
    );

    @POST("dangky.php")
    @FormUrlEncoded
    Observable<UserModel> dangky(
            @Field("email") String email,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("pass") String pass,
            @Field("uid") String uid
    );

    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );

    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> gettoken(
            @Field("status") int status
    );

    @POST("updatezalo.php")
    @FormUrlEncoded
    Observable<MessageModel> updatezalo(
            @Field("id") int id,
            @Field("token") String token
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> donhang(
      @Field("iduser") int id
    );

    @POST("donhangtheotrangthai.php")
    @FormUrlEncoded
    Observable<DonHangModel> donhangtheotrangthai(
            @Field("iduser") int id,
            @Field("trangthai") int tranthai
    );

    @POST("xoadonhang.php")
    @FormUrlEncoded
    Observable<MessageModel> xodonhang(
        @Field("iddonhang") int id
    );
}
