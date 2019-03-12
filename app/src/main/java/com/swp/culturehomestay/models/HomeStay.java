package com.swp.culturehomestay.models;

public class HomeStay {
    private int imgUrl;
    private String type;
    private int bedRoomNumber;
    private String nameHome;
    private double price;
    private String location;

    public HomeStay(int imgUrl, String type, int bedRoomNumber, String nameHome, double price, String location) {
        this.imgUrl = imgUrl;
        this.type = type;
        this.bedRoomNumber = bedRoomNumber;
        this.nameHome = nameHome;
        this.price = price;
        this.location = location;
    }

    public int getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(int imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBedRoomNumber() {
        return bedRoomNumber;
    }

    public void setBedRoomNumber(int bedRoomNumber) {
        this.bedRoomNumber = bedRoomNumber;
    }

    public String getNameHome() {
        return nameHome;
    }

    public void setNameHome(String nameHome) {
        this.nameHome = nameHome;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
