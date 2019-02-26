package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by ahmedsyed on 12/26/2017.
 */

public class WalletEventDetailEnt {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("event_id")
    @Expose
    private String eventId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("transaction_amount")
    @Expose
    private String transactionAmount;
    @SerializedName("transaction_currency")
    @Expose
    private String transactionCurrency;
    @SerializedName("transaction_status")
    @Expose
    private String transactionStatus;
    @SerializedName("purchase_date")
    @Expose
    private String purchaseDate;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("credit_amount")
    @Expose
    private String creditAmount;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("qr_code_url")
    @Expose
    private String qrCodeUrl;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("gift_status")
    @Expose
    private String giftStatus;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
}
