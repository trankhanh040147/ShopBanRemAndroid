package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ApplicationErrorReport;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;
import com.shopeeapp.adapter.StoreAdapter;
import com.shopeeapp.dbhelper.PromoDbHelper;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.entity.Promo;
import com.shopeeapp.entity.Store;
import com.shopeeapp.utilities.CreateData;

import java.util.ArrayList;

public class ShowAdStoreActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_store);

        StoreDbHelper storeDbHelper = new StoreDbHelper(this);
        ArrayList<Store> stores = storeDbHelper.getAllStores();
        StoreAdapter adapter = new StoreAdapter(this, R.layout.admin_store_listview, stores);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdStoreActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDStore);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdStoreActivity.this, AdminStoreActivity.class);
                startActivity(intent);
            }
        });
    }
}