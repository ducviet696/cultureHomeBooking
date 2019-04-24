
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationEvent {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("reservationId")
    @Expose
    private String reservationId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

}
