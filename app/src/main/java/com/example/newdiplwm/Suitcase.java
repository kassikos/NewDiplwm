package com.example.newdiplwm;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class Suitcase extends AppCompatActivity implements SuitcaseEz.OnDataPass, SuitcaseAdv.OnDataPassAdv{


    MaterialButton startbutton;
    int RoundsCounter = 0;
    String menuDifficulty;
    String currentDifficulty;

    int totalhit = 0;
    int totalmiss = 0;
    int TotalRounds =0 ;
    boolean missPoints = false;
    int trueCounter=0;
    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();
    int totalPoints=0;

    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String ROUNDS = "ROUNDS";
    private static final String SPEED = "SPEED";
    private static final String TRUECOUNTER = "TRUECOUNTER";
    private static final String TOTALPOINTS = "TOTALPOINTS";
    private static final String MISSPOINTS = "MISSPOINTS";
    int user_id = -1;
    int game_id = -1;


    private double totalspeed=0;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private Timestamp startTime;
    private Timestamp endTime;

    private TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitcase);



        pointsHashMap.put("EASY",0);
        pointsHashMap.put("MEDIUM",5);
        pointsHashMap.put("ADVANCED",10);
        startbutton = findViewById(R.id.Suitcase_startButton);
        textView = findViewById(R.id.Suitcase_textRounds);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkMode();
                startbutton.setVisibility(View.INVISIBLE);

            }
        });

    }


    public void checkMode()
    {
        if (RoundsCounter == 0)
        {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            SuitcaseEz a =new SuitcaseEz();
            loadFragment(a);
        }
//        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
//        {
//            currentDifficulty = menuDifficulty;
//            TotalRounds = 3;
//            loadFragment(new MemoryMatrixAdv(currentDifficulty));
//        }
//        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
//        {
//            currentDifficulty = menuDifficulty;
//            TotalRounds = 3;
//            loadFragment(new MemoryMatrixAdv(currentDifficulty));
//        }
//        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
//        {
//            TotalRounds = 4;
//            if (RoundsCounter <2 )
//            {
//                currentDifficulty = getResources().getString(R.string.easyValue);
//                MemoryMatrixEz a =new MemoryMatrixEz();
//                loadFragment(a);
//            }
//            else
//            {
//                currentDifficulty = getResources().getString(R.string.mediumValue);
//                loadFragment(new MemoryMatrixAdv(currentDifficulty));
//            }
//        }
//        else
//        {
//            TotalRounds = 5;
//            if (RoundsCounter < 1)
//            {
//                currentDifficulty = getResources().getString(R.string.easyValue);
//                MemoryMatrixEz a =new MemoryMatrixEz();
//                loadFragment(a);
//            }
//            else if (RoundsCounter >=1 && RoundsCounter <= 2 )
//            {
//                currentDifficulty = getResources().getString(R.string.mediumValue);
//                loadFragment(new MemoryMatrixAdv(currentDifficulty));
//            }
//            else
//            {
//                currentDifficulty = getResources().getString(R.string.advancedValue);
//                loadFragment(new MemoryMatrixAdv(currentDifficulty));
//            }
//
//        }
        textView.setText((RoundsCounter+1)+ "/" + TotalRounds);

    }

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,Suitcase.this,totalhit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "rockpaperScissor");
    }



    private void countPoints()
    {

        int currentPoints=0;

        if (!missPoints && trueCounter == 1)
        {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if(!missPoints && trueCounter == 2){
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if (!missPoints && trueCounter >= 3)
        {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
        }
        else if (missPoints)
        {
            currentPoints +=0;
            trueCounter = 0;
        }
        totalPoints += currentPoints;
    }


    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.Suitcase_FragmentLayout, fragment);
        fragmentTransaction.commit(); // save the changes
    }

    @Override
    public void onDataPass(int rounds,int hit,int miss,double speedInSeconds,boolean missp,int truec) {
        RoundsCounter +=rounds;
        totalhit +=hit;
        totalmiss +=miss;
        totalspeed += speedInSeconds;
        missPoints = missp;
        trueCounter +=truec;
        countPoints();
        checkIfEnds();

    }

    private void checkIfEnds()
    {
        startbutton.setVisibility(View.VISIBLE);

        if (RoundsCounter >= TotalRounds)
        {
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id,user_id,totalhit,totalmiss,-1,totalPoints,(double)totalhit/(totalhit+totalmiss),totalspeed/TotalRounds,totalPlayInSeconds,menuDifficulty,startTime,endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id,game_id);
            shopPopUp();
        }
    }

    @Override
    public void onDataPassAdv(int round, int hit, int miss, double speedinSeconds, boolean misspoints, int truecounter) {
        RoundsCounter +=round;
        totalhit +=hit;
        totalmiss +=miss;
        totalspeed += speedinSeconds;
        missPoints = misspoints;
        trueCounter +=truecounter;
        countPoints();
        checkIfEnds();

    }




}
