package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResultGetReservationModel {
    @SerializedName("content")
    @Expose
    private Reservation content = null;

    public Reservation getContent() {
        return content;
    }

    public void setContent(Reservation content) {
        this.content = content;
    }
}
