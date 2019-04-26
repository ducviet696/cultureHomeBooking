package com.swp.culturehomestay.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SearchHomePost {


    @SerializedName("dStart")
    @Expose
    private Date dStart;
    @SerializedName("dEnd")
    @Expose
    private Date dEnd;
    @SerializedName("bookingMethod")
    @Expose
    private String bookingMethod;
    @SerializedName("homestayTypes")
    @Expose
    private List<String> homestayTypes;
    @SerializedName("roomTypes")
    @Expose
    private List<String> roomTypes;
    @SerializedName("numBethRoom")
    @Expose
    private Integer numBethRoom;
    @SerializedName("amenityBeans")
    @Expose
    private List<Integer> amenityBeans = null;
    @SerializedName("cultureServiceBeans")
    @Expose
    private List<Integer> cultureServiceBeans = null;
    @SerializedName("numberPeople")
    @Expose
    private Integer numberPeople;
    @SerializedName("minPrice")
    @Expose
    private Integer minPrice;
    @SerializedName("maxPrice")
    @Expose
    private Integer maxPrice;
    @SerializedName("indexPage")
    @Expose
    private Integer indexPage;
    @SerializedName("sizePage")
    @Expose
    private Integer sizePage;
    @SerializedName("numRoom")
    @Expose
    private Integer numRoom;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("districtId")
    @Expose
    private String districtId;

    public SearchHomePost(@Nullable Date dStart, @Nullable Date dEnd, @Nullable String bookingMethod, @Nullable List<String> homestayTypes, @Nullable List<String> roomTypes, @Nullable Integer numBethRoom, @Nullable List<Integer> amenityBeans, @Nullable List<Integer> cultureServiceBeans, @Nullable Integer numberPeople, @Nullable Integer minPrice, @Nullable Integer maxPrice, @Nullable Integer indexPage, @Nullable Integer sizePage, @Nullable Integer numRoom, @Nullable String cityId, @Nullable String districtId) {
        this.dStart = dStart;
        this.dEnd = dEnd;
        this.bookingMethod = bookingMethod;
        this.homestayTypes = homestayTypes;
        this.roomTypes = roomTypes;
        this.numBethRoom = numBethRoom;
        this.amenityBeans = amenityBeans;
        this.cultureServiceBeans = cultureServiceBeans;
        this.numberPeople = numberPeople;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.indexPage = indexPage;
        this.sizePage = sizePage;
        this.numRoom = numRoom;
        this.cityId = cityId;
        this.districtId = districtId;
    }

    public SearchHomePost(Integer indexPage, Integer sizePage, Integer numRoom, String cityId, String districtId) {
        this.indexPage = indexPage;
        this.sizePage = sizePage;
        this.numRoom = numRoom;
        this.cityId = cityId;
        this.districtId = districtId;
    }

    public Integer getIndexPage() {
        return indexPage;
    }

    public void setIndexPage(Integer indexPage) {
        this.indexPage = indexPage;
    }

    public Integer getSizePage() {
        return sizePage;
    }

    public void setSizePage(Integer sizePage) {
        this.sizePage = sizePage;
    }

    public Integer getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(Integer numRoom) {
        this.numRoom = numRoom;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

}
