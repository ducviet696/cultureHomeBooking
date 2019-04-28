package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReservationContent implements Serializable {
    @SerializedName("content")
    @Expose
    private ReservationGet content;

    @Override
    public String toString() {
        return "ReservationContent{" +
                "content=" + content.toString() +
                '}';
    }

    public ReservationGet getContent() {
        return content;
    }

    public void setContent(ReservationGet content) {
        this.content = content;
    }
}
