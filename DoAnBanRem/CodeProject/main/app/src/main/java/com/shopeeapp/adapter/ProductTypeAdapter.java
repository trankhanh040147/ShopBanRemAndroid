package com.shopeeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.bumptech.glide.Glide;
import com.shopeeapp.R;
import com.shopeeapp.entity.ProductType;

public class ProductTypeAdapter extends BaseAdapter {
    private List<ProductType> productTypes;
    private LayoutInflater inflater;
    private Context context;

    public ProductTypeAdapter(Context context, List<ProductType> productTypes) {
        inflater = LayoutInflater.from(context);
        this.productTypes = productTypes;
        this.context = context;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    public void setInflater(LayoutInflater inflater) {
        this.inflater = inflater;
    }

    public List<ProductType> getProductTypes() {
        return productTypes;
    }

    public void setProductTypes(List<ProductType> productTypes) {
        this.productTypes = productTypes;
    }

    @Override
    public int getCount() {
        return productTypes == null ? 0 : productTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return productTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productTypes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductTypeView productView;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.product_item, parent, false);

            productView = new ProductTypeView();
            productView.ibtnProduct = convertView.findViewById(R.id.ibtnProduct);
            productView.tv_product_name = convertView.findViewById(R.id.tv_product_name);
            productView.iv_special_image = convertView.findViewById(R.id.tvDiscount);

            convertView.setTag(productView);
        } else {
            productView = (ProductTypeView) convertView.getTag();
        }

        ProductType productType = productTypes.get(position);

        Glide.with(context)
                .load(productType.getImage())
                .into(productView.ibtnProduct);

        productView.iv_special_image.setVisibility(View.INVISIBLE);
        productView.tv_product_name.setText(productType.getName());

        return convertView;
    }

    private class ProductTypeView {
        private ImageView ibtnProduct;
        private TextView tv_product_name;
        private TextView iv_special_image;
    }
}
