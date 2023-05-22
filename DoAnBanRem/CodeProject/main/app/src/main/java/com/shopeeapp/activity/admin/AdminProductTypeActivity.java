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
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.entity.ProductType;

public class AdminProductTypeActivity extends AppCompatActivity {
    TextInputEditText txtAdIdProType,
            txtAdTypeName,
            txtAdTypeImg,
            txtAdTypeStatus;
    Button btnAdd,
            btnDelete,
            btnUpdate;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product_type);

        addControl();
        handleEvent();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProductTypeActivity.this, ShowAdProTypeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void addControl() {
        txtAdIdProType = findViewById(R.id.txtAdIdProType);
        txtAdTypeName = findViewById(R.id.txtAdTypeName);
        txtAdTypeImg = findViewById(R.id.txtAdTypeImg);
        txtAdTypeStatus = findViewById(R.id.txtAdTypeStatus);

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
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }

    private boolean validate(String id, String name) {
        if (id.equals("")) {
            txtAdIdProType.setError("Không được bỏ trống");
            return false;
        }
        if (name.equals("")) {
            txtAdTypeName.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void insertData() {
        String id = txtAdIdProType.getText().toString();
        String name = txtAdTypeName.getText().toString();
        String status = txtAdTypeStatus.getText().toString();
        String image = txtAdTypeImg.getText().toString();

        if (!validate(id, name)) {
            Toast.makeText(AdminProductTypeActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProductType productType = new ProductType(Integer.parseInt(id), name, image, status);
            ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);
            long rowId = productTypeDbHelper.insert(productType);
            if (rowId > 0) {
                Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        String id = txtAdIdProType.getText().toString();
        String name = txtAdTypeName.getText().toString();
        String status = txtAdTypeStatus.getText().toString();
        String image = txtAdTypeImg.getText().toString();

        if (!validate(id, name)) {
            Toast.makeText(AdminProductTypeActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductType productType = new ProductType(Integer.parseInt(id), name, image, status);
        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);

        if (productTypeDbHelper.getProductTypeById(Integer.parseInt(id)) == null) {
            Toast.makeText(AdminProductTypeActivity.this, "Không tìm thấy productType", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productTypeDbHelper.update(productType);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();

    }

    private void deleteData() {
        String id = txtAdIdProType.getText().toString();

        if (id.equals("")) {
            Toast.makeText(AdminProductTypeActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);

        ProductType productType = productTypeDbHelper.getProductTypeById(Integer.parseInt(id));

        if (productType == null) {
            Toast.makeText(AdminProductTypeActivity.this, "Không tìm thấy productType", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productTypeDbHelper.delete(productType);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();

    }

}