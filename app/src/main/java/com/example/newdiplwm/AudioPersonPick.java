package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Vibrator;
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
import java.util.ListIterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AudioPersonPick extends AppCompatActivity implements View.OnClickListener {

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
    private static final String MATCH = "MATCH";
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
    private static final String PERSONSOUNDFOURCOLOURS = "PERSONSOUNDFOURCOLOURS";
    private static final String ALLIMAGES = "ALLIMAGES";
    private static final String ALLIMAGESFOUR = "ALLIMAGESFOUR";

    private ImageView imgv1, imgv2, imgv3, imgv4, playAudio ,exit, replayTutorial;
    private MaterialButton startButton;
    private TextView textRounds, animPointsText;
    private LinearLayout logoLinear;
    private Vibrator vibe;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private CountDownTimer Timer;

    private SparseIntArray personSound = new SparseIntArray(5);
    private SparseIntArray personSoundfourColors = new SparseIntArray(10);
    private SparseIntArray imageviewImage = new SparseIntArray(4);
    private ArrayList<Integer> imageviews = new ArrayList<Integer>(4);
    private ArrayList<Integer> pickedImage = new ArrayList<Integer>(1);
    private ArrayList<Integer> allimages = new ArrayList<Integer>(10);
    private ArrayList<Integer> allimagesfour = new ArrayList<Integer>(10);

    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    private int user_id, game_id, currentRound = 0, TotalRounds = 0;
    private int hit = 0, miss = 0, totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;
    private String menuDifficulty, currentDifficulty = "";


    private int click = 0, audioposition = 0, pickedSound = 0, limit = 0, limitReplay = 0;
    boolean gameInit = false, soundPlayed = false;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;
    MediaPlayer mediaPlayer;

    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_person_pick);
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
            allimagesfour = savedInstanceState.getIntegerArrayList(ALLIMAGESFOUR);
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
            personSound = (SparseIntArray) savedInstanceState.getParcelable(MATCH);
            imageviewImage = (SparseIntArray) savedInstanceState.getParcelable(IMAGEVIEWIMAGE);
            personSoundfourColors = (SparseIntArray) savedInstanceState.getParcelable(PERSONSOUNDFOURCOLOURS);
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
                        mediaPlayer = MediaPlayer.create(AudioPersonPick.this, pickedSound);
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

                if (currentRound == 0) {
                    logoLinear.setVisibility(View.VISIBLE);
                    startButton.setVisibility(View.VISIBLE);
                    unclickable();

                } else {
                    logoLinear.setVisibility(View.GONE);
                    startButton.setVisibility(View.VISIBLE);
                    startButton.setText(getResources().getString(R.string.nextRound));
                    textRounds.setText(currentRound + " / " + TotalRounds);
                }
            }

        } else {


            asignAllButtons();
            user_id = session.getUserIdSession();
            game_id = session.getGameIdSession();
            menuDifficulty = session.getModeSession();
            matching();
            matchFourColours();
            unclickable();
        }


        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);

        if (!session.getPlayAgainVideo() && currentRound == 0) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialAudioPersonPick");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }






        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameInit = true;
                logoLinear.setVisibility(View.GONE);
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
                    mediaPlayer = MediaPlayer.create(AudioPersonPick.this, pickedSound);
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




    @Override
    public void onBackPressed()
    {

        onbackAndExitCode();
    }


    private void onbackAndExitCode(){
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

    private void displayGameEz() {
        loadImages();
        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(personSound.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(personSound.valueAt(randpickImgSound));

        pickedImage.add(personSound.valueAt(randpickImgSound));

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

        imageviewImage.put(imageviews.get(randompickedimgv), personSound.valueAt(randpickImgSound));
        pickedSound = personSound.keyAt(randpickImgSound);

        imageviews.remove(randompickedimgv);

        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(allimages.size());

            ivv.setImageResource(allimages.get(i));
            imageviewImage.put(imgv, allimages.get(i));
            allimages.remove(i);
        }

    }

    private void displayGameMedium() {
        loadImagesfour();
        limitReplay = 3;

        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(personSoundfourColors.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(personSoundfourColors.valueAt(randpickImgSound));

        pickedImage.add(personSoundfourColors.valueAt(randpickImgSound));

        for (int i : pickedImage) {
            ListIterator itr = allimagesfour.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }

        imageviewImage.put(imageviews.get(randompickedimgv), personSoundfourColors.valueAt(randpickImgSound));
        pickedSound = personSoundfourColors.keyAt(randpickImgSound);

        imageviews.remove(randompickedimgv);


        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(allimagesfour.size());

            ivv.setImageResource(allimagesfour.get(i));
            imageviewImage.put(imgv, allimagesfour.get(i));
            allimagesfour.remove(i);
        }


    }

    private void displayGameAdv() {
        loadImagesfour();
        limitReplay = 1;
        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(personSoundfourColors.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(personSoundfourColors.valueAt(randpickImgSound));

        pickedImage.add(personSoundfourColors.valueAt(randpickImgSound));

        for (int i : pickedImage) {
            ListIterator itr = allimagesfour.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }

        imageviewImage.put(imageviews.get(randompickedimgv), personSoundfourColors.valueAt(randpickImgSound));
        pickedSound = personSoundfourColors.keyAt(randpickImgSound);

        imageviews.remove(randompickedimgv);

        for (int imgv : imageviews) {
            ImageView ivv = findViewById(imgv);

            int i = rand.nextInt(allimagesfour.size());

            ivv.setImageResource(allimagesfour.get(i));
            imageviewImage.put(imgv, allimagesfour.get(i));
            allimagesfour.remove(i);
        }


    }

    @Override
    public void onClick(View view) {
        unclickable();
        Timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) {
            }

            @Override
            public void onFinish() {
                for (int imageviewId = 0; imageviewId < imageviewImage.size(); imageviewId++) {
                    ImageView v = findViewById(imageviewImage.keyAt(imageviewId));
                    v.setImageResource(0);
                }
                imageviewImage.clear();
                pickedImage.clear();
                startButton.setText(getResources().getString(R.string.nextRound));
                startButton.setVisibility(View.VISIBLE);
            }
        };
        limit = 0;
        playAudio.setVisibility(View.INVISIBLE);
        allimages.clear();
        allimagesfour.clear();
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
            startButton.setVisibility(View.INVISIBLE);
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        }
        Timer.start();


    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(AudioPersonPick.this,R.raw.tutorial_audiopersonpick,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialAudioPersonPick");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, AudioPersonPick.this, hit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "AudioPersonPick");
    }

    private void countPoints() {

        int currentPoints = 0;

        if (!missPoints && trueCounter == 1) {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
        } else if (!missPoints && trueCounter == 2) {
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
        } else if (!missPoints && trueCounter >= 3) {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
        } else if (missPoints) {
            currentPoints += 0;
            trueCounter = 0;
            missPoints = false;
        }
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

    private void loadImages() {
        allimages.add(R.drawable.person1);
        allimages.add(R.drawable.person2);
        allimages.add(R.drawable.person3);
        allimages.add(R.drawable.person4);
        allimages.add(R.drawable.person5);
        allimages.add(R.drawable.person6);
        allimages.add(R.drawable.person7);
        allimages.add(R.drawable.person8);
        allimages.add(R.drawable.person9);
        allimages.add(R.drawable.person10);
        allimages.add(R.drawable.person11);
        allimages.add(R.drawable.person12);
        allimages.add(R.drawable.person13);
        allimages.add(R.drawable.person14);
        allimages.add(R.drawable.person15);
    }

    private void loadImagesfour() {
        allimagesfour.add(R.drawable.person1four);
        allimagesfour.add(R.drawable.person2four);
        allimagesfour.add(R.drawable.person3four);
        allimagesfour.add(R.drawable.person4four);
        allimagesfour.add(R.drawable.person5four);
        allimagesfour.add(R.drawable.person6four);
        allimagesfour.add(R.drawable.person7four);
        allimagesfour.add(R.drawable.person8four);
        allimagesfour.add(R.drawable.person9four);
        allimagesfour.add(R.drawable.person10four);
        allimagesfour.add(R.drawable.person11four);
        allimagesfour.add(R.drawable.person12four);
        allimagesfour.add(R.drawable.person13four);
        allimagesfour.add(R.drawable.person14four);
        allimagesfour.add(R.drawable.person15four);
    }

    private void matching() {


        personSound.put(R.raw.d5ez, R.drawable.person5);
        personSound.put(R.raw.d4ez, R.drawable.person4);
        personSound.put(R.raw.d3ez, R.drawable.person3);
        personSound.put(R.raw.d2ez, R.drawable.person2);
        personSound.put(R.raw.d1ez, R.drawable.person1);

    }

    private void matchFourColours() {
        personSoundfourColors.put(R.raw.person1four, R.drawable.person1four);
        personSoundfourColors.put(R.raw.person2four, R.drawable.person2four);
        personSoundfourColors.put(R.raw.person3four, R.drawable.person3four);
        personSoundfourColors.put(R.raw.person4four, R.drawable.person4four);
        personSoundfourColors.put(R.raw.person5four, R.drawable.person5four);
        personSoundfourColors.put(R.raw.person6four, R.drawable.person6four);
        personSoundfourColors.put(R.raw.person7four, R.drawable.person7four);
        personSoundfourColors.put(R.raw.person8four, R.drawable.person8four);
        personSoundfourColors.put(R.raw.person9four, R.drawable.person9four);
        personSoundfourColors.put(R.raw.person10four, R.drawable.person10four);
    }

    private void asignAllButtons() {
        exit = findViewById(R.id.ExitAPPG);
        replayTutorial = findViewById(R.id.ReplayTutorialAPPG);
        startButton = findViewById(R.id.startButtonAPPG);
        imgv1 = findViewById(R.id.imageView1APPG);
        imgv2 = findViewById(R.id.imageView2APPG);
        imgv3 = findViewById(R.id.imageView3APPG);
        imgv4 = findViewById(R.id.imageView4APPG);
        unclickable();
        playAudio = findViewById(R.id.imageViewPlayAudio);
        playAudio.setVisibility(View.INVISIBLE);
        textRounds = findViewById(R.id.textRoundsAPPG);
        logoLinear = findViewById(R.id.imageLogoDisplayAPPG);

        animPointsText = findViewById(R.id.AnimTextPointsAPPG);

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


    private void fillListImageview() {
        imageviews.add(R.id.imageView1APPG);
        imageviews.add(R.id.imageView2APPG);
        imageviews.add(R.id.imageView3APPG);
        imageviews.add(R.id.imageView4APPG);
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
        outState.putParcelable(MATCH, new SparseIntArrayParcelable(personSound));
        outState.putParcelable(PERSONSOUNDFOURCOLOURS, new SparseIntArrayParcelable(personSoundfourColors));
        outState.putParcelable(IMAGEVIEWIMAGE, new SparseIntArrayParcelable(imageviewImage));
        outState.putIntegerArrayList(IMAGEVIEWS, imageviews);
        outState.putIntegerArrayList(PICKEDIMAGES, pickedImage);
        outState.putIntegerArrayList(ALLIMAGES, allimages);
        outState.putIntegerArrayList(ALLIMAGESFOUR, allimagesfour);
    }


    public class SparseIntArrayParcelable extends SparseIntArray implements Parcelable {
        public Creator<AudioPersonPick.SparseIntArrayParcelable> CREATOR = new Creator<AudioPersonPick.SparseIntArrayParcelable>() {
            @Override
            public AudioPersonPick.SparseIntArrayParcelable createFromParcel(Parcel source) {
                AudioPersonPick.SparseIntArrayParcelable read = new AudioPersonPick.SparseIntArrayParcelable();
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
            public AudioPersonPick.SparseIntArrayParcelable[] newArray(int size) {
                return new AudioPersonPick.SparseIntArrayParcelable[size];
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
