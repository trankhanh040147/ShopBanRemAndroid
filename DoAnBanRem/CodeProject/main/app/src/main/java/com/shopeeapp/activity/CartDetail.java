package com.shopeeapp.activity;

import static com.shopeeapp.utilities.AccountSessionManager.currentUser;
import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.shopeeapp.BuildConfig;
import com.shopeeapp.MainActivity;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.BillDbHelper;
import com.shopeeapp.dbhelper.CartDbHelper;
import com.shopeeapp.dbhelper.DiscountDbHelper;
import com.shopeeapp.dbhelper.NotificationDbHelper;
import com.shopeeapp.dbhelper.StoreDbHelper;
import com.shopeeapp.entity.Bill;
import com.shopeeapp.entity.Cart;
import com.shopeeapp.entity.Discount;
import com.shopeeapp.entity.Notification;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.Store;
import com.shopeeapp.utilities.AccountSessionManager;

public class CartDetail extends AppCompatActivity {

    private static final String YOUR_CLIENT_ID = "";
    private static final String PAYPAL_CLIENT_ID = "AXhz_uGLm7gjTmc3S9hA4hMQxLyPx-Ww5IcPwk_ThA5hsANSewFcpqnu6IUyf1wYGxPqx4Rp5XBV4T9H";

    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
            .clientId(PAYPAL_CLIENT_ID);
    ImageView productImage;
    TextView productTitle;
    EditText txtQuantity;
    ImageButton btnSubtract;
    ImageButton btnPlus;
    TextView productPrice;
    TextView productDiscount;
    TextView txtProductStore;
    TextView txtProductStoreAddress;
    TextInputEditText txtDeliveryAddress;
    TextView cartPrice;
    Button btnCancelOrder;
    Button btnOrder;

