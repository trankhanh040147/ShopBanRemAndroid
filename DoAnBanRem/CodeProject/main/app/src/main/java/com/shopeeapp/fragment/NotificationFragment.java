package com.shopeeapp.fragment;

import static com.shopeeapp.utilities.AccountSessionManager.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;
import com.shopeeapp.adapter.NotificationAdapter;
import com.shopeeapp.dbhelper.NotificationDbHelper;
import com.shopeeapp.entity.Notification;

public class NotificationFragment extends Fragment {

    @BindView(R.id.rvNotifications)
    RecyclerView rvNotifications;
    @BindView(R.id.noMoreNotifications)
    LinearLayout noMoreNotifications;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public NotificationFragment() {
        // Required empty public constructor
    }

    public static NotificationFragment newInstance(String param1, String param2) {
        NotificationFragment fragment = new NotificationFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        ButterKnife.bind(this, view);
        getUnreadNotifications();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnreadNotifications();
    }

    private void getUnreadNotifications() {
        ArrayList<Notification> notificationList = getAllNotifications();
        if (notificationList.size() > 0) {
            NotificationAdapter adapter = new NotificationAdapter(notificationList);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
            rvNotifications.setLayoutManager(manager);
            rvNotifications.setAdapter(adapter);
            noMoreNotifications.setVisibility(View.GONE);
            rvNotifications.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
                @Override
                public void onChildViewAttachedToWindow(@NonNull View view) {

                }

                @Override
                public void onChildViewDetachedFromWindow(@NonNull View view) {
                    if (adapter.getItemCount() == 0) {
                        rvNotifications.setVisibility(View.GONE);
                        noMoreNotifications.setVisibility(View.VISIBLE);
                    }
                }
            });
        } else {
            rvNotifications.setVisibility(View.GONE);
            noMoreNotifications.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<Notification> getAllNotifications() {
        ArrayList<Notification> notifications = new ArrayList<>();
        if (user == null) return notifications;

        NotificationDbHelper notificationDbHelper = new NotificationDbHelper(this.getContext());
        return notificationDbHelper.getAllNotifications(user.getId());
    }
}