package com.example.duan1.DAO;

import static com.example.duan1.SQLite.OpenHelper.ANHXE;
import static com.example.duan1.SQLite.OpenHelper.BANG_HANGXE;
import static com.example.duan1.SQLite.OpenHelper.BANG_XE;
import static com.example.duan1.SQLite.OpenHelper.DONGCO;
import static com.example.duan1.SQLite.OpenHelper.GIAXE;
import static com.example.duan1.SQLite.OpenHelper.KHOILUONG;
import static com.example.duan1.SQLite.OpenHelper.LOGO;
import static com.example.duan1.SQLite.OpenHelper.MAHANG;
import static com.example.duan1.SQLite.OpenHelper.MAHANG_FK;
import static com.example.duan1.SQLite.OpenHelper.MAUXE;
import static com.example.duan1.SQLite.OpenHelper.MAXE;
import static com.example.duan1.SQLite.OpenHelper.MA_XE_FK;
import static com.example.duan1.SQLite.OpenHelper.NHIENLIEU;
import static com.example.duan1.SQLite.OpenHelper.SOLUONG;
import static com.example.duan1.SQLite.OpenHelper.TENHANG;
import static com.example.duan1.SQLite.OpenHelper.TENXE;
import static com.example.duan1.SQLite.OpenHelper.THETICH;
import static com.example.duan1.SQLite.OpenHelper.VANTOC;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.duan1.Database.MyDatabase;
import com.example.duan1.Fragment.HoaDonChiTiet_Fragment;
import com.example.duan1.Fragment.Xe_Fragment;
import com.example.duan1.Model.HangXe;
import com.example.duan1.Model.Xe;

import java.util.ArrayList;
import java.util.List;

