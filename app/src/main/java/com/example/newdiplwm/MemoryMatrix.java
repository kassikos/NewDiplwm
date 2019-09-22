package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class MemoryMatrix extends AppCompatActivity implements MemoryMatrixEz.OnDataPass, MemoryMatrixAdv.OnDataPassAdv {



    private MaterialButton startbutton;

    private String menuDifficulty, currentDifficulty;
    private CountDownTimer nextRoundTimer;

    private ImageView exit ,replayTutorial;
    private LinearLayout logoLinear, textsLinear;

    private int totalhit = 0, totalmiss = 0, TotalRounds =0, RoundsCounter = 0, trueCounter=0, totalPoints=0;

    private boolean missPoints = false;

    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    int user_id = -1;
    int game_id = -1;


    private double totalspeed=0;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private MemoryMatrixViewModel memoryMatrixViewModel;

    private Timestamp startTime;
    private Timestamp endTime;

    private TextView textView, textMsg, textMsgTime;
    private MemoryMatrixEz memoryMatrixEz;
    private MemoryMatrixAdv memoryMatrixAdv;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_matrix);

        session = new Session(getApplicationContext());


        boolean test = session.getPlayAgainVideo();

        if (!test) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialMemoryMatrix");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            fm.popBackStack();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }

       // Fragment existingFragment = getSupportFragmentManager().findFragmentById(R.id.gridLayoutMatrix);

//        if (savedInstanceState != null)
//        {
//
//            menuDifficulty = savedInstanceState.getString(DIFFICULTY);
//            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
//            game_id = savedInstanceState.getInt(GAME_ID);
//            user_id = savedInstanceState.getInt(USER_ID);
//            totalhit = savedInstanceState.getInt(HIT);
//            totalmiss = savedInstanceState.getInt(MISS);
//            RoundsCounter = savedInstanceState.getInt(ROUNDS);
//            totalspeed = savedInstanceState.getDouble(SPEED);
//            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
//            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
//            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
//
//        }

        pointsHashMap.put("EASY",0);
        pointsHashMap.put("MEDIUM",5);
        pointsHashMap.put("ADVANCED",10);
        startbutton = findViewById(R.id.startButtonMatrix);
        textView = findViewById(R.id.textRoundsMatrix);
        exit = findViewById(R.id.ExitMMG);
        replayTutorial = findViewById(R.id.ReplayTutorialMMG);
        logoLinear = findViewById(R.id.imageLogoDisplayMMG);
        textsLinear = findViewById(R.id.textsMMG);
        textMsg = findViewById(R.id.msgMMG);
        textMsgTime = findViewById(R.id.msgMMG1);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        menuDifficulty = session.getModeSession();


        memoryMatrixViewModel = ViewModelProviders.of(this).get(MemoryMatrixViewModel.class);



        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoLinear.setVisibility(View.GONE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialMemoryMatrix");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                checkMode();
                startbutton.setVisibility(View.INVISIBLE);

           //     startbutton.setText(R.string.nextRound);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbackAndExitCode();
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
    public void onBackPressed()
    {
        onbackAndExitCode();
    }

    private void onbackAndExitCode(){
        if (memoryMatrixViewModel.getTimer()!=null)
        {
            memoryMatrixViewModel.getTimer().cancel();
        }

        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
        }
        if (RoundsCounter == 0)
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
            GameEvent gameEvent = new GameEvent(game_id, user_id, totalhit, totalmiss, 1, totalPoints, (double) totalhit / TotalRounds, totalspeed / RoundsCounter, totalPlayInSeconds, menuDifficulty, startTime, endTime);
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
            memoryMatrixEz =new MemoryMatrixEz();
            loadFragment(memoryMatrixEz);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            memoryMatrixAdv = new MemoryMatrixAdv(currentDifficulty);
            loadFragment(memoryMatrixAdv);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            memoryMatrixAdv = new MemoryMatrixAdv(currentDifficulty);
            loadFragment(memoryMatrixAdv);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;
            if (RoundsCounter <2 )
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                memoryMatrixEz =new MemoryMatrixEz();
                loadFragment(memoryMatrixEz);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                memoryMatrixAdv = new MemoryMatrixAdv(currentDifficulty);
                loadFragment(memoryMatrixAdv);
            }
        }
        else
        {
            TotalRounds = 5;
            if (RoundsCounter < 1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                memoryMatrixEz =new MemoryMatrixEz();
                loadFragment(memoryMatrixEz);
            }
            else if (RoundsCounter >=1 && RoundsCounter <= 2 )
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                memoryMatrixAdv = new MemoryMatrixAdv(currentDifficulty);
                loadFragment(memoryMatrixAdv);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                memoryMatrixAdv = new MemoryMatrixAdv(currentDifficulty);
                loadFragment(memoryMatrixAdv);
            }

        }
        textView.setText((RoundsCounter+1)+ "/" + TotalRounds);

    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(MemoryMatrix.this,R.raw.tutorial_memorymatrix,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialMemoryMatrix");
    }

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,MemoryMatrix.this,totalhit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "MemoryMatrixPopUp");
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
//        animPointsText.setText("+ " + currentPoints);
//        if (currentPoints == 0) {
//            animPointsText.setTextColor(Color.RED);
//        } else
//            animPointsText.setTextColor(Color.GREEN);
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
                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                }


            }

            @Override
            public void onFinish() {


                if (RoundsCounter == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                    disableReplayTut();
                }
                else
                {
                    enableReplayTut();
                    nextRoundTimer = null;
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    checkMode();
                }

            }
        }.start();
        disableReplayTut();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }


    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }


    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayoutMatrix, fragment);
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
       // startbutton.setVisibility(View.VISIBLE);

        if (RoundsCounter >= TotalRounds)
        {
            textsLinear.setVisibility(View.VISIBLE);
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id,user_id,totalhit,totalmiss,0,totalPoints,(double)totalhit/(totalhit+totalmiss),totalspeed/TotalRounds,totalPlayInSeconds,menuDifficulty,startTime,endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id,game_id);
            startbutton.setVisibility(View.INVISIBLE);
            shopPopUp();
        }
        else
        {
            nextRound();
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

