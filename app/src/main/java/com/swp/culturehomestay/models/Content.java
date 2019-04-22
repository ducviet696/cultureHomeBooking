package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content {

    @SerializedName("basicFee")
    @Expose
    private Integer basicFee;
    @SerializedName("cultureFee")
    @Expose
    private Integer cultureFee;
    @SerializedName("hollidayFee")
    @Expose
    private Integer hollidayFee;
    @SerializedName("total")
    @Expose
    private Integer total;

    @Override
    public String toString() {
        return "PriceGet{" +
                "basicFee=" + basicFee +
                ", cultureFee=" + cultureFee +
                ", hollidayFee=" + hollidayFee +
                ", total=" + total +
                '}';
    }

    public Integer getBasicFee() {
        return basicFee;
    }

    public void setBasicFee(Integer basicFee) {
        this.basicFee = basicFee;
    }

    public Integer getCultureFee() {
        return cultureFee;
    }

    public void setCultureFee(Integer cultureFee) {
        this.cultureFee = cultureFee;
    }

    public Integer getHollidayFee() {
        return hollidayFee;
    }

    public void setHollidayFee(Integer hollidayFee) {
        this.hollidayFee = hollidayFee;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
