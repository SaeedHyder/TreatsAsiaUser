package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmedsyed on 1/5/2018.
 */

public class TopupCashoutTermsConditionEntity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("credit_to_point")
    @Expose
    private String creditToPoint;
    @SerializedName("cash_to_credit")
    @Expose
    private String cashToCredit;
    @SerializedName("commission_percent")
    @Expose
    private String commissionPercent;
    @SerializedName("term_cashout")
    @Expose
    private String termCashout;
    @SerializedName("ma_term_cashout")
    @Expose
    private String maTermCashout;
    @SerializedName("in_term_cashout")
    @Expose
    private String inTermCashout;
    @SerializedName("term_topup")
    @Expose
    private String termTopup;
    @SerializedName("ma_term_topup")
    @Expose
    private String maTermTopup;
    @SerializedName("in_term_topup")
    @Expose
    private String inTermTopup;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreditToPoint() {
        return creditToPoint;
    }

    public void setCreditToPoint(String creditToPoint) {
        this.creditToPoint = creditToPoint;
    }

    public String getCashToCredit() {
        return cashToCredit;
    }

    public void setCashToCredit(String cashToCredit) {
        this.cashToCredit = cashToCredit;
    }

    public String getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(String commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public String getTermCashout() {
        return termCashout;
    }

    public void setTermCashout(String termCashout) {
        this.termCashout = termCashout;
    }

    public String getMaTermCashout() {
        return maTermCashout;
    }

    public void setMaTermCashout(String maTermCashout) {
        this.maTermCashout = maTermCashout;
    }

    public String getInTermCashout() {
        return inTermCashout;
    }

    public void setInTermCashout(String inTermCashout) {
        this.inTermCashout = inTermCashout;
    }

    public String getTermTopup() {
        return termTopup;
    }

    public void setTermTopup(String termTopup) {
        this.termTopup = termTopup;
    }

    public String getMaTermTopup() {
        return maTermTopup;
    }

    public void setMaTermTopup(String maTermTopup) {
        this.maTermTopup = maTermTopup;
    }

    public String getInTermTopup() {
        return inTermTopup;
    }

    public void setInTermTopup(String inTermTopup) {
        this.inTermTopup = inTermTopup;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
