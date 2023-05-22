package com.shopeeapp.adapter;

import android.annotation.SuppressLint;
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
import java.util.List;

import com.shopeeapp.R;
import com.shopeeapp.entity.MenuItem;
import com.shopeeapp.utilities.AccountSessionManager;

public class RecyleItemViewAdapter extends RecyclerView.Adapter<RecyleItemViewAdapter.ViewHolder> {
    private List<MenuItem> lstRecycleItem;

    public RecyleItemViewAdapter(List<MenuItem> lstRecycleItem) {
        this.lstRecycleItem = lstRecycleItem;
    }

    public List<MenuItem> getLstRecycleItem() {
        return lstRecycleItem;
    }

    public void setLstRecycleItem(ArrayList<MenuItem> lstRecycleItem) {
        this.lstRecycleItem = lstRecycleItem;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MenuItem recycleItem = lstRecycleItem.get(position);
        holder.item_title.setText(recycleItem.getTitle());
        holder.item_img.setImageResource(recycleItem.getDiscountImageID());
        if (AccountSessionManager.user == null && position == 3) {
            holder.itemView.setVisibility(View.GONE);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.menu_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {

        return lstRecycleItem == null ? 0 : lstRecycleItem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_title;
        public ImageView item_img;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_img = itemView.findViewById(R.id.menu_item_img);
            item_title = itemView.findViewById(R.id.menu_item_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Class cls = MenuItem.getLayout(item_title.getText().toString());
                    Intent intent = new Intent(v.getContext(), cls);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
