package com.example.duan1.Model;

public class Admin {
    private int id;
    private String name;
    private String pass;
    private int phone;

    public Admin() {
    }

    public Admin(int id, String name, String pass, int phone) {
        this.id = id;
        this.name = name;
        this.pass = pass;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }
}
