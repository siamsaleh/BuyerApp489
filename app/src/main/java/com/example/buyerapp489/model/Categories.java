package com.example.buyerapp489.model;

public class Categories {
    private String Image;
    private String Name;

    public Categories(String icon_url, String name) {
        this.Image = icon_url;
        this.Name = name;
    }
    public Categories(){}

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
}