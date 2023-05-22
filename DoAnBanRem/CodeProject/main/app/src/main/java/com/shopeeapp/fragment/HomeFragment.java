package com.shopeeapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import com.shopeeapp.R;
import com.shopeeapp.activity.ProductDetail;
import com.shopeeapp.activity.SearchResult;
import com.shopeeapp.adapter.ProductAdapter;
import com.shopeeapp.adapter.ProductTypeAdapter;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.entity.Product;
import com.shopeeapp.entity.ProductType;

public class HomeFragment extends Fragment {

    public static final String PROMO_PRODUCT_ID = "productId";
    public static final String PRODUCT_TYPE_ID = "productType";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    @NotNull
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //Search
        SearchView searchView = view.findViewById(R.id.tvSearch);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getContext(), SearchResult.class);
                intent.putExtra("search", searchView.getQuery().toString());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ProductDbHelper productDbHelper = new ProductDbHelper(this.getContext());
        //Promos
        setPromoItem(view, productDbHelper);
        //Products
        setProductItem(view, productDbHelper);

        return view;
    }

    private void setPromoItem(@NotNull View view, @NotNull ProductDbHelper productDbHelper) {
        List<Product> promoProducts = productDbHelper.getPromoProducts(4);
        ProductAdapter productAdapter = new ProductAdapter(getContext(), promoProducts);
        GridView gv_promo = view.findViewById(R.id.homePromo);
        gv_promo.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this.getContext(), ProductDetail.class);
            intent.putExtra(PROMO_PRODUCT_ID, productAdapter.getItemId(position));
            startActivity(intent);
        });
        gv_promo.setAdapter(productAdapter);
    }

    private void setProductItem(@NotNull View view, ProductDbHelper productDbHelper) {
        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this.getContext());
        List<ProductType> productTypes = productTypeDbHelper.getAllProductTypes();
        ProductTypeAdapter productTypeAdapter = new ProductTypeAdapter(getContext(), productTypes);
        GridView gv_product = view.findViewById(R.id.homeProduct);
        gv_product.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this.getContext(), SearchResult.class);
            intent.putExtra(PRODUCT_TYPE_ID, productTypeAdapter.getItemId(position));
            startActivity(intent);
        });
        gv_product.setAdapter(productTypeAdapter);
    }
}