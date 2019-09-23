package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
import android.util.SparseArray;
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
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShadowGame extends AppCompatActivity implements  View.OnClickListener{


    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
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
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String ANIMALS = "ANIMALS";
    private static final String FRUITS = "FRUITS";
    private static final String ELECTRONICS = "ELECTRONICS";
    private static final String VARIETY = "VARIETY";
    private static final String DISPLAYEDIMAGEVIEW = "DISPLAYEDIMAGEVIEW";
    private static final String DISPLAYEDIMAGES = "DISPLAYEDIMAGES";
    private static final String IMAGEVIEWS = "IMAGEVIEWS";
    private static final String CLOCK = "CLOCK";
    private static final String DISPLAYEDIMAGESSHADOWS = "DISPLAYEDIMAGESSHADOWS";
    private static final String CLICK = "CLICK";
    private static final String PICKEDIMAGE= "PICKEDIMAGE";
    private static final String GAMEINIT= "GAMEINIT";
    private static final String MSGHELPER = "MSGHELPER";
    private static final String NEXTROUNDTIMER = "NEXTROUNDTIMER";

    private static  final int THRESHOLD_EASY = 120;
    private static  final int THRESHOLD_ALL = 180;

    private ImageView imagebutton1,imagebutton2,imagebutton3,imagebutton4,imagebuttoncolorfull ,exit, replayTutorial;
    private MaterialButton startButton;
    private TextView textRounds,textTimer, animationTextPoints ,textMsg , textMsgTime;
    private LinearLayout logoLinear, textsLinear;
    private Vibrator vibe;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private CountDownTimer Timer , cleanTimer, nextRoundTimer;
    private long mTimeLeftInMillis, timeLeftInMillisNextRound = 0;

    private String menuDifficulty , currentDifficulty, msgHelper;

    private ArrayList<Integer> imageviews  = new ArrayList<>();
    private ArrayList<Integer> displayedImageview  = new ArrayList<>();


    private SparseIntArray animals = new SparseIntArray(12);
    private SparseIntArray fruits = new SparseIntArray(16);
    private SparseIntArray electronics = new SparseIntArray(7);
    private SparseIntArray variety = new SparseIntArray(9);
    private SparseIntArray displayedimages = new SparseIntArray(4);
    private SparseIntArray displayedimagesShadows = new SparseIntArray(4);

    private HashMap<String , Integer> pointsHashMap = new HashMap<String, Integer>();

    private SparseArray pickrandSparceArray = new SparseArray(3);

    private int user_id, game_id, currentRound=0 , TotalRounds=0;

    private int hit=0 ,miss=0 ,trueCounter=0 , totalPoints=0;
    private boolean missPoints = false;


    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;
    private int click=0 , pickedimg=0;
    private boolean gameInit = false;

    private Session session;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_game);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new Session(getApplicationContext());
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        assignAllButtons();

        if (savedInstanceState != null)
        {
            gameInit  =savedInstanceState.getBoolean(GAMEINIT);
            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            click =savedInstanceState.getInt(CLICK);
            hit =savedInstanceState.getInt(HIT);
            miss =savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);

            Timer =  userViewModel.getTimer();
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            currentRound = savedInstanceState.getInt(CURRENTROUND);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);
            imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
            displayedImageview = savedInstanceState.getIntegerArrayList(DISPLAYEDIMAGEVIEW);
            //pickrandSparceArray = savedInstanceState.getSparseParcelableArray(PICKRANDSPARSEARRAY);
            animals = (SparseIntArray) savedInstanceState.getParcelable(ANIMALS);
            fruits = (SparseIntArray) savedInstanceState.getParcelable(FRUITS);
            electronics = (SparseIntArray) savedInstanceState.getParcelable(ELECTRONICS);
            variety = (SparseIntArray) savedInstanceState.getParcelable(VARIETY);
            displayedimages = (SparseIntArray) savedInstanceState.getParcelable(DISPLAYEDIMAGES);
            displayedimagesShadows = (SparseIntArray) savedInstanceState.getParcelable(DISPLAYEDIMAGESSHADOWS);
            pickedimg = savedInstanceState.getInt(PICKEDIMAGE);
            msgHelper = savedInstanceState.getString(MSGHELPER);
            timeLeftInMillisNextRound = savedInstanceState.getLong(NEXTROUNDTIMER);

            startButton.setVisibility(View.INVISIBLE);

            if (gameInit)
            {
                logoLinear.setVisibility(View.GONE);
                imagebuttoncolorfull.setImageResource(pickedimg);

                textRounds.setText(currentRound+ " / "+ TotalRounds);
                if (currentDifficulty.equals(getResources().getString(R.string.mediumValue))|| currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {

                    Timer.cancel();
                    setTimer(mTimeLeftInMillis);
                    //Timer.onTick(mTimeLeftInMillis);
                }

                for (int i=0;i<displayedimagesShadows.size();i++)
                {
                    ImageView test = findViewById(displayedimagesShadows.keyAt(i));
                    test.setImageResource(displayedimagesShadows.valueAt(i));

                }
            }
            else
            {
                if (currentRound == 0)
                {
                    logoLinear.setVisibility(View.VISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                    clearScreen();
                    unclickable();

                }
                else
                {
                    disableReplayTut();
                    textMsg.setText(msgHelper);
                    nextRoundTimer = userViewModel.getNextRoundTimer();
                    nextRoundTimer.cancel();
                    textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
                    textsLinear.setVisibility(View.VISIBLE);

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
                                textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                            }


                        }

                        @Override
                        public void onFinish() {

                            timeLeftInMillisNextRound = 0;

                            if (currentRound == TotalRounds)
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
                                fillListImageview();
                                initaliseSparceArray();
                                gameInit = true;
                                createRound();
                            }

                        }
                    }.start();
                    userViewModel.setNextRoundTimer(nextRoundTimer);
                    logoLinear.setVisibility(View.GONE);
                    unclickable();
