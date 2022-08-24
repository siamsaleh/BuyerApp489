package com.example.buyerapp489.model;

import java.io.Serializable;

public class Favourite implements Serializable {
    String key, sellerID;

    public Favourite(String key, String sellerID) {
        this.key = key;
        this.sellerID = sellerID;
    }

    public Favourite() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSellerID() {
        return sellerID;
    }

    public void setSellerID(String sellerID) {
        this.sellerID = sellerID;
    }
}
