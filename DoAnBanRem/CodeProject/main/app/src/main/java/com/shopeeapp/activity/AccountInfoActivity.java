package com.shopeeapp.activity;

import static com.shopeeapp.activity.FirebaseActivity.CHANGE_EMAIL;
import static com.shopeeapp.activity.FirebaseActivity.CHANGE_EMAIL_ACTION;
import static com.shopeeapp.activity.FirebaseActivity.CHANGE_EMAIL_OK;
import static com.shopeeapp.activity.FirebaseActivity.CREATE_ACCOUNT;
import static com.shopeeapp.activity.FirebaseActivity.CREATE_ACCOUNT_OK;
import static com.shopeeapp.activity.FirebaseActivity.EMAIL;
import static com.shopeeapp.utilities.AccountSessionManager.account;
import static com.shopeeapp.utilities.AccountSessionManager.user;
import static com.shopeeapp.utilities.AppUtilities.PERMISSION_REQUEST_CODE;
import static com.shopeeapp.utilities.AppUtilities.SELECT_PICTURE;
import static com.shopeeapp.utilities.AppUtilities.TAKE_PICTURE;
import static com.shopeeapp.utilities.AppUtilities.requestPermission;
import static com.shopeeapp.utilities.AppUtilities.setPic;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.AccountDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;
import com.shopeeapp.utilities.AppUtilities;
import com.shopeeapp.utilities.ImageConverter;

import org.jetbrains.annotations.NotNull;

public class AccountInfoActivity extends AppCompatActivity {
    private static final int IMAGE_PICK_CODE = 1000;


    ImageButton btnBack;
    ImageView imgAvt;
    Button btnTakePhoto;
    Button btnChoosePhoto;
    TextInputEditText txtFullName;
    TextInputEditText txtAddress;
    TextInputEditText txtEmail;
    TextInputEditText txtPhone;
    Button btnUpdate;


    Uri fileImage;
    String urlImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_info);

        if (Build.VERSION.SDK_INT >= 23) {
            if (!AppUtilities.checkPermission(this))
                requestPermission(this);
        }

        addControl();
        addEvent();

        setInfo();
        setAvatar();
    }




    private void addControl() {
        btnBack = findViewById(R.id.btnBack);
        imgAvt = findViewById(R.id.imgAvt);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        btnChoosePhoto = findViewById(R.id.btnChoosePhoto);
        txtFullName = findViewById(R.id.txtFullName);
        txtAddress = findViewById(R.id.txtAddress);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);
        btnUpdate = findViewById(R.id.btnUpdate);

    }

    private void addEvent() {
        btnBack.setOnClickListener(view -> finish());
        btnTakePhoto.setOnClickListener(AppUtilities::setTakePhoto);
        btnChoosePhoto.setOnClickListener(AppUtilities::setChoosePhoto);
        btnUpdate.setOnClickListener(this::setUpdate);
    }

    private void setInfo() {
        if (user != null) {
            txtFullName.setText(user.getFullname());
            txtAddress.setText(user.getAddress());
            txtPhone.setText(user.getPhone());
            txtEmail.setText(account.getEmail());
        }
    }

    private void setAvatar() {
        if (user != null) {
//            imgAvt.setImageBitmap(user.getAvatar());
            System.out.println(user.getAvatar());
            Glide.with(this).load(user.getAvatar()).into(imgAvt);
        }
    }

    private void setUpdate(View view) {
        String fullName = txtFullName.getText().toString();
        String address = txtAddress.getText().toString();
        String phone = txtPhone.getText().toString();
        if (validate(fullName, phone, address)) {
            if (account != null && user != null) {
//            updateEmail(email);
                updateAvatar();
            }
        }

    }

    private boolean validate(@NotNull String fullName, String phone, String address) {
        if (fullName.equals("")) {
            txtFullName.setError("Không được bỏ trống");
            return false;
        }
        if (phone.equals("")) {
            txtPhone.setError("Không được bỏ trống");
            return false;
        }
        if (address.equals("")) {
            txtAddress.setError("Không được bỏ trống");
            return false;
        }
        if (fileImage == null) {
            fileImage = Uri.parse("android.resource://com.shopeeapp/drawable/account");
        }
        return true;
    }
    private void updateAvatar(){
        Intent intent = new Intent(this, FirebaseActivity.class);
        intent.putExtra("imageUrl", fileImage.toString());
        intent.setAction(FirebaseActivity.UPLOAD_PHOTO_ACTION);
        startActivityForResult(intent, FirebaseActivity.UPLOAD_PHOTO);
    }

    private void updateUserInfo() {
        String fullName = txtFullName.getText().toString();
        String address = txtAddress.getText().toString();
        String phone = txtPhone.getText().toString();
        user.setFullname(fullName);
        user.setAddress(address);
        user.setPhone(phone);
        user.setAvatar(urlImage);
        UserDbHelper userDbHelper = new UserDbHelper(this);
        int re = userDbHelper.update(user);
        if (re > 0) {
            Log.i("===Update user info to database===", "Success");
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == FirebaseActivity.UPLOAD_PHOTO && resultCode == FirebaseActivity.UPLOAD_PHOTO_OK) {
            urlImage = data.getStringExtra(FirebaseActivity.UPLOAD_PHOTO_ACTION);
            updateUserInfo();
            Toast.makeText(this, "Đã cập nhật thông tin thành công.", Toast.LENGTH_SHORT).show();
        } else if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_CODE) {
                fileImage = data.getData();
                if (fileImage != null) {
                    imgAvt.setImageURI(data.getData());
                } else {
                    System.out.println("Lỗi");
                }

            } else if (requestCode == TAKE_PICTURE) {
                fileImage = AppUtilities.photoURI;
                imgAvt.setImageURI(fileImage);
            }
        } else if (resultCode == CREATE_ACCOUNT_OK) {
            if (requestCode == CREATE_ACCOUNT) {
                Toast.makeText(this, "Đã đăng ký thành công!", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else if (resultCode == CHANGE_EMAIL_OK) {
            if (requestCode == CHANGE_EMAIL) {
                Toast.makeText(this, "Đã cập nhật thông tin thành công.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}