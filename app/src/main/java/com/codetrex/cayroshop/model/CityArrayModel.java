package com.codetrex.cayroshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CityArrayModel {
    @SerializedName("StateCity")
    @Expose
    private List<CityModel> stateCity = null;

    public List<CityModel> getStateCity() {
        return stateCity;
    }

    public void setStateCity(List<CityModel> stateCity) {
        this.stateCity = stateCity;
    }
}
