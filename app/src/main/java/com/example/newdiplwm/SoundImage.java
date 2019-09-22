package com.example.newdiplwm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
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

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class SoundImage extends AppCompatActivity implements View.OnClickListener{

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
    private static final String TOTALROUNDS = "TOTALROUNDS";
    private static final String DEVICES = "DEVICES";
    private static final String ANIMALS = "ANIMALS";
    private static final String INSTRUMENTS = "INSTRUMENTS";
    private static final String RANDOM = "RANDOM";
    private static final String IMAGEVIEWS = "IMAGEVIEWS";
    private static final String CLICK = "CLICK";
    private static final String MENUDIFFICULTY = "MENUDIFFICULTY";
    private static final String PICKEDIMAGES = "PICKEDIMAGES";
    private static final String IMAGEVIEWIMAGE = "IMAGEVIEWIMAGE";
    private static final String AUDIOPOS = "AUDIOPOS";
    private static final String PICKEDSOUND = "PICKEDSOUND";
    private static final String GAMEINIT = "GAMEINIT";
    private static final String SOUNDPLAYED = "SOUNDPLAYED";
    private static final String LIMITREPLAY = "LIMITREPLAY";
    private static final String LIMIT = "LIMIT";
    private static final String ALLIMAGES = "ALLIMAGES";
    private static final String MSGHELPER = "MSGHELPER";
    private static final String NEXTROUNDTIMER = "NEXTROUNDTIMER";

    private ImageView imgv1, imgv2, imgv3,imgv4,playAudio,exit,replayTutorial;
    private MaterialButton startButton;
    private TextView textRounds, animPointsText, textMsg , textMsgTime;;
    private LinearLayout logoLinear, textsLinear;
    private Vibrator vibe;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private CountDownTimer Timer, nextRoundTimer;
    private long timeLeftInMillisNextRound = 0;

    private SparseIntArray imageSoundDevices = new SparseIntArray();
    private SparseIntArray imageSoundAnimals = new SparseIntArray();
    private SparseIntArray imageSoundInstruments = new SparseIntArray();
    private SparseIntArray imageSoundRandom = new SparseIntArray();


    private SparseIntArray imageviewImage = new SparseIntArray(4);

    private SparseArray pickRandList = new SparseArray();
    
    private ArrayList<Integer> imageviews = new ArrayList<>(4);
    private ArrayList<Integer> allimages = new ArrayList<>(24);
    private ArrayList<Integer> pickedImage = new ArrayList<Integer>(1);

    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    private int limitReplay = 0 , limit = 0 , click = 0,audioposition = 0;
    private boolean gameInit = false;

    private int user_id, game_id, currentRound = 0, TotalRounds = 0;
    private int hit = 0, miss = 0, totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false , soundPlayed = false;
    private String menuDifficulty, currentDifficulty = "", msgHelper;
    private int pickedSound;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;
    MediaPlayer mediaPlayer;
    private  Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_image);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        session = new Session(getApplicationContext());
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);


        if (savedInstanceState != null) {
            gameInit = savedInstanceState.getBoolean(GAMEINIT);
            soundPlayed = savedInstanceState.getBoolean(SOUNDPLAYED);
            user_id = savedInstanceState.getInt(USER_ID);
            game_id = savedInstanceState.getInt(GAME_ID);
            imageviews = savedInstanceState.getIntegerArrayList(IMAGEVIEWS);
            pickedImage = savedInstanceState.getIntegerArrayList(PICKEDIMAGES);
            allimages = savedInstanceState.getIntegerArrayList(ALLIMAGES);
            currentDifficulty = savedInstanceState.getString(CURRENTDIFFICULTY);
            menuDifficulty = savedInstanceState.getString(MENUDIFFICULTY);
            TotalRounds = savedInstanceState.getInt(TOTALROUNDS);
            click = savedInstanceState.getInt(CLICK);
            hit = savedInstanceState.getInt(HIT);
            miss = savedInstanceState.getInt(MISS);
            totalPoints = savedInstanceState.getInt(TOTALPOINTS);
            trueCounter = savedInstanceState.getInt(TRUECOUNTER);
            audioposition = savedInstanceState.getInt(AUDIOPOS);
            pickedSound = savedInstanceState.getInt(PICKEDSOUND);
            limitReplay = savedInstanceState.getInt(LIMITREPLAY);
            limit = savedInstanceState.getInt(LIMIT);
            pickedSound = savedInstanceState.getInt(PICKEDSOUND);
            totalspeed = savedInstanceState.getDouble(TOTALSPEED);
            missPoints = savedInstanceState.getBoolean(MISSPOINTS);
            startTime = (Timestamp) savedInstanceState.getSerializable(STARTTIME);
            endTime = (Timestamp) savedInstanceState.getSerializable(ENDTIME);
            startspeed = (Timestamp) savedInstanceState.getSerializable(STARTSPEED);
            endspeed = (Timestamp) savedInstanceState.getSerializable(ENDSPEED);
            currentRound = savedInstanceState.getInt(CURRENTROUND);

            imageviewImage = (SparseIntArray) savedInstanceState.getParcelable(IMAGEVIEWIMAGE);
            imageSoundRandom = (SparseIntArray) savedInstanceState.getParcelable(RANDOM);
            imageSoundAnimals = (SparseIntArray) savedInstanceState.getParcelable(ANIMALS);
            imageSoundDevices = (SparseIntArray) savedInstanceState.getParcelable(DEVICES);
            imageSoundInstruments = (SparseIntArray) savedInstanceState.getParcelable(INSTRUMENTS);

            msgHelper = savedInstanceState.getString(MSGHELPER);
            timeLeftInMillisNextRound = savedInstanceState.getLong(NEXTROUNDTIMER);

            asignAllButtons();
            startButton.setVisibility(View.INVISIBLE);

            if (gameInit) {
                logoLinear.setVisibility(View.GONE);
                if (limit == 0)
                {
                    playAudio.setImageResource(R.drawable.play_circle_outline_black_48dp);
                    playAudio.setVisibility(View.VISIBLE);
                    unclickable();
                }
                for (int i = 0; i < imageviewImage.size(); i++) {
                    ImageView iv = findViewById(imageviewImage.keyAt(i));
                    iv.setImageResource(imageviewImage.valueAt(i));
                }
                textRounds.setText(currentRound + " / " + TotalRounds);
                if (soundPlayed) {
                    playAudio.setVisibility(View.INVISIBLE);

                    if (mediaPlayer == null) {
                        mediaPlayer = MediaPlayer.create(SoundImage.this, pickedSound);
                        mediaPlayer.seekTo(audioposition);
                        mediaPlayer.start();
                        unclickable();

                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                soundPlayed = false;
                                playAudio.setImageResource(R.drawable.replay_black_48dp);
                                playAudio.setVisibility(View.VISIBLE);
                                clickable();

                                if (limit == 1) {
                                    startspeed = new Timestamp(System.currentTimeMillis());
                                }

                                if ((limitReplay == limit && currentDifficulty.equals(getResources().getString(R.string.mediumValue))) || (limit == limitReplay && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))) {
                                    playAudio.setImageResource(R.drawable.limit_black_48dp);
                                    playAudio.setClickable(false);

                                }
                                mediaPlayer.release();
                                mediaPlayer = null;
                            }
                        });
                    }
                }
            } else {
                unclickable();

                if (currentRound == 0) {
                    startButton.setVisibility(View.VISIBLE);
                    logoLinear.setVisibility(View.VISIBLE);

                } else {
                    logoLinear.setVisibility(View.GONE);
                    textMsg.setText(msgHelper);
                    nextRoundTimer = userViewModel.getNextRoundTimer();
                    nextRoundTimer.cancel();
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
                                textMsgTime.setText("Επομενος γυρος σε: "+l/1000);
                            }


                        }

                        @Override
                        public void onFinish() {

                            timeLeftInMillisNextRound = 0;

                            if (currentRound == TotalRounds)
                            {
                                textsLinear.setVisibility(View.INVISIBLE);
                            }
                            else
                            {
                                nextRoundTimer = null;
                                textMsgTime.setText("");
                                textsLinear.setVisibility(View.INVISIBLE);
                                gameInit = true;
                                unclickable();
                                fillListImageview();
                                createRound();

                            }

                        }
                    }.start();
                    userViewModel.setNextRoundTimer(nextRoundTimer);

