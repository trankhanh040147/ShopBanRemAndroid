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
import com.shopeeapp.entity.User;
import com.shopeeapp.entity.User;

import java.util.ArrayList;

public class AdminUserAdapter extends ArrayAdapter<User> {
    Activity context;
    ArrayList<User> users;
    int layoutResource;

    public AdminUserAdapter(Activity context, int resource, ArrayList<User> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.users = objects;
        this.layoutResource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater=this.context.getLayoutInflater();
        View row=layoutInflater.inflate(this.layoutResource,null);
        TextView userId = row.findViewById(R.id.txt_row_show_idUser);
        userId.setText(users.get(position).getId().toString());
        TextView userName = row.findViewById(R.id.txt_row_show_nameUser);
        userName.setText(users.get(position).getFullname());
        return row;
    }
}
