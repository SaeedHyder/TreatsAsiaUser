package com.app.usertreatzasia.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardsWonEnt {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("lucky_id")
    @Expose
    private Integer luckyId;
    @SerializedName("reward_id")
    @Expose
    private Integer rewardId;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("reward_detail")
    @Expose
    private RewardWonDetail rewardDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLuckyId() {
        return luckyId;
    }

    public void setLuckyId(Integer luckyId) {
        this.luckyId = luckyId;
    }

    public Integer getRewardId() {
        return rewardId;
    }

    public void setRewardId(Integer rewardId) {
        this.rewardId = rewardId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public RewardWonDetail getRewardDetail() {
        return rewardDetail;
    }

    public void setRewardDetail(RewardWonDetail rewardDetail) {
        this.rewardDetail = rewardDetail;
    }
}
