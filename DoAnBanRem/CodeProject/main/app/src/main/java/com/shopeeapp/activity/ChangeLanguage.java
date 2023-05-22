package com.shopeeapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.shopeeapp.R;
import com.shopeeapp.fragment.MessageBoxFragment;

public class ChangeLanguage extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feature_change_language);

        findViewById(R.id.btnBack_ChangeLanguage).setOnClickListener(v -> {
            finish();
        });
    }

    public void chooseLanguage(View view) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MessageBoxFragment msg = new MessageBoxFragment();
        msg.show(transaction, "msg");
    }
}
