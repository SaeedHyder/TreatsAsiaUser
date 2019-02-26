package com.app.usertreatzasia.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 10/9/2017.
 */

public class RedeemEnt {

    @SerializedName("user_id")
    private String user_id;
    @SerializedName("evoucher_id")
    private String evoucher_id;
    @SerializedName("qr_code")
    private String qr_code;
    @SerializedName("qr_code_url")
    private String qr_code_url;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("id")
    private int id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getEvoucher_id() {
        return evoucher_id;
    }

    public void setEvoucher_id(String evoucher_id) {
        this.evoucher_id = evoucher_id;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getQr_code_url() {
        return qr_code_url;
    }

    public void setQr_code_url(String qr_code_url) {
        this.qr_code_url = qr_code_url;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
