package com.shopeeapp.activity.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.ProductImageDbHelper;
import com.shopeeapp.entity.ProductImage;

public class ProductImageActivity extends AppCompatActivity {

    TextInputEditText txtAdIdImage,
            txtAdIdProductImg,
            txtAdImgProductImg,
            txtAdStatusImg;
    Button btnAdd,
            btnDelete,
            btnUpdate;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_image);

        addControl();
        handleEvent();
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductImageActivity.this, AdminShowProImgActivity.class);
                startActivity(intent);
            }
        });

    }

    private void addControl() {
        txtAdIdImage = findViewById(R.id.txtAdIdImage);
        txtAdIdProductImg = findViewById(R.id.txtAdIdProductImg);
        txtAdImgProductImg = findViewById(R.id.txtAdImgProductImg);
        txtAdStatusImg = findViewById(R.id.txtAdStatusImg);

        btnAdd = findViewById(R.id.btnAdAdd);
        btnDelete = findViewById(R.id.btnBackToDashboard);
        btnUpdate = findViewById(R.id.btnAdUpdate);
    }

    private void handleEvent() {
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
                deleteData();
            }
        });
    }


    private boolean validate(String id, String productId) {
        if (id.equals("")) {
            txtAdIdImage.setError("Không được bỏ trống");
            return false;
        }
        if (productId.equals("")) {
            txtAdIdProductImg.setError("Không được bỏ trống");
            return false;
        }
        return true;
    }

    private void insertData() {
        String id = txtAdIdImage.getText().toString();
        String productId = txtAdIdProductImg.getText().toString();
        String status = txtAdStatusImg.getText().toString();
        String image = txtAdImgProductImg.getText().toString();

        if (!validate(id, productId)) {
            Toast.makeText(ProductImageActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ProductImage productImage = new ProductImage(Integer.parseInt(id), Integer.parseInt(productId), image, status);
            ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this);
            long rowId = productImageDbHelper.insert(productImage);
            if (rowId > 0) {
                Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteData() {
        String id = txtAdIdImage.getText().toString();

        if (id.equals("")) {
            Toast.makeText(ProductImageActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this);

        ProductImage productImage = productImageDbHelper.getProductImageById(Integer.parseInt(id));
        if (productImage == null){
            Toast.makeText(ProductImageActivity.this, "Không tìm thấy store ", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productImageDbHelper.delete(productImage);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();

    }

    private void updateData() {
        String id = txtAdIdImage.getText().toString();
        String productId = txtAdIdProductImg.getText().toString();
        String status = txtAdStatusImg.getText().toString();
        String image = txtAdImgProductImg.getText().toString();

        if (!validate(id, productId)) {
            Toast.makeText(ProductImageActivity.this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductImage productImage = new ProductImage(Integer.parseInt(id), Integer.parseInt(productId), image, status);
        ProductImageDbHelper productImageDbHelper = new ProductImageDbHelper(this);

        if (productImageDbHelper.getProductImageById(Integer.parseInt(id)) == null) {
            Toast.makeText(ProductImageActivity.this, "Không tìm thấy productImage ", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productImageDbHelper.update(productImage);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }


}