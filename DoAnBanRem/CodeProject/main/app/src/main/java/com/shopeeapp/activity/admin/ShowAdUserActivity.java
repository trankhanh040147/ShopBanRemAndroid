package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.shopeeapp.R;
import com.shopeeapp.adapter.AdminUserAdapter;
import com.shopeeapp.adapter.StoreAdapter;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;
import com.shopeeapp.entity.Store;
import com.shopeeapp.entity.User;

import java.util.ArrayList;

public class ShowAdUserActivity extends AppCompatActivity {
    ListView listView;
    ImageView back;
    Button crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ad_user);

        UserDbHelper userDbHelper = new UserDbHelper(this);
        ArrayList<User> user = userDbHelper.getAllUser();
        AdminUserAdapter adapter = new AdminUserAdapter(this, R.layout.admin_user_listview, user);
        listView = findViewById(R.id.listview);
        listView.setAdapter(adapter);



        back = findViewById(R.id.btnBackToDashboard);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowAdUserActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });

    }
}