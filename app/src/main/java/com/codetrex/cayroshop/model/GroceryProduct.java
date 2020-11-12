package com.codetrex.cayroshop.model;

public class GroceryProduct {
    private int productimg;
    private String product_name;

    public int getProductimg() {
        return productimg;
    }

    public void setProductimg(int productimg) {
        this.productimg = productimg;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public GroceryProduct(int productimg, String product_name) {
        this.productimg = productimg;
        this.product_name = product_name;
    }
}
