package com.shopeeapp.entity;

import android.graphics.Bitmap;
public class Store {
    private Integer id;
    private String name;
    private String phone;
    private String email;
    private String image;
    private String address;
    private String status;

    public Store(Integer id, String name, String phone, String email, String image, String address, String status) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.image = image;
        this.address = address;
        this.status = status;
    }

    public Store(Integer id, String name, String phone, String email) {
        this(id, name, phone, email, null, null, null);
    }

    public Store(String name, String phone, String email) {
        this(-1, name, phone, email, null, null, null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email.contains("@"))
            this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
