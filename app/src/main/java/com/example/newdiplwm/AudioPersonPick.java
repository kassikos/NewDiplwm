package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class AudioPersonPick extends AppCompatActivity implements View.OnClickListener{

    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private ImageView imgv1 , imgv2 ,imgv3 ,imgv4 , playAudio;
    private MaterialButton startButton;
    private TextView textRounds , textTimer;

    private SparseIntArray personSound = new SparseIntArray(15);
    private SparseIntArray imageviewImage = new SparseIntArray(4);
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();
    private ArrayList<Integer> pickedImage = new ArrayList<Integer>();
    private int user_id, game_id, currentRound=0 , TotalRounds=0;
    private int hit = 0 , miss = 0 , totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;
    private String menuDifficulty,currentDifficulty;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_person_pick);
        asignAllButtons();
        matching();
        fillListImageview();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createRound();
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer  = null;
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
       /* else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMedium(randomPickedLis);
        }
        else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue)))
        {
            currentDifficulty = menuDifficulty;
            TotalRounds=3;
            displayGameAdv(randomPickedLis);
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
                displayGameMedium(randomPickedLis);
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
                displayGameMedium(randomPickedLis);
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv(randomPickedLis);
            }
        }*/
        currentRound++;

       // textRounds.setText(currentRound+"/"+TotalRounds);

    }
    private void displayGameEz(){
        Random rand  = new Random();
        final int randpickImgSound = rand.nextInt(personSound.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(personSound.keyAt(randpickImgSound));

        pickedImage.add(personSound.keyAt(randpickImgSound));

        imageviewImage.put(imageviews.get(randompickedimgv),personSound.keyAt(randpickImgSound));

        imageviews.remove(randompickedimgv);
        personSound.removeAt(randpickImgSound);


        for (int imgv : imageviews)
        {
            ImageView ivv = findViewById(imgv);
            int i = rand.nextInt(personSound.size());
            ivv.setImageResource(personSound.keyAt(i));
            imageviewImage.put(imgv,personSound.keyAt(i));
            personSound.removeAt(i);
        }

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer = MediaPlayer.create(getApplicationContext(), personSound.valueAt(randpickImgSound));
                mediaPlayer.start();
            }

        });

    }

    private void displayGameMedium(){}
    private void displayGameAdv(){}

    private  void fillListImageview(){
        imageviews.add(R.id.imageView1APPG);
        imageviews.add(R.id.imageView2APPG);
        imageviews.add(R.id.imageView3APPG);
        imageviews.add(R.id.imageView4APPG);
    }

    private void asignAllButtons(){
        startButton = findViewById(R.id.startButtonAPPG);
        imgv1 = findViewById(R.id.imageView1APPG);
        imgv2 = findViewById(R.id.imageView2APPG);
        imgv3 = findViewById(R.id.imageView3APPG);
        imgv4 = findViewById(R.id.imageView4APPG);
        playAudio = findViewById(R.id.imageViewPlayAudio);

        imgv1.setOnClickListener(this);
        imgv2.setOnClickListener(this);
        imgv3.setOnClickListener(this);
        imgv4.setOnClickListener(this);

    }


    private void matching(){
        personSound.put(R.drawable.thing_1,R.raw.firstez);
        personSound.put(R.drawable.thing_2,R.raw.secondez);
        personSound.put(R.drawable.thing_3,R.raw.thirdez);
        personSound.put(R.drawable.thing_4,R.raw.fourthez);
        personSound.put(R.drawable.thing_5,R.raw.fifthez);
    }

    @Override
    public void onClick(View view) {


    }
}
