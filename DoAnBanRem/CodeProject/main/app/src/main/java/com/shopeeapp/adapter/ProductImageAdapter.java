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
import com.shopeeapp.entity.ProductImage;

import java.util.ArrayList;

public class ProductImageAdapter extends ArrayAdapter<ProductImage> {
    Activity context;
    ArrayList<ProductImage> productImages;
    int layoutResource;

    public ProductImageAdapter(Activity context, int resource, ArrayList<ProductImage> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.productImages = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.layoutResource,null);
        TextView imageId = row.findViewById(R.id.txt_row_show_idProductImage);
        imageId.setText(productImages.get(position).getId().toString());
        TextView productId = row.findViewById(R.id.txt_row_show_proIdProductImage);
        productId.setText(productImages.get(position).getProductId().toString());

        ImageView image = row.findViewById(R.id.imgeViewAdProductImage);
        Glide.with(context)
                .load(productImages.get(position).getImage())
                .into(image);
        return row;
    }
}
