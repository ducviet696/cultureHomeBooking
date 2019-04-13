
package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticatioModel {

    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("authenticationRoles")
    @Expose
    private List<AuthenticationRoleModel> authenticationRoles = null;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<AuthenticationRoleModel> getAuthenticationRoles() {
        return authenticationRoles;
    }

    public void setAuthenticationRoles(List<AuthenticationRoleModel> authenticationRoles) {
        this.authenticationRoles = authenticationRoles;
    }

}
