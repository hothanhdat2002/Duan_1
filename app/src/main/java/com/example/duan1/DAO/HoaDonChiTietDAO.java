package com.example.duan1.DAO;

import static com.example.duan1.SQLite.OpenHelper.BANG_HANGXE;
import static com.example.duan1.SQLite.OpenHelper.BANG_HDCT;
import static com.example.duan1.SQLite.OpenHelper.LOGO;
import static com.example.duan1.SQLite.OpenHelper.MAHANG;
import static com.example.duan1.SQLite.OpenHelper.MA_HDCT;
import static com.example.duan1.SQLite.OpenHelper.MA_HOADON_FK;
import static com.example.duan1.SQLite.OpenHelper.MA_XE_FK;
import static com.example.duan1.SQLite.OpenHelper.SOLUONGBAN;
import static com.example.duan1.SQLite.OpenHelper.TENHANG;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDonChiTiet;

import java.util.ArrayList;
import java.util.List;

public class HoaDonChiTietDAO implements IHoaDonChiTietDAO{
    MyDatabase mydb;
    public HoaDonChiTietDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }
    @Override
    public List<HoaDonChiTiet> get() {
        List<HoaDonChiTiet> list = new ArrayList<>();
        String sql = " SELECT "+MA_HDCT+","+
                ""+MA_HOADON_FK+","+
                ""+MA_XE_FK+","+
                ""+SOLUONGBAN +" "+
                "FROM "+BANG_HDCT+"";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MA_HDCT));
                    Integer id_hoadon = c.getInt(c.getColumnIndex(MA_HOADON_FK));
                    Integer id_xe = c.getInt(c.getColumnIndex(MA_XE_FK));
                    Integer amount = c.getInt(c.getColumnIndex(SOLUONGBAN));


                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setId(id);
                    hoaDonChiTiet.setId_hoadon(id_hoadon);
                    hoaDonChiTiet.setId_xe(id_xe);
                    hoaDonChiTiet.setAmount(amount);
                    list.add(hoaDonChiTiet);

                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get HDCT Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }




    @Override
    public ArrayList<HoaDonChiTiet> getByID(int id_hd) {
        ArrayList<HoaDonChiTiet> list = new ArrayList<>();
        String sql = " SELECT "+MA_HDCT+","+
                ""+MA_HOADON_FK+","+
                ""+MA_XE_FK+","+
                ""+SOLUONGBAN +" "+
                "FROM "+BANG_HDCT+" WHERE "+MA_HOADON_FK +" = ?";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,new String[]{String.valueOf(id_hd)});
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MA_HDCT));
                    Integer id_hoadon = c.getInt(c.getColumnIndex(MA_HOADON_FK));
                    Integer id_xe = c.getInt(c.getColumnIndex(MA_XE_FK));
                    Integer amount = c.getInt(c.getColumnIndex(SOLUONGBAN));

                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setId(id);
                    hoaDonChiTiet.setId_hoadon(id_hoadon);
                    hoaDonChiTiet.setId_xe(id_xe);
                    hoaDonChiTiet.setAmount(amount);
                    list.add(hoaDonChiTiet);

                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get HDCT by id error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }

    @Override
    public ArrayList<HoaDonChiTiet> getByIDSum(int id_hd) {
        ArrayList<HoaDonChiTiet> list = new ArrayList<>();
        SQLiteDatabase database = mydb.getReadableDatabase();
        String sql =  "SELECT  MAHDCT,MAHOADON,MAXE,SUM(SOLUONG) AS SOLUONG FROM HOADONCHITIET WHERE MAHOADON = ? GROUP BY MAXE";
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(id_hd)});
        try {
            if (c.moveToFirst()){
                do {
                    Integer id = c.getInt(c.getColumnIndex(MA_HDCT));
                    Integer id_hoadon = c.getInt(c.getColumnIndex(MA_HOADON_FK));
                    Integer id_xe = c.getInt(c.getColumnIndex(MA_XE_FK));
                    Integer amount = c.getInt(c.getColumnIndex(SOLUONGBAN));


                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                    hoaDonChiTiet.setId(id);
                    hoaDonChiTiet.setId_hoadon(id_hoadon);
                    hoaDonChiTiet.setId_xe(id_xe);
                    hoaDonChiTiet.setAmount(amount);
                    list.add(hoaDonChiTiet);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Amount Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }
    int amount;
    @Override
    public Integer sumBike(Integer id_xe) {
        SQLiteDatabase database = mydb.getReadableDatabase();
        String sql =  "SELECT  MAXE,SUM(SOLUONG)  FROM HOADONCHITIET  GROUP BY MAXE HAVING MAXE = ?";
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(id_xe)});
        try {
            if (c.moveToFirst()){
                do {
                    amount = c.getInt(1);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Amount Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }

        return amount;
    }

    @Override
    public void insert(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(MA_HOADON_FK,hoaDonChiTiet.getId_hoadon());
            values.put(MA_XE_FK,hoaDonChiTiet.getId_xe());
            values.put(SOLUONGBAN,hoaDonChiTiet.getAmount());


            database.insertOrThrow(BANG_HDCT,null,values);
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Insert HDCT error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(HoaDonChiTiet hoaDonChiTiet) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(MA_HOADON_FK,hoaDonChiTiet.getId_hoadon());
            values.put(MA_XE_FK,hoaDonChiTiet.getId_xe());
            values.put(SOLUONGBAN,hoaDonChiTiet.getAmount());

            database.update(BANG_HDCT,values,MA_HDCT + " = ?",new String[]{String.valueOf(hoaDonChiTiet.getId())});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Update HDCT error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(int maHDCT) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(BANG_HDCT,MA_HDCT+ " = ?",new String[]{String.valueOf(maHDCT)});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Delete HDCT id error", ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void deleteByID(int maHD) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(BANG_HDCT,MA_XE_FK+ " = ?",new String[]{String.valueOf(maHD)});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Delete HDCT id error", ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }
}