public class XeDAO implements IXeDAO{
    MyDatabase mydb;
    Context c;
    public XeDAO(Context c){
        mydb = MyDatabase.getInstance(c);
    }
    @Override
    public List<Xe> get() {
        List<Xe> list = new ArrayList<>();
        String sql = " SELECT "+MAXE+","+
                ""+TENXE+","+
                ""+ANHXE+","+
                ""+MAUXE+","+
                ""+SOLUONG+","+
                ""+GIAXE+","+
                ""+KHOILUONG+","+
                ""+VANTOC+","+
                ""+NHIENLIEU+","+
                ""+THETICH+","+
                ""+DONGCO+","+
                ""+MAHANG_FK +" "+
                "FROM "+BANG_XE+"";
//        String sql = "SELECT * FROM XE";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,null);
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MAXE));
                    String name = c.getString(c.getColumnIndex(TENXE));
                    byte[] images = c.getBlob(c.getColumnIndex(ANHXE));
                    int color = c.getInt(c.getColumnIndex(MAUXE));
                    Integer amount = c.getInt(c.getColumnIndex(SOLUONG));
                    double price = c.getDouble(c.getColumnIndex(GIAXE));
                    int mass = c.getInt(c.getColumnIndex(KHOILUONG));
                    int speed = c.getInt(c.getColumnIndex(VANTOC));
                    double fuel = c.getDouble(c.getColumnIndex(NHIENLIEU));
                    Integer volume = c.getInt(c.getColumnIndex(THETICH));
                    String engine = c.getString(c.getColumnIndex(DONGCO));
                    Integer idhangxe = c.getInt(c.getColumnIndex(MAHANG_FK));

                    Xe xe = new Xe();
                    xe.setId(id);
                    xe.setName(name);
                    xe.setImages(images);
                    xe.setColor(color);
                    xe.setAmount(amount);
                    xe.setPrice(price);
                    xe.setMass(mass);
                    xe.setSpeed(speed);
                    xe.setFuel(fuel);
                    xe.setVolume(volume);
                    xe.setEngine(engine);
                    xe.setIdhangxe(idhangxe);

                    list.add(xe);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Xe Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }
    public Integer sumAmount(int id_xe) {
        int sum = 0;
        SQLiteDatabase database = mydb.getReadableDatabase();
        String sql =  "SELECT  MAXE,SUM(SOLUONG)  FROM XE WHERE MAXE = ? GROUP BY MAXE";
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(id_xe)});
        try {
            if (c.moveToFirst()){
                do {
                    sum = c.getInt(1);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Sum Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }

        return sum;
    }
    @Override
    public List<Xe> getByIDHang(int maHang) {
        List<Xe> list = new ArrayList<>();
        String sql = " SELECT "+MAXE+","+
                ""+TENXE+","+
                ""+ANHXE+","+
                ""+MAUXE+","+
                ""+SOLUONG+","+
                ""+GIAXE+","+
                ""+KHOILUONG+","+
                ""+VANTOC+","+
                ""+NHIENLIEU+","+
                ""+THETICH+","+
                ""+DONGCO+","+
                ""+MAHANG_FK +" "+
                "FROM "+BANG_XE+ " WHERE "+MAHANG_FK +" = ?";

        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c =  database.rawQuery(sql,new String[]{String.valueOf(maHang)});
        try {
            if (c.moveToFirst()) {
                do {
                    Integer id = c.getInt(c.getColumnIndex(MAXE));
                    String name = c.getString(c.getColumnIndex(TENXE));
                    byte[] images = c.getBlob(c.getColumnIndex(ANHXE));
                    int color = c.getInt(c.getColumnIndex(MAUXE));
                    Integer amount = c.getInt(c.getColumnIndex(SOLUONG));
                    double price = c.getDouble(c.getColumnIndex(GIAXE));
                    int mass = c.getInt(c.getColumnIndex(KHOILUONG));
                    int speed = c.getInt(c.getColumnIndex(VANTOC));
                    double fuel = c.getDouble(c.getColumnIndex(NHIENLIEU));
                    Integer volume = c.getInt(c.getColumnIndex(THETICH));
                    String engine = c.getString(c.getColumnIndex(DONGCO));
                    Integer idhangxe = c.getInt(c.getColumnIndex(MAHANG_FK));

                    Xe xe = new Xe();
                    xe.setId(id);
                    xe.setName(name);
                    xe.setImages(images);
                    xe.setColor(color);
                    xe.setAmount(amount);
                    xe.setPrice(price);
                    xe.setMass(mass);
                    xe.setSpeed(speed);
                    xe.setFuel(fuel);
                    xe.setVolume(volume);
                    xe.setEngine(engine);
                    xe.setIdhangxe(idhangxe);

                    list.add(xe);
                } while (c.moveToNext());
            }
        }catch (Exception ex) {
            Log.e("Get Xe Error: ", ex.getMessage());
        }
        finally {
            if (c!=null && c.isClosed()){
                c.close();
            }
        }
        return list;
    }

    @Override
    public Xe getByID(Integer maxe) {
        String sql = " SELECT "+MAXE+","+
                ""+TENXE+","+
                ""+ANHXE+","+
                ""+MAUXE+","+
                ""+SOLUONG+","+
                ""+GIAXE+","+
                ""+KHOILUONG+","+
                ""+VANTOC+","+
                ""+NHIENLIEU+","+
                ""+THETICH+","+
                ""+DONGCO+","+
                ""+MAHANG_FK +" "+
                "FROM "+BANG_XE+ " WHERE "+MAXE +" = ?";
        SQLiteDatabase database = mydb.getReadableDatabase();
        Cursor c = database.rawQuery(sql,new String[]{String.valueOf(maxe)});
        c.moveToFirst();
        Integer id = c.getInt(c.getColumnIndex(MAXE));
        String name = c.getString(c.getColumnIndex(TENXE));
        byte[] images = c.getBlob(c.getColumnIndex(ANHXE));
        int color = c.getInt(c.getColumnIndex(MAUXE));
        Integer amount = c.getInt(c.getColumnIndex(SOLUONG));
        double price = c.getDouble(c.getColumnIndex(GIAXE));
        int mass = c.getInt(c.getColumnIndex(KHOILUONG));
        int speed = c.getInt(c.getColumnIndex(VANTOC));
        double fuel = c.getDouble(c.getColumnIndex(NHIENLIEU));
        Integer volume = c.getInt(c.getColumnIndex(THETICH));
        String engine = c.getString(c.getColumnIndex(DONGCO));
        Integer idhangxe = c.getInt(c.getColumnIndex(MAHANG_FK));

        Xe xe = new Xe();
        xe.setId(id);
        xe.setName(name);
        xe.setImages(images);
        xe.setColor(color);
        xe.setAmount(amount);
        xe.setPrice(price);
        xe.setMass(mass);
        xe.setSpeed(speed);
        xe.setFuel(fuel);
        xe.setVolume(volume);
        xe.setEngine(engine);
        xe.setIdhangxe(idhangxe);
        c.close();
        return xe;
    }


    @Override
    public void insert(Xe xe) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(TENXE,xe.getName());
            values.put(ANHXE,xe.getImages());
            values.put(MAUXE,xe.getColor());
            values.put(SOLUONG,xe.getAmount());
            values.put(GIAXE,xe.getPrice());
            values.put(KHOILUONG,xe.getMass());
            values.put(VANTOC,xe.getSpeed());
            values.put(NHIENLIEU,xe.getFuel());
            values.put(THETICH,xe.getVolume());
            values.put(DONGCO,xe.getEngine());
            values.put(MAHANG_FK,xe.getIdhangxe());
            database.insertOrThrow(BANG_XE,null,values);
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Insert Xe error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void update(Xe xe) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try{
            ContentValues values = new ContentValues();
            values.put(TENXE,xe.getName());
            values.put(ANHXE,xe.getImages());
            values.put(MAUXE,xe.getColor());
            values.put(SOLUONG,xe.getAmount());
            values.put(GIAXE,xe.getPrice());
            values.put(KHOILUONG,xe.getMass());
            values.put(VANTOC,xe.getSpeed());
            values.put(NHIENLIEU,xe.getFuel());
            values.put(THETICH,xe.getVolume());
            values.put(DONGCO,xe.getEngine());
            values.put(MAHANG_FK,xe.getIdhangxe());
            database.update(BANG_XE,values,MAXE + " = ?",new String[]{String.valueOf(xe.getId())});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Update Xe error",ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void delete(int maxe) {
        SQLiteDatabase database = mydb.getWritableDatabase();
        database.beginTransaction();
        try {
            database.delete(BANG_XE,MAXE+ " = ?",new String[]{String.valueOf(maxe)});
            database.setTransactionSuccessful();
        }catch (Exception ex){
            Log.e("Delete Xe error", ex.getMessage());
        }finally {
            database.endTransaction();
        }
    }

    @Override
    public void updateAmount(int maxe_fk, int maxe) {
        String sql = "UPDATE XE  SET SOLUONG = SOLUONG - (SELECT SUM(SOLUONG) FROM HOADONCHITIET WHERE MAXE = ? ) WHERE MAXE = ?";
        SQLiteDatabase database = mydb.getWritableDatabase();
        Cursor c = database.rawQuery(sql, new String[]{String.valueOf(maxe_fk), String.valueOf(maxe)});
        try {
            c.moveToFirst();
            ContentValues values = new ContentValues();
            database.update(BANG_XE, values, MA_XE_FK + " = ?" +MAXE  + " = ?" , new String[]{String.valueOf(maxe_fk), String.valueOf(maxe)});
            database.setTransactionSuccessful();
            c.close();
            database.endTransaction();
        } catch (Exception ex) {
            Log.e("Update Xe error", ex.getMessage());
        }
    }


}
