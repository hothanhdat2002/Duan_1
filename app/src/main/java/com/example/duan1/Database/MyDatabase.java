package com.example.duan1.Database;

import static com.example.duan1.SQLite.OpenHelper.*;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDatabase extends SQLiteOpenHelper {
    private static MyDatabase dbInstance;
    public static synchronized MyDatabase getInstance(Context c){
        if(dbInstance==null){
            dbInstance = new MyDatabase(c);
        }
        return dbInstance;
    }
    public MyDatabase(Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Tao bang Hãng Xe
        String createTableHangXe = "CREATE TABLE IF NOT EXISTS " + BANG_HANGXE+
                "( "+MAHANG +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+TENHANG+" TEXT NOT NULL," +
                ""+LOGO+" BLOB NOT NULL)";
        db.execSQL(createTableHangXe);

        //Tao bang Xe
        String createTableXe = "CREATE TABLE IF NOT EXISTS " + BANG_XE+
                "( "+MAXE +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+TENXE+" TEXT NOT NULL," +
                ""+ANHXE+" BLOB NOT NULL," +
                ""+MAUXE+" INTEGER NOT NULL," +
                ""+SOLUONG+" INTEGER NOT NULL," +
                ""+GIAXE+" REAL NOT NULL," +
                ""+KHOILUONG+" INTEGER NOT NULL," +
                ""+VANTOC+" INTEGER NOT NULL," +
                ""+NHIENLIEU+" REAL NOT NULL," +
                ""+THETICH+" INTEGER NOT NULL," +
                ""+DONGCO+" TEXT NOT NULL," +
                ""+MAHANG_FK+" INTEGER NOT NULL," +
                "FOREIGN KEY ( " +MAHANG_FK+")" +
                " REFERENCES "+BANG_HANGXE+"("+MAHANG+") )";
        db.execSQL(createTableXe);

        //Tao bang Hóa Đơn
        String createTableHoaDon = "CREATE TABLE IF NOT EXISTS " + BANG_HOADON+
                "( "+MA_HOADON +" INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+TENKHACHHANG+" TEXT NOT NULL," +
                ""+NGAYTAO+" DATE NOT NULL)";
        db.execSQL(createTableHoaDon);

        // Tao bang hóa đơn chi tiết
        String createTableBillDetail = "CREATE TABLE IF NOT EXISTS " + BANG_HDCT+
                "( "+MA_HDCT+" INTEGER PRIMARY KEY AUTOINCREMENT," +
                ""+MA_HOADON_FK+" INTEGER NOT NULL," +
                ""+MA_XE_FK+" INTEGER NOT NULL," +
                ""+SOLUONGBAN+" INTEGER NOT NULL," +
                "FOREIGN KEY ("+MA_HOADON_FK+")"+
                " REFERENCES "+BANG_HOADON+"("+MA_HOADON+")," +
                "FOREIGN KEY ( " +MA_XE_FK+")" +
                " REFERENCES "+BANG_XE+"("+MAXE+") )";
        db.execSQL(createTableBillDetail);

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int upgrade = oldVersion + 1;
        while (upgrade <= newVersion) {
            switch (upgrade) {
                case 2:
                    String sql4 = "INSERT INTO "+BANG_HDCT+ " VALUES "+ "(1,1,1,3)";
                    db.execSQL(sql4);
                    break;
                case 3:

                    break;
                default:
                    break;
            }
            upgrade++;
        }
    }
}
