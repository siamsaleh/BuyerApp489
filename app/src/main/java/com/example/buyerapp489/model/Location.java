package com.example.buyerapp489.model;

import java.io.Serializable;

public class Location implements Serializable {

    private boolean mirpur, dhaka, chittagong, rajshahi, khulna, barisal, sylhet, rangpur, mymensingh;

    public Location() {
        mirpur = false;
        dhaka = false;
        chittagong = false;
        rajshahi = false;
        khulna = false;
        barisal = false;
        sylhet = false;
        rangpur = false;
        mymensingh = false;
    }

    public Location(boolean mirpur, boolean dhaka, boolean chittagong, boolean rajshahi, boolean khulna, boolean barisal, boolean sylhet, boolean rangpur, boolean mymensingh) {
        this.mirpur = mirpur;
        this.dhaka = dhaka;
        this.chittagong = chittagong;
        this.rajshahi = rajshahi;
        this.khulna = khulna;
        this.barisal = barisal;
        this.sylhet = sylhet;
        this.rangpur = rangpur;
        this.mymensingh = mymensingh;
    }

    public boolean isMirpur() {
        return mirpur;
    }

    public void setMirpur(boolean mirpur) {
        this.mirpur = mirpur;
    }

    public boolean isDhaka() {
        return dhaka;
    }

    public void setDhaka(boolean dhaka) {
        this.dhaka = dhaka;
    }

    public boolean isChittagong() {
        return chittagong;
    }

    public void setChittagong(boolean chittagong) {
        this.chittagong = chittagong;
    }

    public boolean isRajshahi() {
        return rajshahi;
    }

    public void setRajshahi(boolean rajshahi) {
        this.rajshahi = rajshahi;
    }

    public boolean isKhulna() {
        return khulna;
    }

    public void setKhulna(boolean khulna) {
        this.khulna = khulna;
    }

    public boolean isBarisal() {
        return barisal;
    }

    public void setBarisal(boolean barisal) {
        this.barisal = barisal;
    }

    public boolean isSylhet() {
        return sylhet;
    }

    public void setSylhet(boolean sylhet) {
        this.sylhet = sylhet;
    }

    public boolean isRangpur() {
        return rangpur;
    }

    public void setRangpur(boolean rangpur) {
        this.rangpur = rangpur;
    }

    public boolean isMymensingh() {
        return mymensingh;
    }

    public void setMymensingh(boolean mymensingh) {
        this.mymensingh = mymensingh;
    }
}
