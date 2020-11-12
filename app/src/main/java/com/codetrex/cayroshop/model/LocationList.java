package com.codetrex.cayroshop.model;

public class LocationList {

    private String id,city,state;

    public LocationList(String id, String city) {
        this.id = id;
        this.city = city;
    }

    public LocationList(String id, String city, String state) {
        this.id = id;
        this.city = city;
        this.state = state;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return city;
    }

    public void setName(String name) {
        this.city = name;
    }
}
