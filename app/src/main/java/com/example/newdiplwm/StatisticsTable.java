package com.example.newdiplwm;


import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import java.util.List;

public class StatisticsTable extends AppCompatActivity {

    private UserViewModel userViewModel;
    private int user_id;
    private int game_id;

    private Session session;
    private static final String GAMENAME = "GAMENAME";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String PLAYTOTALTIME = "PLAYTOTALTIME";

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_table);
        session = new Session(getApplicationContext());
        user_id = session.getUserIdSession();


        Toolbar toolbar = (Toolbar) findViewById(R.id.stattoolbar);
        toolbar.setTitle(getString(R.string.statistics,session.getUsernameSession()));
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view_stats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final StatisticAdapter statisticAdapter = new StatisticAdapter();


        recyclerView.setAdapter(statisticAdapter);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        List<UserGameStats> allstats = userViewModel.getAllStatsModel(user_id);
        statisticAdapter.setStatistics(allstats,this);


//        statisticAdapter.setOnClickListener(new StatisticAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(UserGameStats userGameStats) {
//                Toast.makeText(StatisticsTable.this, "to patisa "+userGameStats.name, Toast.LENGTH_SHORT).show();
//
//                String nickname= userGameStats.nickName;
//
////                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(StatisticsTable.this,nickname,"Stringaki");
////                Intent in = new Intent(StatisticsTable.this,Charts.class);
////                startActivity(in,activityOptionsCompat.toBundle());
//
////                Intent intent = new Intent(StatisticsTable.this, Charts.class);
////// Pass data object in the bundle and populate details activity.
////                intent.putExtra(GAMENAME,userGameStats.name);
////                intent.putExtra(HIT,userGameStats.statistic.getHit());
////                intent.putExtra(MISS,userGameStats.statistic.getMiss());
////                intent.putExtra(PLAYTOTALTIME,userGameStats.statistic.getPlayTotalTime());
//
//
//            }
//
//
//        });




        //prepei na φτιαξω ενα model για τα στατισρτικ γτ τωρα τα εχω ολα εμσα στος userview model


    }


}
