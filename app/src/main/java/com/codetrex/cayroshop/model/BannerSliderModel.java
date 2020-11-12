package com.codetrex.cayroshop.model;

public class BannerSliderModel {

    private int image;
    private String bannerid;

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getBannerid() {
        return bannerid;
    }

    public void setBannerid(String bannerid) {
        this.bannerid = bannerid;
    }

    public BannerSliderModel(int image, String bannerid) {
        this.image = image;
        this.bannerid = bannerid;
    }
}
