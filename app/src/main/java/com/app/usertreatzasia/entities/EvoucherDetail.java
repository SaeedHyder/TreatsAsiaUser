package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class EvoucherDetail {

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
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("product_id")
    @Expose
    private Integer productId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("no_of_usage_per_user")
    @Expose
    private String noOfUsagePerUser;
    @SerializedName("qr_code")
    @Expose
    private String qrCode;
    @SerializedName("term_condition")
    @Expose
    private String termCondition;
    @SerializedName("ma_term_condition")
    @Expose
    private String maTermCondition;
    @SerializedName("in_term_condition")
    @Expose
    private String inTermCondition;
    @SerializedName("type_select")
    @Expose
    private String typeSelect;
    @SerializedName("is_deleted")
    @Expose
    private Integer isDeleted;
    @SerializedName("is_exclusive")
    @Expose
    private Integer isExclusive;
    @SerializedName("expiry_date")
    @Expose
    private String expiryDate;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
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

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getNoOfUsagePerUser() {
        return noOfUsagePerUser;
    }

    public void setNoOfUsagePerUser(String noOfUsagePerUser) {
        this.noOfUsagePerUser = noOfUsagePerUser;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
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

    public String getTypeSelect() {
        return typeSelect;
    }

    public void setTypeSelect(String typeSelect) {
        this.typeSelect = typeSelect;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getIsExclusive() {
        return isExclusive;
    }

    public void setIsExclusive(Integer isExclusive) {
        this.isExclusive = isExclusive;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
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

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public void setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
    }
}
