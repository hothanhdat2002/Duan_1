package com.example.duan1.Model;

public class Xe {
    private int id;
    private String name;
    private byte[] images;
    private int color;
    private int amount;         //số lượng
    private double price;
    private int mass;        //khối lượng
    private int speed;
    private double fuel;        //nhiên liệu
    private int volume;         //thể tích xilanh
    private String engine;      //động cơ
    private int idhangxe;       //mã hãng

    public Xe() {
    }

    public Xe(int id, String name, byte[] images, int color, int amount, double price, int mass, int speed, double fuel, int volume, String engine, int idhangxe) {
        this.id = id;
        this.name = name;
        this.images = images;
        this.color = color;
        this.amount = amount;
        this.price = price;
        this.mass = mass;
        this.speed = speed;
        this.fuel = fuel;
        this.volume = volume;
        this.engine = engine;
        this.idhangxe = idhangxe;
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

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public double getFuel() {
        return fuel;
    }

    public void setFuel(double fuel) {
        this.fuel = fuel;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public int getIdhangxe() {
        return idhangxe;
    }

    public void setIdhangxe(int id_mahang) {
        this.idhangxe = id_mahang;
    }
}
