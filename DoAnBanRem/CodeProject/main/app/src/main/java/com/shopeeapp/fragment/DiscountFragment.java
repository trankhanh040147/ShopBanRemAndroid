package com.shopeeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.shopeeapp.R;
import com.shopeeapp.activity.ProductDetail;
import com.shopeeapp.adapter.ProductAdapter;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.entity.Product;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscountFragment extends Fragment {

    @BindView(R.id.layoutSearchDiscount)
    LinearLayout layoutSearchDiscount;
    @BindView(R.id.txtSearchDiscount)
    SearchView txtSearch;
    @BindView(R.id.gvSponsor)
    GridView gvSponsor;
    @BindView(R.id.gvSearchResult)
    GridView gvSearchResult;
    @BindView(R.id.tvNumDiscount)
    TextView tvNumDiscount;
    @BindView(R.id.layoutSponsor)
    LinearLayout layoutSponsor;
    @BindView(R.id.discountGroup)
    ChipGroup discountGroup;
    @BindView(R.id.discount10)
    Chip discount10;
    @BindView(R.id.discount20)
    Chip discount20;
    @BindView(R.id.discount30)
    Chip discount30;
    @BindView(R.id.discountX)
    Chip discountOther;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public DiscountFragment() {
        // Required empty public constructor
    }

    @NotNull
    public static DiscountFragment newInstance(String param1, String param2) {
        DiscountFragment fragment = new DiscountFragment();
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_discount, container, false);

        ButterKnife.bind(this, view);

        discountGroup.setOnCheckedChangeListener(new ChipGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(ChipGroup group, int checkedId) {
                ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
                String discountValue = "-1";
                if (discount10.isChecked())
                    discountValue = "10";
                else if (discount20.isChecked())
                    discountValue = "20";
                else if (discount30.isChecked())
                    discountValue = "30";
                ArrayList<Product> products = productDbHelper.getDiscountProductByName(txtSearch.getQuery().toString(), discountValue);
                if (products != null) {
                    setSearchResult(products);
                    layoutSponsor.setVisibility(View.GONE);
                } else
                    layoutSearchDiscount.setVisibility(View.GONE);
            }
        });

        // List Product Sponsor
        getTopPromo();

        //txtSearch
        layoutSearchDiscount.setVisibility(View.GONE);
        getSearchResult();

        return view;
    }

    private void getTopPromo() {
        ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
        List<Product> productList = productDbHelper.getPromoProducts(4);
        ProductAdapter gv_adapter = new ProductAdapter(getContext(), productList);
        gvSponsor.setAdapter(gv_adapter);
    }


    private void getSearchResult() {
        txtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ProductDbHelper productDbHelper = new ProductDbHelper(getContext());
                String discountValue = "-1";
                if (discount10.isChecked())
                    discountValue = "10";
                else if (discount20.isChecked())
                    discountValue = "20";
                else if (discount30.isChecked())
                    discountValue = "30";
                ArrayList<Product> products = productDbHelper.getDiscountProductByName(txtSearch.getQuery().toString(), discountValue);
                if (products != null) {
                    setSearchResult(products);
                    layoutSponsor.setVisibility(View.GONE);
                } else
                    layoutSearchDiscount.setVisibility(View.GONE);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void setSearchResult(@NotNull ArrayList<Product> products) {
        layoutSearchDiscount.setVisibility(View.VISIBLE);
        tvNumDiscount.setText(String.valueOf(products.size()));

        ProductAdapter adapter = new ProductAdapter(getContext(), products);
        gvSearchResult.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getContext(), ProductDetail.class);
            intent.putExtra(ProductDetail.PRODUCT_ID, adapter.getItemId(position));
            startActivity(intent);
        });
        gvSearchResult.setAdapter(adapter);
    }
}