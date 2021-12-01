package com.example.duan1.Model;

public class Top {
    private String name;
    private int amount;
    private byte[] images;

    public Top() {
    }

    public Top(String name, int amount, byte[] images) {
        this.name = name;
        this.amount = amount;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}
