package com.example.duan1.DAO;

import static com.example.duan1.SQLite.OpenHelper.BANG_HANGXE;
import static com.example.duan1.SQLite.OpenHelper.BANG_HOADON;
import static com.example.duan1.SQLite.OpenHelper.LOGO;
import static com.example.duan1.SQLite.OpenHelper.MAHANG;
import static com.example.duan1.SQLite.OpenHelper.MA_ADMIN_FK;
import static com.example.duan1.SQLite.OpenHelper.MA_HOADON;
import static com.example.duan1.SQLite.OpenHelper.NGAYTAO;
import static com.example.duan1.SQLite.OpenHelper.TENHANG;
import static com.example.duan1.SQLite.OpenHelper.TENKHACHHANG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.HoaDon;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO implements IHoaDonDAO{
    MyDatabase mydb;
    public HoaDonDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }
    @Override
    public List<HoaDon> get() {
        List<HoaDon> list = new ArrayList<>();
        String sql = " SELECT "+MA_HOADON+","+
                ""+TENKHACHHANG+","+
                ""+NGAYTAO+" "+
                "FROM "+BANG_HOADON+"";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MA_HOADON));
                    String name_customer = c.getString(c.getColumnIndex(TENKHACHHANG));
                    Long date = c.getLong(c.getColumnIndex(NGAYTAO));
                    Date create_date = new Date(date);

                    HoaDon hoaDon = new HoaDon();
                    hoaDon.setId(id);
                    hoaDon.setName_customer(name_customer);
                    hoaDon.setDate(create_date);

                    list.add(hoaDon);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Hoa Don Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }

    @Override
    public void insert(HoaDon hoaDon) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TENKHACHHANG,hoaDon.getName_customer());
            values.put(NGAYTAO,hoaDon.getDate().getTime());
            values.put(TENKHACHHANG,hoaDon.getName_customer());

            database.insertOrThrow(BANG_HOADON,null,values);
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Insert HoaDon error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(HoaDon hoaDon) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(TENKHACHHANG,hoaDon.getName_customer());
            values.put(NGAYTAO,hoaDon.getDate().getTime());
            values.put(TENKHACHHANG,hoaDon.getName_customer());
            database.update(BANG_HOADON,values,MA_HOADON + " = ?",new String[]{String.valueOf(hoaDon.getId())});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Update Hóa Đơn error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(int maHD) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(BANG_HOADON,MA_HOADON+ " = ?",new String[]{String.valueOf(maHD)});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Delete Hóa Đơn error", ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }
}
