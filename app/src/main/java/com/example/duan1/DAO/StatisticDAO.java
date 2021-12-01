package com.example.duan1.DAO;



import static com.example.duan1.SQLite.OpenHelper.MA_XE_FK;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Model.Top;
import com.example.duan1.Model.Xe;

import java.util.ArrayList;
import java.util.List;

public class StatisticDAO implements IStatisticDAO{

    MyDatabase mydb;
    Context context;
    public StatisticDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }

    @Override
    public List<Top> getTop() {
        List<Top> list = new ArrayList<>();
        XeDAO dao = new XeDAO(context);
        SQLiteDatabase database = mydb.getReadableDatabase();

        String sql = "SELECT XE.MAXE,TENXE,ANHXE, SUM (HOADONCHITIET.SOLUONG) AS TOTAL FROM HOADONCHITIET INNER JOIN XE " +
                "ON HOADONCHITIET.MAXE = XE.MAXE GROUP BY XE.MAXE ORDER BY TOTAL DESC LIMIT 10;";

        Cursor c = database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()){
                do {
                    Top top = new Top();

                    c.getInt(c.getColumnIndex(MA_XE_FK));
                    top.setName(c.getString(1));
                    top.setImages(c.getBlob(2));
                    top.setAmount(c.getInt(3));
                    list.add(top);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Book Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }

    @Override
    public double getRevenue(Long startDate, Long endDate) {
        double revenue = 0;
        SQLiteDatabase database = mydb.getReadableDatabase();
        String sql = "SELECT SUM(TOTAL) from ( SELECT SUM (XE.GIAXE * HOADONCHITIET.SOLUONG)  as 'TOTAL' " +
                "FROM HOADON INNER JOIN HOADONCHITIET ON HOADON.MAHOADON =HOADONCHITIET.MAHOADON  INNER JOIN XE on HOADONCHITIET.MAXE = XE.MAXE " +
                "WHERE HOADON.NGAYTAO BETWEEN ? AND ? GROUP BY HOADONCHITIET.MAXE);";
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(startDate), String.valueOf(endDate)});
        try {
            if (c.moveToFirst()){
                do {
                    Log.d("Cot doanh thu",c.getDouble(0)+"");
                    revenue = c.getDouble(0);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Book Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return revenue;
    }
}
