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
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.entity.Product;

public class AdminProductActivity extends AppCompatActivity {

    TextInputEditText txtAdIdProduct,
            txtAdStoreId,
            txtAdType,
            txtAdNameProduct,
            txtAdPriceProduct,
            txtAdImgProduct,
            txtAdDetail,
            txtAdStar,
            txtAdStatus;
    Button btnAdd,
            btnDelete,
            btnUpdate;
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_product);

        addControl();
        handleEvent();

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminProductActivity.this, ShowAdProductActivity.class);
                startActivity(intent);
            }
        });
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
                delete();
            }
        });
    }

    private void addControl() {
        txtAdStoreId = findViewById(R.id.txtAdStoreId);
        txtAdIdProduct = findViewById(R.id.txtAdIdProduct);
        txtAdType = findViewById(R.id.txtAdType);
        txtAdNameProduct = findViewById(R.id.txtAdNameProduct);
        txtAdPriceProduct = findViewById(R.id.txtAdPriceProduct);
        txtAdImgProduct = findViewById(R.id.txtAdImgProduct);
        txtAdDetail = findViewById(R.id.txtAdDetail);
        txtAdStar = findViewById(R.id.txtAdStar);
        txtAdStatus = findViewById(R.id.txtAdStatus);

        btnAdd = findViewById(R.id.btnAdAdd);
        btnDelete = findViewById(R.id.btnBackToDashboard);
        btnUpdate = findViewById(R.id.btnAdUpdate);
    }

    private boolean validate(String id, String name, String storeId, String type) {
        if (id.equals("")) {
            txtAdStoreId.setError("Không được bỏ trống");
            return false;
        }
        if (name.equals("")) {
            txtAdNameProduct.setError("Không được bỏ trống");
            return false;
        }
        if (storeId.equals("")) {
            txtAdStoreId.setError("Không được bỏ trống");
            return false;
        }
        if (type.equals("")) {
            txtAdType.setError("Không được bỏ trống");
            return false;
        }

        StoreDbHelper storeDbHelper = new StoreDbHelper(this);
        if (storeDbHelper.getStoreById(Integer.parseInt(storeId)) == null) {
            txtAdStoreId.setError("Không tìm được store");
            return false;
        }

        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);
        if (productTypeDbHelper.getProductTypeById(Integer.parseInt(type)) == null) {
            txtAdType.setError("Không tìm được type");
            return false;
        }
        return true;
    }

    private void insertData() {
        String store = txtAdStoreId.getText().toString();
        String id = txtAdIdProduct.getText().toString();
        String type = txtAdType.getText().toString();
        String name = txtAdNameProduct.getText().toString();
        String price = txtAdPriceProduct.getText().toString();
        String image = txtAdImgProduct.getText().toString();
        String detail = txtAdDetail.getText().toString();

        try {
            if (Double.parseDouble(price) <= 0) {
                txtAdPriceProduct.setError("Không hợp lệ");
                return;
            }

            float star = 0;
            if (!txtAdStar.getText().toString().equals("")) {
                star = Float.parseFloat(txtAdStar.getText().toString());
            }
            String status = txtAdStatus.getText().toString();

            if (!validate(id, name, store, type)) {
                Toast.makeText(this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Product product = new Product(Integer.parseInt(id), Integer.parseInt(store), Integer.parseInt(type), name, Double.parseDouble(price),
                        image, detail, star, status);
                ProductDbHelper productDbHelper = new ProductDbHelper(this);
                long rowId = productDbHelper.insert(product);
                if (rowId > 0) {
                    Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateData() {
        String store = txtAdStoreId.getText().toString();
        String id = txtAdIdProduct.getText().toString();
        String type = txtAdType.getText().toString();
        String name = txtAdNameProduct.getText().toString();
        String price = txtAdPriceProduct.getText().toString();
        String image = txtAdImgProduct.getText().toString();
        String detail = txtAdDetail.getText().toString();
        float star = 0;

        try {
            if (Double.parseDouble(price) <= 0) {
                txtAdPriceProduct.setError("Không hợp lệ");
                return;
            }


            if (!txtAdStar.getText().toString().equals("")) {
                star = Float.parseFloat(txtAdStar.getText().toString());
            }
            String status = txtAdStatus.getText().toString();


            if (!validate(id, name, store, type)) {
                Toast.makeText(this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
                return;
            }

            Product product = new Product(Integer.parseInt(id), Integer.parseInt(store), Integer.parseInt(type), name, Double.parseDouble(price),
                    image, detail, star, status);
            ProductDbHelper productDbHelper = new ProductDbHelper(this);

            if (productDbHelper.getProductById(Integer.parseInt(id)) == null) {
                Toast.makeText(this, "Không tìm thấy store ", Toast.LENGTH_SHORT).show();
                return;
            }

            long rowId = productDbHelper.update(product);
            if (rowId > 0) {
                Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "Sai định dạng", Toast.LENGTH_SHORT).show();
        }
    }

    private void delete() {
        String id = txtAdIdProduct.getText().toString();


        if (id.equals("")) {
            Toast.makeText(this, "Nhập thông tin sai", Toast.LENGTH_SHORT).show();
            return;
        }


        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        Product product = productDbHelper.getProductById(Integer.parseInt(id));
        if (product == null) {
            Toast.makeText(this, "Không tìm thấy store ", Toast.LENGTH_SHORT).show();
            return;
        }

        long rowId = productDbHelper.delete(product);
        if (rowId > 0) {
            Toast.makeText(this, " Thành công !", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "Đã xảy ra lỗi .", Toast.LENGTH_SHORT).show();
    }
}