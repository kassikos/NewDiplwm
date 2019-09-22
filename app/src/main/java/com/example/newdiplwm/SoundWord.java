package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.media.MediaPlayer;
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
import java.util.concurrent.TimeUnit;

public class SoundWord extends AppCompatActivity implements  SoundWordEz.OnDataPassSWEz,SoundWordMed.OnDataPassSWMed,SoundWordAdv.OnDataPassSWAdv{

    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private ImageView playAudio , exit, replayTutorial;
    private MaterialButton startButton;
    private TextView textRounds, textTimer, animPointsText ,  textMsg , textMsgTime;;
    private LinearLayout logoLinear, textsLinear;

    private CountDownTimer Timer , nextRoundTimer;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private SoundWordViewModel soundWordViewModel;

    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();

    private int user_id, game_id, currentRound = 0, TotalRounds = 0 , audioposition = 0;
    private int totalhit = 0, totalmiss = 0, totalPoints = 0, trueCounter = 0;
    private boolean missPoints = false;
    private String menuDifficulty, currentDifficulty = "";

    private boolean gameInit = false , soundPlayed = false;

    private int pickedSound = 0 ,click = 0 ,limit = 0, limitReplay = 0;

    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    MediaPlayer mediaPlayer;

    SoundWordEz soundWordEz;
    SoundWordMed soundWordMed;
    SoundWordAdv soundWordAdv;

