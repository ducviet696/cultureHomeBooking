package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReservationGet implements Serializable {

    @SerializedName("reservationId")
    @Expose
    private String reservationId;
    @SerializedName("reservationCode")
    @Expose
    private String reservationCode;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("purpose")
    @Expose
    private String purpose;
    @SerializedName("hostEmail")
    @Expose
    private String hostEmail;
    @SerializedName("userEmail")
    @Expose
    private String userEmail;
    @SerializedName("houseCode")
    @Expose
    private String houseCode;
    @SerializedName("basicFee")
    @Expose
    private Integer basicFee;
    @SerializedName("dStart")
    @Expose
    private long dStart;
    @SerializedName("numRoom")
    @Expose
    private Integer numRoom;
    @SerializedName("dEnd")
    @Expose
    private long dEnd;
    @SerializedName("cultureServiceFee")
    @Expose
    private Integer cultureServiceFee;
    @SerializedName("holidayFee")
    @Expose
    private Integer holidayFee;
    @SerializedName("totalFee")
    @Expose
    private Integer totalFee;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;
    @SerializedName("numberPeople")
    @Expose
    private Integer numberPeople;
    @SerializedName("cultureServiceChoices")
    @Expose
    private String cultureServiceChoices;
    @SerializedName("roomType")
    @Expose
    private String roomType;

    @Override
    public String toString() {
        return "ReservationGet{" +
                "reservationId='" + reservationId + '\'' +
                ", reservationCode='" + reservationCode + '\'' +
                ", tenantId='" + tenantId + '\'' +
                ", homestayId='" + homestayId + '\'' +
                ", phone='" + phone + '\'' +
                ", fullname='" + fullname + '\'' +
                ", hostId='" + hostId + '\'' +
                ", purpose='" + purpose + '\'' +
                ", hostEmail='" + hostEmail + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", houseCode='" + houseCode + '\'' +
                ", basicFee=" + basicFee +
                ", dStart=" + dStart +
                ", numRoom=" + numRoom +
                ", dEnd=" + dEnd +
                ", cultureServiceFee=" + cultureServiceFee +
                ", holidayFee=" + holidayFee +
                ", totalFee=" + totalFee +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                ", editedDate=" + editedDate +
                ", numberPeople=" + numberPeople +
                ", cultureServiceChoices='" + cultureServiceChoices + '\'' +
                ", roomType='" + roomType + '\'' +
                '}';
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getReservationCode() {
        return reservationCode;
    }

    public void setReservationCode(String reservationCode) {
        this.reservationCode = reservationCode;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public Integer getBasicFee() {
        return basicFee;
    }

    public void setBasicFee(Integer basicFee) {
        this.basicFee = basicFee;
    }

    public long getDStart() {
        return dStart;
    }

    public void setDStart(long dStart) {
        this.dStart = dStart;
    }

    public Integer getNumRoom() {
        return numRoom;
    }

    public void setNumRoom(Integer numRoom) {
        this.numRoom = numRoom;
    }

    public long getDEnd() {
        return dEnd;
    }

    public void setDEnd(long dEnd) {
        this.dEnd = dEnd;
    }

    public Integer getCultureServiceFee() {
        return cultureServiceFee;
    }

    public void setCultureServiceFee(Integer cultureServiceFee) {
        this.cultureServiceFee = cultureServiceFee;
    }

    public Integer getHolidayFee() {
        return holidayFee;
    }

    public void setHolidayFee(Integer holidayFee) {
        this.holidayFee = holidayFee;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(Integer editedDate) {
        this.editedDate = editedDate;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public String getCultureServiceChoices() {
        return cultureServiceChoices;
    }

    public void setCultureServiceChoices(String cultureServiceChoices) {
        this.cultureServiceChoices = cultureServiceChoices;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }
}
