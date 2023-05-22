package com.shopeeapp.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.shopeeapp.R;
import com.shopeeapp.adapter.AdminProductTypeAdapter;
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.entity.ProductType;

import java.util.ArrayList;

public class ShowAdProTypeActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_pro_type);


        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);

        //Tạo table trước
//        SQLiteDatabase db =productTypeDbHelper.getWritableDatabase();
//        productTypeDbHelper.onCreate(db);
        ArrayList<ProductType> productTypes = productTypeDbHelper.getAllProductTypes();
        AdminProductTypeAdapter adapter = new AdminProductTypeAdapter(this, R.layout.admin_producttype_listview, productTypes);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdProTypeActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDProductType);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdProTypeActivity.this, AdminProductTypeActivity.class);
                startActivity(intent);
            }
        });
    }

}