//                    startButton.setVisibility(View.VISIBLE);
//                    startButton.setText(getResources().getString(R.string.nextRound));
                    textRounds.setText(currentRound+ " / "+ TotalRounds);
                }

            }

        }
        else{

            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty = session.getModeSession();
            unclickable();
        }


        if (!session.getPlayAgainVideo() && currentRound == 0) {
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                showTutorialPopUp(R.raw.tutorial_shadowgame_landscape);

            }
            else
            {
                showTutorialPopUp(R.raw.tutorial_shadowgame_portrait);
            }

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialShadowGame");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }


        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);




        matchlists();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialShadowGame");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                logoLinear.setVisibility(View.GONE);
                fillListImageview();
                initaliseSparceArray();
                gameInit = true;
                createRound();
            }
        });



    }

    private void displayGameAdv(){
        mTimeLeftInMillis = 8000;
        Random rand = new Random();

        int picklist = rand.nextInt(pickrandSparceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSparceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));
        pickedimg = temp.keyAt(pickImage);

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));
        displayedImageview.add(imageviews.get(imageviewshadow));

        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));
        displayedimagesShadows.put(imageviews.get(imageviewshadow),temp.valueAt(pickImage));


        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);

        for (int imgv:imageviews)
        {

                ImageView ivv = findViewById(imgv);
                int otherImages = rand.nextInt(temp.size());
                ivv.setImageResource(temp.valueAt(otherImages));
                displayedimages.put(imgv,temp.keyAt(otherImages));
                displayedimagesShadows.put(imgv,temp.valueAt(otherImages));
                temp.removeAt(otherImages);
        }

        startspeed = new Timestamp(System.currentTimeMillis());
        temp.clear();
        imageviews.clear();
        clickable();
        setTimer(mTimeLeftInMillis);

    }

    private void displayGameMed(){
        mTimeLeftInMillis = 12000;
        Random rand  = new Random();

        int picklist = rand.nextInt(pickrandSparceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSparceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));
        pickedimg = temp.keyAt(pickImage);

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));
        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));
        displayedimagesShadows.put(imageviews.get(imageviewshadow),temp.valueAt(pickImage));

        displayedImageview.add(imageviews.get(imageviewshadow));

        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);

        int samelistonemore = rand.nextInt(imageviews.size());
        ImageView iv1 = findViewById(imageviews.get(samelistonemore));
        int samelistimage = rand.nextInt(temp.size());

        iv1.setImageResource(temp.valueAt(samelistimage));

        displayedimages.put(imageviews.get(samelistonemore),temp.keyAt(samelistimage));
        displayedimagesShadows.put(imageviews.get(samelistonemore),temp.valueAt(samelistimage));
        imageviews.remove(samelistonemore);


        //edw isws prepei na kanw kai remove thn eikona apo thn temp lista

        int otherlist = rand.nextInt(pickrandSparceArray.size())+1;

        while (otherlist == picklist)
        {
            otherlist = rand.nextInt(pickrandSparceArray.size())+1;
        }


        for (int imgv :imageviews)
        {
            ImageView iv11 = findViewById(imgv);
            temp = (SparseIntArray) pickrandSparceArray.get(otherlist);

            int otherlistobjects = rand.nextInt(temp.size());
            iv11.setImageResource(temp.valueAt(otherlistobjects));

            displayedimages.put(imgv,temp.keyAt(otherlistobjects));
            displayedimagesShadows.put(imgv,temp.valueAt(otherlistobjects));
            temp.removeAt(otherlistobjects);

        }
        setTimer(mTimeLeftInMillis);

        startspeed = new Timestamp(System.currentTimeMillis());
        temp.clear();
        imageviews.clear();
        clickable();

    }

    private void displayGameEz(){
        Random rand  = new Random();

        int picklist = rand.nextInt(pickrandSparceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSparceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));
        pickedimg = temp.keyAt(pickImage);

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));

        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));
        displayedimagesShadows.put(imageviews.get(imageviewshadow),temp.valueAt(pickImage));

        displayedImageview.add(imageviews.get(imageviewshadow));

        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);


        for (int imgv :imageviews)
        {
            ImageView iv1 = findViewById(imgv);

            int otherlistobjects = rand.nextInt(variety.size());
            iv1.setImageResource(variety.valueAt(otherlistobjects));

            displayedimages.put(imgv,variety.keyAt(otherlistobjects));
            displayedimagesShadows.put(imgv,variety.valueAt(otherlistobjects));

            variety.removeAt(otherlistobjects);

        }

        startspeed = new Timestamp(System.currentTimeMillis());
        variety.clear();
        temp.clear();
        imageviews.clear();
        clickable();

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
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    showTutorialPopUp(R.raw.tutorial_shadowgame_landscape);

                }
                else
                {
                    showTutorialPopUp(R.raw.tutorial_shadowgame_portrait);
                }
            }
        });

    }


    private void onbackAndExitCode(){
        if (Timer != null)
        {
            Timer.cancel();
        }
        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
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
            if (totalPlayInSeconds > THRESHOLD_EASY && currentDifficulty.equals(getResources().getString(R.string.easyValue)))
            {
                totalPlayInSeconds = THRESHOLD_EASY;
            }
            else if (totalPlayInSeconds > THRESHOLD_ALL)
            {
                totalPlayInSeconds = THRESHOLD_ALL;
            }
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss , 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }

    @Override
    public void onBackPressed()
    {
        onbackAndExitCode();
    }


    private void createRound(){
        startButton.setVisibility(View.INVISIBLE);



        if (currentRound == 0)
        {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))){
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMed();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds=3;
            displayGameAdv();
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue)))
        {
            TotalRounds = 4;

            if (currentRound<=1){
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMed();
            }
        }
        else
        {
            TotalRounds =5;
            if (currentRound<1)
            {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            }
            else if (currentRound>=1 && currentRound<=2)
            {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMed();
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv();
            }
        }
        currentRound++;
        textRounds.setText((currentRound)+"/"+TotalRounds);

    }

    private void nextRound(){
        textMsg.setTextColor(getResources().getColor(R.color.greenStrong));
        textsLinear.setVisibility(View.VISIBLE);
        disableReplayTut();
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
                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                }


            }

            @Override
            public void onFinish() {

                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds)
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
                    fillListImageview();
                    initaliseSparceArray();
                    gameInit = true;
                    createRound();
                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

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
        animationTextPoints.setText("+ " +currentPoints);
        if (currentPoints == 0)
        {
            animationTextPoints.setTextColor(Color.RED);
        }
        else
            animationTextPoints.setTextColor(Color.GREEN);
    }

    private void showTutorialPopUp(int res){
        DialogFragment dialogFragment = new Tutorial(ShadowGame.this,res,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialShadowGame");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,ShadowGame.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "ShadowGame");
    }

    private void startAnimation(){
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animationTextPoints,"y",500f,-500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha =  ObjectAnimator.ofFloat(animationTextPoints,View.ALPHA,1.0f,0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY,alpha);
        animatorSet.start();
    }

    private  void fillListImageview(){
        imageviews.add(R.id.imageView1SHG);
        imageviews.add(R.id.imageView2SHG);
        imageviews.add(R.id.imageView3SHG);
        imageviews.add(R.id.imageView4SHG);

    }

    private void setTimer(long mTimeLeftInMillisfun){
        disableReplayTut();

        Timer = new CountDownTimer(mTimeLeftInMillisfun,1000) {
            @Override
            public void onTick(long l) {

                mTimeLeftInMillis=l;
                textTimer.setText("Απομένουν "+ l/1000+" Δευτερόλεπτα");

            }

            @Override
            public void onFinish() {
                enableReplayTut();
                unclickable();
                miss++;
                missPoints =true;
                startAnimation();
                vibe.vibrate(1000);
                textTimer.setText(getResources().getString(R.string.timeFinish));
                countPoints();
                setCleanTimer();
                gameInit = false;
                displayedImageview.clear();
                displayedimages.clear();
 //               startButton.setVisibility(View.VISIBLE);
                if (currentRound == TotalRounds)
                {
                    startButton.setVisibility(View.INVISIBLE);
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
                    GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id,game_id);
                    shopPopUp();
                }
            }


        }.start();
        userViewModel.saveTimer(Timer);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ANIMALS, new SparseIntArrayParcelable(animals));
        outState.putParcelable(FRUITS, new SparseIntArrayParcelable(fruits));
        outState.putParcelable(ELECTRONICS, new SparseIntArrayParcelable(electronics));
        outState.putParcelable(VARIETY, new SparseIntArrayParcelable(variety));
        outState.putParcelable(DISPLAYEDIMAGES, new SparseIntArrayParcelable(displayedimages));
        outState.putParcelable(DISPLAYEDIMAGESSHADOWS, new SparseIntArrayParcelable(displayedimagesShadows));
        //outState.putSparseParcelableArray(PICKRANDSPARSEARRAY,pickrandSparceArray);

        outState.putSerializable(STARTTIME,startTime);
        outState.putSerializable(ENDTIME,endTime);
        outState.putSerializable(STARTSPEED,startspeed);
        outState.putSerializable(ENDSPEED,endspeed);
        outState.putLong(CLOCK,mTimeLeftInMillis);
        outState.putDouble(TOTALSPEED,totalspeed);

        outState.putIntegerArrayList(IMAGEVIEWS,imageviews);
        outState.putIntegerArrayList(DISPLAYEDIMAGEVIEW,displayedImageview);

        outState.putInt(GAME_ID,game_id);
        outState.putInt(USER_ID,user_id);
        outState.putInt(TOTALROUNDS,TotalRounds);
        outState.putInt(CURRENTROUND,currentRound);
        outState.putInt(HIT,hit);
        outState.putInt(MISS,miss);
        outState.putInt(TOTALPOINTS,totalPoints);
        outState.putInt(TRUECOUNTER,trueCounter);
        outState.putInt(CLICK,click);
        outState.putInt(PICKEDIMAGE,pickedimg);


        outState.putString(MENUDIFFICULTY,menuDifficulty);
        outState.putString(CURRENTDIFFICULTY,currentDifficulty);

        outState.putBoolean(MISSPOINTS,missPoints);
        outState.putBoolean(GAMEINIT,gameInit);


        outState.putLong(NEXTROUNDTIMER , timeLeftInMillisNextRound);
        outState.putString(MSGHELPER,msgHelper);
    }

    private void matchlists(){
        pickrandSparceArray.put(1,animals);
        pickrandSparceArray.put(2,fruits);
        pickrandSparceArray.put(3,electronics);
    }

    private void clearScreen(){
        imagebutton1.setImageResource(0);
        imagebutton2.setImageResource(0);
        imagebutton3.setImageResource(0);
        imagebutton4.setImageResource(0);
        imagebuttoncolorfull.setImageResource(0);
        Timer = null;
    }

    private void initaliseSparceArray(){
        animals.put(R.drawable.thing_1,R.drawable.thing_sh_1);
        animals.put(R.drawable.thing_2,R.drawable.thing_sh_2);
        animals.put(R.drawable.thing_3,R.drawable.thing_sh_3);
        animals.put(R.drawable.thing_4,R.drawable.thing_sh_4);
        animals.put(R.drawable.thing_5,R.drawable.thing_sh_5);
        animals.put(R.drawable.thing_6,R.drawable.thing_sh_6);
        animals.put(R.drawable.thing_7,R.drawable.thing_sh_7);
        animals.put(R.drawable.thing_8,R.drawable.thing_sh_8);
        animals.put(R.drawable.thing_9,R.drawable.thing_sh_9);
        animals.put(R.drawable.thing_10,R.drawable.thing_sh_10);
        animals.put(R.drawable.thing_11,R.drawable.thing_sh_11);
        animals.put(R.drawable.thing_12,R.drawable.thing_sh_12);

        fruits.put(R.drawable.thing_13,R.drawable.thing_sh_13);
        fruits.put(R.drawable.thing_14,R.drawable.thing_sh_14);
        fruits.put(R.drawable.thing_15,R.drawable.thing_sh_15);
        fruits.put(R.drawable.thing_16,R.drawable.thing_sh_16);
        fruits.put(R.drawable.thing_17,R.drawable.thing_sh_17);
        fruits.put(R.drawable.thing_18,R.drawable.thing_sh_18);
        fruits.put(R.drawable.thing_19,R.drawable.thing_sh_19);
        fruits.put(R.drawable.thing_20,R.drawable.thing_sh_20);
        fruits.put(R.drawable.thing_23,R.drawable.thing_sh_23);
        fruits.put(R.drawable.thing_24,R.drawable.thing_sh_24);
        fruits.put(R.drawable.thing_25,R.drawable.thing_sh_25);
        fruits.put(R.drawable.thing_26,R.drawable.thing_sh_26);
        fruits.put(R.drawable.thing_27,R.drawable.thing_sh_27);
        fruits.put(R.drawable.thing_28,R.drawable.thing_sh_28);
        fruits.put(R.drawable.thing_29,R.drawable.thing_sh_29);
        fruits.put(R.drawable.thing_30,R.drawable.thing_sh_30);

        electronics.put(R.drawable.thing_31,R.drawable.thing_sh_31);
        electronics.put(R.drawable.thing_32,R.drawable.thing_sh_32);
        electronics.put(R.drawable.thing_33,R.drawable.thing_sh_33);
        electronics.put(R.drawable.thing_35,R.drawable.thing_sh_35);
        electronics.put(R.drawable.thing_37,R.drawable.thing_sh_37);
        electronics.put(R.drawable.thing_38,R.drawable.thing_sh_38);
        electronics.put(R.drawable.thing_39,R.drawable.thing_sh_39);

        variety.put(R.drawable.thing_41,R.drawable.thing_sh_41);
        variety.put(R.drawable.thing_43,R.drawable.thing_sh_43);
        variety.put(R.drawable.thing_49,R.drawable.thing_sh_49);
        variety.put(R.drawable.thing_51,R.drawable.thing_sh_51);
        variety.put(R.drawable.thing_52,R.drawable.thing_sh_52);
        variety.put(R.drawable.thing_55,R.drawable.thing_sh_55);
        variety.put(R.drawable.thing_56,R.drawable.thing_sh_56);
        variety.put(R.drawable.thing_57,R.drawable.thing_sh_57);
        variety.put(R.drawable.thing_58,R.drawable.thing_sh_58);


    }

    private void assignAllButtons(){

        imagebutton1 = findViewById(R.id.imageView1SHG);
        imagebutton2 = findViewById(R.id.imageView2SHG);
        imagebutton3 = findViewById(R.id.imageView3SHG);
        imagebutton4 = findViewById(R.id.imageView4SHG);
        imagebuttoncolorfull = findViewById(R.id.picked);

        exit = findViewById(R.id.ExitSG);
        replayTutorial = findViewById(R.id.ReplayTutorialSG);
        startButton = findViewById(R.id.startButtonSH);
        textRounds = findViewById(R.id.textRoundsSG);
        textTimer = findViewById(R.id.textTimerSG);
        animationTextPoints = findViewById(R.id.AnimTextPointsSHG);
        logoLinear = findViewById(R.id.imageLogoDisplaySH);
        textsLinear = findViewById(R.id.textsSH);
        textMsg = findViewById(R.id.msgSH);
        textMsgTime = findViewById(R.id.msgSH1);

        imagebutton1.setOnClickListener(this);
        imagebutton2.setOnClickListener(this);
        imagebutton3.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
    }

    private void clickable(){
        imagebutton1.setClickable(true);
        imagebutton2.setClickable(true);
        imagebutton3.setClickable(true);
        imagebutton4.setClickable(true);
    }
    private  void unclickable(){
        imagebutton1.setClickable(false);
        imagebutton2.setClickable(false);
        imagebutton3.setClickable(false);
        imagebutton4.setClickable(false);
    }

    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }


    @Override
    public void onClick(View view) {
  //      startButton.setText(getResources().getString(R.string.nextRound));


        if (currentDifficulty.equals(getResources().getString(R.string.mediumValue))|| currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            textTimer.setText("");
            Timer.cancel();

        }

        click++;
        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        ImageView imgv = findViewById(view.getId());
        imgv.setImageResource(displayedimages.get(view.getId()));
        startAnimation();

        if (displayedImageview.contains(view.getId()))
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


        //startButton.setVisibility(View.VISIBLE);
        setCleanTimer();
        unclickable();
        countPoints();

        if (currentRound == TotalRounds)
        {
            startButton.setVisibility(View.INVISIBLE);
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
            GameEvent gameEvent = new GameEvent(game_id,user_id,hit,miss,0,totalPoints,(double)hit/(hit+miss),totalspeed/click,totalPlayInSeconds,menuDifficulty,startTime,endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id,game_id);
            shopPopUp();
        }
        gameInit = false;
        displayedImageview.clear();
        displayedimages.clear();


    }


    private void setCleanTimer(){

        cleanTimer = new CountDownTimer(1500,1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                clearScreen();

            }
        }.start();
        nextRound();
    }

    public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
        public  Creator<SparseIntArrayParcelable> CREATOR = new Creator<SparseIntArrayParcelable>() {
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
