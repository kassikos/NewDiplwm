package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
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

public class RotationGame extends AppCompatActivity {

    private TextView rect1, rect2, rect3, rect4;
    private TextView rectToMatch;
    private TextView rectToCheck;
    private ImageView buttonCheck, buttonrotate, exit, replayTutorial;
    private MaterialButton startButton;
    private TextView rounds, animPointsText, textMsg, textMsgTime;
    private LinearLayout textsLinear;


    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String ALLRECTS = "ALLRECTS";
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String CLICK = "CLICK";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
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
    private static final String CURRENTROUND = "CURRENTROUND";
    private static final String GAMEINIT = "GAMEINIT";
    private static final String RECTCOLOR = "RECTCOLOR";
    private static final String MSGHELPER = "MSGHELPER";
    private static final String NEXTROUNDTIMER = "NEXTROUNDTIMER";
    private static final String TODEGREES = "TODEGREES";
    private static final String FROMDEGREES = "FROMDEGREES";
    private static final String ROTATIONS = "ROTATIONS";


    private SparseIntArray rectColor = new SparseIntArray(4);

    private int game_id, user_id;
    private int TotalRounds = 0, currentRound = 0, click = 0;
    private String menuDifficulty, currentDifficulty, msgHelper;

    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private int hit = 0, miss = 0, totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private int vibeduration = 1000;
    private boolean gameInit = false;
    private Vibrator vibe;
    private CountDownTimer nextRoundTimer;
    private long timeLeftInMillisNextRound = 0;


    private int rotations = 0;
    private LinearLayout LL, logoLinear;
    private float fromDegrees = 0f, toDegrees = 0f;

    private ArrayList<Integer> easyColors = new ArrayList<>(4);

    private ArrayList<ArrayList<Integer>> mediumColors = new ArrayList<>(4);
    private ArrayList<Integer> mediumList1 = new ArrayList<>(4);
    private ArrayList<Integer> mediumList2 = new ArrayList<>(4);
    private ArrayList<Integer> mediumList3 = new ArrayList<>(4);
    private ArrayList<Integer> mediumList4 = new ArrayList<>(4);

    private ArrayList<ArrayList<Integer>> advancedColors = new ArrayList<>(4);
    private ArrayList<Integer> advancedList1 = new ArrayList<>(4);
    private ArrayList<Integer> advancedList2 = new ArrayList<>(4);
    private ArrayList<Integer> advancedList3 = new ArrayList<>(4);
    private ArrayList<Integer> advancedList4 = new ArrayList<>(4);


