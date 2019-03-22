package com.swp.culturehomestay.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class OrderBookingModel {

    @SerializedName("orderNo")
    private String orderNo;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("homestayName")
    private String homestayName;
    @SerializedName("totalpayment")
    private Double totalPayment;
    @SerializedName("roomName")
    private String roomName;
    @SerializedName("dateCheckIn")
    private Date dateCheckIn;
    @SerializedName("dateCheckOut")
    private Date dateCheckOut;

    public OrderBookingModel() {
    }

    public OrderBookingModel(String orderNo, String orderDate, String homestayName, Double totalPayment, String roomName, Date dateCheckIn, Date dateCheckOut) {
        this.orderNo = orderNo;
        this.orderDate = orderDate;
        this.homestayName = homestayName;
        this.totalPayment = totalPayment;
        this.roomName = roomName;
        this.dateCheckIn = dateCheckIn;
        this.dateCheckOut = dateCheckOut;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getHomestayName() {
        return homestayName;
    }

    public void setHomestayName(String homestayName) {
        this.homestayName = homestayName;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = dateCheckIn;
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = dateCheckOut;
    }

    public ArrayList<OrderBookingModel> createOrderRandom(int size){
        ArrayList<OrderBookingModel> orders = new ArrayList<>();
        for (int i = 0; i<size;i++){
            OrderBookingModel order = new OrderBookingModel();
            Random rd = new Random();
            order.setOrderNo(randomString(5));
            order.setHomestayName(randomString(6));
            order.setRoomName(randomString(3));
            order.setTotalPayment(rd.nextDouble());
            order.setDateCheckIn(new Date());
            order.setDateCheckOut(new Date());
            orders.add(order);
        }
        return orders;
    }
    public String randomString(int targetStringLength ){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }
}
