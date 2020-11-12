package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class StateModel {
    @SerializedName("CountryState")
    @Expose
    private List<GetCityStateData> countryState = null;

    public List<GetCityStateData> getCountryState() {
        return countryState;
    }

    public void setCountryState(List<GetCityStateData> countryState) {
        this.countryState = countryState;
    }

}
