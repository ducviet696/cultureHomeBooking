package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DateBooked {

    @SerializedName("content")
    @Expose
    private List<Integer> listDateBooked = null;
    @SerializedName("code")
    @Expose
    private String code;

    public List<Integer> getListDateBooked() {
        return listDateBooked;
    }

    public void setListDateBooked(List<Integer> listDateBooked) {
        this.listDateBooked = listDateBooked;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}