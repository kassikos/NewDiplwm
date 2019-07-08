package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

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
    private ArrayList<Integer> pickedImages = new ArrayList<>();

    private HashMap<Integer, ArrayList<Integer>> listselection = new HashMap<Integer, ArrayList<Integer>>();
    private CountDownTimer Timer;
    private static final long START_TIME_IN_MILLIS = 2000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private ArrayList<Integer> picked = new ArrayList<Integer>();
    private HashMap<Integer, Integer> imageIDS = new HashMap<Integer, Integer>();
    private ArrayList<Integer> imageviews = new ArrayList<Integer>();

    private int user_id, game_id, currentRound=0 , TotalRounds=0 ;
    private String menuDifficulty,currentDifficulty;

    private int click=0;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startSpeed;
    private Timestamp endSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_game);
        assignAllButtons();
        fillListImageview();
        initialiseLists();

        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                click=0;
                createRound();
            }
        });
    }

    private void createRound(){

        initHashmaplistSelection();

        Random rand = new Random();

        int randlist = rand.nextInt(5)+1;
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

        Random rand = new Random();
        int randpick = rand.nextInt(3);
        int pickedImage  = listselection.get(randlist).get(randpick);
        imagebutton2.setImageResource(pickedImage);

        pickedImages.add(listselection.get(randlist).get(pickedImage));

        Timer = new CountDownTimer(4000, 1000) {
            @Override
            public void onTick(long l) {
                textTimer.setText("Remain "+ l/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                textTimer.setText("Ποια αντικείμενα ήταν στην αρχική παραγγελία;");
                imagebutton2.setImageResource(0);

                for (int i=0;i<3;i++)
                {
                    ImageView v = findViewById(imageviews.get(i));
                    v.setImageResource(listselection.get(randlist).get(i));
                    imageIDS.put(imageviews.get(i),listselection.get(randlist).get(i));

                }
            }
        };
        Timer.start();


    }

    private void displayGameMedium(final int randlist){
        Random rand = new Random();
        int randpick1 = rand.nextInt(2);
        int randpick2 = rand.nextInt(2)+2;
        int picked1  = listselection.get(randlist).get(randpick1);
        int picked2  = listselection.get(randlist).get(randpick2);
        imagebutton1.setImageResource(picked1);
        imagebutton2.setImageResource(picked2);

        pickedImages.add(listselection.get(randlist).get(randpick1));
        pickedImages.add(listselection.get(randlist).get(randpick2));


        Timer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long l) {
                textTimer.setText("Remain "+ l/1000+" Seconds");
            }

            @Override
            public void onFinish() {
                textTimer.setText("Ποια αντικείμενα ήταν στην αρχική παραγγελία;");
                imagebutton2.setImageResource(0);
                imagebutton3.setImageResource(0);

                for (int i=0;i<4;i++)
                {
                    ImageView v = findViewById(imageviews.get(i));
                    v.setImageResource(listselection.get(randlist).get(i));
                    imageIDS.put(imageviews.get(i),listselection.get(randlist).get(i));

                }
            }
        };
        Timer.start();

    }
    private void displayGameAdv(final int randlist){
 //       Random rand = new Random();
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


        Timer = new CountDownTimer(9000, 1000) {
            @Override
            public void onTick(long l) {
                textTimer.setText("Remain "+ l/1000+" Seconds");
            }

            @Override
            public void onFinish() {
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
            }
        };
        Timer.start();
    }


    @Override
    public void onClick(View view) {

        Timer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                for (int imageviewId : imageviews) {
                    ImageView v = findViewById(imageviewId);
                    v.setImageResource(0);
//                    startButton.setText("next Round");
//                    startButton.setVisibility(View.VISIBLE);

                }

                imageIDS.clear();
                pickedImages.clear();
            }
        };
        click++;
        if (click == 1 && currentDifficulty.equals(getResources().getString(R.string.easyValue)) && pickedImages.contains(imageIDS.get(view.getId())))
        {
            Timer.start();
        }
        else if (click == 2 && currentDifficulty.equals(getResources().getString(R.string.mediumValue)) && pickedImages.contains(imageIDS.get(view.getId())))
        {
            Timer.start();
        }
        else if (click == 3 && currentDifficulty.equals(getResources().getString(R.string.advancedValue)) && pickedImages.contains(imageIDS.get(view.getId())))
        {
            Timer.start();
        }

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
    //TODO θελουμε λιστα picked gia na 3eroyme tin arxiki paraggelia gt etsi opws to exw einai panta true
    //TODO sto advanced να φτιαξω το feat me to na leipoyn antikeimena
    //TODO otan xanei to idio animation me OS
    //TODO oso epilegei na kanw kati na fainetai ayto poy exei epil3ei kai na kleinw ton click listener gia na min mporei na to 3anaepile3ei
    //TODO na krataw instance tis o8onis otan kanei rotate
}
