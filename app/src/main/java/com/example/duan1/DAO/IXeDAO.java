package com.example.duan1.DAO;

import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;

import java.util.List;

public interface IXeDAO {
    List<Xe> get();
    Xe getByID(Integer maxe);
    void insert(Xe xe);
    void update(Xe xe);
    void delete(int maxe);
}
