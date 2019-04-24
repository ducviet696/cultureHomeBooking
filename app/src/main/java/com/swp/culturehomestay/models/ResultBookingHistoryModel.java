package com.swp.culturehomestay.models;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultBookingHistoryModel {
    @SerializedName("content")
    @Expose
    private List<ReservationModel> content = null;

    public List<ReservationModel> getContent() {
        return content;
    }

    public void setContent(List<ReservationModel> content) {
        this.content = content;
    }
}
