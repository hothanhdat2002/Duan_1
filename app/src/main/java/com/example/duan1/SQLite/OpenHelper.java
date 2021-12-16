package com.example.duan1.SQLite;

public class OpenHelper {
    public static final String DB_NAME ="RIDEFAST"; //tên database
    public static final Integer DB_VERSION =2;

    //Bảng Hãng Xe
    public static final String BANG_HANGXE ="HANGXE";
    public static final String MAHANG ="MAHANG";        //mã hãng
    public static final String TENHANG ="TENHANG";      //tên hãng
    public static final String LOGO ="LOGO";            //logo

    //Bảng Xe
    public static final String BANG_XE ="XE";
    public static final String MAXE ="MAXE";            //mã xe
    public static final String MAHANG_FK ="MAHANGXE";     // mã hãng xe (khóa ngoại)
    public static final String TENXE ="TENXE";          //tên xe
    public static final String ANHXE ="ANHXE";          //ảnh xe
    public static final String MAUXE ="MAUXE";
    public static final String SOLUONG ="SOLUONG";      //số lượng xe
    public static final String GIAXE ="GIAXE";          //giá xe
    public static final String KHOILUONG ="KHOILUONG";  //khối lượng
    public static final String VANTOC ="VANTOC";        //vận tốc
    public static final String NHIENLIEU ="NHIENLIEU";  //nhiên liệu
    public static final String THETICH ="THETICH";      //thể tích
    public static final String DONGCO ="DONGCO";        //động cơ

    //Bảng Hóa đơn
    public static final String BANG_HOADON ="HOADON";
    public static final String MA_HOADON ="MAHOADON";
    public static final String MA_ADMIN_FK ="MAADMIN";
    public static final String TENKHACHHANG ="TENKHACHHANG";
    public static final String NGAYTAO ="NGAYTAO";

    //Bảng Hóa đơn chi tiết
    public static final String BANG_HDCT ="HOADONCHITIET";
    public static final String MA_HDCT ="MAHDCT";
    public static final String MA_HOADON_FK ="MAHOADON";
    public static final String MA_XE_FK ="MAXE";
    public static final String SOLUONGBAN ="SOLUONG";


}
