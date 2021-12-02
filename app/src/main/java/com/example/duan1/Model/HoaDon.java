package com.example.duan1.Model;

import java.util.Date;

public class HoaDon {
    private int id;
    private String name_customer;
    private Date date;

    public HoaDon() {
    }

    public HoaDon(int id, String name_customer, Date date, int id_admin) {
        this.id = id;
        this.name_customer = name_customer;
        this.date = date;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
