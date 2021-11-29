package com.example.duan1.Model;

public class HangXe {
    private int id;
    private String name;
    private byte[] logo;

    public HangXe() {
    }

    public HangXe(int id, String name, byte[] logo) {
        this.id = id;
        this.name = name;
        this.logo = logo;
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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}
