package com.swp.culturehomestay.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomestayImage {

    @SerializedName("orderImage")
    @Expose
    private String orderImage;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("createdDate")
    @Expose
    private Integer createdDate;
    @SerializedName("editedDate")
    @Expose
    private Integer editedDate;

    public String getOrderImage() {
        return orderImage;
    }

    public void setOrderImage(String orderImage) {
        this.orderImage = orderImage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Integer createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Integer editedDate) {
        this.editedDate = editedDate;
    }

}