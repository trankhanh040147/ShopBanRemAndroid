package com.shopeeapp.activity.admin;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import com.shopeeapp.R;
import com.shopeeapp.dbhelper.BillDbHelper;
import com.shopeeapp.dbhelper.ProductTypeDbHelper;
import com.shopeeapp.dto.StatisDTO;
import com.shopeeapp.entity.ProductType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class StaticActivity extends AppCompatActivity {

    ImageView btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static);
        handleChart(handleStatis());

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StaticActivity.this, DashBoard.class);
                startActivity(intent);
            }
        });
    }

    private List<StatisDTO> handleStatis(){
        ProductTypeDbHelper productTypeDbHelper = new ProductTypeDbHelper(this);
        ArrayList<ProductType> productTypes = productTypeDbHelper.getAllProductTypes();

        BillDbHelper billDbHelper = new BillDbHelper(this);
        ArrayList<StatisDTO> statisQuantity = billDbHelper.statis();

        List<StatisDTO> statisDTOS = productTypes.stream().map(item -> new StatisDTO(item.getName())).collect(Collectors.toList());

        for(StatisDTO statisDTO : statisDTOS){
            for (StatisDTO item : statisQuantity){
                String temp1 = statisDTO.getName();
                String item2 = item.getName();
                if (statisDTO.getName().equals(item.getName())){
                    statisDTO.setQuantity(item.getQuantity());
                    break;
                }
                else statisDTO.setQuantity(0);
            }
        }

        return statisDTOS;
    }

    private void handleChart(List<StatisDTO> statisDTOS){


        PieChart pieChart = findViewById(R.id.pieChart);
        ArrayList<PieEntry> visitors = new ArrayList<>();

        for(StatisDTO statisDTO : statisDTOS){
            visitors.add(new PieEntry(statisDTO.getQuantity(), statisDTO.getName()));
        }
        PieDataSet pieDataSet = new PieDataSet(visitors, "Sales figures");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(Color.BLACK);
        pieDataSet.setValueTextSize(16f);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.getDescription().setEnabled(false);
        pieChart.setCenterText("Sales figures");
        pieChart.animate();
    }

}