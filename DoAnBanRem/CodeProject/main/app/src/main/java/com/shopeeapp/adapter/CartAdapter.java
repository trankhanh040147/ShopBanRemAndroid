package com.shopeeapp.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.shopeeapp.activity.CartDetail;
import com.shopeeapp.entity.Cart;
import com.shopeeapp.fragment.CartFragment;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public ArrayList<Cart> carts;
    private CartFragment context;
    public CartAdapter(ArrayList<Cart> carts, CartFragment context) {

        this.carts = carts;
        this.context = context;
    }

    public ArrayList<Cart> getCarts() {
        return carts;
    }

    public void setCarts(ArrayList<Cart> carts) {
        this.carts = carts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cart cart = carts.get(position);
        holder.cart = cart;
        Glide.with(context)
                .load(cart.getProduct().getImage())
                .into(holder.cartImage);
        holder.cartTitle.setText(cart.getProduct().getName());
        holder.cartQuantity.setText(String.valueOf(cart.getQuantity()));
        String status = cart.getStatus() == "Paid" ? "Đã thanh toán" : "Chưa thanh toán";
        holder.cartStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return carts == null ? 0 : carts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView cartImage;
        TextView cartTitle;
        TextView cartQuantity;
        TextView cartStatus;
        Cart cart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cartImage = itemView.findViewById(R.id.cartImage);
            cartTitle = itemView.findViewById(R.id.cartProductName);
            cartQuantity = itemView.findViewById(R.id.cartQuantity);
            cartStatus = itemView.findViewById(R.id.cartStatus);

            itemView.setOnClickListener(v -> {
                CartDetail.cart = cart;
                Intent intent = new Intent(itemView.getContext(), CartDetail.class);
                itemView.getContext().startActivity(intent);
            });
        }
    }
}
