package com.shopeeapp;

import com.shopeeapp.entity.Account;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface JsonPlaceHolderAPI {
    @POST("/register")
    Call<Account> register(@Body Account account);
}
