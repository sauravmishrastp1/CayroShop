package com.codetrex.cayroshop.dao;

public class CartModel {

    private String productColorSizeId;
    private String productId ;
    private String categoryId ;
    private String productName ;
    private String brandName ;
    private String productDetailName ;
    private String sizeId ;
    private String sizeName ;
    private String productAdImageUrl ;
    private String salePrice ;
    private String retailPrice ;
    private String discountAmt ;
    private String bestSeller ;

    public String getProductColorSizeId() {
        return productColorSizeId;
    }

    public void setProductColorSizeId(String productColorSizeId) {
        this.productColorSizeId = productColorSizeId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getProductDetailName() {
        return productDetailName;
    }

    public void setProductDetailName(String productDetailName) {
        this.productDetailName = productDetailName;
    }

    public String getSizeId() {
        return sizeId;
    }

    public void setSizeId(String sizeId) {
        this.sizeId = sizeId;
    }

    public String getSizeName() {
        return sizeName;
    }

    public void setSizeName(String sizeName) {
        this.sizeName = sizeName;
    }

    public String getProductAdImageUrl() {
        return productAdImageUrl;
    }

    public void setProductAdImageUrl(String productAdImageUrl) {
        this.productAdImageUrl = productAdImageUrl;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public String getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(String retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(String discountAmt) {
        this.discountAmt = discountAmt;
    }

    public String getBestSeller() {
        return bestSeller;
    }

    public void setBestSeller(String bestSeller) {
        this.bestSeller = bestSeller;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    private String active ;
    private String quantity ;

}
