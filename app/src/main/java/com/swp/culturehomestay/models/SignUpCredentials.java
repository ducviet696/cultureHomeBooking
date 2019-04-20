
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignUpCredentials {

    @SerializedName("userWant")
    @Expose
    private UserWant userWant;

    public SignUpCredentials() {
    }

    public SignUpCredentials(UserWant userWant) {
        this.userWant = userWant;
    }

    public UserWant getUserWant() {
        return userWant;
    }

    public void setUserWant(UserWant userWant) {
        this.userWant = userWant;
    }

}
