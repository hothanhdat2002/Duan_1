package com.example.duan1.DAO;

import static com.example.duan1.SQLite.OpenHelper.*;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Fragment.HangXe_Fragment;
import com.example.duan1.Model.HangXe;

import java.util.ArrayList;
import java.util.List;

public class HangXeDAO implements IHangXeDAO{
    MyDatabase mydb;
    public HangXeDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }
    @Override
    public List<HangXe> get() {                     //xem hãng xe
        List<HangXe> list = new ArrayList<>();
        String sql = " SELECT "+MAHANG+","+
                ""+TENHANG+","+
                ""+LOGO +" "+
                "FROM "+BANG_HANGXE+"";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MAHANG));
                    String name = c.getString(c.getColumnIndex(TENHANG));
                    byte[] logo = c.getBlob(c.getColumnIndex(LOGO));

                    HangXe hangXe = new HangXe();
                    hangXe.setId(id);
                    hangXe.setName(name);
                    hangXe.setLogo(logo);

                    list.add(hangXe);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Hãng xe Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }
    public HangXe getByID(Integer id_hangxe) {
        String sql = " SELECT "+MAHANG+","+
                ""+TENHANG+", "+
                ""+LOGO +" "+
                " FROM "+BANG_HANGXE+ " WHERE "+MAHANG +" = ?";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(id_hangxe)});
        c.moveToFirst();
        int id = c.getInt(c.getColumnIndex(MAHANG));
        String name = c.getString(c.getColumnIndex(TENHANG));
        byte[] logo = c.getBlob(c.getColumnIndex(LOGO));

        HangXe hangXe = new HangXe();
        hangXe.setId(id);
        hangXe.setName(name);
        hangXe.setLogo(logo);
        c.close();
        return hangXe;
    }
    @Override
    public void insert(HangXe hangXe) {             //thêm hãng xe
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TENHANG,hangXe.getName());
            values.put(LOGO,hangXe.getLogo());
            database.insertOrThrow(BANG_HANGXE,null,values);
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Insert Hãng Xe error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(HangXe hangXe) {             //sửa hãng xe
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(TENHANG,hangXe.getName());
            values.put(LOGO,hangXe.getLogo());
            database.update(BANG_HANGXE,values,MAHANG + " = ?",new String[]{String.valueOf(hangXe.getId())});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Update Hãng Xe error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(int maHang) {             //xóa hãng xe
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(BANG_HANGXE,MAHANG+ " = ?",new String[]{String.valueOf(maHang)});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Delete Hãng xe error", ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }
}
