package com.app.usertreatzasia.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

    public class FilterEnt {

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
        @SerializedName("category_picture")
        @Expose
        private String categoryPicture;
        @SerializedName("category_banner")
        @Expose
        private String categoryBanner;
        @SerializedName("category_body")
        @Expose
        private String categoryBody;
        @SerializedName("ma_category_body")
        @Expose
        private String maCategoryBody;
        @SerializedName("in_category_body")
        @Expose
        private String inCategoryBody;
        @SerializedName("is_deleted")
        @Expose
        private Integer isDeleted;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;
        @SerializedName("category_image")
        @Expose
        private String categoryImage;
        @SerializedName("banner_image")
        @Expose
        private String bannerImage;

        private boolean ischecked;

        public boolean ischecked() {
            return ischecked;
        }

        public void setIschecked(boolean ischecked) {
            this.ischecked = ischecked;
        }

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

        public String getCategoryPicture() {
            return categoryPicture;
        }

        public void setCategoryPicture(String categoryPicture) {
            this.categoryPicture = categoryPicture;
        }

        public String getCategoryBanner() {
            return categoryBanner;
        }

        public void setCategoryBanner(String categoryBanner) {
            this.categoryBanner = categoryBanner;
        }

        public String getCategoryBody() {
            return categoryBody;
        }

        public void setCategoryBody(String categoryBody) {
            this.categoryBody = categoryBody;
        }

        public String getMaCategoryBody() {
            return maCategoryBody;
        }

        public void setMaCategoryBody(String maCategoryBody) {
            this.maCategoryBody = maCategoryBody;
        }

        public String getInCategoryBody() {
            return inCategoryBody;
        }

        public void setInCategoryBody(String inCategoryBody) {
            this.inCategoryBody = inCategoryBody;
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

        public String getCategoryImage() {
            return categoryImage;
        }

        public void setCategoryImage(String categoryImage) {
            this.categoryImage = categoryImage;
        }

        public String getBannerImage() {
            return bannerImage;
        }

        public void setBannerImage(String bannerImage) {
            this.bannerImage = bannerImage;
        }
    }
