package com.shopeeapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.shopeeapp.R;
import com.shopeeapp.entity.Discount;

import java.util.ArrayList;

public class DiscountAdapter extends ArrayAdapter<Discount> {
    Activity context;
    ArrayList<Discount> stores;
    int layoutResource;

    public DiscountAdapter(Activity context, int resource, ArrayList<Discount> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.stores = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();//build layout thanh java de android su dung
        View row=layoutInflater.inflate(this.layoutResource,null);
        TextView discountId = row.findViewById(R.id.txt_row_show_idDiscount);
        discountId.setText(stores.get(position).getId().toString());
        TextView productId = row.findViewById(R.id.txt_row_show_productIdDiscount);
        productId.setText(stores.get(position).getProductId().toString());
        TextView value = row.findViewById(R.id.txt_row_show_valueDiscount);
        value.setText(stores.get(position).getValue().toString());

        return row;
    }
}
