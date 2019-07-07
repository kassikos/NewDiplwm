package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Charts extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;

    private UserViewModel userViewModel;

    private static final String GAMENAME = "GAMENAME";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String PLAYTOTALTIME = "PLAYTOTALTIME";
    private static final String SCORE = "SCORE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);

        textView = (TextView) findViewById(R.id.tbl_TitleCharts);
        imageView = (ImageView) findViewById(R.id.imageViewCharts);
        String transitionName = getIntent().getStringExtra("image");
        int pic = getIntent().getIntExtra("lel",-12);
//        int hit = getIntent().getIntExtra(HIT,-1);
//        int miss = getIntent().getIntExtra(MISS,-1);
//        int score = getIntent().getIntExtra(SCORE,-1);
//        double playtotalTime = getIntent().getDoubleExtra(PLAYTOTALTIME,-1);


        textView.setText(transitionName);
        imageView.setImageResource(pic);



          userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        List<UserGameStats> allstats = userViewModel.getAllStatsModel(1);
        setUpChart(allstats);
    }

    public void setUpChart(List<UserGameStats> allstats) {
        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < allstats.size(); i++) {
            pieEntries.add(new PieEntry(allstats.get(i).statistic.getHit(), allstats.get(i).name));
        }
        PieDataSet pieDataSet = new PieDataSet(pieEntries, "skata");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(pieDataSet);

        PieChart pieChart = (PieChart) findViewById(R.id.chart);

        pieChart.setData(data);
        pieChart.invalidate();
    }
}

