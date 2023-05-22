package com.shopeeapp.activity;

import static com.shopeeapp.MainActivity.mainResources;
import static com.shopeeapp.activity.FirebaseActivity.EMAIL;
import static com.shopeeapp.activity.FirebaseActivity.FORGOT_PASSWORD;
import static com.shopeeapp.activity.FirebaseActivity.FORGOT_PASSWORD_ACTION;
import static com.shopeeapp.activity.FirebaseActivity.FORGOT_PASSWORD_OK;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;

public class ForgotPassword extends AppCompatActivity {
//    @BindView(R.id.btnBack)
    ImageButton btnBack;
//    @BindView(R.id.email)
    TextInputEditText txtEmail;
//    @BindView(R.id.btnGetPassword)
    Button btnGetPassword;
    private final static String message = mainResources.getString(R.string.message_forgot_password);
//    @BindView(R.id.txtLabel)
    TextView txtLabel;
//    @BindView(R.id.layoutGetPassword)
    LinearLayout layoutGetPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        addControl();
        addEvent();

//        ButterKnife.bind(this);
//
//        btnBack.setOnClickListener(view -> finish());
//        btnGetPassword.setOnClickListener(this::setGetPassword);
//        txtLabel.setVisibility(View.GONE);
    }

    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        txtEmail = findViewById(R.id.email);
        btnGetPassword = findViewById(R.id.btnGetPassword);
        txtLabel = findViewById(R.id.txtLabel);
        layoutGetPassword = findViewById(R.id.layoutGetPassword);
    }
    private void addEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnGetPassword.setOnClickListener(this::setGetPassword);
        txtLabel.setVisibility(View.GONE);
    }
    private void setGetPassword(View view) {
        String email = txtEmail.getText().toString();
        if (!email.contains("@")) {
            Toast.makeText(this, "Vui lòng nhập chính xác email.", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, FirebaseActivity.class);
        intent.putExtra(EMAIL, email);
        intent.setAction(FORGOT_PASSWORD_ACTION);
        startActivityForResult(intent, FORGOT_PASSWORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FORGOT_PASSWORD) {
            if (resultCode == FORGOT_PASSWORD_OK) {
                Toast.makeText(this, String.format(message, txtEmail.getText()), Toast.LENGTH_SHORT).show();
                txtLabel.setText(String.format(message, txtEmail.getText()));
                txtLabel.setVisibility(View.VISIBLE);
                layoutGetPassword.setVisibility(View.GONE);
            }
        }
    }
}
