package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeNameGet {

    @SerializedName("content")
    @Expose
    private List<ContentHomeName> content = null;
    @SerializedName("code")
    @Expose
    private String code;

    public List<ContentHomeName> getContent() {
        return content;
    }

    public void setContent(List<ContentHomeName> content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
