
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReservationModel {

    @SerializedName("reservationId")
    @Expose
    private String reservationId;
    @SerializedName("tenantId")
    @Expose
    private String tenantId;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("hostEmail")
    @Expose
    private String hostEmail;
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
    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;
    @SerializedName("paymentWant")
    @Expose
    private PaymentWant paymentWant;

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
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

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
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

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public long getEditedDate() {
        return editedDate;
    }

    public void setEditedDate(long editedDate) {
        this.editedDate = editedDate;
    }

    public PaymentWant getPaymentWant() {
        return paymentWant;
    }

    public void setPaymentWant(PaymentWant paymentWant) {
        this.paymentWant = paymentWant;
    }

}
