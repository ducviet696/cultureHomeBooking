package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContentHomeName {

    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("homestayMultis")
    @Expose
    private List<HomestayMulti> homestayMultis = null;

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public List<HomestayMulti> getHomestayMultis() {
        return homestayMultis;
    }

    public void setHomestayMultis(List<HomestayMulti> homestayMultis) {
        this.homestayMultis = homestayMultis;
    }

}
