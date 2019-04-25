package com.swp.culturehomestay.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PricePost {

    @SerializedName("cultureServiceBeans")
    @Expose
    private ArrayList<Integer> cultureServiceBeans = null;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("numRoom")
    @Expose
    private Integer numRoom;

    @SerializedName("numberPeople")
    @Expose
    private Integer numberPeople;

    @SerializedName("dStart")
    @Expose
    private long dStart;
    @SerializedName("dEnd")
    @Expose
    private long dEnd;

    public Integer getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(Integer numRoom) {
        this.numRoom = numRoom;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public long getdStart() {
        return dStart;
    }

    public void setdStart(long dStart) {
        this.dStart = dStart;
    }

    public long getdEnd() {
        return dEnd;
    }

    @Override
    public String toString() {
        return "PricePost{" +
                "cultureServiceBeans=" + cultureServiceBeans +
                ", homestayId='" + homestayId + '\'' +
                ", numRoom=" + numRoom +
                ", numberPeople=" + numberPeople +
                ", dStart=" + dStart +
                ", dEnd=" + dEnd +
                '}';
    }

    public PricePost(ArrayList<Integer> cultureServiceBeans, String homestayId, Integer numRoom, Integer numberPeople, long dStart, long dEnd) {
        this.cultureServiceBeans = cultureServiceBeans;
        this.homestayId = homestayId;
        this.numRoom = numRoom;
        this.numberPeople = numberPeople;
        this.dStart = dStart;
        this.dEnd = dEnd;
    }

    public ArrayList<Integer> getCultureServiceBeans() {
        return cultureServiceBeans;
    }

    public void setCultureServiceBeans(ArrayList<Integer> cultureServiceBeans) {
        this.cultureServiceBeans = cultureServiceBeans;
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