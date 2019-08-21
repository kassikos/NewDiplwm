package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class MenuChart extends AppCompatActivity {
    private BarChart barChart;
    private UserViewModel userViewModel;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_chart);
        session = new Session(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.menucharttoolbar);
        toolbar.setTitle("BarChart");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        List<UserGameStats> allstats = userViewModel.getAllStatsModel(session.getUserIdSession());
        setUpChart(allstats);


    }


    public void setUpChart(List<UserGameStats> allstats) {
        List<BarEntry> barEntries = new ArrayList<>();

        for (int i=0; i<allstats.size();i++)
        {
            barEntries.add(new BarEntry(i , (int) allstats.get(i).statistic.getPlayTotalTime(),R.drawable.arrowdown24));
        }

        BarDataSet barDataSet = new BarDataSet(barEntries, getString(R.string.SecPerGame));

        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setDrawValues(true);
        barDataSet.setDrawIcons(true);
        BarData data = new BarData(barDataSet);



        barChart = (BarChart) findViewById(R.id.barChart);
        barChart.setFitsSystemWindows(true);
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        for (UserGameStats userGameStats:allstats)
        {
            xAxisLabel.add(userGameStats.name);
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        });
        xAxis.setTextSize((float) 15);

        barChart.setData(data);
        barChart.setFitBars(true);
        barChart.animateY(1000);
        barChart.invalidate();
    }

}
