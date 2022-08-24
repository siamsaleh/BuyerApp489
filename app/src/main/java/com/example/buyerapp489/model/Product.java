package com.example.buyerapp489.model;

import java.io.Serializable;

public class Product implements Serializable {

    String product_name,product_category,product_price,product_image,product_quantity,product_shop,discount,productVideo,product_description, order_status,
            delivery_address, buyer_phone, delivery_charge, order_date;
    String sellerId, buyerId, product_key, minimum_order, order_id;
    String subCategory, policy, stock, review, view, mrp;
    boolean isFeatured;
    Location location;
    ProductImageArray productImageArray;


//    public Product(String product_name, String product_category, String product_price, String product_image, String product_quantity, String product_shop, String discount, String productVideo, String product_description, String order_status, String delivery_address, String buyer_phone, String delivery_charge, String order_date, String sellerId, String product_key, String minimum_order, String order_id, boolean isFeatured, Location location
//                                    ) {
//        this.product_name = product_name;
//        this.product_category = product_category;
//        this.product_price = product_price;
//        this.product_image = product_image;
//        this.product_quantity = product_quantity;
//        this.product_shop = product_shop;
//        this.discount = discount;
//        this.productVideo = productVideo;
//        this.product_description = product_description;
//        this.order_status = order_status;
//        this.delivery_address = delivery_address;
//        this.buyer_phone = buyer_phone;
//        this.delivery_charge = delivery_charge;
//        this.order_date = order_date;
//        this.sellerId = sellerId;
//        this.product_key = product_key;
//        this.minimum_order = minimum_order;
//        this.order_id = order_id;
//        this.isFeatured = isFeatured;
//        this.location = location;
//    }


    public Product(String product_name, String product_category, String product_price, String product_image, String product_quantity,
                   String product_shop, String discount, String productVideo, String product_description, String order_status,
                   String delivery_address, String buyer_phone, String delivery_charge, String order_date, String sellerId, String buyerId,
                   String product_key, String minimum_order, String order_id, String subCategory, String policy, String stock, String review,
                   String view, String mrp, boolean isFeatured, Location location, ProductImageArray productImageArray) {
        this.product_name = product_name;
        this.product_category = product_category;
        this.product_price = product_price;
        this.product_image = product_image;
        this.product_quantity = product_quantity;
        this.product_shop = product_shop;
        this.discount = discount;
        this.productVideo = productVideo;
        this.product_description = product_description;
        this.order_status = order_status;
        this.delivery_address = delivery_address;
        this.buyer_phone = buyer_phone;
        this.delivery_charge = delivery_charge;
        this.order_date = order_date;
        this.sellerId = sellerId;
        this.buyerId = buyerId;
        this.product_key = product_key;
        this.minimum_order = minimum_order;
        this.order_id = order_id;
        this.subCategory = subCategory;
        this.policy = policy;
        this.stock = stock;
        this.review = review;
        this.view = view;
        this.mrp = mrp;
        this.isFeatured = isFeatured;
        this.location = location;
        this.productImageArray = productImageArray;
    }

//    public Product(String product_name, String product_category, String product_price, String product_image,
//                   String product_quantity, String product_shop, String discount, String productVideo,
//                   String product_description, String order_status, String delivery_address, String buyer_phone,
//                   String delivery_charge, String order_date, String sellerId, String buyerId, String product_key,
//                   String minimum_order, String order_id, String subCategory, String policy, String stock, String review,
//                   String view, boolean isFeatured, Location location, String mrp, ProductImageArray productImageArray) {
//        this.product_name = product_name;
//        this.product_category = product_category;
//        this.product_price = product_price;
//        this.product_image = product_image;
//        this.product_quantity = product_quantity;
//        this.product_shop = product_shop;
//        this.discount = discount;
//        this.productVideo = productVideo;
//        this.product_description = product_description;
//        this.order_status = order_status;
//        this.delivery_address = delivery_address;
//        this.buyer_phone = buyer_phone;
//        this.delivery_charge = delivery_charge;
//        this.order_date = order_date;
//        this.sellerId = sellerId;
//        this.buyerId = buyerId;
//        this.product_key = product_key;
//        this.minimum_order = minimum_order;
//        this.order_id = order_id;
//        this.subCategory = subCategory;
//        this.policy = policy;
//        this.stock = stock;
//        this.review = review;
//        this.view = view;
//        this.isFeatured = isFeatured;
//        this.location = location;
//        this.mrp = mrp;
//        this.productImageArray = productImageArray;
//    }

    public Product(){
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_category() {
        return product_category;
    }

    public void setProduct_category(String product_category) {
        this.product_category = product_category;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(String product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_shop() {
        return product_shop;
    }

    public void setProduct_shop(String product_shop) {
        this.product_shop = product_shop;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductVideo() {
        return productVideo;
    }

    public void setProductVideo(String productVideo) {
        this.productVideo = productVideo;
    }

    public String getProduct_description() {
        return product_description;
    }

    public void setProduct_description(String product_description) {
        this.product_description = product_description;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getDelivery_address() {
        return delivery_address;
    }

    public void setDelivery_address(String delivery_address) {
        this.delivery_address = delivery_address;
    }

    public String getBuyer_phone() {
        return buyer_phone;
    }

    public void setBuyer_phone(String buyer_phone) {
        this.buyer_phone = buyer_phone;
    }

    public String getDelivery_charge() {
        return delivery_charge;
    }

    public void setDelivery_charge(String delivery_charge) {
        this.delivery_charge = delivery_charge;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getProduct_key() {
        return product_key;
    }

    public void setProduct_key(String product_key) {
        this.product_key = product_key;
    }

    public String getMinimum_order() {
        return minimum_order;
    }

    public void setMinimum_order(String minimum_order) {
        this.minimum_order = minimum_order;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getPolicy() {
        return policy;
    }

    public void setPolicy(String policy) {
        this.policy = policy;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }

    public ProductImageArray getProductImageArray() {
        return productImageArray;
    }

    public void setProductImageArray(ProductImageArray productImageArray) {
        this.productImageArray = productImageArray;
    }
}

