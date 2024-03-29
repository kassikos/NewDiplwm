package com.example.newdiplwm;


import android.graphics.Color;
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
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ScalingGame extends AppCompatActivity {

    private static  final int THRESHOLD_EASY = 120;
    private static  final int THRESHOLD_ALL = 180;

    MaterialButton startButton,leftButton,rightButton,equalButton;//na ginoyn material buttons
    ImageView exit , replayTutorial;
    private LinearLayout logoLinear, textsLinear;
    TextView leftText, rightText,textRounds , textMsg , textMsgTime;

    TextView textQuestion;

    int leftNumber,rightNumber,result,choice;

    int RoundsCounter = 1, TotalRounds = 0;

    String menuDifficulty,currentDifficulty;

    HashMap<String, Integer> hashMap = new HashMap<String, Integer>();

    HashMap<Integer,Character> symbols = new HashMap<Integer, Character>();

    HashMap<Integer,Integer> divideNumbers = new HashMap<Integer, Integer>();

    HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    CountDownTimer RoundTimer;

    int game_id = -1;
    int user_id = -1;


    private static final String WRONG = "Λάθος! ";
    private static final String CORRECT = "Σωστά! ";

    private String rightResult = "";

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed=0;


    private int hit = 0, miss = 0, totalPoints=0, trueCounter=0;

    private boolean missPoints = false;


    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scaling_game);
        session = new Session(getApplicationContext());

        if (!session.getPlayAgainVideo() && RoundsCounter == 1) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialScalingGame");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            fm.popBackStack();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }

        game_id = session.getGameIdSession();
        user_id = session.getUserIdSession();
        menuDifficulty =  session.getModeSession();

        startButton = findViewById(R.id.startButtonScalling);

        leftButton = findViewById(R.id.buttonLeft);
        leftButton.setBackgroundColor(getResources().getColor(R.color.yellow));
        rightButton = findViewById(R.id.buttonRight);
        rightButton.setBackgroundColor(getResources().getColor(R.color.yellow));
        equalButton = findViewById(R.id.buttonEqual);
        equalButton.setBackgroundColor(getResources().getColor(R.color.yellow));
        exit = findViewById(R.id.ExitScalGame);
        replayTutorial = findViewById(R.id.ReplayTutorialSCG);
        textMsgTime = findViewById(R.id.msgScaling1);
        textMsg = findViewById(R.id.msgScaling);
        textsLinear = findViewById(R.id.textsScaling);

        leftText = findViewById(R.id.textLeft);

        rightText = findViewById(R.id.textRight);

        logoLinear = findViewById(R.id.imageLogoDisplaySCG);

        textRounds = findViewById(R.id.textRoundsScaling);


        textQuestion = findViewById(R.id.calcution_TextQuestion);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        disableButtons();


        hashMap.put("EASY", 3);
        hashMap.put("MEDIUM", 4);
        hashMap.put("ADVANCED", 5);

        pointsHashMap.put("EASY",0);
        pointsHashMap.put("MEDIUM",5);
        pointsHashMap.put("ADVANCED",10);

        symbols.put(1,'+');
        symbols.put(2,'-');
        symbols.put(3,'×');
        symbols.put(4,'÷');


        if (menuDifficulty.equals("ALL"))
        {
            TotalRounds = 5;

            //ksekinaei apo easy
            currentDifficulty = "EASY";

            initializeDivideNumbers();

        }
        else if (menuDifficulty.equals("EASY"))
        {
            TotalRounds = 3;

            currentDifficulty = "EASY";
        }
        else if (menuDifficulty.equals("MEDIUM"))
        {
            TotalRounds = 3;

            currentDifficulty = "MEDIUM";
        }
        else if (menuDifficulty.equals("ADVANCED"))
        {
            TotalRounds = 3;

            currentDifficulty = "ADVANCED";

            initializeDivideNumbers();
        }
        else if (menuDifficulty.equals("EASYtoMEDIUM"))
        {
            TotalRounds = 4;

            currentDifficulty = "EASY";
        }


        RoundTimer = new CountDownTimer(6000, 1000) {


            public void onTick(long millisUntilFinished) {
                textMsgTime.setText(getResources().getString(R.string.nextRound)+millisUntilFinished/1000);
                //roundTimerIsOn =true;
            }

            public void onFinish() {

                //roundTimerIsOn =false;


                textMsgTime.setText("-");

                createRound();

                enableButtons();
                hideMsgDisplayButtons();
                replayTutorial.setClickable(true);
                replayTutorial.setAlpha(1f);


            }

        };


        //mia fora tha treksei auto
        startButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logoLinear.setVisibility(View.GONE);
                rightButton.setVisibility(View.VISIBLE);
                leftButton.setVisibility(View.VISIBLE);
                equalButton.setVisibility(View.VISIBLE);
                rightText.setVisibility(View.VISIBLE);
                leftText.setVisibility(View.VISIBLE);

                startTime = new Timestamp(System.currentTimeMillis());

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialScalingGame");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }

                enableButtons();

                createRound();

                //kalutera na mhn feugei to button ekkinisis ΚΑΛΥΤΕΡΑ View.INVISIBLE
                startButton.setVisibility(View.GONE);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //textQuestion.setText("Η αριστερή πλευρά είναι Μεγαλύτερη από την δεξία.");

                endspeed = new Timestamp(System.currentTimeMillis());
                long speedtime = endspeed.getTime() - startspeed.getTime();
                double speedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(speedtime);
                totalspeed +=speedTimeInSeconds;
                choice = -1;

                checkRound();

                disableButtons();

                if (RoundsCounter>TotalRounds)
                {
                    endTime = new Timestamp(System.currentTimeMillis());
                    long playtime = endTime.getTime() - startTime.getTime();
                    double playTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(playtime);
                    if (playTimeInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        playTimeInSeconds = THRESHOLD_EASY;
                    }
                    else if (playTimeInSeconds > THRESHOLD_ALL)
                    {
                        playTimeInSeconds = THRESHOLD_ALL;
                    }
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/TotalRounds,playTimeInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();

                }
                else {

                    RoundTimer.start();
                    replayTutorial.setClickable(false);
                    replayTutorial.setAlpha(0.5f);

                }
                hidebuttonsdisplayMsgs();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //textQuestion.setText("Η αριστερή πλευρά είναι Μικρότερη από την δεξία.");

                endspeed = new Timestamp(System.currentTimeMillis());
                long speedtime = endspeed.getTime() - startspeed.getTime();
                double speedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(speedtime);
                totalspeed +=speedTimeInSeconds;

                choice = 1;

                checkRound();

                disableButtons();

                if (RoundsCounter>TotalRounds)
                {
                    endTime = new Timestamp(System.currentTimeMillis());
                    long playtime = endTime.getTime() - startTime.getTime();
                    double playTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(playtime);
                    if (playTimeInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        playTimeInSeconds = THRESHOLD_EASY;
                    }
                    else if (playTimeInSeconds > THRESHOLD_ALL)
                    {
                        playTimeInSeconds = THRESHOLD_ALL;
                    }
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/TotalRounds,playTimeInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
                else {

                    RoundTimer.start();
                    replayTutorial.setClickable(false);
                    replayTutorial.setAlpha(0.5f);
                }
                hidebuttonsdisplayMsgs();
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                //textQuestion.setText("Οι δύο πλευρές είναι ίσες.");


                endspeed = new Timestamp(System.currentTimeMillis());
                long speedtime = endspeed.getTime() - startspeed.getTime();
                double speedTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(speedtime);
                totalspeed +=speedTimeInSeconds;

                choice = 0;

                checkRound();

                disableButtons();

                if (RoundsCounter>TotalRounds)
                {
                    endTime = new Timestamp(System.currentTimeMillis());
                    long playtime = endTime.getTime() - startTime.getTime();
                    double playTimeInSeconds = TimeUnit.MILLISECONDS.toSeconds(playtime);
                    if (playTimeInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        playTimeInSeconds = THRESHOLD_EASY;
                    }
                    else if (playTimeInSeconds > THRESHOLD_ALL)
                    {
                        playTimeInSeconds = THRESHOLD_ALL;
                    }
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/TotalRounds,playTimeInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
                else {

                    RoundTimer.start();
                    replayTutorial.setClickable(false);
                    replayTutorial.setAlpha(0.5f);
                }
                hidebuttonsdisplayMsgs();
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

    private void onbackAndExitCode(){

        if (RoundTimer != null) {
            RoundTimer.cancel();
        }

        if (RoundsCounter == 1)
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
            if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
            {
                totalPlayInSeconds = THRESHOLD_EASY;
            }
            else if (totalPlayInSeconds > THRESHOLD_ALL)
            {
                totalPlayInSeconds = THRESHOLD_ALL;
            }
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 1, totalPoints, (double) hit / TotalRounds, totalspeed / RoundsCounter, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }


    @Override
    public void onBackPressed() {
        onbackAndExitCode();
    }



    public void disableButtons()
    {
        leftButton.setEnabled(false);
        leftButton.setAlpha(0.5f);

        equalButton.setEnabled(false);
        equalButton.setAlpha(0.5f);

        rightButton.setEnabled(false);
        rightButton.setAlpha(0.5f);

    }

    public void enableButtons()
    {
        leftButton.setEnabled(true);
        leftButton.setAlpha(1f);

        equalButton.setEnabled(true);
        equalButton.setAlpha(1f);

        rightButton.setEnabled(true);
        rightButton.setAlpha(1f);

    }


    public void initializeDivideNumbers()
    {
        divideNumbers.put(4,2);
        divideNumbers.put(6,2);
        divideNumbers.put(6,3);
        divideNumbers.put(8,4);
        divideNumbers.put(8,2);
        divideNumbers.put(9,3);

        divideNumbers.put(10,2);
        divideNumbers.put(12,2);
        divideNumbers.put(15,3);
        divideNumbers.put(16,2);
        divideNumbers.put(18,2);

        divideNumbers.put(20,2);
        divideNumbers.put(20,5);
        divideNumbers.put(22,2);
        divideNumbers.put(24,2);
        divideNumbers.put(26,2);
        divideNumbers.put(28,2);

        divideNumbers.put(30,2);
        divideNumbers.put(30,3);
        divideNumbers.put(32,2);
        divideNumbers.put(38,2);

        divideNumbers.put(40,2);
        divideNumbers.put(40,5);
        divideNumbers.put(40,8);
        divideNumbers.put(40,10);
        divideNumbers.put(48,2);

        divideNumbers.put(50,5);
        divideNumbers.put(50,10);

        divideNumbers.put(60,2);
        divideNumbers.put(60,3);
        divideNumbers.put(60,10);

        divideNumbers.put(70,2);
        divideNumbers.put(70,10);

        divideNumbers.put(80,2);
        divideNumbers.put(80,8);
        divideNumbers.put(80,10);
        divideNumbers.put(80,20);

        divideNumbers.put(90,2);
        divideNumbers.put(90,3);
        divideNumbers.put(90,10);
    }


    public void createRound()
    {

        textQuestion.setText("Η αριστερή πλευρά είναι __________ από την δεξιά.");

        boolean mediumLeft = false;
        boolean mediumRight = false;

        if (RoundsCounter>TotalRounds)
        {
            textRounds.setText("Τέλος");

        }
        else
        {
            textRounds.setText(RoundsCounter +" / "+TotalRounds);
        }

        //gia na proxwrhsoun oi dyskolies
        if (menuDifficulty.equals("ALL"))
        {

            if (RoundsCounter == 1) {
                currentDifficulty = "EASY";
            }
            else if (RoundsCounter >1 && RoundsCounter <=3)
            {
                currentDifficulty = "MEDIUM";
            }
            else
            {
                currentDifficulty = "ADVANCED";
            }
        }

        if (menuDifficulty.equals("EASYtoMEDIUM"))
        {
            if (RoundsCounter<=2)
            {
                currentDifficulty = "EASY";
            }
            else
            {
                currentDifficulty = "MEDIUM";
            }

        }


        textQuestion.setTextColor(Color.BLACK);

        Random r = new Random();

        if (currentDifficulty.equals("EASY"))
        {
            leftNumber = r.nextInt((99 - 1) + 1) + 1;

            rightNumber = r.nextInt((99 - 1) + 1) + 1;

            leftText.setText(String.valueOf(leftNumber));

            rightText.setText(String.valueOf(rightNumber));

            result = calculateCorrectResult();

        }
        else if (currentDifficulty.equals("MEDIUM"))
        {
            int mediumMode;

            mediumMode = r.nextInt((2 - 1) + 1) + 1;

            //travaw tyxaia to 1 h to 2 apo to hashmap me ta symbola
            //dhladh + or -

            int randomSymbol = r.nextInt((2 - 1) + 1) + 1;

            char mediumSymbol = symbols.get(randomSymbol);

            if (mediumMode==1)
            {
                int leftNumber1;
                int leftNumber2;

                if (mediumSymbol=='+')
                {
                    leftNumber1 = r.nextInt((99 - 1) + 1) + 1;

                    //panta to prwto noumero na einai megalytero gia na einai pio eykolh h prosthesh
                    //kai antistoixa h afairesh na mhn vgazei arnhtiko

                    do {
                        leftNumber2 = r.nextInt((99 - 1) + 1) + 1;
                    } while (leftNumber2 > leftNumber1);

                    leftNumber = leftNumber1 + leftNumber2;

                    leftText.setText(leftNumber1 + " + " +leftNumber2);

                    rightNumber = r.nextInt((99 - 1) + 1) + 1;

                    rightText.setText(String.valueOf(rightNumber));
                }
                else if (mediumSymbol=='-')
                {
                    leftNumber1 = r.nextInt((99 - 1) + 1) + 1;

                    do {
                        leftNumber2 = r.nextInt((99 - 1) + 1) + 1;
                    } while (leftNumber2 > leftNumber1);

                    leftNumber = leftNumber1 - leftNumber2;

                    leftText.setText(leftNumber1 + " - " +leftNumber2);

                    rightNumber = r.nextInt((99 - 1) + 1) + 1;

                    rightText.setText(String.valueOf(rightNumber));
                }
            }
            else if (mediumMode==2)
            {
                int rightNumber1;
                int rightNumber2;

                if (mediumSymbol=='+')
                {
                    rightNumber1 = r.nextInt((99 - 1) + 1) + 1;

                    do {
                        rightNumber2 = r.nextInt((99 - 1) + 1) + 1;
                    } while (rightNumber2 > rightNumber1);

                    rightNumber = rightNumber1 + rightNumber2;

                    rightText.setText(rightNumber1 + " + " +rightNumber2);

                    leftNumber = r.nextInt((99 - 1) + 1) + 1;

                    leftText.setText(String.valueOf(leftNumber));
                }
                else if (mediumSymbol=='-')
                {
                    rightNumber1 = r.nextInt((99 - 1) + 1) + 1;

                    do {
                        rightNumber2 = r.nextInt((99 - 1) + 1) + 1;
                    } while (rightNumber2 > rightNumber1);

                    rightNumber = rightNumber1 - rightNumber2;

                    rightText.setText(rightNumber1 + " - " +rightNumber2);

                    leftNumber = r.nextInt((99 - 1) + 1) + 1;

                    leftText.setText(String.valueOf(leftNumber));
                }
            }

            result = calculateCorrectResult();
        }
        else if (currentDifficulty.equals("ADVANCED"))
        {

            int advancedMode;

            advancedMode = r.nextInt((2 - 1) + 1) + 1;

            //travaw tyxaia to 3 h to 4 apo to hashmap me ta symbola
            //dhladh x or /
            int randomSymbol = r.nextInt((4 - 3) + 1) + 3;

            char advancedSymbol = symbols.get(randomSymbol);


            //travaw tyxaia to 1 h to 2 apo to hashmap me ta symbola
            //dhladh + or -
            int randomMediumSymbol = r.nextInt((2 - 1) + 1) + 1;

            char mediumSymbol = symbols.get(randomMediumSymbol);


            int leftNumber1;
            int leftNumber2;

            leftNumber1 = r.nextInt((99 - 1) + 1) + 1;

            //panta to prwto noumero na einai megalytero gia na einai pio eykolh h prosthesh
            //kai antistoixa h afairesh na mhn vgazei arnhtiko
            do {
                leftNumber2 = r.nextInt((99 - 1) + 1) + 1;
            } while (leftNumber2 > leftNumber1);


            int rightNumber1;
            int rightNumber2;

            rightNumber1 = r.nextInt((99 - 1) + 1) + 1;

            //panta to prwto noumero na einai megalytero gia na einai pio eykolh h prosthesh
            //kai antistoixa h afairesh na mhn vgazei arnhtiko
            do {
                rightNumber2 = r.nextInt((99 - 1) + 1) + 1;
            } while (rightNumber2 > rightNumber1);

            if (advancedMode==1)
            {
                if (advancedSymbol=='×')
                {
                    //eidikoi arithmoi mexri to 11 gia ton pollaplasiasmo

                    int specialNumber1 = r.nextInt((11 - 1) + 1) + 1;
                    int specialNumber2;

                    do {
                        specialNumber2 = r.nextInt((11 - 1) + 1) + 1;
                    } while (specialNumber2 > specialNumber1);

                    leftNumber = specialNumber1 * specialNumber2;

                    //ston pollaplasiasmo vazw prwta ton mikro arithmo gia na einai pio eukolh h praksh
                    leftText.setText(specialNumber2 + " x " +specialNumber1);

                    if (mediumSymbol=='+')
                    {
                        rightNumber = rightNumber1 + rightNumber2;

                        rightText.setText(rightNumber1 + " + " +rightNumber2);
                    }
                    else if (mediumSymbol=='-')
                    {
                        rightNumber = rightNumber1 - rightNumber2;

                        rightText.setText(rightNumber1 + " - " +rightNumber2);
                    }
                }
                else if (advancedSymbol=='÷')
                {
                    int divideNumber1;
                    int divideNumber2;

                    Random generator = new Random();
                    Object[] keys = divideNumbers.keySet().toArray();
                    int randomKey = (int) keys[generator.nextInt(keys.length)];

                    divideNumber1 = randomKey;

                    divideNumber2 = divideNumbers.get(randomKey);

                    leftNumber = divideNumber1 / divideNumber2;

                    leftText.setText(divideNumber1 + " ÷ " +divideNumber2);

                    if (mediumSymbol=='+')
                    {
                        rightNumber = rightNumber1 + rightNumber2;

                        rightText.setText(rightNumber1 + " + " +rightNumber2);
                    }
                    else if (mediumSymbol=='-')
                    {
                        rightNumber = rightNumber1 - rightNumber2;

                        rightText.setText(rightNumber1 + " - " +rightNumber2);
                    }
                }
            }
            else if (advancedMode==2)
            {

                if (advancedSymbol=='×')
                {

                    int specialNumber1 = r.nextInt((11 - 1) + 1) + 1;
                    int specialNumber2;

                    do {
                        specialNumber2 = r.nextInt((11 - 1) + 1) + 1;
                    } while (specialNumber2 > specialNumber1);


                    rightNumber = specialNumber1 * specialNumber2;

                    //ston pollaplasiasmo vazw prwta ton mikro arithmo gia na einai pio eukolh h praksh
                    rightText.setText(specialNumber2 + " x " +specialNumber1);

                    if (mediumSymbol=='+')
                    {
                        leftNumber = leftNumber1 + leftNumber2;

                        leftText.setText(leftNumber1 + " + " +leftNumber2);
                    }
                    else if (mediumSymbol=='-')
                    {
                        leftNumber = leftNumber1 - leftNumber2;

                        leftText.setText(leftNumber1 + " - " +leftNumber2);
                    }
                }
                else if (advancedSymbol=='÷')
                {
                    int divideNumber1;
                    int divideNumber2;

                    Random generator = new Random();
                    Object[] keys = divideNumbers.keySet().toArray();
                    int randomKey = (int) keys[generator.nextInt(keys.length)];

                    divideNumber1 = randomKey;

                    divideNumber2 = divideNumbers.get(randomKey);

                    rightNumber = divideNumber1 / divideNumber2;

                    rightText.setText(divideNumber1 + " ÷ " +divideNumber2);

                    if (mediumSymbol=='+')
                    {
                        leftNumber = leftNumber1 + leftNumber2;

                        leftText.setText(leftNumber1 + " + " +leftNumber2);
                    }
                    else if (mediumSymbol=='-')
                    {
                        leftNumber = leftNumber1 - leftNumber2;

                        leftText.setText(leftNumber1 + " - " +leftNumber2);
                    }
                }
            }

            result = calculateCorrectResult();
        }

        startspeed = new Timestamp(System.currentTimeMillis());


        RoundsCounter++;
    }

    public int calculateCorrectResult()
    {
        int res;

        if (leftNumber>rightNumber)
        {
            res = -1;

            rightResult = "Η αριστερή πλευρά είναι ΜΕΓΑΛΥΤΕΡΗ από την δεξιά.";
        }
        else if (rightNumber>leftNumber)
        {
            res = 1;

            rightResult = "Η αριστερή πλευρά είναι ΜΙΚΡΟΤΕΡΗ από την δεξιά.";
        }
        else
        {
            res = 0;

            rightResult = "Οι δύο πλευρές είναι ίσες.";
        }

        return res;
    }

    public void checkRound()
    {
        if (result==choice)
        {
            hit++;
            missPoints=false;
            trueCounter++;

            textQuestion.setText(CORRECT+rightResult);

            //tsekare prasinaki
            textQuestion.setTextColor(Color.parseColor("#00CC00"));

        }
        else
        {
            miss++;
            missPoints=true;

            textQuestion.setText(WRONG+rightResult);
            textQuestion.setTextColor(Color.RED);

        }
        countPoints();
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

    private void hidebuttonsdisplayMsgs(){
        if (RoundsCounter>TotalRounds)
        {
            textMsgTime.setText("");
        }
        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
        textsLinear.setVisibility(View.VISIBLE);
        leftButton.setVisibility(View.INVISIBLE);
        rightButton.setVisibility(View.INVISIBLE);
        equalButton.setVisibility(View.INVISIBLE);
    }
    private void hideMsgDisplayButtons(){
        textsLinear.setVisibility(View.INVISIBLE);
        leftButton.setVisibility(View.VISIBLE);
        rightButton.setVisibility(View.VISIBLE);
        equalButton.setVisibility(View.VISIBLE);
    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(ScalingGame.this,R.raw.tutorial_scalinggame,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialScalingGame");
    }

    public void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,ScalingGame.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "ScalingGame");
    }

}

