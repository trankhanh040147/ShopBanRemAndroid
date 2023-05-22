package com.shopeeapp.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.shopeeapp.activity.admin.DashBoard;
import com.shopeeapp.dbhelper.AccountDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;
import com.shopeeapp.entity.Account;
import com.shopeeapp.entity.User;

public class AccountSessionManager {
    public static FirebaseUser currentUser;
    public static FirebaseAuth mAuth;
    public static Account account;
    public static User user;

    public static FirebaseUser getCurrentUser() {
        if (currentUser == null)
            currentUser = mAuth.getCurrentUser();
        return currentUser;
    }

    public static FirebaseAuth getAuth() {
        if (mAuth == null)
            mAuth = FirebaseAuth.getInstance();
        return mAuth;
    }

    public static boolean checkLogin(Context context) {
        mAuth = getAuth();
        currentUser = getCurrentUser();
        if (currentUser != null) {
            String email = currentUser.getEmail();
            AccountDbHelper accountDbHelper = new AccountDbHelper(context);
            account = accountDbHelper.getAccountByEmail(email);

            if (account == null) {
                currentUser = null;
                user = null;
                return false;
            }
            UserDbHelper userDbHelper = new UserDbHelper(context);
            user = userDbHelper.getUserByAccountId(account.getId());

            if (user == null) {
                account = null;
                //AppUtilities.clearSession(context);
                return false;
            } else {
                if (account.getRoleID() == 1) {
                    Intent intent = new Intent(context, DashBoard.class);
                    context.startActivity(intent);
                    Activity activity = (Activity) context;
                    Toast.makeText(context, "Đã đăng nhập với tên " + user.getFullname(), Toast.LENGTH_SHORT).show();
                    activity.finish();
                }
                Toast.makeText(context, "Đã đăng nhập với tên " + user.getFullname(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;
    }

    public static void logout() {
        FirebaseAuth.getInstance().signOut();
        account = null;
        user = null;
    }

    public static boolean isEmailVerified() {
        if (mAuth != null && currentUser != null) {
            return currentUser.isEmailVerified();
        }
        return false;
    }
}
