package com.example.duan1.DAO;



import com.example.duan1.Model.HoaDon;

import java.util.List;

public interface IHoaDonDAO {
    List<HoaDon> get();
    void insert(HoaDon hoaDon);
    void update(HoaDon hoaDon);
    void delete(int maHD);
}
