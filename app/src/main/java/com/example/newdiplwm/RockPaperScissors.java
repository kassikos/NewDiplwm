package com.example.newdiplwm;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.SparseIntArray;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class RockPaperScissors extends AppCompatActivity implements View.OnClickListener {


    private static final String MISSPOINTS = "MISSPOINTS";
    private static final String HIT = "HIT";
    private static final String MISS = "MISS";
    private static final String TOTALPOINTS = "TOTALPOINTS";
    private static final String TRUECOUNTER = "TRUECOUNTER";
    private static final String TOTALSPEED = "TOTALSPEED";
    private static final String STARTTIME = "STARTTIME";
    private static final String ENDTIME = "ENDTIME";
    private static final String STARTSPEED = "STARTSPEED";
    private static final String ENDSPEED = "ENDSPEED";
    private static final String ROUNDSCOUNTER = "ROUNDSCOUNTER";
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String CLOCK = "CLOCK";
    private static final String CLICK = "CLICK";
    private static final String GAMEINIT = "GAMEINIT";
    private static final String IMAGES = "IMAGES";
    private static final String SPARSEARRAY = "SPARSEARRAY";
    private static final String MODE = "MODE";
    private static final String CURRENTROUND = "CURRENTROUND";
    private static final String MSGHELPER = "MSGHELPER";
    private static final String NEXTROUNDTIMER = "NEXTROUNDTIMER";



    private long mTimeLeftInMillis = 0 , timeLeftInMillisNextRound = 0;
    private ImageView imageView1 , imageView2 , exit , replayTutorial;
    private LinearLayout logoLinear , textsLinear;

    private MaterialButton startButton;

    private TextView textRounds, textQuestion, advancedTextTimer, animPointsText , textMsg , textMsgTime;
    private String msgHelper;
    private ArrayList<Integer> images = new ArrayList<>();
    private int currentRound=0 , mode = -1,TotalRounds = 0;

    private SparseIntArray imageViewImage = new SparseIntArray(2);
    private String currentDifficulty, menuDifficulty;

    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();
    private CountDownTimer Timer, Advancedtimer , nextRoundTimer;

    int vibeduration = 1000, click = 0;


    private boolean gameinit = false;
    boolean missPoints = false;
    int totalPoints = 0, trueCounter = 0;

    int hit = 0 , miss = 0;

    int game_id = -1, user_id = -1;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private Vibrator vibe;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_paper_scissors);


        session = new Session(getApplicationContext());

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);


        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        if (savedInstanceState != null)
        {

            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            currentRound = savedInstanceState.getInt(CURRENTROUND);
            mode = savedInstanceState.getInt(MODE);
            gameinit = savedInstanceState.getBoolean(GAMEINIT);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            hit = savedInstanceState.getInt(HIT);
            miss = savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);
            imageViewImage = (SparseIntArray) savedInstanceState.getParcelable(SPARSEARRAY);
            images = savedInstanceState.getIntegerArrayList(IMAGES);
            click = savedInstanceState.getInt(CLICK);
            msgHelper = savedInstanceState.getString(MSGHELPER);
            timeLeftInMillisNextRound = savedInstanceState.getLong(NEXTROUNDTIMER);
            assignAllbuttons();
            unclickable();
            startButton.setVisibility(View.INVISIBLE);

            if (gameinit)
            {
                logoLinear.setVisibility(View.GONE);
                clickable();
                textRounds.setText(currentRound + "/" + TotalRounds);
                if (mode == 0)
                {
                    textQuestion.setText(R.string.WinMode);
                }
                else
                {
                    textQuestion.setText(R.string.LoseMode);
                }
                for (int i=0;i<imageViewImage.size();i++)
                {
                    ImageView imageView = findViewById(imageViewImage.keyAt(i));
                    imageView.setImageResource(imageViewImage.valueAt(i));
                }
                if (mTimeLeftInMillis > 1000)
                {
                    Advancedtimer = userViewModel.getTimer();
                    Advancedtimer.cancel();

                    Advancedtimer = new CountDownTimer(mTimeLeftInMillis,1000) {
                        @Override
                        public void onTick(long l) {
                            mTimeLeftInMillis = l;
                            advancedTextTimer.setText("Έχεις ακόμα: " + l / 1000);

                        }

                        @Override
                        public void onFinish() {

                            advancedTextTimer.setText(R.string.TimeOut);
                            missPoints = true;
                            startAnimation();
                            countPoints();
                            miss++;
                            vibe.vibrate(vibeduration);
                            clearScreen();

                            if (currentRound == TotalRounds) {
                                endTime = new Timestamp(System.currentTimeMillis());
                                long longTime = endTime.getTime() - startTime.getTime();
                                float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                                if (click == 0)
                                {
                                    totalspeed =10;
                                    click=1;
                                }
                                GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
                                gameEventViewModel.insertGameEvent(gameEvent);
                                userViewModel.updatestatsTest(user_id, game_id);
                                shopPopUp();
                            }

                        }
                    }.start();
                    userViewModel.saveTimer(Advancedtimer);
                }

            }else {

                if (currentRound == 0) {
                    startButton.setVisibility(View.VISIBLE);
                }

                    else {
                        if (timeLeftInMillisNextRound > 1000)
                        {
                            textsLinear.setVisibility(View.VISIBLE);
                            nextRoundTimer = userViewModel.getNextRoundTimer();
                            nextRoundTimer.cancel();
                            nextRoundTimer = new CountDownTimer(timeLeftInMillisNextRound,1000) {
                                @Override
                                public void onTick(long l) {
                                    timeLeftInMillisNextRound = l;

                                    if (currentRound == TotalRounds)
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

                                    timeLeftInMillisNextRound = 0;

                                    if (currentRound == TotalRounds)
                                    {
                                        textsLinear.setVisibility(View.INVISIBLE);
                                    }
                                    else
                                    {
                                        textMsgTime.setText("");
                                        textsLinear.setVisibility(View.INVISIBLE);
                                        gameinit = true;
                                        createRound();
                                    }

                                }
                            }.start();
                            userViewModel.setNextRoundTimer(nextRoundTimer);
                        }

                    logoLinear.setVisibility(View.GONE);
                    startButton.setVisibility(View.INVISIBLE);
                    textMsg.setText(msgHelper);
                    //startButton.setText(getResources().getString(R.string.nextRound));
                    textRounds.setText(currentRound + " / " + TotalRounds);
                    //textsLinear.setVisibility(View.VISIBLE);

                }
            }

        }
        else
        {

            game_id = session.getGameIdSession();
            user_id = session.getUserIdSession();
            menuDifficulty = session.getModeSession();
            assignAllbuttons();
            unclickable();
            loadImages();

        }


        if (!session.getPlayAgainVideo() && currentRound == 0) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("Tutorial");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameinit = true;
                logoLinear.setVisibility(View.GONE);
                createRound();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Advancedtimer != null)
                {
                    Advancedtimer.cancel();
                }
                if (currentRound == 0 || click ==0 )
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
                    if (startspeed == null || endspeed==null)
                    {
                        totalspeed +=0;
                    }
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss , 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
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
//                if(Advancedtimer != null)
//                {
//                    Advancedtimer.cancel();
//                }

            }
        });
    }


    @Override
    public void onBackPressed()
    {
        if(Advancedtimer != null)
        {
            Advancedtimer.cancel();
        }

        if (currentRound == 0 || click ==0 )
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
            if (startspeed == null || endspeed==null)
            {
                totalspeed +=0;
            }
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }

    private void createRound() {

        startButton.setVisibility(View.INVISIBLE);
        Random rand = new Random();
        Collections.shuffle(images);
        if (currentRound == 0) {
            startTime = new Timestamp(System.currentTimeMillis());
            mode = rand.nextInt(2);
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz();
        } else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMedium();
        } else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameAdv();
        } else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue))) {
            TotalRounds = 4;

            if (currentRound <= 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            } else {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium();
            }
        } else {
            TotalRounds = 5;
            if (currentRound < 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            } else if (currentRound >= 1 && currentRound <= 2) {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium();
            } else {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv();
            }
        }
        startspeed = new Timestamp(System.currentTimeMillis());

        currentRound++;

        textRounds.setText(currentRound + "/" + TotalRounds);
        clickable();

    }


    //0->win mode
    //1->lose mode
    private void displayGameEz(){
        imageView1.setImageResource(images.get(0));
        imageView2.setImageResource(images.get(1));

        imageViewImage.put(imageView1.getId(),images.get(0));
        imageViewImage.put(imageView2.getId(),images.get(1));


        if (mode == 0)
        {
            textQuestion.setText(R.string.WinMode);
        }
        else
        {
            textQuestion.setText(R.string.LoseMode);
        }
    }

    private void displayGameMedium(){
        Random rand = new Random();
        mode = rand.nextInt(2);
        imageView1.setImageResource(images.get(0));
        imageView2.setImageResource(images.get(1));

        imageViewImage.put(imageView1.getId(),images.get(0));
        imageViewImage.put(imageView2.getId(),images.get(1));


        if (mode == 0)
        {
            textQuestion.setText(R.string.WinMode);
        }
        else
        {
            textQuestion.setText(R.string.LoseMode);
        }
    }
    private void displayGameAdv(){
        Random rand = new Random();
        mode = rand.nextInt(2);
        imageView1.setImageResource(images.get(0));
        imageView2.setImageResource(images.get(1));

        imageViewImage.put(imageView1.getId(),images.get(0));
        imageViewImage.put(imageView2.getId(),images.get(1));


        if (mode == 0)
        {
            textQuestion.setText(R.string.WinMode);
        }
        else
        {
            textQuestion.setText(R.string.LoseMode);
        }
        Advancedtimer = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis = l;
                advancedTextTimer.setText("Έχεις ακόμα: " + l / 1000);

            }

            @Override
            public void onFinish() {
                advancedTextTimer.setText(R.string.TimeOut);
                missPoints = true;
                startAnimation();
                countPoints();
                miss++;
                vibe.vibrate(vibeduration);

                if (currentRound == TotalRounds) {
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    if (click == 0)
                    {
                        totalspeed =10;
                        click=1;
                    }
                    GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id, game_id);
                    shopPopUp();
                }
                clearScreen();

            }
        }.start();
        userViewModel.saveTimer(Advancedtimer);

    }



    private void loadImages(){
        images.add(R.drawable.paper);
        images.add(R.drawable.rock);
        images.add(R.drawable.scissors);
    }

    private void assignAllbuttons(){
        imageView1 = findViewById(R.id.imageButton1);
        imageView2 = findViewById(R.id.imageButton2);
        exit = findViewById(R.id.ExitRPSG);
        replayTutorial = findViewById(R.id.ReplayTutorialRps);
        textRounds = findViewById(R.id.textRounds);
        textQuestion = findViewById(R.id.textQuestion);
        advancedTextTimer = findViewById(R.id.textTimer);
        startButton = findViewById(R.id.startButton);
        animPointsText = (TextView) findViewById(R.id.myImageViewTextAnim);
        logoLinear = findViewById(R.id.imageLogoDisplay);
        textMsg = findViewById(R.id.msgRps);
        textMsgTime = findViewById(R.id.msgRps1);
        textsLinear = findViewById(R.id.textsRps);

        imageView1.setOnClickListener(this);
        imageView2.setOnClickListener(this);
    }


    private void clearScreen(){

        Timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {
                    advancedTextTimer.setText("");
                    Advancedtimer = null;

                }
                textQuestion.setText("");
                imageView1.setImageResource(0);
                imageView2.setImageResource(0);
//                startButton.setText(getResources().getString(R.string.nextRound));
//                startButton.setVisibility(View.VISIBLE);

                unclickable();

//                if (currentRound == TotalRounds)
//                {
//                    startButton.setVisibility(View.INVISIBLE);
//                }
            }
        }.start();
        nextRound();



    }
    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);

        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisNextRound = l;

                if (currentRound == TotalRounds)
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

                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                }
                else
                {
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    gameinit = true;
                    createRound();
                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }

    @Override
    public void onClick(View view) {
        unclickable();
        if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            Advancedtimer.cancel();
            advancedTextTimer.setText("");

        }
        textQuestion.setText("");

        click++;
        gameinit = false;
        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        if (!(imageViewImage.indexOfKey(view.getId()) < 0))
        {
            int picked = imageViewImage.get(view.getId());
            imageViewImage.delete(view.getId());

            int temp = result(picked,imageViewImage.valueAt(0));
            if (temp == 1)
            {
                hit++;
                trueCounter++;
            }
            else
            {
                Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                view.startAnimation(animShake);
                int vibeduration = 1000;
                vibe.vibrate(vibeduration);
                miss++;
                missPoints = true;

            }
        }

        startAnimation();
        countPoints();

        if (currentRound == TotalRounds) {
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            if (click == 0)
            {
                totalspeed =10;
                click=1;
            }
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        }
        clearScreen();

    }

    private void clickable(){
        imageView1.setClickable(true);
        imageView2.setClickable(true);
    }
    private void unclickable(){
        imageView1.setClickable(false);
        imageView2.setClickable(false);
    }
    private int result(int picked , int nonpicked){
        if (mode == 0)
        {
            if (picked == R.drawable.paper && nonpicked == R.drawable.rock)
            {
                return  1;
            }
            else if (picked == R.drawable.rock && nonpicked == R.drawable.scissors)
            {
                return 1;
            }
            else if (picked == R.drawable.scissors && nonpicked == R.drawable.paper)
            {
                return 1;
            }
            else
                return 0;
        }
        else if (mode == 1)
        {
            if (picked == R.drawable.paper && nonpicked == R.drawable.rock)
            {
                return  0;
            }
            else if (picked == R.drawable.rock && nonpicked == R.drawable.scissors)
            {
                return 0;
            }
            else if (picked == R.drawable.scissors && nonpicked == R.drawable.paper)
            {
                return 0;
            }
            else
                return 1;
        }

        return -1;
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, RockPaperScissors.this, hit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "RockPaperScissors");
    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(RockPaperScissors.this,R.raw.tutorial_rps,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"Tutorial");
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
        msgHelper = textMsg.getText().toString();
        totalPoints += currentPoints;
        animPointsText.setText("+ " + currentPoints);
        if (currentPoints == 0) {
            animPointsText.setTextColor(Color.RED);
        } else
            animPointsText.setTextColor(Color.GREEN);
    }


    private void startAnimation() {
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animPointsText, "y", 500f, -500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(animPointsText, View.ALPHA, 1.0f, 0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY, alpha);
        animatorSet.start();
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CURRENTDIFFICULTY , currentDifficulty);
        outState.putString(MENUDIFFICULTY , menuDifficulty);
        outState.putInt(TOTALROUNDS , TotalRounds);
        outState.putInt(HIT, hit);
        outState.putInt(MISS, miss);
        outState.putInt(TOTALPOINTS, totalPoints);
        outState.putInt(TRUECOUNTER, trueCounter);
        outState.putInt(CLICK, click);
        outState.putBoolean(MISSPOINTS,missPoints);
        outState.putBoolean(GAMEINIT,gameinit);
        outState.putSerializable(STARTTIME, startTime);
        outState.putSerializable(ENDTIME, endTime);
        outState.putSerializable(STARTSPEED, startspeed);
        outState.putSerializable(ENDSPEED, endspeed);
        outState.putDouble(TOTALSPEED,totalspeed);
        outState.putLong(CLOCK, mTimeLeftInMillis);
        outState.putIntegerArrayList(IMAGES , images);
        outState.putParcelable(SPARSEARRAY, new SparseIntArrayParcelable(imageViewImage));
        outState.putInt(MODE,mode);
        outState.putInt(CURRENTROUND,currentRound);
        outState.putInt(USER_ID,user_id);
        outState.putInt(GAME_ID,game_id);
        outState.putString(MSGHELPER,msgHelper);
        outState.putLong(NEXTROUNDTIMER,timeLeftInMillisNextRound);
    }


    public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
        public Creator<SparseIntArrayParcelable> CREATOR = new Creator<SparseIntArrayParcelable>() {
            @Override
            public SparseIntArrayParcelable createFromParcel(Parcel source) {
                SparseIntArrayParcelable read = new SparseIntArrayParcelable();
                int size = source.readInt();

                int[] keys = new int[size];
                int[] values = new int[size];

                source.readIntArray(keys);
                source.readIntArray(values);

                for (int i = 0; i < size; i++) {
                    read.put(keys[i], values[i]);
                }

                return read;
            }

            @Override
            public SparseIntArrayParcelable[] newArray(int size) {
                return new SparseIntArrayParcelable[size];
            }
        };

        public SparseIntArrayParcelable() {

        }

        public SparseIntArrayParcelable(SparseIntArray sparseIntArray) {
            for (int i = 0; i < sparseIntArray.size(); i++) {
                this.put(sparseIntArray.keyAt(i), sparseIntArray.valueAt(i));
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            int[] keys = new int[size()];
            int[] values = new int[size()];

            for (int i = 0; i < size(); i++) {
                keys[i] = keyAt(i);
                values[i] = valueAt(i);
            }

            dest.writeInt(size());
            dest.writeIntArray(keys);
            dest.writeIntArray(values);
        }
    }
}
