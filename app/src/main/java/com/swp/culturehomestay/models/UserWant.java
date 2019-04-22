
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserWant {

    @SerializedName("authorizationMethodBean")
    @Expose
    private AuthorizationMethodBean authorizationMethodBean;

    public UserWant() {
    }

    public UserWant(AuthorizationMethodBean authorizationMethodBean) {
        this.authorizationMethodBean = authorizationMethodBean;
    }

    public AuthorizationMethodBean getAuthorizationMethodBean() {
        return authorizationMethodBean;
    }

    public void setAuthorizationMethodBean(AuthorizationMethodBean authorizationMethodBean) {
        this.authorizationMethodBean = authorizationMethodBean;
    }

}
