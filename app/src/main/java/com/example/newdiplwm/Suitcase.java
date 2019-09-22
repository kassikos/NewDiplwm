package com.example.newdiplwm;


import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageView exit , replayTutorial;
    private LinearLayout logoLinear, textsLinear;

    private String menuDifficulty, currentDifficulty;
    private int totalhit = 0, totalmiss = 0, TotalRounds =0, trueCounter=0, totalPoints=0,RoundsCounter = 0;
    private int click=0;

    boolean missPoints = false;

    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    int user_id = -1;
    int game_id = -1;


    private double totalspeed=0;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private Timestamp startTime;
    private Timestamp endTime;

    private TextView textView, textMsg , textMsgTime;

    SuitcaseEz suitcaseEz;
    SuitcaseAdv suitcaseMed;
    SuitcaseAdv suitcaseAdv;

    private CountDownTimer shopPopUpTimer, nextRoundTimer;
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
        replayTutorial = findViewById(R.id.ReplayTutorialSuitcase);
        logoLinear = findViewById(R.id.imageLogoDisplaySuitcase);
        textsLinear = findViewById(R.id.textsSuitcase);
        textMsg = findViewById(R.id.msgSuitcase);
        textMsgTime = findViewById(R.id.msgSuitcase1);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        menuDifficulty = session.getModeSession();

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoLinear.setVisibility(View.GONE);

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

                if (nextRoundTimer != null)
                {
                    nextRoundTimer.cancel();
                }

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

        replayTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTutorialPopUp();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
        }
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
       // startbutton.setVisibility(View.INVISIBLE);
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




    private void countPoints() {

        int currentPoints = 0;

        if (!missPoints && trueCounter == 1) {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win);
        } else if (!missPoints && trueCounter == 2) {
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win1);
        } else if (!missPoints && trueCounter >= 3) {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win2);
        } else if (missPoints) {
            currentPoints += 0;
            trueCounter = 0;
            missPoints = false;
            textMsg.setText(R.string.lose);
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
            textsLinear.setVisibility(View.VISIBLE);
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
        else
        {
            nextRound();
        }

    }


    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);

        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {


                if (RoundsCounter == TotalRounds)
                {
                    textMsgTime.setText("");
                }
                else
                {
                    textMsgTime.setText("Επομενος γυρος σε: "+l/1000);
                }


            }

            @Override
            public void onFinish() {


                if (RoundsCounter == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    nextRoundTimer = null;
                    checkMode();
                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

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
