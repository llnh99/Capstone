package com.example.capstone;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class RadarChartActivity extends AppCompatActivity {

    private Chart radarChart;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radar_chart);
        radarChart = findViewById(R.id.radarChart);

        ArrayList<RadarEntry> visitors = new ArrayList<>();
        visitors.add(new RadarEntry(420));
        visitors.add(new RadarEntry(475));
        visitors.add(new RadarEntry(508));
        visitors.add(new RadarEntry(660));
        visitors.add(new RadarEntry(550));
        visitors.add(new RadarEntry(470));

        RadarDataSet radarDataSet = new RadarDataSet(visitors,"visitors");
        radarDataSet.setColor(Color.BLUE);
        radarDataSet.setLineWidth(2f);
        radarDataSet.setValueTextColor(Color.BLUE);
        radarDataSet.setValueTextSize(14f);

        ArrayList<RadarEntry> visitors2 = new ArrayList<>();
        visitors2.add(new RadarEntry(100));
        visitors2.add(new RadarEntry(200));
        visitors2.add(new RadarEntry(800));
        visitors2.add(new RadarEntry(900));
        visitors2.add(new RadarEntry(500));
        visitors2.add(new RadarEntry(600));

        RadarDataSet radarDataSet2 = new RadarDataSet(visitors2,"visitors2");
        radarDataSet2.setColor(Color.RED);
        radarDataSet2.setLineWidth(2f);
        radarDataSet2.setValueTextColor(Color.RED);
        radarDataSet2.setValueTextSize(14f);

        RadarData radarData = new RadarData();
        radarData.addDataSet(radarDataSet);
        radarData.addDataSet(radarDataSet2);

        String[] labels = {"2014","2015","2016","2017","2018","2019"};

        XAxis xAxis = radarChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));

        radarChart.setData(radarData);
    }
}