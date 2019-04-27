
package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tenant {

    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("createdDate")
    @Expose
    private Integer createdDate;
    @SerializedName("editedDate")
    @Expose
    private Integer editedDate;
    @SerializedName("balanceTranfer")
    @Expose
    private Integer balanceTranfer;
    @SerializedName("balance")
    @Expose
    private Integer balance;
    @SerializedName("wishLists")
    @Expose
    private List<Wishlist> wishLists = null;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
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

    public Integer getBalanceTranfer() {
        return balanceTranfer;
    }

    public void setBalanceTranfer(Integer balanceTranfer) {
        this.balanceTranfer = balanceTranfer;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public List<Wishlist> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<Wishlist> wishLists) {
        this.wishLists = wishLists;
    }

}
