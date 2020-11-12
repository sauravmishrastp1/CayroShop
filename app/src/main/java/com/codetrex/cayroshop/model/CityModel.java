package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CityModel {

    private Integer countryId;

    private Integer stateId;

    private Integer cityId;

    private String cityName;

    private Boolean isHighRisk;

    private String updatedDate;

    private Integer updatedByUserId;

    private Boolean isActive;

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Boolean getHighRisk() {
        return isHighRisk;
    }

    public void setHighRisk(Boolean highRisk) {
        isHighRisk = highRisk;
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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CityModel(Integer countryId, Integer stateId, Integer cityId, String cityName, Boolean isHighRisk, String updatedDate, Integer updatedByUserId, Boolean isActive) {
        this.countryId = countryId;
        this.stateId = stateId;
        this.cityId = cityId;
        this.cityName = cityName;
        this.isHighRisk = isHighRisk;
        this.updatedDate = updatedDate;
        this.updatedByUserId = updatedByUserId;
        this.isActive = isActive;
    }
}
