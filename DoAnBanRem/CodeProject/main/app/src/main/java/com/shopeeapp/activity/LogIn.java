package com.shopeeapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.shopeeapp.MainActivity;
import com.shopeeapp.R;
import com.shopeeapp.activity.admin.AdminStoreActivity;
import com.shopeeapp.activity.admin.DashBoard;
import com.shopeeapp.entity.Account;
import com.shopeeapp.entity.User;
import com.shopeeapp.utilities.AccountSessionManager;

public class LogIn extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText txtEmail;
    TextInputEditText txtPassword;
    TextView txtSignUp;
    Button btnForgotPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        addControl();
        addEvent();

    }

    private void addControl() {
        btnLogin = findViewById(R.id.btnLogIn);
        txtEmail = findViewById(R.id.email);
        txtPassword = findViewById(R.id.txtPassword);
        txtSignUp = findViewById(R.id.txtSignUp);
        btnForgotPassword = findViewById(R.id.btnForgotPassword);
    }

    private void addEvent() {
        findViewById(R.id.btnBack).setOnClickListener(view -> finish());
        btnLogin.setOnClickListener(this::setLogin);
        txtSignUp.setOnClickListener(this::setSignUp);
        btnForgotPassword.setOnClickListener(this::setForgotPassword);
    }

    private void setLogin(View view) {
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        if (email.equals("admin") && password.equals("123")){
            AccountSessionManager.account = new Account(1, "admin", "admin", "123", 1, "");
            AccountSessionManager.user = new User(1, 1, "admin", null, null, null, null, null, "");
            SharedPreferences sharedPreferences = getSharedPreferences("admin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("Admin", "true");
            editor.commit();
            Intent adminActivity = new Intent(this, DashBoard.class);
            startActivity(adminActivity);
            finish();
            return;
        }
        Intent intent = new Intent(this, FirebaseActivity.class);
        intent.putExtra(FirebaseActivity.EMAIL, email);
        intent.putExtra(FirebaseActivity.PASSWORD, password);
        intent.setAction(FirebaseActivity.SIGN_IN_ACTION);
        startActivityForResult(intent, FirebaseActivity.SIGN_IN);

        if (AccountSessionManager.user != null) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            if (AccountSessionManager.account.getRoleID() == 1) {
                Intent adminActivity = new Intent(this, DashBoard.class);
                startActivity(adminActivity);
                finish();
            } else if (AccountSessionManager.account.getRoleID() == 2) {
                Intent mainActivity = new Intent(this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            } else {
                Toast.makeText(this, "Tài khoản không hợp lệ!", Toast.LENGTH_SHORT).show();
            }
//            finish();
        }
    }

    private void setSignUp(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

    private void setForgotPassword(View view) {
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivityForResult(intent, FirebaseActivity.FORGOT_PASSWORD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FirebaseActivity.SIGN_IN) {
            if (resultCode == FirebaseActivity.SIGN_IN_OK) {
                if (AccountSessionManager.user != null) {
                    if (AccountSessionManager.account.getRoleID() == 1) {
                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        Intent adminActivity = new Intent(this, DashBoard.class);
                        startActivity(adminActivity);
                        finish();
                    } else if (AccountSessionManager.account.getRoleID() == 2) {
                        Intent mainActivity = new Intent(this, MainActivity.class);
                        startActivity(mainActivity);
                        Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(this, "Tài khoản không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
            finish();
                }
            }
        }
    }
}
