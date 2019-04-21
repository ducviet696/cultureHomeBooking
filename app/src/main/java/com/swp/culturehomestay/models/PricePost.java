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
    @SerializedName("dStart")
    @Expose
    private long dStart;
    @SerializedName("dEnd")
    @Expose
    private long dEnd;

    @Override
    public String toString() {
        return "PricePost{" +
                "cultureServiceBeans=" + cultureServiceBeans +
                ", homestayId='" + homestayId + '\'' +
                ", dStart=" + dStart +
                ", dEnd=" + dEnd +
                '}';
    }

    public PricePost(ArrayList<Integer> cultureServiceBeans, String homestayId, long dStart, long dEnd) {
        this.cultureServiceBeans = cultureServiceBeans;
        this.homestayId = homestayId;
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