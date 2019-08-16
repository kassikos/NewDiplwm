package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class MemoryMatrix extends AppCompatActivity implements MemoryMatrixEz.OnDataPass, MemoryMatrixAdv.OnDataPassAdv {



    MaterialButton startbutton;

    String menuDifficulty, currentDifficulty;

    ImageView exit;

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

    private TextView textView;
    private MemoryMatrixEz memoryMatrixEz;
    private MemoryMatrixAdv memoryMatrixAdv;
    private Session session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_matrix);

        session = new Session(getApplicationContext());

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

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        menuDifficulty = session.getModeSession();


        memoryMatrixViewModel = ViewModelProviders.of(this).get(MemoryMatrixViewModel.class);



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
                if (memoryMatrixViewModel.getTimer()!=null)
                {
                    memoryMatrixViewModel.getTimer().cancel();
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
        });
    }

    @Override
    public void onBackPressed()
    {

        if (memoryMatrixViewModel.getTimer()!=null)
        {
            memoryMatrixViewModel.getTimer().cancel();
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

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,MemoryMatrix.this,totalhit,totalPoints);
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
        startbutton.setVisibility(View.VISIBLE);

        if (RoundsCounter >= TotalRounds)
        {
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id,user_id,totalhit,totalmiss,0,totalPoints,(double)totalhit/(totalhit+totalmiss),totalspeed/TotalRounds,totalPlayInSeconds,menuDifficulty,startTime,endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id,game_id);
            startbutton.setVisibility(View.INVISIBLE);
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

//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString(DIFFICULTY,menuDifficulty);
//        outState.putString(CURRENTDIFFICULTY,currentDifficulty);
//        outState.putInt(GAME_ID,game_id);
//        outState.putInt(USER_ID,user_id);
//        outState.putInt(HIT,totalhit);
//        outState.putInt(MISS,totalmiss);
//        outState.putInt(ROUNDS,RoundsCounter);
//        outState.putDouble(SPEED,totalspeed);
//        outState.putInt(TRUECOUNTER,trueCounter);
//        outState.putInt(TOTALPOINTS,totalPoints);
//        outState.putBoolean(MISSPOINTS,missPoints);
//    }
}

