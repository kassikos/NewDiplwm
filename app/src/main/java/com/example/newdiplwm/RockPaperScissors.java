package com.example.newdiplwm;


import androidx.appcompat.app.AppCompatActivity;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class RockPaperScissors extends AppCompatActivity {


    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";


    ImageView imageButton1;
    ImageView imageButton2;

    MaterialButton startButton;

    TextView textRounds, textQuestion, advancedTextTimer,test;// exw vgalei to textResults ara varaei la8os null pointer

    int id1, id2;

    boolean boolean1, boolean2, expired, roundTimerIsOn = false;

    String currentDifficulty, menuDifficulty;

    HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    int RoundsCounter = 1, TotalRounds = 0;

    int easyFirstMode;

    ArrayList<Integer> mediumList = new ArrayList<>();
    ArrayList<Integer> advancedList = new ArrayList<>();

    int mediumListCounter = 0, advancedListCounter = 0 , vibeduration = 1000;

    CountDownTimer RoundTimer;

    CountDownTimer advancedTimer;

    boolean missPoints = false;
    int totalPoints=0;
    int trueCounter=0;

    int hit = 0;
    int miss = 0;

    int game_id = -1;
    int user_id = -1;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed=0;


    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private Vibrator vibe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_paper_scissors);

        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        game_id = extras.getInt(GAME_ID);
        user_id = extras.getInt(USER_ID);
        menuDifficulty = extras.getString(DIFFICULTY);

        hashMap.put("EASY", 3);
        hashMap.put("MEDIUM", 4);
        hashMap.put("ADVANCED", 5);

        pointsHashMap.put("EASY",0);
        pointsHashMap.put("MEDIUM",5);
        pointsHashMap.put("ADVANCED",10);


        if (menuDifficulty.equals("ALL")) {
            TotalRounds = 5;

            //ksekinaei apo easy
            currentDifficulty = "EASY";

            initializeMediumList();

            initializeAdvancedList();

        } else if (menuDifficulty.equals("EASY")) {
            TotalRounds = 3;

            currentDifficulty = "EASY";
        } else if (menuDifficulty.equals("MEDIUM")) {
            TotalRounds = 3;

            initializeMediumList();

            currentDifficulty = "MEDIUM";
        } else if (menuDifficulty.equals("ADVANCED")) {
            TotalRounds = 3;

            initializeAdvancedList();

            currentDifficulty = "ADVANCED";
        } else if (menuDifficulty.equals("EASYtoMEDIUM")) {

            //TotalRounds = hashMap.get("EASY") + hashMap.get("MEDIUM");

            TotalRounds = 4;

            initializeMediumList();

            currentDifficulty = "EASY";
        }


        //syndesh me to XML
        imageButton1 = findViewById(R.id.imageButton1);

        imageButton2 = findViewById(R.id.imageButton2);

        textRounds = findViewById(R.id.textRounds);

        textQuestion = findViewById(R.id.textQuestion);

        advancedTextTimer = findViewById(R.id.textTimer);

        advancedTextTimer.setText("");

        startButton = findViewById(R.id.startButton);

        test = (TextView) findViewById(R.id.myImageViewTextAnim);
        

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        RoundTimer = new CountDownTimer(2000, vibeduration) {

            public void onTick(long millisUntilFinished) {
           //     mTextField.setText("Επόμενος Γύρος σε: " + millisUntilFinished / vibeduration);
                roundTimerIsOn = true;
            }

            public void onFinish() {

                roundTimerIsOn = false;

            //   mTextField.setText("-");

                createRound();

                enableButtons();

                if (currentDifficulty.equals("MEDIUM")) {
                    if (mediumList.get(mediumListCounter) == 1) {
                        textQuestion.setText("Ποιός κερδίζει;M");
                    } else if (mediumList.get(mediumListCounter) == 2) {
                        textQuestion.setText("Ποιός χάνει;M");
                    }
                } else if (currentDifficulty.equals("ADVANCED")) {
                    if (advancedList.get(advancedListCounter) == 1) {
                        textQuestion.setText("Ποιός κερδίζει;A");
                    } else if (advancedList.get(advancedListCounter) == 2) {
                        textQuestion.setText("Ποιός χάνει;A");
                    }
                }

            }

        };

        //mia fora tha treksei auto 1111111111111111111
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startTime = new Timestamp(System.currentTimeMillis());


                //arxikos gyros symfwna me tis dyskolies
                createRound();

                if (currentDifficulty.equals("EASY")) {
                    Random r1 = new Random();
                    easyFirstMode = r1.nextInt((2 - 1) + 1) + 1;
                    if (easyFirstMode == 1) {
                        textQuestion.setText("Ποιός κερδίζει;E");
                        checkWinMode();
                    } else if (easyFirstMode == 2) {
                        textQuestion.setText("Ποιός χάνει;E");
                        checkLoseMode();
                    }
                } else if (currentDifficulty.equals("MEDIUM")) {
                    //prwth erwthsh
//                    if (mediumList.get(0)==1)//edw 8a exei sigoura  1 pote 2
//                    {
                    textQuestion.setText("Ποιός κερδίζει1;");
//
//                    }
//                    else if (mediumList.get(0)==2)
//                    {
//                        textQuestion.setText("Ποιός χάνει1;");
//                    }

                } else if (currentDifficulty.equals("ADVANCED") || currentDifficulty.equals("ALL")) {
                    //prwth erwthsh
//                    if (advancedList.get(0)==1)// to idio me panw
//                    {
                    textQuestion.setText("Ποιός κερδίζει1;");
//                    }
//                    else if (advancedList.get(0)==2)
//                    {
//                        textQuestion.setText("Ποιός χάνει1;");
//                    }
                }

                //kalutera na mhn feugei to button ekkinisis ΚΑΛΥΤΕΡΑ View.INVISIBLE
                startButton.setVisibility(View.GONE);
            }
        });

        imageButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //pare telos gitou timestamp

                boolean1 = true;
                boolean2 = false;

                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime() - startspeed.getTime();
                double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
                totalspeed += speedseconds;


                checkRound();

                disableButtons();

                if (RoundsCounter > TotalRounds) {
                    textRounds.setText("ξεκολλα τελος");
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    double seconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,-1,totalPoints,(double)hit/(hit+miss),totalspeed/TotalRounds,seconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();

                } else {
                    RoundTimer.start();
                }

            }

        });

        imageButton2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                boolean1 = false;
                boolean2 = true;

                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime()-startspeed.getTime();
                double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
                totalspeed += speedseconds;

                checkRound();

                disableButtons();

                if (RoundsCounter > TotalRounds) {
                    textRounds.setText("ξεκολλα τελος");
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float seconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
//                    double elapsedTimeInSecond = (double) longTime / 1_000_000_000;
//                    long convert = TimeUnit.SECONDS.convert(longTime, TimeUnit.NANOSECONDS);
//                    Timestamp playtime = new Timestamp(longTime);
//                    float minutes = playtime.getMinutes();
//                    int sec = playtime.getSeconds();
//                    float seconds = sec/60;
//                    minutes += seconds;
//                    System.out.println(playtime);
//                    System.out.println(minutes);
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,-1,totalPoints,(double)hit/(hit+miss),totalspeed/TotalRounds,seconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);

                    shopPopUp();
                } else {
                    RoundTimer.start();
                }
            }

        });

    }

    public void initializeMediumList() {
        for (int i = 0; i < hashMap.get("MEDIUM"); i++)//gemisma listas gia ta modes
        {
            if ((i % 2) == 0) {
                mediumList.add(1);
            } else {
                mediumList.add(2);
            }

        }
    }


    public void initializeAdvancedList() {
        for (int i = 0; i < hashMap.get("ADVANCED"); i++)//gemisma listas gia ta modes
        {
            if ((i % 2) == 0) {
                advancedList.add(1);
            } else {
                advancedList.add(2);
            }

        }

    }


    public void disableButtons() {
        imageButton2.setEnabled(false);
        imageButton2.setAlpha(0.5f);

        imageButton1.setEnabled(false);
        imageButton1.setAlpha(0.5f);

    }

    public void enableButtons() {

        imageButton1.setEnabled(true);
        imageButton1.setAlpha(1f);

        imageButton2.setEnabled(true);
        imageButton2.setAlpha(1f);

    }


    public void createRound() {
        //pare timestamp arxis girou

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        expired = false;

        if (RoundsCounter > TotalRounds) {
            textRounds.setText("Τέλος");
            //fyge alliws krasarei
        } else {
            textRounds.setText( "Round :" + String.valueOf(RoundsCounter) + " / "+ String.valueOf(TotalRounds));

        }

        //gia na proxwrhsoun oi dyskolies
        if (menuDifficulty.equals("EASYtoMEDIUM")) {

            if (RoundsCounter <= 2) {
                currentDifficulty = "EASY";
            }
            else
            {
                currentDifficulty = "MEDIUM";
            }
        }

        if (menuDifficulty.equals("ALL"))
        {
            if (RoundsCounter == 1)
            {
                currentDifficulty = "EASY";
            }
            else if (RoundsCounter>1 && RoundsCounter <=3)
            {
                currentDifficulty = "MEDIUM";
            }
            else if (RoundsCounter>3)
            {
                currentDifficulty = "ADVANCED";
            }
        }

        Random r = new Random();
        id1 = r.nextInt((3 - 1) + 1) + 1;
        do {
            id2 = r.nextInt((3 - 1) + 1) + 1;
        } while (id1 == id2);

        if (id1 == 1) {
            imageButton1.setImageResource(R.drawable.xarti);
        } else if (id1 == 2) {
            imageButton1.setImageResource(R.drawable.petra);
        } else if (id1 == 3) {
            imageButton1.setImageResource(R.drawable.psalidi);
        }

        if (id2 == 1) {
            imageButton2.setImageResource(R.drawable.xarti);
        } else if (id2 == 2) {
            imageButton2.setImageResource(R.drawable.petra);
        } else if (id2 == 3) {
            imageButton2.setImageResource(R.drawable.psalidi);
        }


        startspeed = new Timestamp(System.currentTimeMillis());
        

        if (currentDifficulty.equals("ADVANCED")) {

            //opws eipe o goumo to phgame sta 10 deuterolepta
            advancedTimer = new CountDownTimer(10000, vibeduration) {


                public void onTick(long millisUntilFinished) {
                    advancedTextTimer.setText("Έχεις ακόμα: " + millisUntilFinished / vibeduration);
                }

                public void onFinish() {

                    //to timestamp 8a einai oso einai o timer

                    advancedTextTimer.setText("Ο χρόνος του γύρου τελείωσε!");
                    expired = true;
                    missPoints = true;
                    countPoints();
                    startAnimation();
                    miss++;
                    vibe.vibrate(vibeduration);

                    disableButtons();


                    if (RoundsCounter > TotalRounds) {
                        textRounds.setText("Finish");
                        endTime = new Timestamp(System.currentTimeMillis());
                        long longTime = endTime.getTime() - startTime.getTime();
                        float seconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                        GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,-1,totalPoints,(double)hit/(hit+miss),5,seconds,menuDifficulty,startTime,endTime);
                        gameEventViewModel.insertGameEvent(gameEvent);
                        userViewModel.updatestatsTest(user_id,game_id);
                        shopPopUp();

                    } else {
                        RoundTimer.start();
                    }
                }


            }.start();
        }

        if (expired) {
            //h edw paizeis me to miss h mesa sthn onfinish
   //         textResults.setText("Ο χρόνος τελείωσε!");
        }


        RoundsCounter++;

    }


    public void checkRound() {

        if (currentDifficulty.equals("EASY")) {
            if (easyFirstMode == 1) {
                checkWinMode();
            } else if (easyFirstMode == 2) {
                checkLoseMode();
            }
        } else if (currentDifficulty.equals("MEDIUM")) {
            if (mediumList.get(mediumListCounter) == 1) {
                checkWinMode();
            } else if (mediumList.get(mediumListCounter) == 2) {
                checkLoseMode();
            }
            mediumListCounter++;
        } else if (currentDifficulty.equals("ADVANCED")) {
            //stamatei o timer tou gyrou
            advancedTimer.cancel();

            if (roundTimerIsOn) {
                RoundTimer.cancel();
            }

            advancedTextTimer.setText("");

            if (advancedList.get(advancedListCounter) == 1) {
                checkWinMode();
            } else if (advancedList.get(advancedListCounter) == 2) {
                checkLoseMode();
            }
            advancedListCounter++;
        }

    }

    public void checkWinMode() {

        if (boolean1) {
            if (id1 == 1 && id2 == 2) {
                missPoints = false;

                trueCounter++;
                hit++;

            } else if (id1 == 2 && id2 == 3) {

                missPoints = false;

                trueCounter++;
                hit++;

            } else if (id1 == 3 && id2 == 1) {

                missPoints = false;

                trueCounter++;
                hit++;

            } else {

                missPoints = true;

                miss++;
                vibe.vibrate(vibeduration);

            }
            countPoints();
            startAnimation();
        } else if (boolean2) {
            if (id2 == 1 && id1 == 2) {

                missPoints = false;

                trueCounter++;
                hit++;

            } else if (id2 == 2 && id1 == 3) {

                missPoints = false;

                trueCounter++;
                hit++;

            } else if (id2 == 3 && id1 == 1) {

                missPoints = false;

                trueCounter++;
                hit++;

            } else {

                missPoints = true;

                miss++;
                vibe.vibrate(vibeduration);


            }
            countPoints();
            startAnimation();
        }
    }

    public void checkLoseMode() {

        if (boolean1) {
            if (id1 == 1 && id2 == 2) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else if (id1 == 2 && id2 == 3) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else if (id1 == 3 && id2 == 1) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else {

                missPoints = false;
                trueCounter++;
                hit++;

            }
            countPoints();
            startAnimation();
        } else if (boolean2) {
            if (id2 == 1 && id1 == 2) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else if (id2 == 2 && id1 == 3) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else if (id2 == 3 && id1 == 1) {

                missPoints = true;
                miss++;
                vibe.vibrate(vibeduration);

            } else {

                missPoints = false;
                trueCounter++;
                hit++;
            }

            countPoints();
            startAnimation();
        }
    }

    public void countPoints()
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
            trueCounter = 0;
        }
        totalPoints += currentPoints;
        test.setText("+ " +String.valueOf(currentPoints));
        if (currentPoints == 0)
        {
            test.setTextColor(Color.RED);
        }
        else
            test.setTextColor(Color.GREEN);
    }
    

    public void startAnimation(){
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(test,"y",500f,-500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha =  ObjectAnimator.ofFloat(test,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY,alpha);
        animatorSet.start();
    }

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,RockPaperScissors.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "rockpaperScissor");
    }


}
