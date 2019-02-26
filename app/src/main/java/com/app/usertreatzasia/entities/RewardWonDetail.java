package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RewardWonDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ma_title")
    @Expose
    private String maTitle;
    @SerializedName("in_title")
    @Expose
    private String inTitle;
    @SerializedName("reward_picture")
    @Expose
    private String rewardPicture;
    @SerializedName("original_price")
    @Expose
    private String originalPrice;
    @SerializedName("reward_price")
    @Expose
    private String rewardPrice;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("result_date")
    @Expose
    private String resultDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ma_description")
    @Expose
    private String maDescription;
    @SerializedName("in_description")
    @Expose
    private String inDescription;
    @SerializedName("term_condition")
    @Expose
    private String termCondition;
    @SerializedName("ma_term_condition")
    @Expose
    private String maTermCondition;
    @SerializedName("in_term_condition")
    @Expose
    private String inTermCondition;
    @SerializedName("slug")
    @Expose
    private String slug;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("reward_image")
    @Expose
    private String rewardImage;
    @SerializedName("product_detail")
    @Expose
    private ProductDetail productDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMaTitle() {
        return maTitle;
    }

    public void setMaTitle(String maTitle) {
        this.maTitle = maTitle;
    }

    public String getInTitle() {
        return inTitle;
    }

    public void setInTitle(String inTitle) {
        this.inTitle = inTitle;
    }

    public String getRewardPicture() {
        return rewardPicture;
    }

    public void setRewardPicture(String rewardPicture) {
        this.rewardPicture = rewardPicture;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
    }

    public String getRewardPrice() {
        return rewardPrice;
    }

    public void setRewardPrice(String rewardPrice) {
        this.rewardPrice = rewardPrice;
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

    public String getResultDate() {
        return resultDate;
    }

    public void setResultDate(String resultDate) {
        this.resultDate = resultDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public String getTermCondition() {
        return termCondition;
    }

    public void setTermCondition(String termCondition) {
        this.termCondition = termCondition;
    }

    public String getMaTermCondition() {
        return maTermCondition;
    }

    public void setMaTermCondition(String maTermCondition) {
        this.maTermCondition = maTermCondition;
    }

    public String getInTermCondition() {
        return inTermCondition;
    }

    public void setInTermCondition(String inTermCondition) {
        this.inTermCondition = inTermCondition;
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

    public String getRewardImage() {
        return rewardImage;
    }

    public void setRewardImage(String rewardImage) {
        this.rewardImage = rewardImage;
    }

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
