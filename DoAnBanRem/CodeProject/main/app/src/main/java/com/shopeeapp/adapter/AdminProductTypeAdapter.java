package com.shopeeapp.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.shopeeapp.R;
import com.shopeeapp.entity.ProductType;

import java.util.ArrayList;

public class AdminProductTypeAdapter extends ArrayAdapter<ProductType> {
    Activity context;
    ArrayList<ProductType> productTypes;
    int layoutResource;

    public AdminProductTypeAdapter(Activity context, int resource, ArrayList<ProductType> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.productTypes = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.layoutResource,null);
        TextView productTypeId = row.findViewById(R.id.txt_row_show_idDiscount);
        productTypeId.setText(productTypes.get(position).getId().toString());
        TextView producTypeName = row.findViewById(R.id.txt_row_show_valueDiscount);
        producTypeName.setText(productTypes.get(position).getName());

        ImageView image = row.findViewById(R.id.imgeViewAdProductType);
        Glide.with(context)
                .load(productTypes.get(position).getImage())
                .into(image);
        return row;
    }
}
