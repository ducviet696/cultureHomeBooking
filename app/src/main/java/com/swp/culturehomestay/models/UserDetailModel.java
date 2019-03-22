package com.swp.culturehomestay.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class UserDetailModel {
    @SerializedName("username")
    private String username;
    @SerializedName("firstName")
    private String firstName;
    @SerializedName("lastName")
    private String lastName;
    @SerializedName("email")
    private String email;
    @SerializedName("dob")
    private Date dob;
    @SerializedName("gender")
    private boolean gender;
    @SerializedName("inforDescription")
    private String infoDescription;
    @SerializedName("phoneNumber")
    private String phoneNumber;
    @SerializedName("profileImage")
    private String profileImage;
    @SerializedName("address")
    private String address;

    public UserDetailModel() {
    }

    public UserDetailModel(String username, String firstName, String lastName, String email, Date dob, boolean gender, String infoDescription, String phoneNumber, String profileImage, String address) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.infoDescription = infoDescription;
        this.phoneNumber = phoneNumber;
        this.profileImage = profileImage;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getInfoDescription() {
        return infoDescription;
    }

    public void setInfoDescription(String infoDescription) {
        this.infoDescription = infoDescription;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
