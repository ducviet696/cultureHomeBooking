package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationBean {

    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("houseCode")
    @Expose
    private String houseCode;
    @SerializedName("status")
    @Expose
    private String status;

    public ReservationBean(String homestayId, String phone, String fullname, String hostId, String purpose, String userEmail, String houseCode, String status) {
        this.homestayId = homestayId;
        this.phone = phone;
        this.fullname = fullname;
        this.hostId = hostId;
        this.purpose = purpose;
        this.userEmail = userEmail;
        this.houseCode = houseCode;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservationBean{" +
                "homestayId='" + homestayId + '\'' +
                ", phone='" + phone + '\'' +
                ", fullname='" + fullname + '\'' +
                ", hostId='" + hostId + '\'' +
                ", purpose='" + purpose + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", houseCode='" + houseCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
