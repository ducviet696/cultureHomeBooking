package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchHomePost {
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
