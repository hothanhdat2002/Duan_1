package com.example.duan1.DAO;



import com.example.duan1.Model.Top;

import java.util.List;

public interface IStatisticDAO {
    List<Top> getTop();
    double getRevenue(Long startDate,Long endDate);
}
