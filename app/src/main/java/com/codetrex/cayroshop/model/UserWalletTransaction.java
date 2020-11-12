package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserWalletTransaction {
    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("Cash")
    @Expose
    private Integer cash;
    @SerializedName("TransactionTypeId")
    @Expose
    private Integer transactionTypeId;
    @SerializedName("ValidFromDate")
    @Expose
    private String validFromDate;
    @SerializedName("ExpiryDate")
    @Expose
    private String expiryDate;
    @SerializedName("SourceOrderId")
    @Expose
    private Integer sourceOrderId;
    @SerializedName("DestinationOrderId")
    @Expose
    private Integer destinationOrderId;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedByUserId")
    @Expose
    private Integer updatedByUserId;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCash() {
        return cash;
    }

    public void setCash(Integer cash) {
        this.cash = cash;
    }

    public Integer getTransactionTypeId() {
        return transactionTypeId;
    }

    public void setTransactionTypeId(Integer transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public String getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(String validFromDate) {
        this.validFromDate = validFromDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getSourceOrderId() {
        return sourceOrderId;
    }

    public void setSourceOrderId(Integer sourceOrderId) {
        this.sourceOrderId = sourceOrderId;
    }

    public Integer getDestinationOrderId() {
        return destinationOrderId;
    }

    public void setDestinationOrderId(Integer destinationOrderId) {
        this.destinationOrderId = destinationOrderId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(Integer updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
