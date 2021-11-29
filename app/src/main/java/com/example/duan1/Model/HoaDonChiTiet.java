package com.example.duan1.Model;

public class HoaDonChiTiet {
    private int id;
    private int id_hoadon;
    private int id_xe;
    private int amount;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(int id, int id_hoadon, int id_xe, int amount) {
        this.id = id;
        this.id_hoadon = id_hoadon;
        this.id_xe = id_xe;
        this.amount = amount;

    }
    public HoaDonChiTiet( int id_hoadon, int id_xe, int amount) {
        this.id_hoadon = id_hoadon;
        this.id_xe = id_xe;
        this.amount = amount;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_hoadon() {
        return id_hoadon;
    }

    public void setId_hoadon(int id_hoadon) {
        this.id_hoadon = id_hoadon;
    }

    public int getId_xe() {
        return id_xe;
    }

    public void setId_xe(int id_xe) {
        this.id_xe = id_xe;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
