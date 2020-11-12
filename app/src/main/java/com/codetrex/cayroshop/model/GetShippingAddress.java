package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetShippingAddress {
    @SerializedName("ClientShippingAddress")
    @Expose
    private List<ClientShippingAddress> clientShippingAddress = null;

    public List<ClientShippingAddress> getClientShippingAddress() {
        return clientShippingAddress;
    }

    public void setClientShippingAddress(List<ClientShippingAddress> clientShippingAddress) {
        this.clientShippingAddress = clientShippingAddress;
    }

}
