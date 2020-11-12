package com.codetrex.cayroshop.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterUser {

    @SerializedName("response")
    @Expose
    private Response response;
    @SerializedName("ClientInfo")
    @Expose
    private ClientInfo clientInfo;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ClientInfo getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(ClientInfo clientInfo) {
        this.clientInfo = clientInfo;
    }

}
