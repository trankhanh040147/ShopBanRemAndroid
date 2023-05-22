package com.shopeeapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.bumptech.glide.Glide;
import com.shopeeapp.R;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.entity.Bill;
import com.shopeeapp.entity.Cart;
import com.shopeeapp.entity.Product;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.ViewHolder> {
    private ArrayList<Bill> bills;
    private View view;
    private Context context;

    public BillAdapter(ArrayList<Bill> bills, Context context) {
        this.bills = bills;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        this.view = inflater.inflate(R.layout.bill_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Bill bill = bills.get(position);
        Cart cart = bill.getCart();
        Integer productId = cart.getProductId();
        ProductDbHelper productDbHelper = new ProductDbHelper(context);
        Product product = productDbHelper.getProductById(productId);

        Glide.with(context)
                .load(product.getImage())
                .into(holder.productImage);

        holder.productName.setText(product.getName());
        holder.productPrice.setText(product.getPrice().toString() + "đ");
        holder.billQuantity.setText(cart.getQuantity().toString());
        holder.billTotalPrice.setText(bill.getPrice().toString());
        holder.billDeliveryAddress.setText(bill.getAddress());
        holder.billTime.setText(bill.getDate());
        holder.billDiscount.setText(bill.getDiscount().toString() + "%");
        String status = bill.getStatus();
        if (status.equals(Bill.BILL_UNPAID))
            holder.billStatus.setText("Chưa thanh toán");
        else if (status.equals(Bill.BILL_PAID))
            holder.billStatus.setText("Đã thanh toán");
    }

    @Override
    public int getItemCount() {
        return (bills == null) ? 0 : bills.size();
    }

    public ArrayList<Bill> getBills() {
        return bills;
    }

    public void setBills(ArrayList<Bill> bills) {
        this.bills = bills;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productPrice;
        TextView billQuantity;
        TextView billTotalPrice;
        TextView billDeliveryAddress;
        TextView billStatus;
        TextView billTime;
        TextView billDiscount;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            productImage = itemView.findViewById(R.id.billProductImage);
            productName = itemView.findViewById(R.id.billProductName);
            productPrice = itemView.findViewById(R.id.productPrice);
            billQuantity = itemView.findViewById(R.id.billQuantity);
            billTotalPrice = itemView.findViewById(R.id.billTotalPrice);
            billDeliveryAddress = itemView.findViewById(R.id.billDeliveryAddress);
            billStatus = itemView.findViewById(R.id.billStatus);
            billTime = itemView.findViewById(R.id.billTime);
            billDiscount = itemView.findViewById(R.id.billDiscount);
        }
    }
}
