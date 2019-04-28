package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentPost {

    @SerializedName("cultureServiceBeans")
    @Expose
    private List<Integer> cultureServiceBeans = null;
    @SerializedName("numberPeople")
    @Expose
    private Integer numberPeople;
    @SerializedName("dStart")
    @Expose
    private long dStart;
    @SerializedName("dEnd")
    @Expose
    private long dEnd;
    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("hostEmail")
    @Expose
    private String hostEmail;
    @SerializedName("paymentWantBean")
    @Expose
    private PaymentWantBean paymentWantBean;

    public PaymentPost(List<Integer> cultureServiceBeans, Integer numberPeople, long dStart, long dEnd, String homestayId, String hostEmail, PaymentWantBean paymentWantBean) {
        this.cultureServiceBeans = cultureServiceBeans;
        this.numberPeople = numberPeople;
        this.dStart = dStart;
        this.dEnd = dEnd;
        this.homestayId = homestayId;
        this.hostEmail = hostEmail;
        this.paymentWantBean = paymentWantBean;
    }

    @Override
    public String toString() {
        return "PaymentPost{" +
                "cultureServiceBeans=" + cultureServiceBeans +
                ", numberPeople=" + numberPeople +
                ", dStart=" + dStart +
                ", dEnd=" + dEnd +
                ", homestayId='" + homestayId + '\'' +
                ", hostEmail='" + hostEmail + '\'' +
                ", paymentWantBean=" + paymentWantBean.toString() +
                '}';
    }

    public List<Integer> getCultureServiceBeans() {
        return cultureServiceBeans;
    }

    public void setCultureServiceBeans(List<Integer> cultureServiceBeans) {
        this.cultureServiceBeans = cultureServiceBeans;
    }

    public Integer getNumberPeople() {
        return numberPeople;
    }

    public void setNumberPeople(Integer numberPeople) {
        this.numberPeople = numberPeople;
    }

    public long getDStart() {
        return dStart;
    }

    public void setDStart(long dStart) {
        this.dStart = dStart;
    }

    public long getDEnd() {
        return dEnd;
    }

    public void setDEnd(long dEnd) {
        this.dEnd = dEnd;
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

    public PaymentWantBean getPaymentWantBean() {
        return paymentWantBean;
    }

    public void setPaymentWantBean(PaymentWantBean paymentWantBean) {
        this.paymentWantBean = paymentWantBean;
    }

}
