package com.app.usertreatzasia.entities;

/**
 * Created by saeedhyder on 9/14/2017.
 */

public class HomeEnt {

    String image;
    String discountDetail;
    String totalAmount;
    String afterDiscount;
    String expireOn;
    String address;

    public HomeEnt(String image, String discountDetail, String totalAmount, String afterDiscount, String expireOn, String address) {
        this.image = image;
        this.discountDetail = discountDetail;
        this.totalAmount = totalAmount;
        this.afterDiscount = afterDiscount;
        this.expireOn = expireOn;
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDiscountDetail() {
        return discountDetail;
    }

    public void setDiscountDetail(String discountDetail) {
        this.discountDetail = discountDetail;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getAfterDiscount() {
        return afterDiscount;
    }

    public void setAfterDiscount(String afterDiscount) {
        this.afterDiscount = afterDiscount;
    }

    public String getExpireOn() {
        return expireOn;
    }

    public void setExpireOn(String expireOn) {
        this.expireOn = expireOn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
