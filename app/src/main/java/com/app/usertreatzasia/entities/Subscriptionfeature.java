package com.app.usertreatzasia.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subscriptionfeature {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;

    @SerializedName("feature_id")
    @Expose
    private Integer featureId;

    @SerializedName("configure_amount")
    @Expose
    private String configureAmount;

    @SerializedName("layer1")
    @Expose
    private String layer1;

    @SerializedName("layer2")
    @Expose
    private String layer2;

    @SerializedName("created_at")
    @Expose
    private String createdAt;

    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    @SerializedName("feature_detail")
    @Expose
    private FeatureDetail featureDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public String getConfigureAmount() {
        return configureAmount;
    }

    public void setConfigureAmount(String configureAmount) {
        this.configureAmount = configureAmount;
    }

    public String getLayer1() {
        return layer1;
    }

    public void setLayer1(String layer1) {
        this.layer1 = layer1;
    }

    public String getLayer2() {
        return layer2;
    }

    public void setLayer2(String layer2) {
        this.layer2 = layer2;
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

    public FeatureDetail getFeatureDetail() {
        return featureDetail;
    }

    public void setFeatureDetail(FeatureDetail featureDetail) {
        this.featureDetail = featureDetail;
    }
}
