package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientShippingAddress {

    @SerializedName("ClientId")
    @Expose
    private Integer clientId;
    @SerializedName("ShippingAddressId")
    @Expose
    private Integer shippingAddressId;
    @SerializedName("AddressTypeId")
    @Expose
    private Integer addressTypeId;
    @SerializedName("Firstname")
    @Expose
    private String firstname;
    @SerializedName("Middlename")
    @Expose
    private String middlename;
    @SerializedName("Lastname")
    @Expose
    private String lastname;
    @SerializedName("AddressLine1")
    @Expose
    private String addressLine1;
    @SerializedName("AddressLine2")
    @Expose
    private String addressLine2;
    @SerializedName("CityId")
    @Expose
    private Integer cityId;
    @SerializedName("StatId")
    @Expose
    private Integer statId;
    @SerializedName("Zip")
    @Expose
    private String zip;
    @SerializedName("CountrId")
    @Expose
    private Integer countrId;
    @SerializedName("MobilNumber")
    @Expose
    private String mobilNumber;
    @SerializedName("AlternateMobileNumber")
    @Expose
    private String alternateMobileNumber;
    @SerializedName("AddressLocationTypeId")
    @Expose
    private Integer addressLocationTypeId;
    @SerializedName("UpdatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("UpdatedbyUserId")
    @Expose
    private Integer updatedbyUserId;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("IsInvalidShippingAddress")
    @Expose
    private Boolean isInvalidShippingAddress;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getShippingAddressId() {
        return shippingAddressId;
    }

    public void setShippingAddressId(Integer shippingAddressId) {
        this.shippingAddressId = shippingAddressId;
    }

    public Integer getAddressTypeId() {
        return addressTypeId;
    }

    public void setAddressTypeId(Integer addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getStatId() {
        return statId;
    }

    public void setStatId(Integer statId) {
        this.statId = statId;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public Integer getCountrId() {
        return countrId;
    }

    public void setCountrId(Integer countrId) {
        this.countrId = countrId;
    }

    public String getMobilNumber() {
        return mobilNumber;
    }

    public void setMobilNumber(String mobilNumber) {
        this.mobilNumber = mobilNumber;
    }

    public String getAlternateMobileNumber() {
        return alternateMobileNumber;
    }

    public void setAlternateMobileNumber(String alternateMobileNumber) {
        this.alternateMobileNumber = alternateMobileNumber;
    }

    public Integer getAddressLocationTypeId() {
        return addressLocationTypeId;
    }

    public void setAddressLocationTypeId(Integer addressLocationTypeId) {
        this.addressLocationTypeId = addressLocationTypeId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Integer getUpdatedbyUserId() {
        return updatedbyUserId;
    }

    public void setUpdatedbyUserId(Integer updatedbyUserId) {
        this.updatedbyUserId = updatedbyUserId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsInvalidShippingAddress() {
        return isInvalidShippingAddress;
    }

    public void setIsInvalidShippingAddress(Boolean isInvalidShippingAddress) {
        this.isInvalidShippingAddress = isInvalidShippingAddress;
    }
}
