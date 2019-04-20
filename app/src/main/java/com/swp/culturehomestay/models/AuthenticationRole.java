
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationRole {

    @SerializedName("authenticationId")
    @Expose
    private String authenticationId;

    public AuthenticationRole() {
    }

    public AuthenticationRole(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

}
