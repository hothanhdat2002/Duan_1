package com.example.duan1.DAO;

import com.example.duan1.Model.Admin;
import com.example.duan1.Model.HangXe;

import java.util.List;

public interface IAdminDAO {
    List<Admin> get();
    Admin getByID(Integer maAdmin);
    void insert(Admin admin);
    void update(Admin admin);
    void delete(int maAdmin);
}
