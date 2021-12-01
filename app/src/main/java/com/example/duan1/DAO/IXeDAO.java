package com.example.duan1.DAO;

import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;

import java.util.List;

public interface IXeDAO {
    List<Xe> get();
    List<Xe> getByIDHang(int maHang);
    Xe getByID(Integer maxe);
    Integer sumAmount(int id_xe);
    void insert(Xe xe);
    void update(Xe xe);
    void delete(int maxe);
    void updateAmount(int maxe_fk, int maxe);
}
