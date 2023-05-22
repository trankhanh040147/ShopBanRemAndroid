package com.shopeeapp.activity;

import static com.shopeeapp.utilities.AccountSessionManager.account;
import static com.shopeeapp.utilities.AccountSessionManager.currentUser;
import static com.shopeeapp.utilities.AccountSessionManager.getAuth;
import static com.shopeeapp.utilities.AccountSessionManager.getCurrentUser;
import static com.shopeeapp.utilities.AccountSessionManager.mAuth;
import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.shopeeapp.dbhelper.AccountDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;

public class FirebaseActivity extends Activity {

    public static final String TAG = "EmailPassword";
    public static final String EMAIL = "Email";
    public static final String PASSWORD = "Password";
    public static final String SIGN_IN_ACTION = "Sign in";
    public static final String CREATE_ACCOUNT_ACTION = "Create account";
    public static final String VERIFY_ACTION = "Verify";
    public static final String CHANGE_EMAIL_ACTION = "Change email";
    public static final String CHANGE_PASSWORD_ACTION = "Change password";
    public static final String FORGOT_PASSWORD_ACTION = "Forgot password";
    public static final String UPLOAD_PHOTO_ACTION = "Upload Photo";
    public static final int SIGN_IN = 1;
    public static final int CREATE_ACCOUNT = 2;
    public static final int VERIFY = 3;
    public static final int CHANGE_EMAIL = 4;
    public static final int CHANGE_PASSWORD = 5;
    public static final int FORGOT_PASSWORD = 6;
    public static final int UPLOAD_PHOTO = 7;
    public static final int SIGN_IN_OK = 100;
    public static final int CREATE_ACCOUNT_OK = 200;
    public static final int VERIFY_OK = 300;
    public static final int CHANGE_EMAIL_OK = 400;
    public static final int CHANGE_PASSWORD_OK = 500;
    public static final int FORGOT_PASSWORD_OK = 600;
    public static final int UPLOAD_PHOTO_OK = 700;

    FirebaseStorage storage;
    StorageReference storageReference;
    private String email;
    private String password; //encoded

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        Intent intent = getIntent();
        this.email = intent.getStringExtra(EMAIL);
        this.password = intent.getStringExtra(PASSWORD);
        action(Objects.requireNonNull(intent.getAction()));
    }

    private void action(@NotNull String action) {
        if (action.equals(SIGN_IN_ACTION)) {
            signIn();
        } else if (action.equals(CREATE_ACCOUNT_ACTION)) {
            createAccount();
        } else if (action.equals(VERIFY_ACTION)) {
            sendEmailVerification();
        } else if (action.equals(CHANGE_PASSWORD_ACTION)) {
            updatePassword(this.password);
        } else if (action.equals(FORGOT_PASSWORD_ACTION)) {
            forgotPassword(this.email);
        } else if (action.equals(CHANGE_EMAIL_ACTION)) {
            updateEmail();
        } else if (action.equals(UPLOAD_PHOTO_ACTION)) {
            String imageUrl = getIntent().getStringExtra("imageUrl");
            Uri mImageUri = Uri.parse(imageUrl);
            uploadImage(mImageUri);
        }
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
    }
    // [END on_start_check_user]

    private void createAccount() {
        // [START create_user_with_email]
        mAuth = getAuth();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success");
                        currentUser = mAuth.getCurrentUser();
                        updateAccountSession();
                        setResult(CREATE_ACCOUNT_OK);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(FirebaseActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        setResult(-1);
                        finish();
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn() {
        // [START sign_in_with_email]
        // password has been encoded
        mAuth = getAuth();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        updateAccountSession();
                        setResult(SIGN_IN_OK);
                        finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(FirebaseActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        setResult(-1);
                        finish();
                    }
                });
        // [END sign_in_with_email]
    }

    private void sendEmailVerification() {
        // Send verification email
        // [START send_email_verification]
        currentUser = getCurrentUser();
        currentUser.sendEmailVerification()
                .addOnCompleteListener(this, task -> {
                    Log.i(EMAIL, "Email sent.");
                    Toast.makeText(this, "Vui lòng kiểm tra email để xác thực tài khoản.", Toast.LENGTH_SHORT).show();
                    setResult(VERIFY_OK);
                    finish();
                });
        // [END send_email_verification]
    }

    private void updatePassword(String newPassword) {
        currentUser = getCurrentUser();
        currentUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            setResult(CHANGE_PASSWORD_OK);
                            finish();
                        }
                    }
                });
    }

    private void forgotPassword(String email) {
        FirebaseAuth auth = getAuth();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            setResult(FORGOT_PASSWORD_OK);
                            finish();
                        }
                    }
                });
    }

    private void uploadImage(Uri fileImage)
    {
        String urlImage = "";
        // Code for showing progressDialog while uploading
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Waiting...");
        progressDialog.show();
        if (fileImage != null) {


            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();

            // Defining the child of storageReference
            StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());


            ref.putFile(fileImage).addOnSuccessListener( new OnSuccessListener<UploadTask.TaskSnapshot>() {

                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                {
                    // Image uploaded successfully
                    // Dismiss dialog
                    Toast.makeText(FirebaseActivity.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onSuccess(Uri uri) {
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
                            String urlImage = String.valueOf(uri);
                            Intent intent = new Intent();
                            intent.putExtra(UPLOAD_PHOTO_ACTION,urlImage);
                            setResult(UPLOAD_PHOTO_OK, intent);
                            progressDialog.dismiss();
                            finish();
                        }
                    });


                }

            })


            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e)
                {

                    // Error, Image not uploaded
                    progressDialog.dismiss();
                    Toast.makeText(FirebaseActivity.this,"Failed " + e.getMessage(),Toast.LENGTH_SHORT).show();
                    setResult(-1);
                    finish();
                }
            })
            .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {

                // Progress Listener for loading
                // percentage on the dialog box
                @Override
                public void onProgress(
                        UploadTask.TaskSnapshot taskSnapshot)
                {
                    double progress
                            = (100.0
                            * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage(
                            "Uploaded "
                                    + (int)progress + "%");
                }
            });
        }
    }
    private void updateAccountSession() {
        currentUser = getCurrentUser();
        AccountDbHelper accountDbHelper = new AccountDbHelper(this);
        account = accountDbHelper.getAccountByEmail(email);
        if (account != null) {
            UserDbHelper userDbHelper = new UserDbHelper(this);
            user = userDbHelper.getUserByAccountId(account.getId());
            if (user == null) {
                account = null;
                Toast.makeText(this, "Đã có lỗi phát sinh trong quá trình đăng nhập.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Đăng nhập thất bại. Vui lòng đăng nhập lại.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateEmail() {
        currentUser = getCurrentUser();
        currentUser.updateEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            setResult(CHANGE_EMAIL_OK);
                            finish();
                        }
                    }
                });
    }
}