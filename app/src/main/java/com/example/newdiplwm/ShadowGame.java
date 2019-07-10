package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ShadowGame extends AppCompatActivity implements  View.OnClickListener{

    private ImageView imagebutton1,imagebutton2,imagebutton3,imagebutton4,imagebuttoncolorfull;
    private MaterialButton startButton;
    private TextView textRounds,textTimer;
    private Vibrator vibe;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;


    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private String menuDifficulty , currentDifficulty;

    private ArrayList<Integer> imageviews  = new ArrayList<>();
    private ArrayList<Integer> displayedImageview  = new ArrayList<>();


    private SparseIntArray animals = new SparseIntArray(4);
    private SparseIntArray fruits = new SparseIntArray(4);
    private SparseIntArray electronics = new SparseIntArray(4);
    private SparseIntArray variety = new SparseIntArray(6);
    private SparseIntArray displayedimages = new SparseIntArray(4);

    private HashMap<String , Integer> pointsHashMap = new HashMap<String, Integer>();

    private SparseArray pickrandSprceArray = new SparseArray(4);

    private int user_id, game_id, currentRound=0 , TotalRounds=0;

    private int hit, miss, trueCounter,vibeduration = 1000,totalPoints=0;
    private boolean missPoints = false;


    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;
    private int click=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_game);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        assignAllButtons();
        matchlists();

        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fillListImageview();
                initaliseSparceArray();
                createRound();
            }
        });



    }

    private void displayGameAdv(){
        Random rand = new Random();

        int picklist = rand.nextInt(pickrandSprceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSprceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));
        displayedImageview.add(imageviews.get(imageviewshadow));

        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));


        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);

        for (int imgv:imageviews)
        {

                ImageView ivv = findViewById(imgv);
                int otherImages = rand.nextInt(temp.size());
                ivv.setImageResource(temp.valueAt(otherImages));
                displayedimages.put(imgv,temp.keyAt(otherImages));
                temp.removeAt(otherImages);
        }

        startspeed = new Timestamp(System.currentTimeMillis());
        temp.clear();
        imageviews.clear();
        clickable();

    }

    private void displayGameMed(){
        Random rand  = new Random();

        int picklist = rand.nextInt(pickrandSprceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSprceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));
        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));

        displayedImageview.add(imageviews.get(imageviewshadow));

        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);

        int samelistonemore = rand.nextInt(imageviews.size());
        ImageView iv1 = findViewById(imageviews.get(samelistonemore));
        int samelistimage = rand.nextInt(temp.size());

        iv1.setImageResource(temp.valueAt(samelistimage));

        displayedimages.put(imageviews.get(samelistonemore),temp.keyAt(samelistimage));
        imageviews.remove(samelistonemore);


        //edw isws prepei na kanw kai remove thn eikona apo thn temp lista

        int otherlist = rand.nextInt(pickrandSprceArray.size())+1;

        while (otherlist == picklist)
        {
            otherlist = rand.nextInt(pickrandSprceArray.size())+1;
        }


        for (int imgv :imageviews)
        {
            ImageView iv11 = findViewById(imgv);
            temp = (SparseIntArray) pickrandSprceArray.get(otherlist);

            int otherlistobjects = rand.nextInt(temp.size());
            iv11.setImageResource(temp.valueAt(otherlistobjects));

            displayedimages.put(imgv,temp.keyAt(otherlistobjects));
            temp.removeAt(otherlistobjects);

        }

        startspeed = new Timestamp(System.currentTimeMillis());
        temp.clear();
        imageviews.clear();
        clickable();

    }

    private void displayGameEz(){
        Random rand  = new Random();

        int picklist = rand.nextInt(pickrandSprceArray.size())+1;
        SparseIntArray temp;
        temp = (SparseIntArray) pickrandSprceArray.get(picklist);

        int pickImage= rand.nextInt(temp.size());
        imagebuttoncolorfull.setImageResource(temp.keyAt(pickImage));

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(temp.valueAt(pickImage));

        displayedimages.put(imageviews.get(imageviewshadow),temp.keyAt(pickImage));

        displayedImageview.add(imageviews.get(imageviewshadow));

        imageviews.remove(imageviewshadow);
        temp.removeAt(pickImage);


        for (int imgv :imageviews)
        {
            ImageView iv1 = findViewById(imgv);

            int otherlistobjects = rand.nextInt(variety.size());
            iv1.setImageResource(variety.valueAt(otherlistobjects));

            displayedimages.put(imgv,variety.keyAt(otherlistobjects));

            variety.removeAt(otherlistobjects);

        }

        startspeed = new Timestamp(System.currentTimeMillis());
        variety.clear();
        imageviews.clear();
        clickable();

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
        textRounds.setText((currentRound+1)+"/"+TotalRounds);

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
//        animPointsText.setText("+ " +String.valueOf(currentPoints));
//        if (currentPoints == 0)
//        {
//            animPointsText.setTextColor(Color.RED);
//        }
//        else
//            animPointsText.setTextColor(Color.GREEN);
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id,ShadowGame.this,hit,totalPoints);
        newFragment.show(getSupportFragmentManager(), "ShadowGame");
    }
    private  void fillListImageview(){
        imageviews.add(R.id.imageView1SHG);
        imageviews.add(R.id.imageView2SHG);
        imageviews.add(R.id.imageView3SHG);
        imageviews.add(R.id.imageView4SHG);

    }

    private void matchlists(){
        pickrandSprceArray.put(1,animals);
        pickrandSprceArray.put(2,fruits);
        pickrandSprceArray.put(3,electronics);
    }

    private void initaliseSparceArray(){
        animals.put(R.drawable.thing_1,R.drawable.thing_sh_1);
        animals.put(R.drawable.thing_2,R.drawable.thing_sh_2);
        animals.put(R.drawable.thing_3,R.drawable.thing_sh_3);
        animals.put(R.drawable.thing_4,R.drawable.thing_sh_4);

        fruits.put(R.drawable.thing_14,R.drawable.thing_sh_14);
        fruits.put(R.drawable.thing_15,R.drawable.thing_sh_15);
        fruits.put(R.drawable.thing_16,R.drawable.thing_sh_16);
        fruits.put(R.drawable.thing_17,R.drawable.thing_sh_17);

        electronics.put(R.drawable.thing_31,R.drawable.thing_sh_31);
        electronics.put(R.drawable.thing_32,R.drawable.thing_sh_32);
        electronics.put(R.drawable.thing_37,R.drawable.thing_sh_37);
        electronics.put(R.drawable.thing_39,R.drawable.thing_sh_39);

        variety.put(R.drawable.thing_41,R.drawable.thing_sh_41);
        variety.put(R.drawable.thing_43,R.drawable.thing_sh_43);
        variety.put(R.drawable.thing_51,R.drawable.thing_sh_51);
        variety.put(R.drawable.thing_52,R.drawable.thing_sh_52);
        variety.put(R.drawable.thing_56,R.drawable.thing_sh_56);
        variety.put(R.drawable.thing_58,R.drawable.thing_sh_58);


    }

    private void assignAllButtons(){

        imagebutton1 = findViewById(R.id.imageView1SHG);
        imagebutton2 = findViewById(R.id.imageView2SHG);
        imagebutton3 = findViewById(R.id.imageView3SHG);
        imagebutton4 = findViewById(R.id.imageView4SHG);
        imagebuttoncolorfull = findViewById(R.id.picked);

        startButton = findViewById(R.id.startButtonSH);
        textRounds = findViewById(R.id.textRoundsSG);
        textTimer = findViewById(R.id.textTimerSG);

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


    @Override
    public void onClick(View view) {


        click++;
        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        ImageView imgv = findViewById(view.getId());
        imgv.setImageResource(displayedimages.get(view.getId()));

        if (displayedImageview.contains(view.getId()))
        {
            textTimer.setText("SWSTA");
            hit++;
            trueCounter++;

        }
        else
        {
            textTimer.setText("la8os");
            vibe.vibrate(vibeduration);
            miss++;
            missPoints = true;
        }
        unclickable();
        currentRound++;
        countPoints();

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

        displayedImageview.clear();
        displayedimages.clear();
        startButton.setVisibility(View.VISIBLE);

    }
}
