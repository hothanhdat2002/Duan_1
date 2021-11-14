package com.example.myapplication.modal;

import android.widget.ImageView;
import android.widget.TextView;

public class HarleyDavidson {
    int background;
    String name;
    String category;
    Float price;

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public HarleyDavidson(int background, String name, String category, Float price) {
        this.background = background;
        this.name = name;
        this.category = category;
        this.price = price;
    }



}
