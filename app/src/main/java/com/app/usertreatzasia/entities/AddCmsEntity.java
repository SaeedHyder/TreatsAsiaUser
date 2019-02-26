package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddCmsEntity {

    @SerializedName("advertisement")
    @Expose
    private AdvertisementEntity advertisement;
    @SerializedName("reward")
    @Expose
    private String reward;
    @SerializedName("contactus")
    @Expose
    private String contactus;

    public AdvertisementEntity getAdvertisement() {
        return advertisement;
    }

    public void setAdvertisement(AdvertisementEntity advertisement) {
        this.advertisement = advertisement;
    }

    public String getReward() {
        return reward;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public String getContactus() {
        return contactus;
    }

    public void setContactus(String contactus) {
        this.contactus = contactus;
    }
}
