package com.shopeeapp.activity;

import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;
import com.shopeeapp.adapter.BillAdapter;
import com.shopeeapp.dbhelper.BillDbHelper;
import com.shopeeapp.entity.Bill;


public class BillHistory extends AppCompatActivity {

    @BindView(R.id.btnBack)
    ImageButton btnBack;
    @BindView(R.id.swViewAll)
    SwitchMaterial swViewAll;
    @BindView(R.id.rvBillHistory)
    RecyclerView rvBillHistory;
    @BindView(R.id.noMoreBills)
    LinearLayout noMoreBills;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bill_history);
        ButterKnife.bind(this);

        btnBack.setOnClickListener(view -> finish());
        swViewAll.setOnClickListener(view -> setBillSDisplay());
        setBillSDisplay();
    }

    private void setBillSDisplay() {
        BillDbHelper billDbHelper = new BillDbHelper(this);
        ArrayList<Bill> bills = new ArrayList<>();
        if (swViewAll.isChecked())
            bills = billDbHelper.getAllBills(user.getId());
        else
            bills = billDbHelper.getUnpaidBills(user.getId());
        if (bills.size() > 0) {
            BillAdapter adapter = new BillAdapter(bills, this);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            rvBillHistory.setLayoutManager(layoutManager);
            rvBillHistory.setAdapter(adapter);
            rvBillHistory.setVisibility(View.VISIBLE);
            noMoreBills.setVisibility(View.GONE);
            for (Bill bill : bills) {
                System.out.println(bill);
            }
        } else {
            rvBillHistory.setVisibility(View.GONE);
            noMoreBills.setVisibility(View.VISIBLE);
        }
    }
}
