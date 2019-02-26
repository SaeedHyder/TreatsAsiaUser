package com.app.usertreatzasia.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubscriptionEntity {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("subscription_name")
    @Expose
    private String subscriptionName;

    @SerializedName("ma_subscription_name")
    @Expose
    private String maSubscriptionName;

    @SerializedName("in_subscription_name")
    @Expose
    private String inSubscriptionName;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("period")
    @Expose
    private String period;

    @SerializedName("plan")
    @Expose
    private String plan;

    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("subscriptionfeatures")
    @Expose
    private List<Subscriptionfeature> subscriptionfeatures = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubscriptionName() {
        return subscriptionName;
    }

    public void setSubscriptionName(String subscriptionName) {
        this.subscriptionName = subscriptionName;
    }

    public String getMaSubscriptionName() {
        return maSubscriptionName;
    }

    public void setMaSubscriptionName(String maSubscriptionName) {
        this.maSubscriptionName = maSubscriptionName;
    }

    public String getInSubscriptionName() {
        return inSubscriptionName;
    }

    public void setInSubscriptionName(String inSubscriptionName) {
        this.inSubscriptionName = inSubscriptionName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public List<Subscriptionfeature> getSubscriptionfeatures() {
        return subscriptionfeatures;
    }

    public void setSubscriptionfeatures(List<Subscriptionfeature> subscriptionfeatures) {
        this.subscriptionfeatures = subscriptionfeatures;
    }
}
