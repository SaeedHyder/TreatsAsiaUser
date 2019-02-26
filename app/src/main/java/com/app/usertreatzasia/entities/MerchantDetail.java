package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MerchantDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("mobile_no")
    @Expose
    private String mobileNo;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("referral_code")
    @Expose
    private String referralCode;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;
    @SerializedName("role_id")
    @Expose
    private Integer roleId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("ma_service")
    @Expose
    private String maService;
    @SerializedName("in_service")
    @Expose
    private String inService;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("push_notification")
    @Expose
    private Integer pushNotification;
    @SerializedName("profile_picture")
    @Expose
    private String profilePicture;
    @SerializedName("socialmedia_type")
    @Expose
    private String socialmediaType;
    @SerializedName("socialmedia_id")
    @Expose
    private String socialmediaId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("merchant_id_code")
    @Expose
    private String merchantIdCode;
    @SerializedName("earned_credit")
    @Expose
    private String earnedCredit;
    @SerializedName("used_credit")
    @Expose
    private String usedCredit;
    @SerializedName("earned_point")
    @Expose
    private String earnedPoint;
    @SerializedName("used_point")
    @Expose
    private String usedPoint;
    @SerializedName("is_subscription")
    @Expose
    private Integer isSubscription;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMaService() {
        return maService;
    }

    public void setMaService(String maService) {
        this.maService = maService;
    }

    public String getInService() {
        return inService;
    }

    public void setInService(String inService) {
        this.inService = inService;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public Integer getPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(Integer pushNotification) {
        this.pushNotification = pushNotification;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getSocialmediaType() {
        return socialmediaType;
    }

    public void setSocialmediaType(String socialmediaType) {
        this.socialmediaType = socialmediaType;
    }

    public String getSocialmediaId() {
        return socialmediaId;
    }

    public void setSocialmediaId(String socialmediaId) {
        this.socialmediaId = socialmediaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantIdCode() {
        return merchantIdCode;
    }

    public void setMerchantIdCode(String merchantIdCode) {
        this.merchantIdCode = merchantIdCode;
    }

    public String getEarnedCredit() {
        return earnedCredit;
    }

    public void setEarnedCredit(String earnedCredit) {
        this.earnedCredit = earnedCredit;
    }

    public String getUsedCredit() {
        return usedCredit;
    }

    public void setUsedCredit(String usedCredit) {
        this.usedCredit = usedCredit;
    }

    public String getEarnedPoint() {
        return earnedPoint;
    }

    public void setEarnedPoint(String earnedPoint) {
        this.earnedPoint = earnedPoint;
    }

    public String getUsedPoint() {
        return usedPoint;
    }

    public void setUsedPoint(String usedPoint) {
        this.usedPoint = usedPoint;
    }

    public Integer getIsSubscription() {
        return isSubscription;
    }

    public void setIsSubscription(Integer isSubscription) {
        this.isSubscription = isSubscription;
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

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
}
