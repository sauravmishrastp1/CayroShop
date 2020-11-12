package com.codetrex.cayroshop.model;

public class OrderModel {
    private int img;
    private String id;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public OrderModel(int img, String id) {
        this.img = img;
        this.id = id;
    }
}
