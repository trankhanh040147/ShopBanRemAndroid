package com.shopeeapp.fragment;

import static com.shopeeapp.utilities.AccountSessionManager.account;
import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;
import com.shopeeapp.adapter.RecyleItemViewAdapter;
import com.shopeeapp.entity.MenuItem;
import com.shopeeapp.utilities.ImageConverter;

public class MenuFragment extends Fragment {
    @BindView(R.id.menuAvatar)
    ImageView menuAvatar;
    @BindView(R.id.menuFullName)
    TextView menuFullName;
    @BindView(R.id.menuUsername)
    TextView menuUsername;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MenuFragment() {
        // Required empty public constructor
    }

    @NotNull
    public static MenuFragment newInstance(String param1, String param2) {
        MenuFragment fragment = new MenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkLogin();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        ButterKnife.bind(this, view);

        setMenuItemSection(view);
        checkLogin();

        return view;
    }

    private void checkLogin() {
        if (user != null)
            setLoggedInUserInfo();
        else
            setUnknownAccount();
    }

    private void setLoggedInUserInfo() {
//        menuAvatar.setImageBitmap(user.getAvatar());
        menuFullName.setText(user.getFullname());
        menuUsername.setText("@" + account.getUsername());
    }

    private void setUnknownAccount() {
        menuAvatar.setImageBitmap(ImageConverter.resource2Bitmap(R.drawable.ic_account));
        menuFullName.setText(R.string.action_sign_in);
        menuUsername.setText(R.string.username);
    }

    private void setMenuItemSection(@NotNull View view) {
        List<MenuItem> lstMenuItems = MenuItem.createListMenuItem();
        RecyleItemViewAdapter adapter = new RecyleItemViewAdapter(lstMenuItems);
        RecyclerView rv_account = view.findViewById(R.id.rv_menu_item);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rv_account.setLayoutManager(layoutManager);
        rv_account.setAdapter(adapter);
    }
}