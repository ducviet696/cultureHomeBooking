package com.swp.culturehomestay.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HomeStay {

    @SerializedName("homestayId")
    @Expose
    private String homestayId;
    @SerializedName("hostId")
    @Expose
    private String hostId;
    @SerializedName("hostEmail")
    @Expose
    private String hostEmail;
    @SerializedName("roomType")
    @Expose
    private String roomType;
    @SerializedName("standartGuest")
    @Expose
    private Integer standartGuest;
    @SerializedName("maximunGuest")
    @Expose
    private Integer maximunGuest;
    @SerializedName("addressId")
    @Expose
    private String addressId;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("numberRoom")
    @Expose
    private Integer numberRoom;
    @SerializedName("bathRoom")
    @Expose
    private Integer bathRoom;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("priceNightly")
    @Expose
    private Integer priceNightly;
    @SerializedName("priceWeekend")
    @Expose
    private Integer priceWeekend;
    @SerializedName("priceLongTerm")
    @Expose
    private Integer priceLongTerm;
    @SerializedName("cancelPolicy")
    @Expose
    private String cancelPolicy;
    @SerializedName("bookingMethod")
    @Expose
    private String bookingMethod;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("imageProfileUrl")
    @Expose
    private String imageProfileUrl;
    @SerializedName("rooms")
    @Expose
    private List<Object> rooms = null;
    @SerializedName("amenities")
    @Expose
    private List<Amenity> amenities = null;
    @SerializedName("homestayImages")
    @Expose
    private List<HomestayImage> homestayImages = null;
    @SerializedName("homestayMultis")
    @Expose
    private List<HomestayMulti> homestayMultis = null;
    @SerializedName("priceSpecials")
    @Expose
    private List<PriceSpecial> priceSpecials = null;
    @SerializedName("homestay_Cultures")
    @Expose
    private List<Object> homestayCultures = null;
    @SerializedName("feedBack")
    @Expose
    private List<Object> feedBack = null;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("bedType")
    @Expose
    private String bedType;

    public String getHomestayId() {
        return homestayId;
    }

    public void setHomestayId(String homestayId) {
        this.homestayId = homestayId;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getHostEmail() {
        return hostEmail;
    }

    public void setHostEmail(String hostEmail) {
        this.hostEmail = hostEmail;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public Integer getStandartGuest() {
        return standartGuest;
    }

    public void setStandartGuest(Integer standartGuest) {
        this.standartGuest = standartGuest;
    }

    public Integer getMaximunGuest() {
        return maximunGuest;
    }

    public void setMaximunGuest(Integer maximunGuest) {
        this.maximunGuest = maximunGuest;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getNumberRoom() {
        return numberRoom;
    }

    public void setNumberRoom(Integer numberRoom) {
        this.numberRoom = numberRoom;
    }
    public Integer getBathRoom() {
        return bathRoom;
    }

    public void setBathRoom(Integer bathRoom) {
        this.bathRoom = bathRoom;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Integer getPriceNightly() {
        return priceNightly;
    }

    public void setPriceNightly(Integer priceNightly) {
        this.priceNightly = priceNightly;
    }

    public Integer getPriceWeekend() {
        return priceWeekend;
    }

    public void setPriceWeekend(Integer priceWeekend) {
        this.priceWeekend = priceWeekend;
    }

    public Integer getPriceLongTerm() {
        return priceLongTerm;
    }

    public void setPriceLongTerm(Integer priceLongTerm) {
        this.priceLongTerm = priceLongTerm;
    }

    public String getCancelPolicy() {
        return cancelPolicy;
    }

    public void setCancelPolicy(String cancelPolicy) {
        this.cancelPolicy = cancelPolicy;
    }

    public String getBookingMethod() {
        return bookingMethod;
    }

    public void setBookingMethod(String bookingMethod) {
        this.bookingMethod = bookingMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageProfileUrl() {
        return imageProfileUrl;
    }

    public void setImageProfileUrl(String imageProfileUrl) {
        this.imageProfileUrl = imageProfileUrl;
    }

    public List<Object> getRooms() {
        return rooms;
    }

    public void setRooms(List<Object> rooms) {
        this.rooms = rooms;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<HomestayImage> getHomestayImages() {
        return homestayImages;
    }

    public void setHomestayImages(List<HomestayImage> homestayImages) {
        this.homestayImages = homestayImages;
    }

    public List<HomestayMulti> getHomestayMultis() {
        return homestayMultis;
    }

    public void setHomestayMultis(List<HomestayMulti> homestayMultis) {
        this.homestayMultis = homestayMultis;
    }

    public List<PriceSpecial> getPriceSpecials() {
        return priceSpecials;
    }

    public void setPriceSpecials(List<PriceSpecial> priceSpecials) {
        this.priceSpecials = priceSpecials;
    }

    public List<Object> getHomestayCultures() {
        return homestayCultures;
    }

    public void setHomestayCultures(List<Object> homestayCultures) {
        this.homestayCultures = homestayCultures;
    }

    public List<Object> getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(List<Object> feedBack) {
        this.feedBack = feedBack;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

}