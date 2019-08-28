package com.example.newdiplwm;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
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


public class Suitcase extends AppCompatActivity implements SuitcaseEz.onDataPassEz, SuitcaseAdv.onDataPassAdv{


    MaterialButton startbutton;
    private ImageView exit;

    private String menuDifficulty, currentDifficulty;
    private int totalhit = 0, totalmiss = 0, TotalRounds =0, trueCounter=0, totalPoints=0,RoundsCounter = 0;
    private int click=0;

    boolean missPoints = false;

    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();


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

    SuitcaseEz suitcaseEz;
    SuitcaseAdv suitcaseMed;
    SuitcaseAdv suitcaseAdv;

    private CountDownTimer shopPopUpTimer;
    private Session session;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suitcase);
        session = new Session(getApplicationContext());

        if (!session.getPlayAgainVideo() && RoundsCounter == 0) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialSuitcase");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }



        pointsHashMap.put("EASY",0);
        pointsHashMap.put("MEDIUM",5);
        pointsHashMap.put("ADVANCED",10);

        startbutton = findViewById(R.id.Suitcase_startButton);
        textView = findViewById(R.id.Suitcase_textRounds);
        exit = findViewById(R.id.ExitSuitcase);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        menuDifficulty = session.getModeSession();

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkMode();
                startbutton.setVisibility(View.INVISIBLE);
                startbutton.setText(R.string.nextRound);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (RoundsCounter == 0 || click ==0 )
                {
                    startTime = new Timestamp(System.currentTimeMillis());
                    endTime = new Timestamp(System.currentTimeMillis());
                    GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id, game_id);
                    finish();

                }
                else
                {

                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    GameEvent gameEvent = new GameEvent(game_id, user_id, totalhit, totalmiss, 1, totalPoints, (double) totalhit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id, game_id);
                    finish();

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (RoundsCounter == 0 || click ==0 )
        {
            startTime = new Timestamp(System.currentTimeMillis());
            endTime = new Timestamp(System.currentTimeMillis());
            GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }
        else
        {

            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, totalhit, totalmiss, 1, totalPoints, (double) totalhit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

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
            suitcaseEz =new SuitcaseEz();
            loadFragment(suitcaseEz);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            suitcaseAdv =new SuitcaseAdv(currentDifficulty);
            loadFragment(suitcaseAdv);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            suitcaseAdv =new SuitcaseAdv(currentDifficulty);
            loadFragment(suitcaseAdv);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;
            if (RoundsCounter <2 )
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                suitcaseEz =new SuitcaseEz();
                loadFragment(suitcaseEz);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                suitcaseMed =new SuitcaseAdv(currentDifficulty);
                loadFragment(suitcaseMed);
            }
        }
        else
        {
            TotalRounds = 5;
            if (RoundsCounter < 1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                suitcaseEz =new SuitcaseEz();
                loadFragment(suitcaseEz);
            }
            else if (RoundsCounter >=1 && RoundsCounter <= 2 )
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                suitcaseMed =new SuitcaseAdv(currentDifficulty);
                loadFragment(suitcaseMed);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                suitcaseAdv =new SuitcaseAdv(currentDifficulty);
                loadFragment(suitcaseAdv);
            }

        }

        RoundsCounter++;

        textView.setText((RoundsCounter)+ "/" + TotalRounds);

    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(Suitcase.this,R.raw.tutorial_suitcase,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialSuitcase");
    }

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,Suitcase.this,totalhit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "Suitcase");
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

    private void checkIfEnds()
    {
        //startbutton.setVisibility(View.VISIBLE);

        if (RoundsCounter >= TotalRounds)
        {


            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id,user_id,totalhit,totalmiss,0,totalPoints,(double)totalhit/(totalhit+totalmiss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id,game_id);


            shopPopUpTimer = new CountDownTimer(6000, 1000) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {

                    startbutton.setVisibility(View.INVISIBLE);

                    shopPopUp();

                }
            }.start();
        }
    }


    @Override
    public void onDataPass(int hit, int miss, double speedInSeconds, boolean misspoints, int truecounter) {

        click++;
        totalhit +=hit;
        totalmiss +=miss;
        totalspeed += speedInSeconds;
        missPoints = misspoints;
        trueCounter +=truecounter;
        countPoints();
        checkIfEnds();

    }
}
