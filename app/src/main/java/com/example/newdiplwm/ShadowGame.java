package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class ShadowGame extends AppCompatActivity implements  View.OnClickListener{

    private ImageView imagebutton1,imagebutton2,imagebutton3,imagebutton4,imagebuttoncolorfull;
    private MaterialButton startButton;
    private TextView textRounds,textTimer;

    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private int user_id,game_id;
    private String menuDifficulty;

    private ArrayList<Integer> imageviews  = new ArrayList<>();


    private SparseIntArray animals = new SparseIntArray(4);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shadow_game);
        assignAllButtons();


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
                displayGameAdv();
            }
        });


    }

    private void displayGameAdv(){
        Random rand = new Random();
        int pick = rand.nextInt(animals.size());
        imagebuttoncolorfull.setImageResource(animals.keyAt(pick));

        int imageviewshadow  = rand.nextInt(imageviews.size());
        ImageView iv = findViewById(imageviews.get(imageviewshadow));
        iv.setImageResource(animals.valueAt(pick));

        int i = 0;

        imageviews.remove(imageviewshadow);
        animals.removeAt(pick);


        for (int imgv:imageviews)
        {

                ImageView ivv = findViewById(imgv);
                ivv.setImageResource(animals.valueAt(i));
                i++;
        }
        animals.clear();
        imageviews.clear();

    }
    private  void fillListImageview(){
        imageviews.add(R.id.imageView1SHG);
        imageviews.add(R.id.imageView2SHG);
        imageviews.add(R.id.imageView3SHG);
        imageviews.add(R.id.imageView4SHG);

    }

    private void initaliseSparceArray(){
        animals.put(R.drawable.thing_1,R.drawable.thing_sh_1);
        animals.put(R.drawable.thing_2,R.drawable.thing_sh_2);
        animals.put(R.drawable.thing_3,R.drawable.thing_sh_3);
        animals.put(R.drawable.thing_4,R.drawable.thing_sh_4);
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
        imagebuttoncolorfull.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
