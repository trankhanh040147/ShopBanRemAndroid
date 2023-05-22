package com.shopeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.shopeeapp.R;
import com.shopeeapp.adapter.ProductAdapter;
import com.shopeeapp.dbhelper.ProductDbHelper;
import com.shopeeapp.entity.Product;
import com.shopeeapp.fragment.HomeFragment;

public class SearchResult extends AppCompatActivity {
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.chipGroupProductType)
    ChipGroup chipGroupProductType;
    @BindView(R.id.chipClothes)
    Chip chipClothes;
    @BindView(R.id.chipDrink)
    Chip chipDrinks;
    @BindView(R.id.chipFood)
    Chip chipFood;
    @BindView(R.id.chipElectronic)
    Chip chipElectronic;
    @BindView(R.id.chipFreshFood)
    Chip chipFreshFood;
    @BindView(R.id.chipFruit)
    Chip chipFruit;
    @BindView(R.id.chipOthers)
    Chip chipOthers;
    @BindView(R.id.chipAllProductTypes)
    Chip chipAllTypes;

    private ArrayList<String> selectedProductTypes = new ArrayList<>();
    private String txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        findViewById(R.id.btnBack).setOnClickListener(v -> {
            finish();
        });

        chipClothes.setOnCheckedChangeListener(this::setChipClothesOnCheckedChanged);
        chipFood.setOnCheckedChangeListener(this::setChipFoodOnCheckedChanged);
        chipFreshFood.setOnCheckedChangeListener(this::setChipFreshFoodOnCheckedChanged);
        chipFruit.setOnCheckedChangeListener(this::setChipFruitOnCheckedChanged);
        chipDrinks.setOnCheckedChangeListener(this::setChipDrinkOnCheckedChanged);
        chipElectronic.setOnCheckedChangeListener(this::setChipElectronicOnCheckedChanged);
        chipOthers.setOnCheckedChangeListener(this::setChipOthersOnCheckedChanged);
        chipAllTypes.setOnCheckedChangeListener(this::setChipAllTypesOnCheckedChanged);
        chipAllTypes.setChecked(true);

        this.txtSearch = getIntent().getStringExtra("search");
        long productTypeId = getIntent().getLongExtra(HomeFragment.PRODUCT_TYPE_ID, -1);
        if (this.txtSearch != null) {
            tvSearch.setText(txtSearch);
            getSearchResult();
        } else {
            tvSearch.setText("Xem sản phẩm theo loại");
            setShowProductByTypeId(productTypeId);
        }
    }

    private void setShowProductByTypeId(long productTypeId) {
        int typeId = (int) productTypeId;
        if (productTypeId != -1) {
            switch (typeId) {
                case 1:
                    chipClothes.setChecked(true);
                    break;
                case 2:
                    chipFood.setChecked(true);
                    break;
                case 3:
                    chipFruit.setChecked(true);
                    break;
                case 4:
                    chipDrinks.setChecked(true);
                    break;
                case 5:
                    chipElectronic.setChecked(true);
                    break;
                case 6:
                    chipFreshFood.setChecked(true);
                case 7:
                    chipOthers.setChecked(true);
                default:
                    break;
            }
            chipAllTypes.setChecked(false);
            ProductDbHelper productDbHelper = new ProductDbHelper(this);
            ArrayList<Product> products = productDbHelper.getProductByTypeId(typeId);
            setProductsOnGridView(products);
        }
    }

    private void setChipClothesOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("1");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("1");
        getSearchResultByType();
    }

    private void setChipFoodOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("2");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("2");
        getSearchResultByType();
    }

    private void setChipFruitOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("3");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("3");
        getSearchResultByType();
    }

    private void setChipDrinkOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("4");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("4");
        getSearchResultByType();
    }

    private void setChipElectronicOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("5");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("5");
        getSearchResultByType();
    }

    private void setChipFreshFoodOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("6");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("6");
        getSearchResultByType();
    }

    private void setChipOthersOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (b) {
            selectedProductTypes.add("7");
            chipAllTypes.setChecked(false);
        } else
            selectedProductTypes.remove("7");
        getSearchResultByType();
    }

    private void setChipAllTypesOnCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (this.txtSearch == null)
            return;
        if (b) {
            chipFood.setChecked(false);
            chipClothes.setChecked(false);
            chipDrinks.setChecked(false);
            chipFreshFood.setChecked(false);
            chipFruit.setChecked(false);
            chipElectronic.setChecked(false);
            chipOthers.setChecked(false);
            selectedProductTypes = new ArrayList<>();
            for (int i = 1; i <= 7; i++)
                selectedProductTypes.add(String.valueOf(i));
            getSearchResult();
        } else {
            selectedProductTypes.clear();
        }
        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        ArrayList<Product> products = productDbHelper.getFullSearchResult(txtSearch);
        setProductsOnGridView(products);
    }

    private void getSearchResultByType() {
        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        ArrayList<Product> products = productDbHelper.getProductByListTypeId(this.selectedProductTypes);
        setProductsOnGridView(products);
    }

    private void getSearchResult() {
        if (this.txtSearch == null)
            return;
        ProductDbHelper productDbHelper = new ProductDbHelper(this);
        ArrayList<Product> products = productDbHelper.getFullSearchResult(txtSearch);
        if (products == null)
            return;

        setProductsOnGridView(products);
    }

    private void setProductsOnGridView(ArrayList<Product> products) {
        TextView tvNumberProducts = findViewById(R.id.tvNumProducts);
        tvNumberProducts.setText(String.valueOf(products.size()));
        ProductAdapter adapter = new ProductAdapter(this, products);

        GridView gridView = findViewById(R.id.searchResult);
        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(this, ProductDetail.class);
            intent.putExtra(ProductDetail.PRODUCT_ID, adapter.getItemId(position));
            startActivity(intent);
        });
        gridView.setAdapter(adapter);
    }
}