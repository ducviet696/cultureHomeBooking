package com.swp.culturehomestay.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CultureService {

    @SerializedName("cultureServiceId")
    @Expose
    private String cultureServiceId;
    @SerializedName("cultureServiceName")
    @Expose
    private String cultureServiceName;
    @SerializedName("englisgName")
    @Expose
    private String englisgName;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("active")
    @Expose
    private Boolean active;

    public String getCultureServiceId() {
        return cultureServiceId;
    }

    public void setCultureServiceId(String cultureServiceId) {
        this.cultureServiceId = cultureServiceId;
    }

    public String getCultureServiceName() {
        return cultureServiceName;
    }

    public void setCultureServiceName(String cultureServiceName) {
        this.cultureServiceName = cultureServiceName;
    }

    public String getEnglisgName() {
        return englisgName;
    }

    public void setEnglisgName(String englisgName) {
        this.englisgName = englisgName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

}
