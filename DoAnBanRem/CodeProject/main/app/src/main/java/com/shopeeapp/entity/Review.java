package com.shopeeapp.entity;

import java.time.LocalDateTime;

public class Review {
    private Integer id;
    private Integer userId;
    private Integer productId;
    private String content;
    private String time;
    private String status;

    public Review(Integer id, Integer userId, Integer productId, String content, String time, String status) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.content = content;
        this.time = time;
        this.status = status;
    }

    public Review(Integer userId, Integer productId, String content) {
        this(-1, userId, productId, content, LocalDateTime.now().toString(), null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
