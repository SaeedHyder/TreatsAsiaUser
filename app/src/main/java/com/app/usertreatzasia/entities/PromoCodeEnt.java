package com.app.usertreatzasia.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PromoCodeEnt {

    @SerializedName("amount")
    @Expose
    private String amount;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
