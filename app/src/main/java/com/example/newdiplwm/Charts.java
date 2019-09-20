package com.example.newdiplwm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

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
    PieChart pieChart;

    Session session;
    private static final String GAMENAME = "GAMENAME";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String PLAYTOTALTIME = "PLAYTOTALTIME";
    private static final String SCORE = "SCORE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charts);
        session = new Session(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.charttoolbar);
        toolbar.setTitle("Γράφημα Πίτας");
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        textView = (TextView) findViewById(R.id.tbl_TitleCharts);
        imageView = (ImageView) findViewById(R.id.imageViewCharts);
        String transitionName = getIntent().getStringExtra("image");
        int pic = getIntent().getIntExtra("lel",-12);
        int gameID = getIntent().getIntExtra("gameId",-1);


        textView.setText(transitionName);
        imageView.setImageResource(pic);



        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        UserGameStats allstats = userViewModel.getAllStatsByUserIdandGameId(session.getUserIdSession(),gameID);
        setUpChart(allstats);
    }

    public void setUpChart(UserGameStats allstats) {
        List<PieEntry> pieEntries = new ArrayList<>();

            pieEntries.add(new PieEntry(allstats.statistic.getHit(), getResources().getString(R.string.hit)));
            pieEntries.add(new PieEntry(allstats.statistic.getMiss(),getResources().getString(R.string.miss)));

        PieDataSet pieDataSet = new PieDataSet(pieEntries, allstats.name);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(pieDataSet);
        data.setValueTextSize(18f);

        pieChart = (PieChart) findViewById(R.id.chart);

        pieChart.setData(data);
        pieChart.animateY(1000);
        pieChart.setHoleRadius(25f);
        pieChart.invalidate();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        pieChart.setVisibility(View.GONE);
    }
}

