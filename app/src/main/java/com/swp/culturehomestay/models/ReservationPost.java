package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationPost {

    @SerializedName("cultureServiceBeans")
    @Expose
    private List<Integer> cultureServiceBeans = null;
    @SerializedName("numberPeople")
    @Expose
    private Integer numberPeople;
    @SerializedName("numRoom")
    @Expose
    private Integer numRoom;
    @SerializedName("dStart")
    @Expose
    private long dStart;
    @SerializedName("dEnd")
    @Expose
    private long dEnd;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("hostEmail")
    @Expose
    private String hostEmail;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("reservationBean")
    @Expose
    private ReservationBean reservationBean;


    public ReservationPost(List<Integer> cultureServiceBeans, Integer numberPeople, Integer numRoom,long dStart, long dEnd, String homestayId, String hostEmail, String tenantId, ReservationBean reservationBean) {
        this.cultureServiceBeans = cultureServiceBeans;
        this.numberPeople = numberPeople;
        this.numRoom = numRoom;
        this.dStart = dStart;
        this.dEnd = dEnd;
        this.homestayId = homestayId;
        this.hostEmail = hostEmail;
        this.tenantId = tenantId;
        this.reservationBean = reservationBean;
    }

    @Override
    public String toString() {
        return "ReservationPost{" +
                "cultureServiceBeans=" + cultureServiceBeans +
                ", numberPeople=" + numberPeople +
                ", numRoom=" + numRoom +
                ", dStart=" + dStart +
                ", dEnd=" + dEnd +
                ", homestayId='" + homestayId + '\'' +
                ", hostEmail='" + hostEmail + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", reservationBean=" + reservationBean.toString() +
                '}';
    }

    public Integer getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(Integer numRoom) {
        this.numRoom = numRoom;
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

    public void setdEnd(long dEnd) {
        this.dEnd = dEnd;
    }

    public List<Integer> getCultureServiceBeans() {
        return cultureServiceBeans;
    }

    public void setCultureServiceBeans(List<Integer> cultureServiceBeans) {
        this.cultureServiceBeans = cultureServiceBeans;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
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

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public ReservationBean getReservationBean() {
        return reservationBean;
    }

    public void setReservationBean(ReservationBean reservationBean) {
        this.reservationBean = reservationBean;
    }

}