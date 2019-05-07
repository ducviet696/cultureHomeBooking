package com.swp.culturehomestay.models;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class SearchHomePost {
    @SerializedName("homestayStatus")
    @Expose
    String homestayStatus;
    @SerializedName("fullText")
    @Expose
    private String fullText;
    @SerializedName("dStart")
    @Expose
    private String dStart;
    @SerializedName("dEnd")
    @Expose
    private String dEnd;
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
    @SerializedName("orderBy")
    @Expose
    private String orderBy;
    @SerializedName("districtId")
    @Expose
    private String districtId;
    @SerializedName("ascOrDesc")
    @Expose
    private String ascOrDesc;


    public SearchHomePost(Integer indexPage, Integer sizePage, String orderBy, String ascOrDesc, String homestayStatus) {
        this.indexPage = indexPage;
        this.sizePage = sizePage;
        this.orderBy = orderBy;
        this.ascOrDesc = ascOrDesc;
        this.homestayStatus = homestayStatus;
    }

    @Override
    public String toString() {
        return "SearchHomePost{" +
                "fullText='" + fullText + '\'' +
                ", dStart='" + dStart + '\'' +
                ", dEnd='" + dEnd + '\'' +
                ", bookingMethod='" + bookingMethod + '\'' +
                ", homestayTypes=" + homestayTypes +
                ", roomTypes=" + roomTypes +
                ", numBethRoom=" + numBethRoom +
                ", amenityBeans=" + amenityBeans +
                ", cultureServiceBeans=" + cultureServiceBeans +
                ", numberPeople=" + numberPeople +
                ", minPrice=" + minPrice +
                ", maxPrice=" + maxPrice +
                ", indexPage=" + indexPage +
                ", sizePage=" + sizePage +
                ", numRoom=" + numRoom +
                ", cityId='" + cityId + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", districtId='" + districtId + '\'' +
                ", ascOrDesc='" + ascOrDesc + '\'' +
                '}';
    }



    public SearchHomePost(@Nullable String dStart, @Nullable String dEnd, @Nullable String bookingMethod, @Nullable List<String> homestayTypes, @Nullable List<String> roomTypes, @Nullable Integer numBethRoom, @Nullable List<Integer> amenityBeans, @Nullable List<Integer> cultureServiceBeans, @Nullable Integer numberPeople, @Nullable Integer minPrice, @Nullable Integer maxPrice, @Nullable Integer indexPage, @Nullable Integer sizePage, @Nullable Integer numRoom, @Nullable String cityId, @Nullable String districtId, @Nullable String fullText, String homestayStatus) {
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
        this.fullText = fullText;
        this.homestayStatus = homestayStatus;
    }

    public SearchHomePost(Integer indexPage, Integer sizePage, Integer numRoom, String cityId, String districtId, String homestayStatus) {
        this.indexPage = indexPage;
        this.sizePage = sizePage;
        this.numRoom = numRoom;
        this.cityId = cityId;
        this.districtId = districtId;
        this.homestayStatus = homestayStatus;
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
