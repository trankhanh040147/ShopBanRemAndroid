package com.shopeeapp.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.entity.Store;

public class AdminStoreActivity extends AppCompatActivity {

    TextInputEditText txtAdStoreId,
            txtAdNameStore,
            txtAdPhoneStore,
            txtAdEmailStore,
            txtAdAddressStore,
            txtAdImgStore,
            txtAdStatusStore;
    Button  btnAdd,
            btnDelete,
            btnUpdate;
    ImageView btnBack;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_store);

        addControl();
        handleEvent();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminStoreActivity.this, ShowAdStoreActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        txtAdStoreId = findViewById(R.id.txtAdIdStore1);
        txtAdAddressStore = findViewById(R.id.txtAdAddressStore);
        txtAdEmailStore = findViewById(R.id.txtAdEmailStore);
        txtAdNameStore = findViewById(R.id.txtAdNameStore);
        txtAdPhoneStore = findViewById(R.id.txtAdPhoneStore);
        txtAdStatusStore = findViewById(R.id.txtAdStatusStore);
        txtAdImgStore = findViewById(R.id.txtAdImgStore);

        btnAdd = findViewById(R.id.btnAdAdd);
        btnDelete = findViewById(R.id.btnBackToDashboard);
        btnUpdate = findViewById(R.id.btnAdUpdate);
    }

    private void handleEvent(){
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });
    }

    private boolean validate(String id, String name, String phone, String email) {
        if (id.equals("")) {
            txtAdStoreId.setError("Không được bỏ trống");
            return false;
        }
        if (name.equals("")) {
            txtAdNameStore.setError("Không được bỏ trống");
            return false;
        }
        if (phone.equals("")) {
            txtAdPhoneStore.setError("Không được bỏ trống");
            return false;
        }
        if (email.equals("")) {
            txtAdEmailStore.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }


    private void insertData(){
        String id = txtAdStoreId.getText().toString();
        String phone = txtAdPhoneStore.getText().toString();
        String address = txtAdAddressStore.getText().toString();
        String name = txtAdNameStore.getText().toString();
        String email = txtAdEmailStore.getText().toString();
        String status = txtAdStatusStore.getText().toString();
        String image = txtAdImgStore.getText().toString();

        if (!validate(id, name, phone, email)) {
            Toast.makeText(AdminStoreActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Store store = new Store(Integer.parseInt(id), name, phone, email, image, address, status);
            StoreDbHelper storeDbHelper = new StoreDbHelper(this);
            long rowId = storeDbHelper.insert(store);
            if (rowId > 0) {
                Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        String id = txtAdStoreId.getText().toString();
        String phone = txtAdPhoneStore.getText().toString();
        String address = txtAdAddressStore.getText().toString();
        String name = txtAdNameStore.getText().toString();
        String email = txtAdEmailStore.getText().toString();
        String status = txtAdStatusStore.getText().toString();
        String image = txtAdImgStore.getText().toString();

        if (!validate(id, name, phone, email)) {
            Toast.makeText(AdminStoreActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        Store store = new Store(Integer.parseInt(id), name, phone, email, image, address, status);
        StoreDbHelper storeDbHelper = new StoreDbHelper(this);

        if (storeDbHelper.getStoreById(Integer.parseInt(id)) == null){
            Toast.makeText(AdminStoreActivity.this, "Không tìm thấy store ", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = storeDbHelper.update(store);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();

    }

    private void delete() {
        String id = txtAdStoreId.getText().toString();


        if (id.equals("")) {
            Toast.makeText(AdminStoreActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }


        StoreDbHelper storeDbHelper = new StoreDbHelper(this);
        Store store = storeDbHelper.getStoreById(Integer.parseInt(id));
        if (store == null){
            Toast.makeText(AdminStoreActivity.this, "Không tìm thấy store ", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = storeDbHelper.delete(store);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }

}