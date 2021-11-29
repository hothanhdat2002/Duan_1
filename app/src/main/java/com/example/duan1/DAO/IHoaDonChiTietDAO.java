package com.example.duan1.DAO;

import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public interface IHoaDonChiTietDAO {
    List<HoaDonChiTiet> get();
    ArrayList<HoaDonChiTiet> getByID(int id_hoadon);
    ArrayList<HoaDonChiTiet> getByIDSum(int id_hoadon);
    Integer sumBike(Integer id_xe);
    void insert(HoaDonChiTiet hoaDonChiTiet);
    void update(HoaDonChiTiet hoaDonChiTiet);
    void delete(int maHDCT);
    void deleteByID(int maHoaDon);
}
