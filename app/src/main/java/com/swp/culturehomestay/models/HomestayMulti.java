package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomestayMulti {

    @SerializedName("homestayMultiId")
    @Expose
    private String homestayMultiId;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("summary")
    @Expose
    private String summary;
    @SerializedName("homestayName")
    @Expose
    private String homestayName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("des_unifuture")
    @Expose
    private String desUnifuture;
    @SerializedName("des_specialNote")
    @Expose
    private String desSpecialNote;
    @SerializedName("houseRule")
    @Expose
    private String houseRule;
    @SerializedName("houseManual")
    @Expose
    private String houseManual;
    @SerializedName("checkinManual")
    @Expose
    private String checkinManual;

    public String getHomestayMultiId() {
        return homestayMultiId;
    }

    public void setHomestayMultiId(String homestayMultiId) {
        this.homestayMultiId = homestayMultiId;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getHomestayName() {
        return homestayName;
    }

    public void setHomestayName(String homestayName) {
        this.homestayName = homestayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesUnifuture() {
        return desUnifuture;
    }

    public void setDesUnifuture(String desUnifuture) {
        this.desUnifuture = desUnifuture;
    }

    public String getDesSpecialNote() {
        return desSpecialNote;
    }

    public void setDesSpecialNote(String desSpecialNote) {
        this.desSpecialNote = desSpecialNote;
    }

    public String getHouseRule() {
        return houseRule;
    }

    public void setHouseRule(String houseRule) {
        this.houseRule = houseRule;
    }

    public String getHouseManual() {
        return houseManual;
    }

    public void setHouseManual(String houseManual) {
        this.houseManual = houseManual;
    }

    public String getCheckinManual() {
        return checkinManual;
    }

    public void setCheckinManual(String checkinManual) {
        this.checkinManual = checkinManual;
    }

}