    private ArrayList<Integer> randomList = new ArrayList<>(4);
    private ArrayList<Integer> allrects = new ArrayList<>(4);


    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotation_game);
        assignAllButtons();
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new Session(getApplicationContext());
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);

        if (savedInstanceState != null) {
            gameInit = savedInstanceState.getBoolean(GAMEINIT);
            allrects = savedInstanceState.getIntegerArrayList(ALLRECTS);
            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            initializeColorLists();
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            click = savedInstanceState.getInt(CLICK);
            hit = savedInstanceState.getInt(HIT);
            miss = savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            currentRound = savedInstanceState.getInt(CURRENTROUND);
            msgHelper = savedInstanceState.getString(MSGHELPER);
            timeLeftInMillisNextRound = savedInstanceState.getLong(NEXTROUNDTIMER);
            toDegrees = savedInstanceState.getFloat(TODEGREES,0);
            fromDegrees = savedInstanceState.getFloat(FROMDEGREES,0);
            rotations = savedInstanceState.getInt(ROTATIONS,0);

            if (rotations % 4 == 0) {
                rectToCheck = rect1;
            } else if (rotations % 4 == 1) {
                rectToCheck = rect2;
            } else if (rotations % 4 == 2) {
                rectToCheck = rect3;
            } else if (rotations % 4 == 3) {
                rectToCheck = rect4;
            }
            ObjectAnimator rotate = ObjectAnimator.ofFloat(LL, "rotation", fromDegrees, toDegrees);
            rotate.setDuration(0);
            rotate.start();
            if (gameInit) {
                click();
                logoLinear.setVisibility(View.GONE);
                buttonrotate.setVisibility(View.VISIBLE);
                buttonCheck.setVisibility(View.VISIBLE);
                startButton.setVisibility(View.INVISIBLE);
                if (currentDifficulty.equals(getResources().getString(R.string.easyValue))){
                rectToMatch.setText("▼");
                rect1.setText("▲");
                rect2.setText("◀");
                rect3.setText("▼");
                rect4.setText("▶");
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                {
                    rectToMatch.setText("■");
                    rect1.setText("■");
                    rect2.setText("■");
                    rect3.setText("■");
                    rect4.setText("■");
                }
                else{
                    rectToMatch.setText("⬤");
                    rect1.setText("⬤");
                    rect2.setText("⬤");
                    rect3.setText("⬤");
                    rect4.setText("⬤");
                }

                rectColor = (SparseIntArray) savedInstanceState.getParcelable(RECTCOLOR);
                rounds.setText((currentRound) + " / " + TotalRounds);
                rectToMatch.setTextColor(rectColor.get(rectToMatch.getId()));
                for (int i = 0; i < rectColor.size(); i++) {
                    TextView textView = findViewById(rectColor.keyAt(i));
                    textView.setTextColor(rectColor.valueAt(i));
                }

            } else {

                if (currentRound == 0) {
                    startButton.setVisibility(View.VISIBLE);
                } else {
                    buttonrotate.setVisibility(View.INVISIBLE);
                    buttonCheck.setVisibility(View.INVISIBLE);
                    if (currentDifficulty.equals(getResources().getString(R.string.easyValue))){
                        rectToMatch.setText("▼");
                        rect1.setText("▲");
                        rect2.setText("◀");
                        rect3.setText("▼");
                        rect4.setText("▶");
                    }
                    else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                    {
                        rectToMatch.setText("■");
                        rect1.setText("■");
                        rect2.setText("■");
                        rect3.setText("■");
                        rect4.setText("■");
                    }
                    else{
                        rectToMatch.setText("⬤");
                        rect1.setText("⬤");
                        rect2.setText("⬤");
                        rect3.setText("⬤");
                        rect4.setText("⬤");
                    }
                    logoLinear.setVisibility(View.GONE);
                    startButton.setVisibility(View.INVISIBLE);
                    unclick();
                    rectToMatch.setTextColor(getResources().getColor(R.color.statisticColor));
                    for (int rect : allrects) {
                        TextView textView = findViewById(rect);
                        textView.setTextColor(getResources().getColor(R.color.statisticColor));
                    }

                    if (timeLeftInMillisNextRound > 1000) {
                        textMsg.setText(msgHelper);
                        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
                        disableReplayTut();
                        textsLinear.setVisibility(View.VISIBLE);
                        nextRoundTimer = userViewModel.getNextRoundTimer();
                        nextRoundTimer.cancel();
                        nextRoundTimer = new CountDownTimer(timeLeftInMillisNextRound, 1000) {
                            @Override
                            public void onTick(long l) {
                                timeLeftInMillisNextRound = l;

                                if (currentRound == TotalRounds) {
                                    textMsgTime.setText(" ");
                                } else {
                                    textMsgTime.setText(getResources().getString(R.string.nextRound) + l / 1000);
                                }


                            }

                            @Override
                            public void onFinish() {
                                click();
                                timeLeftInMillisNextRound = 0;

                                if (currentRound == TotalRounds) {
                                    textsLinear.setVisibility(View.INVISIBLE);
                                    disableReplayTut();
                                } else {
                                    buttonrotate.setVisibility(View.VISIBLE);
                                    buttonCheck.setVisibility(View.VISIBLE);
                                    enableReplayTut();
                                    nextRoundTimer = null;
                                    textMsgTime.setText("");
                                    textsLinear.setVisibility(View.INVISIBLE);
                                    gameInit = true;
                                    createRound();
                                }
                            }
                        }.start();

                        userViewModel.setNextRoundTimer(nextRoundTimer);
                    }

                }
            }

        } else {

            loadRects();
            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty = session.getModeSession();
            unclick();
            initializeColorLists();

        }


        if (!session.getPlayAgainVideo() && currentRound == 0) {
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("TutorialRotationGame");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            fm.popBackStack();
        }


        buttonrotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotations++;
                toDegrees += 90;
                ObjectAnimator rotate = ObjectAnimator.ofFloat(LL, "rotation", fromDegrees, toDegrees);
                rotate.setDuration(500);
                rotate.start();
                fromDegrees += 90;


            }
        });

        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rectColor.clear();

                unclick();
                click++;
                gameInit = false;
                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime() - startspeed.getTime();
                double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
                totalspeed += speedseconds;

                if (rotations % 4 == 0) {
                    rectToCheck = rect1;
                } else if (rotations % 4 == 1) {
                    rectToCheck = rect2;
                } else if (rotations % 4 == 2) {
                    rectToCheck = rect3;
                } else if (rotations % 4 == 3) {
                    rectToCheck = rect4;
                }


                if (rectToCheck.getCurrentTextColor() == rectToMatch.getCurrentTextColor()) {
                    startAnimation();
                    hit++;
                    trueCounter++;


                } else {
                    startAnimation();
                    Animation animShake = AnimationUtils.loadAnimation(RotationGame.this, R.anim.shake);
                    rectToCheck.startAnimation(animShake);
                    vibe.vibrate(vibeduration);
                    miss++;
                    missPoints = true;
                }
                countPoints();
                checkifEnds();

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoLinear.setVisibility(View.GONE);
                buttonrotate.setVisibility(View.VISIBLE);
                buttonCheck.setVisibility(View.VISIBLE);
                click();
                gameInit = true;
                createRound();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialRotationGame");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                }
                startButton.setVisibility(View.INVISIBLE);

            }
        });


    }

    private void checkifEnds() {
        if (currentRound == TotalRounds) {
            startButton.setVisibility(View.INVISIBLE);
            textMsgTime.setText("");
            textsLinear.setVisibility(View.VISIBLE);
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        } else {
            nextRound();
        }
    }

    private void nextRound() {
        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
        textsLinear.setVisibility(View.VISIBLE);
        buttonrotate.setVisibility(View.INVISIBLE);
        buttonCheck.setVisibility(View.INVISIBLE);
        disableReplayTut();
        unclick();
        rectToMatch.setTextColor(getResources().getColor(R.color.statisticColor));
        for (int rect : allrects) {
            TextView textView = findViewById(rect);
            textView.setTextColor(getResources().getColor(R.color.statisticColor));
        }

        nextRoundTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long l) {
                timeLeftInMillisNextRound = l;

                if (currentRound == TotalRounds) {
                    textMsgTime.setText("");
                } else {
                    textMsgTime.setText(getResources().getString(R.string.nextRound) + l / 1000);
                }
            }

            @Override
            public void onFinish() {
                click();
                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds) {
                    textsLinear.setVisibility(View.INVISIBLE);

                    disableReplayTut();
                } else {
                    buttonrotate.setVisibility(View.VISIBLE);
                    buttonCheck.setVisibility(View.VISIBLE);
                    enableReplayTut();
                    nextRoundTimer = null;
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    gameInit = true;
                    createRound();
                }
            }
        }.start();

        userViewModel.setNextRoundTimer(nextRoundTimer);

    }

    private void onbackAndExitCode() {
        if (nextRoundTimer != null) {
            nextRoundTimer.cancel();
        }
        if (currentRound == 0 || click == 0) {
            startTime = new Timestamp(System.currentTimeMillis());
            endTime = new Timestamp(System.currentTimeMillis());
            GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        } else {
            if (startspeed == null || endspeed == null) {
                totalspeed += 0;
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
    public void onBackPressed() {
        onbackAndExitCode();
    }


    private void createRound() {

        if (currentRound == 0) {
            startTime = new Timestamp(System.currentTimeMillis());
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
        currentRound++;
        rounds.setText((currentRound) + " / " + TotalRounds);

    }

    private void displayGameEz() {
        unclick();
        rectToMatch.setText("▼");
        rect1.setText("▲");
        rect2.setText("◀");
        rect3.setText("▼");
        rect4.setText("▶");

        Collections.shuffle(easyColors);

        rectToMatch.setTextColor(easyColors.get(0));
        rectColor.put(rectToMatch.getId(), easyColors.get(0));

        int i = 0;
        for (int rect : allrects) {
            if (rect == rectToCheck.getId() && i == 0) {
                TextView textView = findViewById(rect);
                textView.setTextColor(easyColors.get(i));
                while (textView.getCurrentTextColor() == rectToMatch.getCurrentTextColor()) {
                    Collections.shuffle(easyColors);
                    textView.setTextColor(easyColors.get(i));
                }
                rectColor.put(textView.getId(), textView.getCurrentTextColor());
            } else {
                TextView textView = findViewById(rect);
                textView.setTextColor(easyColors.get(i));
                rectColor.put(textView.getId(), textView.getCurrentTextColor());

            }
            i++;
        }

        startspeed = new Timestamp(System.currentTimeMillis());
        click();

    }

    private void displayGameMedium() {
        unclick();

        rectToMatch.setText("■");
        rect1.setText("■");
        rect2.setText("■");
        rect3.setText("■");
        rect4.setText("■");

        Random rand = new Random();

        randomList = mediumColors.get(rand.nextInt(mediumColors.size()));
        Collections.shuffle(randomList);
        rectToMatch.setTextColor(randomList.get(0));
        rectColor.put(rectToMatch.getId(), rectToMatch.getCurrentTextColor());

        int i = 0;
        for (int rect : allrects) {
            if (rect == rectToCheck.getId() && i == 0) {
                TextView textView = findViewById(rect);
                textView.setTextColor(randomList.get(i));
                while (textView.getCurrentTextColor() == rectToMatch.getCurrentTextColor()) {
                    Collections.shuffle(randomList);
                    textView.setTextColor(randomList.get(i));
                    rectColor.put(textView.getId(), textView.getCurrentTextColor());
                }
                rectColor.put(textView.getId(), textView.getCurrentTextColor());
            } else {
                TextView textView = findViewById(rect);
                textView.setTextColor(randomList.get(i));
                rectColor.put(textView.getId(), textView.getCurrentTextColor());

            }
            i++;
        }


        startspeed = new Timestamp(System.currentTimeMillis());
        click();
    }

    private void displayGameAdv() {
        unclick();

        rectToMatch.setText("⬤");
        rect1.setText("⬤");
        rect2.setText("⬤");
        rect3.setText("⬤");
        rect4.setText("⬤");

        //⬤

        Random rand = new Random();

        randomList = advancedColors.get(rand.nextInt(advancedColors.size()));
        Collections.shuffle(randomList);
        rectToMatch.setTextColor(randomList.get(0));
        rectColor.put(rectToMatch.getId(), rectToMatch.getCurrentTextColor());

        int i = 0;
        for (int rect : allrects) {
            if (rect == rectToCheck.getId() && i == 0) {
                TextView textView = findViewById(rect);
                textView.setTextColor(randomList.get(i));
                while (textView.getCurrentTextColor() == rectToMatch.getCurrentTextColor()) {
                    Collections.shuffle(randomList);
                    textView.setTextColor(randomList.get(i));
                }
                rectColor.put(textView.getId(), textView.getCurrentTextColor());
            } else {
                TextView textView = findViewById(rect);
                textView.setTextColor(randomList.get(i));
                rectColor.put(textView.getId(), textView.getCurrentTextColor());

            }
            i++;
        }


        startspeed = new Timestamp(System.currentTimeMillis());
        click();


    }

    private void loadRects() {
        allrects.add(R.id.RG_rect1);
        allrects.add(R.id.RG_rect2);
        allrects.add(R.id.RG_rect3);
        allrects.add(R.id.RG_rect4);

    }


    private void assignAllButtons() {
        startButton = findViewById(R.id.startButtonRG);
        buttonrotate = findViewById(R.id.RG_buttonrotate);
        buttonCheck = findViewById(R.id.RG_buttoncheck);
        exit = findViewById(R.id.ExitRG);
        replayTutorial = findViewById(R.id.ReplayTutorialRotGame);
        textsLinear = findViewById(R.id.textsRotation);
        textMsg = findViewById(R.id.msgRotation);
        textMsgTime = findViewById(R.id.msgRotation1);

        LL = findViewById(R.id.RG_LL);
        logoLinear = findViewById(R.id.imageLogoDisplayRotaion);

        rect1 = findViewById(R.id.RG_rect1);
        rect2 = findViewById(R.id.RG_rect2);
        rect3 = findViewById(R.id.RG_rect3);
        rect4 = findViewById(R.id.RG_rect4);
        rectToCheck = rect1;

        rectToMatch = findViewById(R.id.RG_rectToMatch);
        rounds = findViewById(R.id.RG_textRounds);
        animPointsText = findViewById(R.id.AnimTextPointsRG);

        unclick();
    }

    private void initializeColorLists() {

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))) {
            initializeEasyColors();
        } else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue))) {
            initializeMediumColors();
        } else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue))) {
            initializeAdvancedColors();
        } else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue))) {
            initializeEasyColors();
            initializeMediumColors();
        } else {
            initializeEasyColors();
            initializeMediumColors();
            initializeAdvancedColors();
        }

    }

    private void initializeEasyColors() {
        easyColors.add(Color.rgb(255, 0, 0));
        easyColors.add(Color.rgb(0, 102, 255));
        easyColors.add(Color.rgb(0, 153, 51));
        easyColors.add(Color.rgb(255, 204, 0));
    }

    private void initializeMediumColors() {
        mediumColors.add(mediumList1);
        mediumList1.add(Color.rgb(204, 0, 102));
        mediumList1.add(Color.rgb(204, 0, 0));
        mediumList1.add(Color.rgb(102, 153, 0));
        mediumList1.add(Color.rgb(0, 102, 0));

        mediumColors.add(mediumList2);
        mediumList2.add(Color.rgb(255, 153, 0));
        mediumList2.add(Color.rgb(204, 51, 0));
        mediumList2.add(Color.rgb(153, 255, 51));
        mediumList2.add(Color.rgb(0, 204, 0));

        mediumColors.add(mediumList3);
        mediumList3.add(Color.rgb(0, 153, 204));
        mediumList3.add(Color.rgb(0, 102, 204));
        mediumList3.add(Color.rgb(102, 102, 51));
        mediumList3.add(Color.rgb(153, 153, 102));

        mediumColors.add(mediumList4);
        mediumList4.add(Color.rgb(102, 102, 153));
        mediumList4.add(Color.rgb(102, 0, 204));
        mediumList4.add(Color.rgb(153, 255, 51));
        mediumList4.add(Color.rgb(102, 153, 0));
    }

    private void initializeAdvancedColors() {
        advancedColors.add(advancedList1);
        advancedList1.add(Color.rgb(204, 0, 0));
        advancedList1.add(Color.rgb(255, 0, 0));
        advancedList1.add(Color.rgb(255, 51, 0));
        advancedList1.add(Color.rgb(128, 0, 0));

        advancedColors.add(advancedList2);
        advancedList2.add(Color.rgb(0, 51, 0));
        advancedList2.add(Color.rgb(0, 102, 0));
        advancedList2.add(Color.rgb(0, 153, 0));
        advancedList2.add(Color.rgb(51, 204, 51));

        advancedColors.add(advancedList3);
        advancedList3.add(Color.rgb(0, 0, 102));
        advancedList3.add(Color.rgb(0, 204, 255));
        advancedList3.add(Color.rgb(0, 51, 204));
        advancedList3.add(Color.rgb(0, 153, 255));

        advancedColors.add(advancedList4);
        advancedList4.add(Color.rgb(204, 204, 0));
        advancedList4.add(Color.rgb(204, 153, 0));
        advancedList4.add(Color.rgb(255, 102, 0));
        advancedList4.add(Color.rgb(255, 153, 51));
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

    private void unclick() {

        buttonrotate.setEnabled(false);
        buttonCheck.setEnabled(false);

    }

    private void click() {
        buttonrotate.setEnabled(true);
        buttonCheck.setEnabled(true);
    }

    private void enableReplayTut() {
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }

    private void disableReplayTut() {
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }

    private void showTutorialPopUp() {
        DialogFragment dialogFragment = new Tutorial(RotationGame.this, R.raw.tutorial_rotationgame, getPackageName());
        dialogFragment.show(getSupportFragmentManager(), "TutorialRotationGame");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, RotationGame.this, hit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "RotationGame");
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECTCOLOR, new AudioPersonPick.SparseIntArrayParcelable(rectColor));
        outState.putIntegerArrayList(ALLRECTS, allrects);
        outState.putString(CURRENTDIFFICULTY, currentDifficulty);
        outState.putInt(GAME_ID, game_id);
        outState.putInt(USER_ID, user_id);
        outState.putInt(TOTALROUNDS, TotalRounds);
        outState.putInt(CLICK, click);
        outState.putString(MENUDIFFICULTY, menuDifficulty);
        outState.putBoolean(MISSPOINTS, missPoints);
        outState.putInt(HIT, hit);
        outState.putInt(MISS, miss);
        outState.putInt(TOTALPOINTS, totalPoints);
        outState.putInt(TRUECOUNTER, trueCounter);
        outState.putDouble(TOTALSPEED, totalspeed);
        outState.putSerializable(STARTTIME, startTime);
        outState.putSerializable(ENDTIME, endTime);
        outState.putSerializable(STARTSPEED, startspeed);
        outState.putSerializable(ENDSPEED, endspeed);
        outState.putInt(CURRENTROUND, currentRound);
        outState.putBoolean(GAMEINIT, gameInit);
        outState.putString(MSGHELPER, msgHelper);
        outState.putLong(NEXTROUNDTIMER, timeLeftInMillisNextRound);
        outState.putFloat(TODEGREES,toDegrees);
        outState.putFloat(FROMDEGREES, fromDegrees);
        outState.putInt(ROTATIONS,rotations);

    }
}
