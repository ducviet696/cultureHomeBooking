
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
    private long createdDate;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;
    @SerializedName("balanceTranfer")
    @Expose
    private double balanceTranfer;
    @SerializedName("balance")
    @Expose
    private double balance;
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

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(long editedDate) {
        this.editedDate = editedDate;
    }

    public double getBalanceTranfer() {
        return balanceTranfer;
    }

    public void setBalanceTranfer(double balanceTranfer) {
        this.balanceTranfer = balanceTranfer;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Wishlist> getWishLists() {
        return wishLists;
    }

    public void setWishLists(List<Wishlist> wishLists) {
        this.wishLists = wishLists;
    }

}
