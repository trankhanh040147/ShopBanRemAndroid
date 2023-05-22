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
import com.shopeeapp.entity.Product;

import java.util.ArrayList;

public class AdminProductAdapter extends ArrayAdapter<Product> {
    Activity context;
    ArrayList<Product> products;
    int layoutResource;

    public AdminProductAdapter(Activity context, int resource, ArrayList<Product> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.products = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.layoutResource,null);
        TextView productId = row.findViewById(R.id.txt_row_show_idStore);
        productId.setText(products.get(position).getId().toString());
        TextView productName = row.findViewById(R.id.txt_row_show_nameStore);
        productName.setText(products.get(position).getName());
        TextView productPrice = row.findViewById(R.id.txt_row_PriceProduct);
        productPrice.setText(products.get(position).getPrice().toString());

        ImageView image = row.findViewById(R.id.imgeViewAdProduct);
        Glide.with(context)
                .load(products.get(position).getImage())
                .into(image);
        return row;
    }
}
