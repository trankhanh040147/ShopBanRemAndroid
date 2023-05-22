package com.shopeeapp.entity;

public class ProductImage {
    private Integer id;
    private Integer productId;
    private String image;
    private String status;

    public ProductImage(Integer id, Integer productId, String image, String status) {
        this.id = id;
        this.productId = productId;
        this.image = image;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
