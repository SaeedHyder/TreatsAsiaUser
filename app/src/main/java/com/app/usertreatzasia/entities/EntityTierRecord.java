package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EntityTierRecord {

    @SerializedName("user")
    @Expose
    private String user;
    @SerializedName("amount")
    @Expose
    private String amount;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
