package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;
import com.shopeeapp.adapter.AdminProductAdapter;
import com.shopeeapp.adapter.ProductAdapter;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.entity.Product;
import com.shopeeapp.utilities.CreateData;

import java.util.ArrayList;

public class ShowAdProductActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    Button crud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_product);

        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        ArrayList<Product> products = productDbHelper.getAllProducts();
        AdminProductAdapter adapter = new AdminProductAdapter(this, R.layout.admin_product_listview, products);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdProductActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDProduct);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdProductActivity.this, AdminProductActivity.class);
                startActivity(intent);
            }
        });
    }
}