//                    startButton.setVisibility(View.VISIBLE);
//                    startButton.setText(getResources().getString(R.string.nextRound));
                    textRounds.setText(currentRound + " / " + TotalRounds);
                }
            }

        } else {

            loadImageSounds();
            asignAllButtons();

            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty =  session.getModeSession();
            unclickable();
        }



        matchlists();
        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);


        if (!session.getPlayAgainVideo() && currentRound == 0) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialSoundImage");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }




        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoLinear.setVisibility(View.GONE);
                gameInit = true;
                unclickable();
                fillListImageview();
                createRound();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mediaPlayer != null){
            audioposition = mediaPlayer.getCurrentPosition();
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit++;
                soundPlayed = true;
                playAudio.setVisibility(View.INVISIBLE);
                if (mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(SoundImage.this, pickedSound);
                    mediaPlayer.start();
                    unclickable();

                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            soundPlayed = false;
                            playAudio.setVisibility(View.VISIBLE);
                            playAudio.setImageResource(R.drawable.replay_black_48dp);
                            clickable();
                            if (limit == 1) {
                                startspeed = new Timestamp(System.currentTimeMillis());
                            }

                            if ((limitReplay == limit && currentDifficulty.equals(getResources().getString(R.string.mediumValue))) || (limit == limitReplay && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))) {
                                playAudio.setImageResource(R.drawable.limit_black_48dp);
                                playAudio.setClickable(false);

                            }
                            mediaPlayer.release();
                            mediaPlayer = null;
                        }
                    });
                }

            }

        });
        if ((limitReplay == limit && currentDifficulty.equals(getResources().getString(R.string.mediumValue))) || (limit == limitReplay && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))) {

            playAudio.setClickable(false);
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss , 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
                    gameEventViewModel.insertGameEvent(gameEvent);
                    userViewModel.updatestatsTest(user_id, game_id);
                    finish();

                }
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
    public void onBackPressed()
    {
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
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 1, totalPoints, (double) hit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }

    private void createRound() {
        startButton.setVisibility(View.INVISIBLE);
        playAudio.setImageResource(R.drawable.play_circle_outline_black_48dp);
        playAudio.setVisibility(View.VISIBLE);
        playAudio.setClickable(true);
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

        textRounds.setText(currentRound + "/" + TotalRounds);

    }

    private void displayGameEz(){
        loadAllimages();
        Random rand  = new Random();
        int picklist = rand.nextInt(pickRandList.size())+1;
        SparseIntArray tempList;
        tempList = (SparseIntArray) pickRandList.get(picklist);

        int randpickImgSound= rand.nextInt(tempList.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(tempList.valueAt(randpickImgSound));

        pickedSound = tempList.keyAt(randpickImgSound);


        imageviewImage.put(imageviews.get(randompickedimgv),tempList.valueAt(randpickImgSound));
        pickedImage.add(tempList.valueAt(randpickImgSound));

        for (int i : pickedImage) {
            ListIterator itr = allimages.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }

        imageviews.remove(randompickedimgv);

        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(allimages.size());

            ivv.setImageResource(allimages.get(i));
            imageviewImage.put(imgv, allimages.get(i));
            allimages.remove(i);
        }

    }
    private void displayGameMedium(){
        limitReplay = 3;
        loadAllimages();
        Random rand  = new Random();
        int picklist = rand.nextInt(pickRandList.size())+1;
        SparseIntArray tempList;
        tempList = (SparseIntArray) pickRandList.get(picklist);

        int randpickImgSound= rand.nextInt(tempList.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(tempList.valueAt(randpickImgSound));

        imageviewImage.put(imageviews.get(randompickedimgv),tempList.valueAt(randpickImgSound));

        pickedSound = tempList.keyAt(randpickImgSound);

        pickedImage.add(tempList.valueAt(randpickImgSound));

        ArrayList<Integer> helper = new ArrayList<>(6);

        for (int i = 0; i<tempList.size();i++)
        {
            helper.add(tempList.valueAt(i));
        }

        for (int i : pickedImage) {
            ListIterator itr = helper.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }


        for (int i : pickedImage) {
            ListIterator itr = allimages.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }

        imageviews.remove(randompickedimgv);


        int sec = rand.nextInt(helper.size());
        int secrandImgv = rand.nextInt(imageviews.size());
        ImageView ivvv = findViewById(imageviews.get(secrandImgv));
        ivvv.setImageResource(helper.get(sec));
        imageviewImage.put(imageviews.get(secrandImgv), helper.get(sec));


            ListIterator itr1 = allimages.listIterator();
            while (itr1.hasNext()) {
                int temp = (int) itr1.next();
                if (temp == helper.get(sec)) {
                    itr1.remove();
                    break;
                }
            }

        helper.remove(sec);
        imageviews.remove(secrandImgv);

        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(allimages.size());

            ivv.setImageResource(allimages.get(i));
            imageviewImage.put(imgv, allimages.get(i));
            allimages.remove(i);
        }

        helper.clear();
        allimages.clear();
    }


    private  void displayGameAdv(){
        limitReplay = 1;
        Random rand  = new Random();
        int picklist = rand.nextInt(pickRandList.size())+1;
        SparseIntArray tempList;
        tempList = (SparseIntArray) pickRandList.get(picklist);

        int randpickImgSound= rand.nextInt(tempList.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(tempList.valueAt(randpickImgSound));

        imageviewImage.put(imageviews.get(randompickedimgv),tempList.valueAt(randpickImgSound));

        pickedSound = tempList.keyAt(randpickImgSound);

        pickedImage.add(tempList.valueAt(randpickImgSound));

        ArrayList<Integer> helper = new ArrayList<>(6);

        for (int i = 0; i<tempList.size();i++)
        {
            helper.add(tempList.valueAt(i));
        }

        for (int i : pickedImage) {
            ListIterator itr = helper.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }

        imageviews.remove(randompickedimgv);

        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(helper.size());

            ivv.setImageResource(helper.get(i));
            imageviewImage.put(imgv, helper.get(i));
            helper.remove(i);
        }

        helper.clear();

    }


    private void fillListImageview() {
        imageviews.add(R.id.imageView1SIG);
        imageviews.add(R.id.imageView2SIG);
        imageviews.add(R.id.imageView3SIG);
        imageviews.add(R.id.imageView4SIG);
    }

    private void loadImageSounds(){
        imageSoundDevices.put(R.raw.car,R.drawable.si_devices_car);
        imageSoundDevices.put(R.raw.bike,R.drawable.si_devices_motorcycle);
        imageSoundDevices.put(R.raw.formula,R.drawable.si_devices_formula);
        imageSoundDevices.put(R.raw.mixer , R.drawable.si_devices_mixer);
        imageSoundDevices.put(R.raw.vacuum , R.drawable.si_devices_vacuum);
        imageSoundDevices.put(R.raw.drill , R.drawable.si_devices_drill);

        imageSoundAnimals.put(R.raw.cat ,R.drawable.si_animals_cat);
        imageSoundAnimals.put(R.raw.frog ,R.drawable.si_animals_frog);
        imageSoundAnimals.put(R.raw.dog ,R.drawable.si_animals_dog);
        imageSoundAnimals.put(R.raw.lion ,R.drawable.si_animals_lion);
        imageSoundAnimals.put(R.raw.birds ,R.drawable.si_animals_bird);
        imageSoundAnimals.put(R.raw.wolf ,R.drawable.si_animals_wolf);

        imageSoundInstruments.put(R.raw.accordeon , R.drawable.si_instruments_accordion);
        imageSoundInstruments.put(R.raw.bouzouki1 , R.drawable.si_instruments_bouzouki);
        imageSoundInstruments.put(R.raw.guitar , R.drawable.si_instruments_guitar);
        imageSoundInstruments.put(R.raw.piano , R.drawable.si_instruments_piano);
        imageSoundInstruments.put(R.raw.violin , R.drawable.si_instruments_violin);
        imageSoundInstruments.put(R.raw.trumbet , R.drawable.si_instruments_trumpet);

        imageSoundRandom.put(R.raw.gun, R.drawable.si_random_gun);
        imageSoundRandom.put(R.raw.bell_ring, R.drawable.si_random_bell);
        imageSoundRandom.put(R.raw.phone, R.drawable.si_random_phone);
        imageSoundRandom.put(R.raw.thunder, R.drawable.si_random_lightning);
        imageSoundRandom.put(R.raw.helicopter, R.drawable.si_random_helicopter);
        imageSoundRandom.put(R.raw.fireworks, R.drawable.si_random_fireworks);

    }

    private void loadAllimages(){
        allimages.add(R.drawable.si_devices_car);
        allimages.add(R.drawable.si_devices_motorcycle);
        allimages.add(R.drawable.si_devices_formula);
        allimages.add(R.drawable.si_devices_mixer);
        allimages.add(R.drawable.si_devices_vacuum);
        allimages.add(R.drawable.si_devices_drill);
        allimages.add(R.drawable.si_animals_cat);
        allimages.add(R.drawable.si_animals_frog);
        allimages.add(R.drawable.si_animals_dog);
        allimages.add(R.drawable.si_animals_lion);
        allimages.add(R.drawable.si_animals_bird);
        allimages.add(R.drawable.si_animals_wolf);
        allimages.add(R.drawable.si_instruments_accordion);
        allimages.add(R.drawable.si_instruments_bouzouki);
        allimages.add(R.drawable.si_instruments_guitar);
        allimages.add(R.drawable.si_instruments_piano);
        allimages.add(R.drawable.si_instruments_violin);
        allimages.add(R.drawable.si_instruments_trumpet);
        allimages.add(R.drawable.si_random_gun);
        allimages.add(R.drawable.si_random_bell);
        allimages.add(R.drawable.si_random_phone);
        allimages.add(R.drawable.si_random_lightning);
        allimages.add(R.drawable.si_random_helicopter);
        allimages.add(R.drawable.si_random_fireworks);
    }

    private void matchlists(){
        pickRandList.put(1,imageSoundDevices);
        pickRandList.put(2,imageSoundAnimals);
        pickRandList.put(3,imageSoundInstruments);
        pickRandList.put(4,imageSoundRandom);
    }

    private void asignAllButtons() {
        exit = findViewById(R.id.ExitSIG);
        replayTutorial = findViewById(R.id.ReplayTutorialSIG);
        startButton = findViewById(R.id.startButtonSIG);
        imgv1 = findViewById(R.id.imageView1SIG);
        imgv2 = findViewById(R.id.imageView2SIG);
        imgv3 = findViewById(R.id.imageView3SIG);
        imgv4 = findViewById(R.id.imageView4SIG);
        unclickable();
        playAudio = findViewById(R.id.imageViewPlayAudioSIG);
        playAudio.setVisibility(View.INVISIBLE);
        textRounds = findViewById(R.id.textRoundsSIG);

        animPointsText = findViewById(R.id.AnimTextPointsSIG);
        logoLinear = findViewById(R.id.imageLogoDisplaySIG);
        textsLinear = findViewById(R.id.textsSIG);
        textMsg = findViewById(R.id.msgSIG);
        textMsgTime = findViewById(R.id.msgSIG1);

        imgv1.setOnClickListener(this);
        imgv2.setOnClickListener(this);
        imgv3.setOnClickListener(this);
        imgv4.setOnClickListener(this);

       if ((limitReplay>limit && gameInit) || (gameInit && limit >=1 && currentDifficulty.equals(getResources().getString(R.string.easyValue))))
        {
            playAudio.setVisibility(View.VISIBLE);
            playAudio.setImageResource(R.drawable.replay_black_48dp);
            clickable();

        }

        if ((limitReplay == limit && currentDifficulty.equals(getResources().getString(R.string.mediumValue)) && gameInit) || (gameInit && limit == limitReplay && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))) {
            playAudio.setImageResource(R.drawable.limit_black_48dp);
            playAudio.setVisibility(View.VISIBLE);
            playAudio.setClickable(false);
            clickable();

        }

    }


    private void unclickable() {
        imgv1.setClickable(false);
        imgv2.setClickable(false);
        imgv3.setClickable(false);
        imgv4.setClickable(false);
    }

    private void clickable() {
        imgv1.setClickable(true);
        imgv2.setClickable(true);
        imgv3.setClickable(true);
        imgv4.setClickable(true);
    }

    @Override
    public void onClick(View view) {
        unclickable();

        Timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) { }

            @Override
            public void onFinish() {
                for (int imageviewId = 0; imageviewId < imageviewImage.size(); imageviewId++) {
                    ImageView v = findViewById(imageviewImage.keyAt(imageviewId));
                    v.setImageResource(0);
                }
                imageviewImage.clear();
                pickedImage.clear();
//                startButton.setText(getResources().getString(R.string.nextRound));
//                startButton.setVisibility(View.VISIBLE);
                if (currentRound == TotalRounds)
                {
                    startButton.setVisibility(View.INVISIBLE);
                }
            }
        };
        limit = 0;
        playAudio.setVisibility(View.INVISIBLE);
        allimages.clear();
        imageviews.clear();
        gameInit = false;
        click++;
        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        if (!(imageviewImage.indexOfKey(view.getId()) < 0)) {

            startAnimation();
            if (pickedImage.contains(imageviewImage.get(view.getId()))) {
                hit++;
                trueCounter++;

            } else {

                Animation animShake = AnimationUtils.loadAnimation(this, R.anim.shake);
                view.startAnimation(animShake);
                int vibeduration = 1000;
                vibe.vibrate(vibeduration);
                miss++;
                missPoints = true;
            }
            countPoints();
        }

        if (currentRound == TotalRounds) {
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        }
        Timer.start();
        nextRound();



    }


    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);

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
                    textMsgTime.setText("Επομενος γυρος σε: "+l/1000);
                }


            }

            @Override
            public void onFinish() {

                timeLeftInMillisNextRound = 0;

                if (currentRound == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                }
                else
                {
                    nextRoundTimer = null;
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    gameInit = true;
                    unclickable();
                    fillListImageview();
                    createRound();

                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(SoundImage.this,R.raw.tutorial_soundimage,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialSoundImage");
    }



    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, SoundImage.this, hit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "SoundImage");
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



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(MENUDIFFICULTY, menuDifficulty);
        outState.putString(CURRENTDIFFICULTY, currentDifficulty);
        outState.putInt(GAME_ID, game_id);
        outState.putInt(USER_ID, user_id);
        outState.putInt(TOTALROUNDS, TotalRounds);
        outState.putInt(CURRENTROUND, currentRound);
        outState.putInt(HIT, hit);
        outState.putInt(MISS, miss);
        outState.putInt(TOTALPOINTS, totalPoints);
        outState.putInt(TRUECOUNTER, trueCounter);
        outState.putInt(CLICK, click);
        outState.putInt(PICKEDSOUND, pickedSound);
        outState.putInt(LIMIT, limit);
        outState.putInt(LIMITREPLAY, limitReplay);
        outState.putDouble(TOTALSPEED, totalspeed);
        outState.putSerializable(STARTTIME, startTime);
        outState.putSerializable(ENDTIME, endTime);
        outState.putSerializable(STARTSPEED, startspeed);
        outState.putSerializable(ENDSPEED, endspeed);
        outState.putBoolean(MISSPOINTS, missPoints);
        outState.putBoolean(GAMEINIT, gameInit);
        outState.putBoolean(SOUNDPLAYED, soundPlayed);
        outState.putInt(AUDIOPOS,audioposition);
        outState.putParcelable(DEVICES, new SparseIntArrayParcelable(imageSoundDevices));
        outState.putParcelable(ANIMALS, new SparseIntArrayParcelable(imageSoundAnimals));
        outState.putParcelable(INSTRUMENTS, new SparseIntArrayParcelable(imageSoundInstruments));
        outState.putParcelable(RANDOM, new SparseIntArrayParcelable(imageSoundRandom));
        outState.putParcelable(IMAGEVIEWIMAGE, new SparseIntArrayParcelable(imageviewImage));
        outState.putIntegerArrayList(IMAGEVIEWS, imageviews);
        outState.putIntegerArrayList(PICKEDIMAGES, pickedImage);
        outState.putIntegerArrayList(ALLIMAGES, allimages);
        outState.putString(MSGHELPER, msgHelper);
        outState.putLong(NEXTROUNDTIMER,timeLeftInMillisNextRound);

    }


    public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
        public Creator<SparseIntArrayParcelable> CREATOR = new Creator<SparseIntArrayParcelable>() {
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
