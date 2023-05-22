package com.shopeeapp.activity;

import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.bumptech.glide.Glide;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.CartDbHelper;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.entity.Cart;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.Store;

public class ProductDetail extends AppCompatActivity {

    public static final String PRODUCT_ID = "productId";
    private Product product;
    private Cart cart;
    private int quantity = 0;

    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.productImage)
    ImageView productImage;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.btnAddCart)
    Button btnAddCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.btnViewCart)
    Button btnViewCart;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.subtract)
    ImageButton btnSubtract;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.plus)
    ImageButton btnPlus;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.txtQuantity)
    EditText txtQuantity;
    @SuppressLint("NonConstantResourceId")
    @BindView(value = R.id.svReview)
    ScrollView svReview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        ButterKnife.bind(this);
        this.quantity = 0;
        txtQuantity.setText("0");
        btnViewCart.setVisibility(View.GONE);

        findViewById(R.id.btnBack_detail).setOnClickListener(v -> {
            finish();
        });

        setProduct();
        btnSubtract.setOnClickListener(this::setSubtractQuantity);
        btnPlus.setOnClickListener(this::setAddQuantity);
        btnAddCart.setOnClickListener(this::setAddCart);
        btnViewCart.setOnClickListener(this::setViewCart);
    }

    private void setViewCart(View view) {
        Intent intent = new Intent(view.getContext(), CartDetail.class);
        CartDetail.cart = this.cart;
        view.getContext().startActivity(intent);
        finish();
    }

    private void setAddCart(View view) {
        if (this.quantity <= 0) {
            Toast.makeText(this, "Số lượng sản phẩm tối thiểu là 1.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (user != null) {
            CartDbHelper cartDbHelper = new CartDbHelper(this);
            Cart cart = new Cart(
                    user.getId(),
                    this.product.getId(),
                    this.quantity);
            long rowId = cartDbHelper.insert(cart);
            if (rowId > 0) {
                Toast.makeText(this, "Đã thêm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
                this.cart = cart;
                this.cart.setId(cartDbHelper.getCartIdByRowId(rowId));
                btnAddCart.setVisibility(View.GONE);
                btnViewCart.setVisibility(View.VISIBLE);
            } else
                Toast.makeText(this, "Đã xảy ra lỗi khi thêm giỏ hàng.", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, LogIn.class);
            startActivity(intent);
        }
    }

    private void setAddQuantity(View view) {
        this.quantity += 1;
        txtQuantity.setText(String.valueOf(quantity));
    }

    private void setSubtractQuantity(View view) {
        if (this.quantity <= 0) {
            this.quantity = 0;
            txtQuantity.setText("0");
        } else {
            quantity -= 1;
            txtQuantity.setText(String.valueOf(quantity));
        }
    }

    private void setProduct() {
        Bundle bundle = getIntent().getExtras();
        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        this.product = productDbHelper.getProductById((int) bundle.getLong(PRODUCT_ID));

        if (this.product != null) {
            setProductImage();
            setProductTitle();
            setProductDetail();
            setProductStore(productDbHelper);
            svReview.setVisibility(View.GONE);
        }
    }

    private void setProductImage() {
        if (product.getProductImages() != null) {
            if (product.getProductImages().size() > 0)
                Glide.with(this)
                        .load(product.getProductImages().get(0))
                        .into(productImage);
        }
    }

    private void setProductTitle() {
        ((TextView) findViewById(R.id.productTitle)).setText(this.product.getName());
        ((TextView) findViewById(R.id.productPrice)).setText(product.getPrice().toString());
    }

    private void setProductDetail() {
        String productDetail = product.getDetail();
        TextView tvProductDetail = findViewById(R.id.productDescription);
        if (productDetail == null)
            tvProductDetail.setVisibility(TextView.GONE);
        else
            tvProductDetail.setText(product.getDetail());
    }

    private void setProductStore(ProductDbHelper productDbHelper) {
        Store store = productDbHelper.getStore(this.product.getStoreId());
        if (store != null) {
            ((TextView) findViewById(R.id.productStore)).setText(store.getName());
            ((TextView) findViewById(R.id.productStoreAddress)).setText(store.getAddress());
        }
    }
}