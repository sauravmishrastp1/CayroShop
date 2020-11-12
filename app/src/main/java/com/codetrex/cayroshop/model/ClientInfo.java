package com.codetrex.cayroshop.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientInfo {

    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("AccessCode")
    @Expose
    private String accessCode;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

}
