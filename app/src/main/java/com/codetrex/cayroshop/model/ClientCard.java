package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientCard {
    @SerializedName("CardDetailsId")
    @Expose
    private Integer cardDetailsId;
    @SerializedName("CreditCardTypeId")
    @Expose
    private String creditCardTypeId;
    @SerializedName("CreditCardType")
    @Expose
    private String creditCardType;
    @SerializedName("CreditCardNo")
    @Expose
    private String creditCardNo;
    @SerializedName("CreditCardNoShort")
    @Expose
    private String creditCardNoShort;
    @SerializedName("CreditCardExpMonth")
    @Expose
    private String creditCardExpMonth;
    @SerializedName("CreditCardExpYear")
    @Expose
    private String creditCardExpYear;
    @SerializedName("CreditCardTypeIdUE")
    @Expose
    private Integer creditCardTypeIdUE;
    @SerializedName("CreditCardSecurityNo")
    @Expose
    private String creditCardSecurityNo;
    @SerializedName("CreditCardFirstName")
    @Expose
    private String creditCardFirstName;
    @SerializedName("CreditCardMiddleName")
    @Expose
    private String creditCardMiddleName;
    @SerializedName("CreditCardLastName")
    @Expose
    private String creditCardLastName;
    @SerializedName("PhoneOnCreditCardFile")
    @Expose
    private String phoneOnCreditCardFile;
    @SerializedName("IssuerBankName")
    @Expose
    private String issuerBankName;
    @SerializedName("IssuerBankPhone")
    @Expose
    private String issuerBankPhone;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedByUserID")
    @Expose
    private Integer updatedByUserID;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;

    public Integer getCardDetailsId() {
        return cardDetailsId;
    }

    public void setCardDetailsId(Integer cardDetailsId) {
        this.cardDetailsId = cardDetailsId;
    }

    public String getCreditCardTypeId() {
        return creditCardTypeId;
    }

    public void setCreditCardTypeId(String creditCardTypeId) {
        this.creditCardTypeId = creditCardTypeId;
    }

    public String getCreditCardType() {
        return creditCardType;
    }

    public void setCreditCardType(String creditCardType) {
        this.creditCardType = creditCardType;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo;
    }

    public String getCreditCardNoShort() {
        return creditCardNoShort;
    }

    public void setCreditCardNoShort(String creditCardNoShort) {
        this.creditCardNoShort = creditCardNoShort;
    }

    public String getCreditCardExpMonth() {
        return creditCardExpMonth;
    }

    public void setCreditCardExpMonth(String creditCardExpMonth) {
        this.creditCardExpMonth = creditCardExpMonth;
    }

    public String getCreditCardExpYear() {
        return creditCardExpYear;
    }

    public void setCreditCardExpYear(String creditCardExpYear) {
        this.creditCardExpYear = creditCardExpYear;
    }

    public Integer getCreditCardTypeIdUE() {
        return creditCardTypeIdUE;
    }

    public void setCreditCardTypeIdUE(Integer creditCardTypeIdUE) {
        this.creditCardTypeIdUE = creditCardTypeIdUE;
    }

    public String getCreditCardSecurityNo() {
        return creditCardSecurityNo;
    }

    public void setCreditCardSecurityNo(String creditCardSecurityNo) {
        this.creditCardSecurityNo = creditCardSecurityNo;
    }

    public String getCreditCardFirstName() {
        return creditCardFirstName;
    }

    public void setCreditCardFirstName(String creditCardFirstName) {
        this.creditCardFirstName = creditCardFirstName;
    }

    public String getCreditCardMiddleName() {
        return creditCardMiddleName;
    }

    public void setCreditCardMiddleName(String creditCardMiddleName) {
        this.creditCardMiddleName = creditCardMiddleName;
    }

    public String getCreditCardLastName() {
        return creditCardLastName;
    }

    public void setCreditCardLastName(String creditCardLastName) {
        this.creditCardLastName = creditCardLastName;
    }

    public String getPhoneOnCreditCardFile() {
        return phoneOnCreditCardFile;
    }

    public void setPhoneOnCreditCardFile(String phoneOnCreditCardFile) {
        this.phoneOnCreditCardFile = phoneOnCreditCardFile;
    }

    public String getIssuerBankName() {
        return issuerBankName;
    }

    public void setIssuerBankName(String issuerBankName) {
        this.issuerBankName = issuerBankName;
    }

    public String getIssuerBankPhone() {
        return issuerBankPhone;
    }

    public void setIssuerBankPhone(String issuerBankPhone) {
        this.issuerBankPhone = issuerBankPhone;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedByUserID() {
        return updatedByUserID;
    }

    public void setUpdatedByUserID(Integer updatedByUserID) {
        this.updatedByUserID = updatedByUserID;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
