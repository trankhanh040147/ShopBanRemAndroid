package com.shopeeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.shopeeapp.activity.admin.DashBoard;
import com.shopeeapp.entity.Account;
import com.shopeeapp.fragment.CartFragment;
import com.shopeeapp.fragment.DiscountFragment;
import com.shopeeapp.fragment.HomeFragment;
import com.shopeeapp.fragment.MenuFragment;
import com.shopeeapp.fragment.NotificationFragment;
import com.shopeeapp.utilities.AccountSessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static Resources mainResources;
    public static final String NOTIFICATION_ACTION = "Notification Action";
    private JsonPlaceHolderAPI jsonPlaceHolderAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://127.0.0.1:8080/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolderAPI = retrofit.create(JsonPlaceHolderAPI.class);

        register();
    }

    private void register(){
        Account account = new Account(10,"trankhanhx1","trankhanhx1@gmail.com","khanh123",1,"1");
        Call<Account> call = jsonPlaceHolderAPI.register(account);

        call.enqueue(new Callback<Account>() {
            @Override
            public void onResponse(Call<Account> call, Response<Account> response) {
                if(!response.isSuccessful()){
                    System.out.println("Code :" + response.code());
                    return;
                }

                Account accountResponse = response.body();

                String content = "";
                content += "username: " + account.getUsername() + "\n";
                content += "email: " + account.getEmail() + "\n";
                content += "password: " + account.getPassword() + "\n\n";

                System.out.println("Account: \n " + content);
            }

            @Override
            public void onFailure(Call<Account> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

}