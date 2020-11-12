package com.codetrex.cayroshop.model;

public class ProductItemModel {
    private int img;
    private String productname;

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public ProductItemModel(int img, String productname) {
        this.img = img;
        this.productname = productname;
    }
}
