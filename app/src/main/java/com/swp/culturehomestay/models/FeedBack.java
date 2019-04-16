package com.swp.culturehomestay.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedBack {

    @SerializedName("comment")
    @Expose
    private String comment;
    @SerializedName("start")
    @Expose
    private Integer start;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}