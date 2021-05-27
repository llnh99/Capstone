package com.example.capstone;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;



public class ChartActivity extends AppCompatActivity {
    //private ActivityChartBinding binding;
    private BarChart barchart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        //binding = ActivityChartBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(2014,429));
        visitors.add(new BarEntry(2015,475));
        visitors.add(new BarEntry(2016,508));
        visitors.add(new BarEntry(2017,660));
        visitors.add(new BarEntry(2018,550));
        visitors.add(new BarEntry(2019,630));
        visitors.add(new BarEntry(2020,470));

        BarDataSet barDataSet = new BarDataSet(visitors,"Visiters");
        barDataSet.setColors(ColorTemplate.PASTEL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);

        barchart = findViewById(R.id.barchart);
        barchart.setFitBars(true);
        barchart.setData(barData);
        //binding.barchart.getDescription().setText("Bar Chart Example");
        barchart.animateY(2000);


    }

}
