package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;

public class ShowAdRoleActivity extends AppCompatActivity {
    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_role);

        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdRoleActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

        crud = findViewById(R.id.btnCRUDRole);
        crud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdRoleActivity.this, AdminRoleActivity.class);
                startActivity(intent);
            }
        });
    }
}