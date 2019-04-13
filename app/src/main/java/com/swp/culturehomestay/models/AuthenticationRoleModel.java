
package com.swp.culturehomestay.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AuthenticationRoleModel {

    @SerializedName("authenticationId")
    @Expose
    private String authenticationId;
    @SerializedName("role")
    @Expose
    private String role;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdDate")
    @Expose
    private long createdDate;
    @SerializedName("editeddate")
    @Expose
    private long editeddate;

    public String getAuthenticationId() {
        return authenticationId;
    }

    public void setAuthenticationId(String authenticationId) {
        this.authenticationId = authenticationId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public long getEditeddate() {
        return editeddate;
    }

    public void setEditeddate(long editeddate) {
        this.editeddate = editeddate;
    }

}
