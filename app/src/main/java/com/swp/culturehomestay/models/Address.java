package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Address {

    @SerializedName("addressId")
    @Expose
    private String addressId;
    @SerializedName("addressFull")
    @Expose
    private String addressFull;

    @Expose
    private Double lng;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("countryId")
    @Expose
    private String countryId;
    @SerializedName("districtId")
    @Expose
    private String districtId;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getAddressFull() {
        return addressFull;
    }

    public void setAddressFull(String addressFull) {
        this.addressFull = addressFull;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

}