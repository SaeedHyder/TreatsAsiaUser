package com.app.usertreatzasia.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created on 10/9/2017.
 */

public class DetailEnt {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("ma_title")
    private String ma_title;
    @SerializedName("in_title")
    private String in_title;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("product_id")
    private int product_id;
    @SerializedName("type")
    private String type;
    @SerializedName("amount")
    private String amount;
    @SerializedName("merchant_id")
    private int merchant_id;
    @SerializedName("no_of_usage_per_user")
    private String no_of_usage_per_user;
    @SerializedName("qr_code")
    private String qr_code;
    @SerializedName("term_condition")
    private String term_condition;
    @SerializedName("ma_term_condition")
    private String ma_term_condition;
    @SerializedName("in_term_condition")
    private String in_term_condition;
    @SerializedName("type_select")
    private String type_select;
    @SerializedName("is_deleted")
    private int is_deleted;
    @SerializedName("is_exclusive")
    private int is_exclusive;
    @SerializedName("expiry_date")
    private String expiry_date;
    @SerializedName("created_at")
    private String created_at;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("count")
    private int count;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMa_title() {
        return ma_title;
    }

    public void setMa_title(String ma_title) {
        this.ma_title = ma_title;
    }

    public String getIn_title() {
        return in_title;
    }

    public void setIn_title(String in_title) {
        this.in_title = in_title;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getNo_of_usage_per_user() {
        return no_of_usage_per_user;
    }

    public void setNo_of_usage_per_user(String no_of_usage_per_user) {
        this.no_of_usage_per_user = no_of_usage_per_user;
    }

    public String getQr_code() {
        return qr_code;
    }

    public void setQr_code(String qr_code) {
        this.qr_code = qr_code;
    }

    public String getTerm_condition() {
        return term_condition;
    }

    public void setTerm_condition(String term_condition) {
        this.term_condition = term_condition;
    }

    public String getMa_term_condition() {
        return ma_term_condition;
    }

    public void setMa_term_condition(String ma_term_condition) {
        this.ma_term_condition = ma_term_condition;
    }

    public String getIn_term_condition() {
        return in_term_condition;
    }

    public void setIn_term_condition(String in_term_condition) {
        this.in_term_condition = in_term_condition;
    }

    public String getType_select() {
        return type_select;
    }

    public void setType_select(String type_select) {
        this.type_select = type_select;
    }

    public int getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(int is_deleted) {
        this.is_deleted = is_deleted;
    }

    public int getIs_exclusive() {
        return is_exclusive;
    }

    public void setIs_exclusive(int is_exclusive) {
        this.is_exclusive = is_exclusive;
    }

    public String getExpiry_date() {
        return expiry_date;
    }

    public void setExpiry_date(String expiry_date) {
        this.expiry_date = expiry_date;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
