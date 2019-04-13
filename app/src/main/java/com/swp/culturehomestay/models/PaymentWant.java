
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentWant {

    @SerializedName("transactionId")
    @Expose
    private String transactionId;
    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("sucessUrl")
    @Expose
    private String sucessUrl;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("urlForpay")
    @Expose
    private String urlForpay;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("interact")
    @Expose
    private String interact;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSucessUrl() {
        return sucessUrl;
    }

    public void setSucessUrl(String sucessUrl) {
        this.sucessUrl = sucessUrl;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getUrlForpay() {
        return urlForpay;
    }

    public void setUrlForpay(String urlForpay) {
        this.urlForpay = urlForpay;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getInteract() {
        return interact;
    }

    public void setInteract(String interact) {
        this.interact = interact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public void setEditedDate(long editedDate) {
        this.editedDate = editedDate;
    }

}