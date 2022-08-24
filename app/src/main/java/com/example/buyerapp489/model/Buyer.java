package com.example.buyerapp489.model;

import java.io.Serializable;

public class Buyer implements Serializable {
    String email, id, phone, division, profileImage, storeName;
    String address, city, state, postalCode, ownerName, uniqueID;
    int isVerified, rating;

    public Buyer() {
    }

    public Buyer(String email, String id, String phone, String division, String profileImage, String storeName, String address, String city, String state, String postalCode, String ownerName, int rating, int isVerified, String uniqueID) {
        this.email = email;
        this.id = id;
        this.phone = phone;
        this.division = division;
        this.profileImage = profileImage;
        this.storeName = storeName;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postalCode = postalCode;
        this.ownerName = ownerName;
        this.rating = rating;
        this.isVerified = isVerified;
        this.uniqueID = uniqueID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public int getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(int isVerified) {
        this.isVerified = isVerified;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }
}
