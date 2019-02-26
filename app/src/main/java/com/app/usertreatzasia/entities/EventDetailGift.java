package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EventDetailGift {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("ma_event_name")
    @Expose
    private String maEventName;
    @SerializedName("in_event_name")
    @Expose
    private String inEventName;
    @SerializedName("event_picture")
    @Expose
    private String eventPicture;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ma_description")
    @Expose
    private String maDescription;
    @SerializedName("in_description")
    @Expose
    private String inDescription;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("start_format")
    @Expose
    private String startFormat;
    @SerializedName("end_format")
    @Expose
    private String endFormat;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("is_point")
    @Expose
    private Integer isPoint;
    @SerializedName("is_credit")
    @Expose
    private Integer isCredit;
    @SerializedName("is_paypal")
    @Expose
    private Integer isPaypal;
    @SerializedName("is_free")
    @Expose
    private Integer isFree;
    @SerializedName("event_like")
    @Expose
    private Integer event_like;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("venue")
    @Expose
    private String venue;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("event_image")
    @Expose
    private String eventImage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMaEventName() {
        return maEventName;
    }

    public void setMaEventName(String maEventName) {
        this.maEventName = maEventName;
    }

    public String getInEventName() {
        return inEventName;
    }

    public void setInEventName(String inEventName) {
        this.inEventName = inEventName;
    }

    public String getEventPicture() {
        return eventPicture;
    }

    public void setEventPicture(String eventPicture) {
        this.eventPicture = eventPicture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaDescription() {
        return maDescription;
    }

    public void setMaDescription(String maDescription) {
        this.maDescription = maDescription;
    }

    public String getInDescription() {
        return inDescription;
    }

    public void setInDescription(String inDescription) {
        this.inDescription = inDescription;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartFormat() {
        return startFormat;
    }

    public void setStartFormat(String startFormat) {
        this.startFormat = startFormat;
    }

    public String getEndFormat() {
        return endFormat;
    }

    public void setEndFormat(String endFormat) {
        this.endFormat = endFormat;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(Integer isPoint) {
        this.isPoint = isPoint;
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIsPaypal() {
        return isPaypal;
    }

    public void setIsPaypal(Integer isPaypal) {
        this.isPaypal = isPaypal;
    }

    public Integer getIsFree() {
        return isFree;
    }

    public void setIsFree(Integer isFree) {
        this.isFree = isFree;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public Integer getEvent_like() {
        return event_like;
    }

    public void setEvent_like(Integer event_like) {
        this.event_like = event_like;
    }
}
