package com.shopeeapp.entity;

public class Promo {
    private Integer id;
    private Integer productId;
    private String type;
    private String expirationDate;
    private String status;

    public Promo(Integer id, Integer productId, String type, String expirationDate, String status) {
        this.id = id;
        this.productId = productId;
        this.type = type;
        this.expirationDate = expirationDate;
        this.status = status;
    }

    public Promo(Integer productId, String type, String expirationDate) {
        this(-1, productId, type, expirationDate, null);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
