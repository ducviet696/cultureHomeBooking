package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchBody {

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
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("dStart")
    @Expose
    private Long dStart;
    @SerializedName("dEnd")
    @Expose
    private Long dEnd;

    public SearchBody() {
    }

    public SearchBody(Integer indexPage, Integer sizePage, Integer numRoom, String cityId, String homestayId, Long dStart, Long dEnd) {
        this.indexPage = indexPage;
        this.sizePage = sizePage;
        this.numRoom = numRoom;
        this.cityId = cityId;
        this.homestayId = homestayId;
        this.dStart = dStart;
        this.dEnd = dEnd;
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

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public long getDStart() {
        return dStart;
    }

    public void setDStart(long dStart) {
        this.dStart = dStart;
    }

    public long getDEnd() {
        return dEnd;
    }

    public void setDEnd(long dEnd) {
        this.dEnd = dEnd;
    }

}