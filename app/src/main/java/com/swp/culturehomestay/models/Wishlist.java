package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Wishlist implements Serializable {

    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("homestay")
    @Expose
    private HomeStay homestay;

    public Wishlist(String tenantId, String homestayId) {
        this.tenantId = tenantId;
        this.homestayId = homestayId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public HomeStay getHomestay() {
        return homestay;
    }

    public void setHomestay(HomeStay homestay) {
        this.homestay = homestay;
    }

}
