package com.example.duan1.DAO;

import com.example.duan1.Model.HangXe;

import java.util.List;

public interface IHangXeDAO {
    List<HangXe> get();
    HangXe getByID(Integer mahang);
    void insert(HangXe hangXe);
    void update(HangXe hangXe);
    void delete(int maHang);
}
