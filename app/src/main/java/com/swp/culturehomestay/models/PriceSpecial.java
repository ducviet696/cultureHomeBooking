package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PriceSpecial {

    @SerializedName("specialPriceId")
    @Expose
    private Integer specialPriceId;
    @SerializedName("night")
    @Expose
    private Integer night;
    @SerializedName("weekend")
    @Expose
    private Integer weekend;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("startDate")
    @Expose
    private long startDate;
    @SerializedName("endDate")
    @Expose
    private long endDate;

    public Integer getSpecialPriceId() {
        return specialPriceId;
    }

    public void setSpecialPriceId(Integer specialPriceId) {
        this.specialPriceId = specialPriceId;
    }

    public Integer getNight() {
        return night;
    }

    public void setNight(Integer night) {
        this.night = night;
    }

    public Integer getWeekend() {
        return weekend;
    }

    public void setWeekend(Integer weekend) {
        this.weekend = weekend;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

}
