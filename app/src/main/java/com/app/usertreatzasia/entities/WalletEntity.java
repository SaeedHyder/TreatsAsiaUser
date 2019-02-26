package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletEntity {

    @SerializedName("user_evoucher_count")
    @Expose
    private Integer userEvoucherCount;
    @SerializedName("user_gift_evoucher_count")
    @Expose
    private Integer userGiftEvoucherCount;
    @SerializedName("user_event_count")
    @Expose
    private Integer userEventCount;
    @SerializedName("user_gift_event_count")
    @Expose
    private Integer userGiftEventCount;
    @SerializedName("user_reward")
    @Expose
    private Integer userReward;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("credit")
    @Expose
    private String credit;

    public Integer getUserEvoucherCount() {
        return userEvoucherCount;
    }

    public void setUserEvoucherCount(Integer userEvoucherCount) {
        this.userEvoucherCount = userEvoucherCount;
    }

    public Integer getUserGiftEvoucherCount() {
        return userGiftEvoucherCount;
    }

    public void setUserGiftEvoucherCount(Integer userGiftEvoucherCount) {
        this.userGiftEvoucherCount = userGiftEvoucherCount;
    }

    public Integer getUserEventCount() {
        return userEventCount;
    }

    public void setUserEventCount(Integer userEventCount) {
        this.userEventCount = userEventCount;
    }

    public Integer getUserGiftEventCount() {
        return userGiftEventCount;
    }

    public void setUserGiftEventCount(Integer userGiftEventCount) {
        this.userGiftEventCount = userGiftEventCount;
    }

    public Integer getUserReward() {
        return userReward;
    }

    public void setUserReward(Integer userReward) {
        this.userReward = userReward;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
}
