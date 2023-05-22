package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;
import com.shopeeapp.adapter.ProductImageAdapter;
import com.shopeeapp.dbhelper.ProductImageDbHelper;
import com.shopeeapp.entity.ProductImage;

import java.util.ArrayList;

public class AdminShowProImgActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_show_pro_img);

        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this);
        ArrayList<ProductImage> productImages = productImageDbHelper.getAllProductImages();
        ProductImageAdapter adapter = new ProductImageAdapter(this, R.layout.admin_productimg_listview, productImages);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminShowProImgActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDProductImg);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminShowProImgActivity.this, ProductImageActivity.class);
                startActivity(intent);
            }
        });
    }


}