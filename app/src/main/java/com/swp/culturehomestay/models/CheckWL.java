package com.swp.culturehomestay.models;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckWL {
    @SerializedName("content")
    @Expose
    private Integer content;
    @SerializedName("code")
    @Expose
    private String code;

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
