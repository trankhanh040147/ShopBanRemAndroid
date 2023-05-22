package com.shopeeapp.entity;

import android.graphics.Bitmap;

import java.util.ArrayList;

import com.shopeeapp.utilities.ImageConverter;

public class Product {
    private Integer id;
    private Integer storeId;
    private Integer type;
    private String name;
    private Double price;
    private String image;
    private int defaultImage;
    private String detail;
    private Float star;
    private String status;
    private Discount discount;
    private ArrayList<String> productImages;

    public Product(Integer id, Integer storeId, Integer type, String name, Double price,
                   String image, String detail, Float star, String status) {
        this.setId(id);
        this.setStoreId(storeId);
        this.setType(type);
        this.setName(name);
        this.setPrice(price);
        this.setImage(image);
        this.setDetail(detail);
        this.setStar(star);
        this.setStatus(status);
    }

    public Product(Integer id, Integer storeId, Integer type, String name, Double price,
                   int defaultImage, String detail, Float star, String status) {
        this.setId(id);
        this.setStoreId(storeId);
        this.setType(type);
        this.setName(name);
        this.setPrice(price);
        this.setImage(image);
        this.setDetail(detail);
        this.setStar(star);
        this.setStatus(status);
        this.defaultImage = defaultImage;
    }

    public Product(Integer id, Integer storeId, Integer type, String name, Double price) {
        this(id, storeId, type, name, price, null, null, 0.0f, null);
    }

    public Product(Integer storeId, Integer type, String name, int defaultImage) {
        this(-1, storeId, type, name, 0.0, defaultImage, null, 0.0f, null);
    }

    public Product(Integer storeId, Integer type, String name, String image) {
        this(-1, storeId, type, name, 0.0, image, null, 0.0f, null);
    }

    public Product(Integer storeId, Integer type, String name, Double price) {
        this(-1, storeId, type, name, price, null, null, 0.0f, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        if (type > 0 && type < 8)
            this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        if (price >= 0)
            this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setImage(int defaultImage) {
//        this.image = ImageConverter.resource2Bitmap(defaultImage);
        this.defaultImage = defaultImage;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Float getStar() {
        return star;
    }

    public void setStar(Float star) {
        if (star >= 0)
            this.star = star;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }

    public ArrayList<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(ArrayList<String> productImages) {
        this.productImages = productImages;
    }

    public void addProductImage(String image) {
        if (this.getProductImages() == null)
            this.productImages = new ArrayList<>();
        this.getProductImages().add(image);
    }

    public void addProductImage(ArrayList<String> images) {
        if (this.getProductImages() == null)
            this.productImages = new ArrayList<>();
        this.getProductImages().addAll(images);
    }
}
