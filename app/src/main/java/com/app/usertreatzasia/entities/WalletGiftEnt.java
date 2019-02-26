package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletGiftEnt {

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
    @SerializedName("gift_status")
    @Expose
    private String giftStatus;
    @SerializedName("remaining_amount")
    @Expose
    private String remainingAmount;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_currency")
    @Expose
    private String transactionCurrency;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("credit_amount")
    @Expose
    private String creditAmount;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("evoucher_detail")
    @Expose
    private EvoucherDetailGift evoucherDetail;

    @SerializedName("user_detail")
    @Expose
    private UserDetailGift userDetail;

    @SerializedName("merchant_detail")
    @Expose
    private Object merchantDetail;

    @SerializedName("gift_user_detail")
    @Expose
    private GiftUserDetail giftUserDetail;

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

    public String getGiftStatus() {
        return giftStatus;
    }

    public void setGiftStatus(String giftStatus) {
        this.giftStatus = giftStatus;
    }

    public String getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(String remainingAmount) {
        this.remainingAmount = remainingAmount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(String transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getTransactionCurrency() {
        return transactionCurrency;
    }

    public void setTransactionCurrency(String transactionCurrency) {
        this.transactionCurrency = transactionCurrency;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(String creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public EvoucherDetailGift getEvoucherDetail() {
        return evoucherDetail;
    }

    public void setEvoucherDetail(EvoucherDetailGift evoucherDetail) {
        this.evoucherDetail = evoucherDetail;
    }

    public UserDetailGift getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetailGift userDetail) {
        this.userDetail = userDetail;
    }

    public Object getMerchantDetail() {
        return merchantDetail;
    }

    public void setMerchantDetail(Object merchantDetail) {
        this.merchantDetail = merchantDetail;
    }

    public GiftUserDetail getGiftUserDetail() {
        return giftUserDetail;
    }

    public void setGiftUserDetail(GiftUserDetail giftUserDetail) {
        this.giftUserDetail = giftUserDetail;
    }
}
