package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class WalletEvoucherDetailEnt {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("evoucher_id")
    @Expose
    private Integer evoucherId;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("qr_code_url")
    @Expose
    private String qrCodeUrl;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("evoucher_like")
    @Expose
    private Integer evoucher_like;
    @SerializedName("gift_status")
    @Expose
    private Integer giftStatus;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("evoucher_detail")
    @Expose
    private WalletEvoucherDetail evoucher_detail;

    public Integer getEvoucher_like() {
        return evoucher_like;
    }

    public void setEvoucher_like(Integer evoucher_like) {
        this.evoucher_like = evoucher_like;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getEvoucherId() {
        return evoucherId;
    }

    public void setEvoucherId(Integer evoucherId) {
        this.evoucherId = evoucherId;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(Integer giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
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

    public WalletEvoucherDetail getEvoucher_detail() {
        return evoucher_detail;
    }

    public void setEvoucher_detail(WalletEvoucherDetail evoucher_detail) {
        this.evoucher_detail = evoucher_detail;
    }
}
