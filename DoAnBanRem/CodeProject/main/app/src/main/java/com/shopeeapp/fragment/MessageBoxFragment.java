package com.shopeeapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.shopeeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MessageBoxFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MessageBoxFragment extends DialogFragment {

    private String selectedLanguage;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public MessageBoxFragment() {
        // Required empty public constructor
    }

    public MessageBoxFragment(String selectedLanguage) {
        this.selectedLanguage = selectedLanguage;
    }

    public static MessageBoxFragment newInstance(String param1, String param2) {
        MessageBoxFragment fragment = new MessageBoxFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message_box, container, false);
        view.findViewById(R.id.btnCloseMsg).setOnClickListener(v -> {
            this.dismiss();
        });
        view.findViewById(R.id.btnCancelMsg).setOnClickListener(v -> {
            this.dismiss();
        });
        view.findViewById(R.id.btnOkMsg).setOnClickListener(v -> {
            ChangeLanguage();
            this.dismiss();
        });
        return view;
    }

    private void ChangeLanguage() {
        Toast.makeText(getContext(), this.selectedLanguage, Toast.LENGTH_SHORT).show();
    }
}