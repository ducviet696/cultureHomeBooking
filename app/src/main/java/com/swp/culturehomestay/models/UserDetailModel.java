
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDetailModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("createdDate")
    @Expose
    private Integer createdDate;
    @SerializedName("editedDate")
    @Expose
    private Integer editedDate;
    @SerializedName("tenant")
    @Expose
    private Tenant tenant;
    @SerializedName("imangeUrl")
    @Expose
    private String imangeUrl;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("dob")
    @Expose
    private Integer dob;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;

    public UserDetailModel() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public String getImangeUrl() {
        return imangeUrl;
    }

    public void setImangeUrl(String imangeUrl) {
        this.imangeUrl = imangeUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getDob() {
        return dob;
    }

    public void setDob(Integer dob) {
        this.dob = dob;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
