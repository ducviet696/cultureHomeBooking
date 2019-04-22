
package com.swp.culturehomestay.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthorizationMethodBean {

    @SerializedName("method")
    @Expose
    private String method;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("logFor")
    @Expose
    private String logFor;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("editedDate")
    @Expose
    private long editedDate;
    @SerializedName("authenticationRoles")
    @Expose
    private List<AuthenticationRole> authenticationRoles = null;

    public AuthorizationMethodBean() {
    }

    public AuthorizationMethodBean(String method, String username, String password, String logFor, long createdDate, long editedDate, List<AuthenticationRole> authenticationRoles) {
        this.method = method;
        this.username = username;
        this.password = password;
        this.logFor = logFor;
        this.createdDate = createdDate;
        this.editedDate = editedDate;
        this.authenticationRoles = authenticationRoles;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogFor() {
        return logFor;
    }

    public void setLogFor(String logFor) {
        this.logFor = logFor;
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

    public List<AuthenticationRole> getAuthenticationRoles() {
        return authenticationRoles;
    }

    public void setAuthenticationRoles(List<AuthenticationRole> authenticationRoles) {
        this.authenticationRoles = authenticationRoles;
    }

}
