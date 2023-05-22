package com.shopeeapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.shopeeapp.R;
import com.shopeeapp.activity.admin.ShowPaymentActivity;
import com.shopeeapp.dbhelper.BillDbHelper;
import com.shopeeapp.dbhelper.UserDbHelper;
import com.shopeeapp.entity.Bill;
import com.shopeeapp.entity.Bill;

import java.util.ArrayList;

public class AdminBillAdapter extends ArrayAdapter<Bill> {
    Activity context;
    ArrayList<Bill> bills;
    int layoutResource;

    public AdminBillAdapter(Activity context, int resource, ArrayList<Bill> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.bills = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();//build layout thanh java de android su dung
        View row=layoutInflater.inflate(this.layoutResource,null);

        TextView paymenId = row.findViewById(R.id.txt_row_show_idPayment);
        String billId = bills.get(position).getId().toString();
        paymenId.setText(billId);

        BillDbHelper billDbHelper = new BillDbHelper(context);
        UserDbHelper userDbHelper = new UserDbHelper(context);

        TextView txtusername = row.findViewById(R.id.txt_row_show_nameUser);
        String userId = bills.get(position).getUserId().toString();
        String userName = userDbHelper.getUser(Integer.parseInt(userId)).getFullname();
        txtusername.setText(userName);

        Button btnConfirm = row.findViewById(R.id.buttonConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bill bill  = billDbHelper.getBillById(Integer.parseInt(billId));
                bill.setStatus("Paid");
                billDbHelper.update(bill);
                Intent intent = new Intent(context, ShowPaymentActivity.class);
                context.startActivity(intent);
            }
        });
        return row;
    }
}