    private Session session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_word);
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        soundWordViewModel = ViewModelProviders.of(this).get(SoundWordViewModel.class);
        session = new Session(getApplicationContext());

        boolean test = session.getPlayAgainVideo();

        if (!test) {
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        Fragment prev = fm.findFragmentByTag("TutorialSoundWord");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
            //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
        }







        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);
        assignAllbuttons();


        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        menuDifficulty = session.getModeSession();







        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoLinear.setVisibility(View.GONE);

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialSoundWord");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                gameInit = true;
                createRound();

              //  startButton.setText(R.string.nextRound);
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        if (soundPlayed) {
            playAudio.setVisibility(View.INVISIBLE);
            disableReplayTut();

            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(SoundWord.this, soundWordViewModel.getPickedSound());
                mediaPlayer.seekTo(audioposition);
                mediaPlayer.start();
                if (currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                {
                    soundWordEz.unclickable();
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                {
                    soundWordMed.unclickable();
                }
                else if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                {
                    soundWordAdv.unclickable();
                }


                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        enableReplayTut();
                        soundPlayed = false;
                        playAudio.setImageResource(R.drawable.replay_black_48dp);
                        playAudio.setVisibility(View.VISIBLE);
                        if (currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                        {
                            soundWordEz.clickable();
                        }
                        else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                        {
                            soundWordMed.clickable();
                        }
                        else if (currentDifficulty.equals(getResources().getString(R.string.advancedValue)))
                        {
                            soundWordAdv.clickable();
                        }

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


        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limit++;
                disableReplayTut();
                soundPlayed = true;
                playAudio.setVisibility(View.INVISIBLE);
                if (mediaPlayer == null) {
                    if (currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                    {
                        soundWordEz.unclickable();
                    }
                    else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                    {
                        soundWordMed.unclickable();
                    }
                    else
                        soundWordAdv.unclickable();

                    int sth = soundWordViewModel.getPickedSound();
                    mediaPlayer = MediaPlayer.create(SoundWord.this,sth );
                    mediaPlayer.start();


                    mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            soundPlayed = false;
                            enableReplayTut();
                            playAudio.setVisibility(View.VISIBLE);
                            playAudio.setImageResource(R.drawable.replay_black_48dp);
                            playAudio.setClickable(true);

                            if (currentDifficulty.equals(getResources().getString(R.string.easyValue)))
                            {
                                soundWordEz.clickable();
                            }
                            else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)))
                            {
                                soundWordMed.clickable();
                            }
                            else
                                soundWordAdv.clickable();

                            if (limit == 1) {
                                startspeed = new Timestamp(System.currentTimeMillis());
                            }

                            if ((soundWordViewModel.getReplaylimit() == limit && currentDifficulty.equals(getResources().getString(R.string.mediumValue))) || (limit == soundWordViewModel.getReplaylimit() && currentDifficulty.equals(getResources().getString(R.string.advancedValue)))) {
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
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
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
            GameEvent gameEvent = new GameEvent(game_id, user_id, totalhit, totalmiss, 1, totalPoints, (double) totalhit / TotalRounds, totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }
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
    public void onBackPressed()
    {
        onbackAndExitCode();
    }


    private void destroyFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().
                remove(fragment).commit();
    }

    private void loadFragment(Fragment fragment) {
// create a FragmentManager
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.replace(R.id.frameLayoutSoundWord, fragment);
        fragmentTransaction.commit(); // save the changes
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

            soundWordEz = new SoundWordEz();
            loadFragment(soundWordEz);
        } else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            soundWordMed = new SoundWordMed();
            loadFragment(soundWordMed);

        } else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            soundWordAdv = new SoundWordAdv();
            loadFragment(soundWordAdv);

        } else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue))) {
            TotalRounds = 4;

            if (currentRound <= 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                soundWordEz = new SoundWordEz();
                loadFragment(soundWordEz);
            } else {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                soundWordMed = new SoundWordMed();
                loadFragment(soundWordMed);
            }
        } else {
            TotalRounds = 5;
            if (currentRound < 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                soundWordEz = new SoundWordEz();
                loadFragment(soundWordEz);

            } else if (currentRound >= 1 && currentRound <= 2) {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                soundWordMed = new SoundWordMed();
                loadFragment(soundWordMed);

            } else {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                soundWordAdv = new SoundWordAdv();
                loadFragment(soundWordAdv);

            }
        }
        currentRound++;

        textRounds.setText(currentRound + "/" + TotalRounds);

    }



    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);

        disableReplayTut();
        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {


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
                    gameInit = true;
                    createRound();

                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }


    private void assignAllbuttons() {
        startButton = findViewById(R.id.startButtonSWG);
        exit = findViewById(R.id.Exit);
        replayTutorial = findViewById(R.id.ReplayTutorialSWG);
        playAudio = findViewById(R.id.imageViewPlayAudioSWG);
        playAudio.setVisibility(View.INVISIBLE);
        textRounds = findViewById(R.id.textRoundsSWG);
        animPointsText = findViewById(R.id.AnimTextPointsSWG);
        logoLinear = findViewById(R.id.imageLogoDisplaySWG);
        textsLinear = findViewById(R.id.textsSWG);
        textMsg = findViewById(R.id.msgSWG);
        textMsgTime = findViewById(R.id.msgSWG1);


    }


    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }

    private void showTutorialPopUp(){
        DialogFragment dialogFragment = new Tutorial(SoundWord.this,R.raw.tutorial_soundword,getPackageName());
        dialogFragment.show(getSupportFragmentManager(),"TutorialSoundWord");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, SoundWord.this, totalhit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "SoundWordPopUp");
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

    private void checkIfEnds()
    {
        //startButton.setVisibility(View.VISIBLE);

        if (currentRound == TotalRounds) {
            startButton.setVisibility(View.INVISIBLE);
            textsLinear.setVisibility(View.VISIBLE);
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, totalhit, totalmiss, 0, totalPoints, (double) totalhit / (totalhit + totalmiss), totalspeed / click, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        }
        else
        {
            nextRound();
        }
    }

    @Override
    public void onDataPass(int hit, int miss, long endspeedLong, boolean misspoints, int truecounter) {
        playAudio.setVisibility(View.INVISIBLE);
        limit = 0;
        click++;
        totalhit +=hit;
        totalmiss +=miss;

        long diffspeed = endspeedLong - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;


        missPoints = misspoints;
        trueCounter +=truecounter;
        startAnimation();
        countPoints();
        checkIfEnds();
        //destroyFragment();

    }

}
