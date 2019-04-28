package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentWantBean {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("interact")
    @Expose
    private String interact;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("reservationCode")
    @Expose
    private String reservationCode;

    public PaymentWantBean(String method, String interact, String type, String purpose, String reservationCode) {
        this.method = method;
        this.interact = interact;
        this.type = type;
        this.purpose = purpose;
        this.reservationCode = reservationCode;
    }

    @Override
    public String toString() {
        return "PaymentWantBean{" +
                "method='" + method + '\'' +
                ", interact='" + interact + '\'' +
                ", type='" + type + '\'' +
                ", purpose='" + purpose + '\'' +
                ", reservationCode='" + reservationCode + '\'' +
                '}';
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getInteract() {
        return interact;
    }

    public void setInteract(String interact) {
        this.interact = interact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

}