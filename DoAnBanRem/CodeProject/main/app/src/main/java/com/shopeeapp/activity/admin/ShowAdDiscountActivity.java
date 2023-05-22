package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;
import com.shopeeapp.adapter.DiscountAdapter;
import com.shopeeapp.dbhelper.DiscountDbHelper;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.utilities.CreateData;

import java.util.ArrayList;

public class ShowAdDiscountActivity extends AppCompatActivity {

    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_discount);

        DiscountDbHelper discountDbHelper = new DiscountDbHelper(this);
        ArrayList<Discount> discounts = discountDbHelper.getAllDiscounts();
        DiscountAdapter adapter = new DiscountAdapter(this, R.layout.admin_discount_listview, discounts);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdDiscountActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDDiscount);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdDiscountActivity.this, AdminDiscountActivity.class);
                startActivity(intent);
            }
        });
    }
}