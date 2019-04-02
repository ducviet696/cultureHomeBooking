package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("addressId")
    @Expose
    private String addressId;
    @SerializedName("createdDate")
    @Expose
    private Integer createdDate;
    @SerializedName("editedDate")
    @Expose
    private Integer editedDate;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
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