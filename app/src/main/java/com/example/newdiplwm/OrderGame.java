package com.example.newdiplwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class OrderGame extends AppCompatActivity implements View.OnClickListener{

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
    private static final String CURRENTDIFFICULTY = "CURRENTDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String MATCH = "MATCH";
    private static final String IMAGEVIEWS = "IMAGEVIEWS";
    private static final String CLICK = "CLICK";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String PICKEDIMAGES = "PICKEDIMAGES";
    private static final String LISTSELECTION = "LISTSELECTION";
    private static final String RIGHTPICK = "RIGHTPICK";
    private static final String CASEMISSOBJ = "CASEMISSOBJ";
    private static final String FAlSEPICK = "FAlSEPICK";
    private static final String CLEANLIST = "CLEANLIST";
    private static final String ELECTRONICSLIST = "ELECTRONICSLIST";
    private static final String SWEETSLIST = "SWEETSLIST";
    private static final String FRUITSlIST = "FRUITSlIST";
    private static final String JANKFOODLIST = "JANKFOODLIST";
    private static final String CLOCK = "CLOCK";

    private ImageView imagebutton1, imagebutton2, imagebutton3, imagebutton4, imagebutton5;
    private MaterialButton startButton, missingObj;
    private TextView textRounds, textTimer, animPointsText;

    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;

    private static final String GAME_ID = "GAME_ID";
    private static final String USER_ID = "USER_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private ArrayList<Integer> cleanList = new ArrayList<>();
    private ArrayList<Integer> electonicsList = new ArrayList<>();
    private ArrayList<Integer> sweetsList = new ArrayList<>();
    private ArrayList<Integer> fruitsList = new ArrayList<>();
    private ArrayList<Integer> jankfoodList = new ArrayList<>();

    private ArrayList<Integer> pickedImages = new ArrayList<>();

    private HashMap<Integer, ArrayList<Integer>> listselection = new HashMap<Integer, ArrayList<Integer>>();
    private CountDownTimer Timer;
    private static final long START_TIME_IN_MILLIS = 2000;
    private long mTimeLeftInMillis = 0;

    private HashMap<Integer, Integer> imageIDS = new HashMap<Integer, Integer>();
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();

    private HashMap<String,Integer> pointsHashMap = new HashMap<String, Integer>();
    private int user_id, game_id, currentRound=0 , TotalRounds=0;
    private int hit = 0 , miss = 0 , totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;
    private String menuDifficulty,currentDifficulty;

    private int click=0 , rightpick=0 ,vibeduration = 1000, caseMissingObj = 0;

    private  boolean falsepick =false;

    private Vibrator vibe;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private ArrayList<Integer> helperwhenRotate = new ArrayList<>();
    private static final String HELPERWHENROTATE = "HELPERWHENROTATE";
    private static final String RANDLIST = "RANDLIST";
    private int randlist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        assignAllButtons();

        if (savedInstanceState != null)
        {
            startButton.setVisibility(View.INVISIBLE);
            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            imageIDS = (HashMap<Integer, Integer>) savedInstanceState.getSerializable(MATCH);
            imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
            pickedImages = savedInstanceState.getIntegerArrayList(PICKEDIMAGES);
            cleanList = savedInstanceState.getIntegerArrayList(CLEANLIST);
            electonicsList = savedInstanceState.getIntegerArrayList(ELECTRONICSLIST);
            sweetsList = savedInstanceState.getIntegerArrayList(SWEETSLIST);
            fruitsList = savedInstanceState.getIntegerArrayList(FRUITSlIST);
            jankfoodList = savedInstanceState.getIntegerArrayList(JANKFOODLIST);
            listselection = (HashMap<Integer, ArrayList<Integer>>) savedInstanceState.getSerializable(LISTSELECTION);
            helperwhenRotate = savedInstanceState.getIntegerArrayList(HELPERWHENROTATE);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);
            randlist = savedInstanceState.getInt(RANDLIST);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            click =savedInstanceState.getInt(CLICK);
            caseMissingObj =savedInstanceState.getInt(CASEMISSOBJ);
            rightpick =savedInstanceState.getInt(RIGHTPICK);
            hit =savedInstanceState.getInt(HIT);
            miss =savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
            falsepick = savedInstanceState.getBoolean(FAlSEPICK);
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            currentRound = savedInstanceState.getInt(CURRENTROUND);



            if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
            {

                if (helperwhenRotate.isEmpty()) {

                    for (int i = 0; i < pickedImages.size(); i++) {
                        ImageView v = findViewById(imageviews.get(i));
                        v.setClickable(false);
                        v.setImageResource(pickedImages.get(i));

                    }
                }
                Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {
                        mTimeLeftInMillis=l;
                        textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
                    }

                    @Override
                    public void onFinish() {

                        mTimeLeftInMillis=0;
                        setTimerobjAdv(randlist);
                        for (int imgv:helperwhenRotate)
                        {
                            ImageView iv= findViewById(imgv);
                            iv.setClickable(false);
                            iv.setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                        }

                    }
                }.start();

            }
            else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
            {
                if (helperwhenRotate.isEmpty()) {
                    imagebutton1.setClickable(false);
                    imagebutton2.setClickable(false);
                    imagebutton1.setImageResource(pickedImages.get(0));
                    imagebutton2.setImageResource(pickedImages.get(1));
                }
                Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {
                        mTimeLeftInMillis=l;
                        textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
                    }

                    @Override
                    public void onFinish() {

                        mTimeLeftInMillis=0;
                        setTimerobjmed(randlist);
                        for (int imgv:helperwhenRotate)
                        {
                            ImageView iv= findViewById(imgv);
                            iv.setClickable(false);
                            iv.setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                        }

                    }
                }.start();
            }
            else
            {
                imagebutton2.setClickable(false);
                imagebutton2.setImageResource(pickedImages.get(0));
                Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                    @Override
                    public void onTick(long l) {
                        mTimeLeftInMillis=l;
                        textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
                    }

                    @Override
                    public void onFinish() {
                        mTimeLeftInMillis=0;
                        setTimerobjez(randlist);
                        for (int imgv:helperwhenRotate)
                        {
                            ImageView iv= findViewById(imgv);
                            iv.setClickable(false);
                            iv.setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                        }



                    }
                }.start();

            }




        }
        else{
            Intent intent = getIntent();
            Bundle extras = intent.getExtras();
            user_id = extras.getInt(USER_ID);
            game_id = extras.getInt(GAME_ID);
            menuDifficulty = extras.getString(DIFFICULTY);
            initialiseLists();
            fillListImageview();
        }


        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rightpick=0;
                createRound();
            }
        });

    }

    private void createRound(){
        startButton.setVisibility(View.INVISIBLE);

        initHashmaplistSelection();

        Random rand = new Random();
        randlist = rand.nextInt(5)+1;

        Collections.shuffle(listselection.get(randlist));

        if (currentRound == 0)
        {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))){
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz(randlist);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMedium(randlist);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds=3;
            displayGameAdv(randlist);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;

            if (currentRound<=1){
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz(randlist);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium(randlist);
            }
        }
        else
        {
            TotalRounds =5;
            if (currentRound<1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz(randlist);
            }
            else if (currentRound>=1 && currentRound<=2)
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMedium(randlist);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv(randlist);
            }
        }
        textRounds.setText((currentRound+1)+"/"+TotalRounds);

    }

    private void displayGameEz(final int randlist)
    {
        unclickable();

        Random rand = new Random();

        int randpick = rand.nextInt(3);
        int pickedImage  = listselection.get(randlist).get(randpick);

        imagebutton2.setImageResource(pickedImage);
        pickedImages.add(listselection.get(randlist).get(randpick));
        mTimeLeftInMillis = 4000;

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis=0;
                setTimerobjez(randlist);

            }
        };
        Timer.start();
    }

    private void setTimerobjez(int randlist){
        textTimer.setText("Ποια αντικείμενα ήταν στην αρχική παραγγελία;");
        imagebutton2.setImageResource(0);

        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(listselection.get(randlist).get(i));
            imageIDS.put(imageviews.get(i),listselection.get(randlist).get(i));


        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());

    }
    private void displayGameMedium(final int randlist){
        unclickable();
        Random rand = new Random();
        int randpick1 = rand.nextInt(2);
        int randpick2 = rand.nextInt(2)+2;
        int picked1  = listselection.get(randlist).get(randpick1);
        int picked2  = listselection.get(randlist).get(randpick2);
        imagebutton1.setImageResource(picked1);
        imagebutton2.setImageResource(picked2);

        pickedImages.add(listselection.get(randlist).get(randpick1));
        pickedImages.add(listselection.get(randlist).get(randpick2));
        mTimeLeftInMillis = 6000;


        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis=0;

                setTimerobjmed(randlist);
            }
        };
        Timer.start();
    }

    private void setTimerobjmed(int randlist)
    {
        textTimer.setText("Ποια αντικείμενα ήταν στην αρχική παραγγελία;");
        imagebutton1.setImageResource(0);
        imagebutton2.setImageResource(0);

        for (int i=0;i<4;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(listselection.get(randlist).get(i));
            imageIDS.put(imageviews.get(i),listselection.get(randlist).get(i));


        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());
    }

    private void displayGameAdv(final int randlist){

        unclickable();
        Random rand = new Random();
        caseMissingObj = rand.nextInt(2);
        ArrayList<Integer> randomNums = new ArrayList<Integer>();
        for (int i = 0 ; i<5; i++)
        {
            randomNums.add(i);

        }
        Collections.shuffle(randomNums);

        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));

            v.setImageResource(listselection.get(randlist).get(randomNums.get(i)));
            pickedImages.add(listselection.get(randlist).get(randomNums.get(i)));
        }

        if (caseMissingObj == 1) {
            Collections.shuffle(pickedImages);
            ListIterator  itr = listselection.get(randlist).listIterator();
            while (itr.hasNext())
            {
                int temp = (int) itr.next();
                if (temp == pickedImages.get(0))
                {
                    itr.remove();
                    break;
                }
            }
        }
        mTimeLeftInMillis= 9000;

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) {
                mTimeLeftInMillis=l;
                textTimer.setText("Remain "+ mTimeLeftInMillis/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                mTimeLeftInMillis=0;

                setTimerobjAdv(randlist);

            }
        };
        Timer.start();
    }
    private void setTimerobjAdv(int randlist){
        textTimer.setText("Ποια αντικείμενα ήταν στην αρχική παραγγελία;");
        for (int i=0;i<3;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(0);
        }

        for (int i=0;i<5;i++)
        {
            ImageView v = findViewById(imageviews.get(i));
            v.setImageResource(listselection.get(randlist).get(i));
            imageIDS.put(imageviews.get(i),listselection.get(randlist).get(i));
        }
        clickable();
        startspeed = new Timestamp(System.currentTimeMillis());
        missingObj.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClick(View view) {
        Timer = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                for (int imageviewId : imageviews) {
                    ImageView v = findViewById(imageviewId);
                    v.setImageResource(0);
                    v.setColorFilter(0);
                }
                mTimeLeftInMillis=0;

                falsepick = false;
                currentRound++;
                helperwhenRotate.clear();
                imageIDS.clear();
                pickedImages.clear();
                startButton.setVisibility(View.VISIBLE);
                missingObj.setVisibility(View.INVISIBLE);
                missingObj.setText(getResources().getString(R.string.MissingObjects));

                if (currentRound == TotalRounds)
                {
                    endTime = new Timestamp(System.currentTimeMillis());
                    long longTime = endTime.getTime() - startTime.getTime();
                    float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,-1,totalPoints,(double)hit/(hit+miss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
            }
        };

        click++;
        if (missingObj.getId() == view.getId() && caseMissingObj == 1)
        {
            startAnimation();
            MaterialButton iv = (MaterialButton)findViewById(view.getId());
            iv.setText("Σωστά");
            hit++;
            trueCounter++;
            countPoints();
            Timer.start();
        }
        else if (imageIDS.containsKey(view.getId()))
        {

            endspeed = new Timestamp(System.currentTimeMillis());
            long diffspeed = endspeed.getTime() - startspeed.getTime();
            double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
            totalspeed += speedseconds;
            if (pickedImages.contains(imageIDS.get(view.getId())))
            {
                view.setClickable(false);
                rightpick++;
                ImageView iv = (ImageView)findViewById(view.getId());
                iv.setColorFilter(Color.GREEN, PorterDuff.Mode.LIGHTEN);
                helperwhenRotate.add(view.getId());

            }
            else
            {
                falsepick = true;
            }


            if (rightpick == pickedImages.size())
            {
                startAnimation();
                hit++;
                trueCounter++;
                countPoints();
                Timer.start();
            }
            else if (falsepick)
            {
                startAnimation();
                ImageView iv = (ImageView)findViewById(view.getId());
                iv.setColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN);
                Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                view.startAnimation(animShake);
                vibe.vibrate(vibeduration);
                miss++;
                missPoints = true;
                countPoints();
                Timer.start();
            }

        }
        else
        {
            startAnimation();
            MaterialButton iv = (MaterialButton)findViewById(view.getId());
            iv.setText("Λαθος");
            Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
            view.startAnimation(animShake);
            vibe.vibrate(vibeduration);
            miss++;
            missPoints = true;
            countPoints();
            Timer.start();

        }
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,OrderGame.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "OrderGame");
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
            missPoints = false;
        }
        totalPoints += currentPoints;
        animPointsText.setText("+ " +String.valueOf(currentPoints));
        if (currentPoints == 0)
        {
            animPointsText.setTextColor(Color.RED);
        }
        else
            animPointsText.setTextColor(Color.GREEN);
    }

    private void startAnimation(){
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animPointsText,"y",500f,-500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha =  ObjectAnimator.ofFloat(animPointsText,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY,alpha);
        animatorSet.start();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MENUDIFFICULTY,menuDifficulty);
        outState.putString(CURRENTDIFFICULTY,currentDifficulty);
        outState.putInt(GAME_ID,game_id);
        outState.putInt(USER_ID,user_id);
        outState.putIntegerArrayList(IMAGEVIEWS,imageviews);
        outState.putIntegerArrayList(PICKEDIMAGES,pickedImages);
        outState.putIntegerArrayList(ELECTRONICSLIST,electonicsList);
        outState.putIntegerArrayList(FRUITSlIST,fruitsList);
        outState.putIntegerArrayList(SWEETSLIST,sweetsList);
        outState.putIntegerArrayList(JANKFOODLIST,jankfoodList);
        outState.putIntegerArrayList(CLEANLIST,cleanList);
        outState.putIntegerArrayList(HELPERWHENROTATE,helperwhenRotate);
        outState.putSerializable(MATCH,imageIDS);
        outState.putSerializable(LISTSELECTION,listselection);



        outState.putInt(TOTALROUNDS,TotalRounds);
        outState.putInt(CURRENTROUND,currentRound);
        outState.putInt(RIGHTPICK,rightpick);
        outState.putInt(CASEMISSOBJ,caseMissingObj);
        outState.putInt(RANDLIST,randlist);

        outState.putBoolean(MISSPOINTS,missPoints);
        outState.putBoolean(FAlSEPICK,falsepick);
        outState.putInt(HIT,hit);
        outState.putInt(MISS,miss);
        outState.putInt(TOTALPOINTS,totalPoints);
        outState.putInt(TRUECOUNTER,trueCounter);
        outState.putInt(CLICK,click);
        outState.putDouble(TOTALSPEED,totalspeed);
        outState.putSerializable(STARTTIME,startTime);
        outState.putSerializable(ENDTIME,endTime);
        outState.putSerializable(STARTSPEED,startspeed);
        outState.putSerializable(ENDSPEED,endspeed);
        outState.putLong(CLOCK,mTimeLeftInMillis);


    }

    private  void fillListImageview(){
        imageviews.add(R.id.imageView1OG);
        imageviews.add(R.id.imageView2OG);
        imageviews.add(R.id.imageView3OG);
        imageviews.add(R.id.imageView4OG);
        imageviews.add(R.id.imageView5OG);
    }

    private void initHashmaplistSelection(){
        listselection.put(1,cleanList);
        listselection.put(2,electonicsList);
        listselection.put(3,sweetsList);
        listselection.put(4,fruitsList);
        listselection.put(5,jankfoodList);
    }


    private void assignAllButtons(){

        imagebutton1 = findViewById(R.id.imageView1OG);
        imagebutton2 = findViewById(R.id.imageView2OG);
        imagebutton3 = findViewById(R.id.imageView3OG);
        imagebutton4 = findViewById(R.id.imageView4OG);
        imagebutton5 = findViewById(R.id.imageView5OG);

        startButton = findViewById(R.id.startButtonOG);
        missingObj = findViewById(R.id.missingObjectsOG);
        missingObj.setVisibility(View.INVISIBLE);
        textRounds = findViewById(R.id.textRoundsOG);
        textTimer = findViewById(R.id.textTimerOG);
        animPointsText = findViewById(R.id.AnimTextPointsOG);

        imagebutton1.setOnClickListener(this);
        imagebutton2.setOnClickListener(this);
        imagebutton3.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
        missingObj.setOnClickListener(this);
    }

    private void clickable(){
        imagebutton1.setClickable(true);
        imagebutton2.setClickable(true);
        imagebutton3.setClickable(true);
        imagebutton4.setClickable(true);
        imagebutton5.setClickable(true);
    }
    private  void unclickable(){
        imagebutton1.setClickable(false);
        imagebutton2.setClickable(false);
        imagebutton3.setClickable(false);
        imagebutton4.setClickable(false);
        imagebutton5.setClickable(false);
    }

    private void initialiseLists(){
        cleanList.add(R.drawable.og_h_broom);
        cleanList.add(R.drawable.og_h_mop);
        cleanList.add(R.drawable.og_h_pills);
        cleanList.add(R.drawable.og_h_shampoo);
        cleanList.add(R.drawable.og_h_sponge);
        cleanList.add(R.drawable.og_h_spray);
        cleanList.add(R.drawable.og_h_toothbrush);
        cleanList.add(R.drawable.og_h_toothpaste);

        electonicsList.add(R.drawable.og_e_camera);
        electonicsList.add(R.drawable.og_e_headset);
        electonicsList.add(R.drawable.og_e_keyboard);
        electonicsList.add(R.drawable.og_e_mouse);
        electonicsList.add(R.drawable.og_e_smartphone);
        electonicsList.add(R.drawable.og_e_speakers);
        electonicsList.add(R.drawable.og_e_tv);
        electonicsList.add(R.drawable.og_e_usb);

        sweetsList.add(R.drawable.og_c_cake);
        sweetsList.add(R.drawable.og_c_candy);
        sweetsList.add(R.drawable.og_c_coffee);
        sweetsList.add(R.drawable.og_c_donut);
        sweetsList.add(R.drawable.og_c_icecream);
        sweetsList.add(R.drawable.og_c_juice);
        sweetsList.add(R.drawable.og_c_milkshake);
        sweetsList.add(R.drawable.og_c_muffin);

        fruitsList.add(R.drawable.og_f_apple);
        fruitsList.add(R.drawable.og_f_banana);
        fruitsList.add(R.drawable.og_f_cherry);
        fruitsList.add(R.drawable.og_f_grape);
        fruitsList.add(R.drawable.og_f_mellon);
        fruitsList.add(R.drawable.og_f_pineapple);
        fruitsList.add(R.drawable.og_f_strawberry);
        fruitsList.add(R.drawable.og_f_watermellon);

        jankfoodList.add(R.drawable.og_sn_burger);
        jankfoodList.add(R.drawable.og_sn_chicken);
        jankfoodList.add(R.drawable.og_sn_eggs);
        jankfoodList.add(R.drawable.og_sn_fries);
        jankfoodList.add(R.drawable.og_sn_hotdog);
        jankfoodList.add(R.drawable.og_sn_pizza);
        jankfoodList.add(R.drawable.og_sn_salad);
        jankfoodList.add(R.drawable.og_sn_steak);
    }

    //TODO na krataw instance tis o8onis otan kanei rotate
}