    public static Cart cart;
    private Product product;
    private int quantity = 0;
    private Float discount = (float) 0;
    private Float price;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_detail);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startActivity(intent);

        checkVerify();
        addControl();
        addEvent();
        if (cart == null) return;
        setCartInfo();

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    private void checkVerify() {
        boolean isVerified = AccountSessionManager.isEmailVerified();
        if (!isVerified) {
            androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Thông báo")
                    .setMessage("Tài khoản của bạn chưa được xác minh. Vùi lòng truy cập cài đặt tài khoản dể xác nhận người dùng.")
                    .setIcon(R.drawable.warning_32px)
                    .setPositiveButton("OK", (dialogInterface, i) -> {
                        finish();
                    })
                    .show();
        }
    }


    private void addControl() {
        productImage = findViewById(R.id.productImage);
        productTitle = findViewById(R.id.productTitle);
        txtQuantity = findViewById(R.id.txtQuantity);
        btnSubtract = findViewById(R.id.subtract);
        btnPlus = findViewById(R.id.plus);
        productPrice = findViewById(R.id.productPrice);
        productDiscount = findViewById(R.id.productDiscount);
        txtProductStore = findViewById(R.id.productStore);
        txtProductStoreAddress = findViewById(R.id.productStoreAddress);
        txtDeliveryAddress = findViewById(R.id.txtDeliveryAddress);
        cartPrice = findViewById(R.id.cartPrice);
        btnCancelOrder = findViewById(R.id.btnCancelOrder);
        btnOrder = findViewById(R.id.btnOrder);
    }

    private void addEvent() {
        findViewById(R.id.btnBack).setOnClickListener(view -> finish());
        btnCancelOrder.setOnClickListener(this::setCancelOrder);
        btnOrder.setOnClickListener(this::setOrder);
        btnPlus.setOnClickListener(this::setPlus);
        btnSubtract.setOnClickListener(this::setSubtract);
        txtQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtQuantity.getText().equals("0"))
                    txtQuantity.setText("");
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (txtQuantity.getText().length() <= 0)
                    txtQuantity.setText("0");
                calcPrice();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                quantity = Integer.parseInt(txtQuantity.getText().toString());
                calcPrice();
            }
        });
    }


    private void setCartInfo() {
        if (cart == null)
            return;
        this.product = cart.getProduct();
        productTitle.setText(product.getName());
        productPrice.setText(product.getPrice().toString());
        DiscountDbHelper dbHelper = new DiscountDbHelper(this);
        Discount discountEnitity =  dbHelper.getDiscountByProduct(product.getId());
        discount = discountEnitity == null ? 0 : discountEnitity.getValue();
        productDiscount.setText(discount.toString());
        price = (float) Math.round(cart.getTotalPrice() * (100 - discount) / 100);
        cartPrice.setText(price.toString());
        setAddress();
        setQuantity();
        setBackgroundImage();

        StoreDbHelper storeDbHelper = new StoreDbHelper(this);
        Store store = storeDbHelper.getStoreById(product.getStoreId());

        if (store == null)
            return;
        txtProductStore.setText(store.getName());
        txtProductStoreAddress.setText(store.getAddress());
    }

    private void setOrder(@NotNull View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.payment_resource);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        dialog.setCancelable(true);

        Button btnNext = dialog.findViewById(R.id.btnNext);
        RadioGroup rdgPayment = dialog.findViewById(R.id.rdgPayment);
        RadioButton rdPaypal = dialog.findViewById(R.id.rdPaypal);
        RadioButton rdTrucTiep = dialog.findViewById(R.id.rdTrucTiep);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!rdPaypal.isChecked() && !rdTrucTiep.isChecked()) {
                    return;
                }
                if (rdPaypal.isChecked()) {
                    processPayment();
                } else {
                    BillDbHelper billDbHelper = new BillDbHelper(view.getContext());
                    String deliveryAddress = txtDeliveryAddress.getText().toString();
                    Bill bill = new Bill(user.getId(), cart.getId(), deliveryAddress, discount, price);
                    long result = billDbHelper.insert(bill);
                    if (result > 0) {
                        createCart();
                        Integer id = Integer.valueOf(createNotification(product.getName()).toString());
                        showNotification(id);
                        Toast.makeText(view.getContext(), "Đã đặt hàng thành công.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            }
        });

        dialog.show();
    }
    private void processPayment() {
        PayPalPayment payPalPayment = new PayPalPayment(new BigDecimal(String.valueOf(price)), "USD", cart.getProduct().getName(), PayPalPayment.PAYMENT_INTENT_SALE);
        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payPalPayment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void setCancelOrder(@NotNull View view) {
        CartDbHelper cartDbHelper = new CartDbHelper(view.getContext());
        int re = cartDbHelper.delete(cart.getId());
        if (re > 0) {
            Toast.makeText(view.getContext(), "Đã xóa giỏ hàng.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


    private void createCart() {
        CartDbHelper cartDbHelper = new CartDbHelper(this);
        cart.setCartOrdered();
        cart.setQuantity(quantity);
        cart.setStatus(Cart.CART_ORDERED);
        cartDbHelper.update(cart);
    }

    private Long createNotification(String productName) {
        NotificationDbHelper notificationDbHelper = new NotificationDbHelper(this);
        Notification notification = new Notification(
                user.getId(),
                Notification.NOTIFY_CART,
                Notification.NOTIFY_ORDER_PRODUCT + productName);
        return notificationDbHelper.insert(notification);
    }

    private void setBackgroundImage() {
        ArrayList<String> images = product.getProductImages();
        if (images != null) {
            if (images.size() > 0)
                Glide.with(this)
                        .load(images.get(0))
                        .into(productImage);
        }
    }

    private void setAddress() {
        String address = user.getAddress();
        if (address.length() > 0) {
            txtDeliveryAddress.setText(address);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setQuantity() {
        this.quantity = cart.getQuantity();
        txtQuantity.setText(cart.getQuantity().toString());

    }

    private void calcPrice() {
        String quantity = txtQuantity.getText().toString();
        double totalPrice = product.getPrice() * Integer.parseInt(quantity);
        price = (float) Math.round(totalPrice * (100 - discount) / 100);
        cartPrice.setText(String.valueOf(price.toString()));
    }

    private void setSubtract(View view) {
        if (quantity <= 1) return;
        this.quantity--;
        txtQuantity.setText(String.valueOf(quantity));
        cart.setQuantity(quantity);
        CartDbHelper cartDbHelper = new CartDbHelper(this);
        cartDbHelper.update(cart);
    }

    private void setPlus(View view) {
        this.quantity++;
        txtQuantity.setText(String.valueOf(quantity));
        cart.setQuantity(quantity);
        CartDbHelper cartDbHelper = new CartDbHelper(this);
        cartDbHelper.update(cart);

    }

    private void showNotification(Integer id){
        NotificationManager nm = (NotificationManager) getSystemService(NotificationManager.class);
        NotificationChannel channel = new NotificationChannel("1", "Đặt hàng thành công", NotificationManager.IMPORTANCE_DEFAULT);
        nm.createNotificationChannel(channel);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setContentTitle("Đặt hàng thành công ")
                .setContentText("Cảm ơn bạn đã đặt thành công "+product.getName()+" tại ShopeeApp. Chúc bạn một ngày vui vẻ!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("action", MainActivity.NOTIFICATION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pendingIntent);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        android.app.Notification notification = mBuilder.build();
        nm.notify(id, notification);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirmation = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirmation != null ) {
                    try {
                        String paymentDetals = confirmation.toJSONObject().toString();
                        androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                                .setMessage("Đã thanh toán thành công!")
                                .setIcon(R.drawable.warning_32px)
                                .setPositiveButton("OK", (dialogInterface, i) -> {
                                    BillDbHelper billDbHelper = new BillDbHelper(this);
                                    String deliveryAddress = txtDeliveryAddress.getText().toString();
                                    Bill bill = new Bill(user.getId(), cart.getId(), deliveryAddress, discount, price, Bill.BILL_PAID);
                                    long result = billDbHelper.insert(bill);
                                    if (result > 0) {
                                        createCart();
                                        Integer id = Integer.valueOf(createNotification(product.getName()).toString());
                                        showNotification(id);
                                        Toast.makeText(this, "Đã đặt hàng thành công.", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }
                                })
                                .show();
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
