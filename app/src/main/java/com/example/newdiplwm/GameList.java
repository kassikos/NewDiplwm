package com.example.newdiplwm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.util.ArrayList;
import java.util.List;

public class GameList extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener {

    private  GameViewModel gameViewModel;
    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    List<Game> allgamesList = new ArrayList<Game>();
    private boolean mToolBarNavigationListenerIsRegistered = false, rememberMe;
    private String preferenceDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        session = new Session(getApplicationContext());
        setupPreferences();

        if (GameHelper.sizeOfMap() <= 0)
        {
            GameHelper.initHasmap();
        }


//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbarLayout.setTitle("username");
//



        Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(session.getUsernameSession());
        setSupportActionBar(toolbar);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
        }
        else
        {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

        }

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbar.setTitle("username");




        final GameAdapter gameAdapter = new GameAdapter();
        recyclerView.setAdapter(gameAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.gameList);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,toolbar,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        allgamesList = gameViewModel.loadAllGames();

        gameAdapter.setGames(allgamesList);



        gameAdapter.setOnClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Toast.makeText(GameList.this, "Επέλεξες: "+GameHelper.getGreekName(game.getName()), Toast.LENGTH_SHORT).show();


                session.setModeSession(preferenceDifficulty);
                if (game.getName().equals("Rock"))
                {

                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,RockPaperScissors.class);
                    startActivity(intent);
                }
                else if (game.getName().equals("Calcution"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,ScalingGame.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("MemoryMatrix"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,MemoryMatrix.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("ObjectSelector"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,ObjectSelector.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("OrderGame"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,OrderGame.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("Suitcase"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,Suitcase.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("ShadowGame"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,ShadowGame.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("PersonPickGame"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,AudioPersonPick.class);
                    startActivity(intent);

                }
                else if (game.getName().equals("SoundWord"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,SoundWord.class);

                    startActivity(intent);

                }
                else if (game.getName().equals("SoundImage"))
                {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this,SoundImage.class);
                    startActivity(intent);

                }
                //finishAffinity();

        }
        });

    }


    private void enableViews(boolean enable) {

        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if(enable) {
            //You may not want to open the drawer on swipe from the left in this case
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if(!mToolBarNavigationListenerIsRegistered) {
                actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            //You must regain the power of swipe for the drawer.
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            actionBarDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }
        
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ΕΞΟΔΟΣ");
        builder.setMessage("Έξοδος από την εφαρμογή;");
        builder.setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                 finishAffinity();
                if (isTaskRoot())
                {
                    moveTaskToBack(true);
                }

            }
        });

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } //else {
//            super.onBackPressed();
//        }

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(22);
                btnPositive.setTextColor(getResources().getColor(R.color.black));

                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setTextSize(28);
                textView.setTextColor(getResources().getColor(R.color.black));

            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }


    private void setupPreferences()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey),getString(R.string.mediumValue));
        rememberMe = sharedPreferences.getBoolean("rememberME",false);
        session.setRememberme(rememberMe);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.equals(getString(R.string.difficultyKey)))
        {
            preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey),getString(R.string.mediumValue));
        }
        if (key.equals("rememberME"))
        {
            rememberMe = sharedPreferences.getBoolean("rememberME",false);
        }
        if (key.equals("testing"))
        {
            session.setPlayAgainVideo(sharedPreferences.getBoolean("testing",false));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId())
        {
            case R.id.nav_settings:
                Intent intentSettings = new Intent(this,Settings.class);
                startActivity(intentSettings);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_statistics:
                Intent intentStats = new Intent(this,StatisticsTable.class);
                startActivity(intentStats);
                Animatoo.animateDiagonal(GameList.this);
                break;
            case R.id.nav_chart:
                Intent intentChart = new Intent(this,MenuChart.class);
                startActivity(intentChart);
                break;
            case R.id.nav_questionnaire:
                Intent intentQuestionnaire = new Intent(this,Questionnaire.class);
                startActivity(intentQuestionnaire);
                break;
            case R.id.nav_logout:
                session.setRememberme(false);
                finishAndRemoveTask();
                Intent intent = new Intent(GameList.this,Login.class);
                startActivity(intent);
                break;
        }



        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}