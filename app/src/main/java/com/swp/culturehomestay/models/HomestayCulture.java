package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomestayCulture {

    @SerializedName("cultureServiceId")
    @Expose
    private String cultureServiceId;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("cultureService")
    @Expose
    private CultureService cultureService;
    @SerializedName("discription")
    @Expose
    private String discription;
    @SerializedName("price")
    @Expose
    private Integer price;

    public String getCultureServiceId() {
        return cultureServiceId;
    }

    public void setCultureServiceId(String cultureServiceId) {
        this.cultureServiceId = cultureServiceId;
    }

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public CultureService getCultureService() {
        return cultureService;
    }

    public void setCultureService(CultureService cultureService) {
        this.cultureService = cultureService;
    }

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}