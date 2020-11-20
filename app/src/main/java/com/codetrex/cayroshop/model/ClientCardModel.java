package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ClientCardModel {
    @SerializedName("CardDetails")
    @Expose
    private List<ClientCard> cardDetails = null;

    public List<ClientCard> getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(List<ClientCard> cardDetails) {
        this.cardDetails = cardDetails;
    }
}
