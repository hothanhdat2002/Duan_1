package com.example.duan1.DAO;

import static com.example.duan1.SQLite.OpenHelper.BANG_ADMIN;
import static com.example.duan1.SQLite.OpenHelper.BANG_HANGXE;
import static com.example.duan1.SQLite.OpenHelper.LOGO;
import static com.example.duan1.SQLite.OpenHelper.MAHANG;
import static com.example.duan1.SQLite.OpenHelper.MATKHAU;
import static com.example.duan1.SQLite.OpenHelper.MA_ADMIN;
import static com.example.duan1.SQLite.OpenHelper.SDT;
import static com.example.duan1.SQLite.OpenHelper.TENHANG;
import static com.example.duan1.SQLite.OpenHelper.TENTAIKHOAN;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Model.Admin;
import com.example.duan1.Model.HangXe;

import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements IAdminDAO{
    MyDatabase mydb;
    public AdminDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }
    @Override
    public List<Admin> get() {
        List<Admin> list = new ArrayList<>();
        String sql = " SELECT "+MA_ADMIN+","+
                ""+TENTAIKHOAN+","+
                ""+MATKHAU+","+
                ""+SDT +" "+
                "FROM "+BANG_ADMIN+"";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MA_ADMIN));
                    String name = c.getString(c.getColumnIndex(TENTAIKHOAN));
                    String pass = c.getString(c.getColumnIndex(MATKHAU));
                    Integer phone = c.getInt(c.getColumnIndex(SDT));

                    Admin admin = new Admin();
                    admin.setId(id);
                    admin.setName(name);
                    admin.setPass(pass);
                    admin.setPhone(phone);

                    list.add(admin);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Admin Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }

    @Override
    public Admin getByID(Integer maAdmin) {
        String sql = " SELECT "+MA_ADMIN+","+
                ""+TENTAIKHOAN+","+
                ""+MATKHAU+","+
                ""+SDT +" "+
                "FROM "+BANG_ADMIN+ " WHERE "+MA_ADMIN +" = ?";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(maAdmin)});
        c.moveToFirst();
        Integer id = c.getInt(c.getColumnIndex(MA_ADMIN));
        String name = c.getString(c.getColumnIndex(TENTAIKHOAN));
        String pass = c.getString(c.getColumnIndex(MATKHAU));
        Integer phone = c.getInt(c.getColumnIndex(SDT));

        Admin admin = new Admin();
        admin.setId(id);
        admin.setName(name);
        admin.setPass(pass);
        admin.setPhone(phone);
        c.close();
        return admin;
    }

    @Override
    public void insert(Admin admin) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TENTAIKHOAN,admin.getName());
            values.put(MATKHAU,admin.getPass());
            values.put(SDT,admin.getPhone());
            database.insertOrThrow(BANG_ADMIN,null,values);
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Insert Admin error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(Admin admin) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(TENTAIKHOAN,admin.getName());
            values.put(MATKHAU,admin.getPass());
            values.put(SDT,admin.getPhone());
            database.update(BANG_ADMIN,values,MA_ADMIN + " = ?",new String[]{String.valueOf(admin.getId())});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Update Admin error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(int maAdmin) {

    }
}
