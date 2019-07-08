package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class OrderGame extends AppCompatActivity implements View.OnClickListener{

    private ImageView imagebutton1, imagebutton2, imagebutton3, imagebutton4, imagebutton5;
    private MaterialButton startButton, missingObj;
    private TextView textRounds, textTimer;

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

    private HashMap<Integer, ArrayList<Integer>> listselection = new HashMap<>();



    private ArrayList<Integer> picked = new ArrayList<Integer>();
    private HashMap<Integer, Integer> imageIDS = new HashMap<Integer, Integer>();
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();

    private int user_id, game_id, currentRound=0 , TotalRounds=0 ;
    private String menuDifficulty,currentDifficulty;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startSpeed;
    private Timestamp endSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);
        assignAllButtons();
        initialiseLists();


        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);
    }

    private void createRound(){

        initHashmaplistSelection();
        //Collections.shuffle(listselection.keySet());

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
            displayGameMedium();
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
                displayGameMedium();
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
                displayGameMedium();
            }
            else
            {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv();
            }
        }
        textRounds.setText((currentRound+1)+"/"+TotalRounds);

    }

    private void displayGameEz()
    {


    }




    private void displayGameMedium(){}
    private void displayGameAdv(){}

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
        textRounds = findViewById(R.id.textRoundsOG);
        textTimer = findViewById(R.id.textTimerOG);

        imagebutton1.setOnClickListener(this);
        imagebutton2.setOnClickListener(this);
        imagebutton3.setOnClickListener(this);
        imagebutton4.setOnClickListener(this);
        imagebutton5.setOnClickListener(this);
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



    @Override
    public void onClick(View view) {

    }
}
