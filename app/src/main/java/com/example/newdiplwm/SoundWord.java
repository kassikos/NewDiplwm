package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class SoundWord extends AppCompatActivity {

    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";

    private ImageView imgv1, imgv2, imgv3, imgv4, playAudio;
    private MaterialButton startButton;
    private TextView textRounds, textTimer, animPointsText;

    private int user_id, game_id;
    private String menuDifficulty;
    MediaPlayer mediaPlayer;


    private ArrayList<Integer> allImages = new ArrayList<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sound_word);
        assignAllbuttons();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);


    }

    @Override
    protected void onResume() {
        super.onResume();
        playAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAudio.setImageResource(R.drawable.pause);
                mediaPlayer = initMediaplayer();
                mediaPlayer.start();

            }
        });

        if (mediaPlayer != null)
        {
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    Log.d("finish", "finish");
                }
            });
        }



//        if (mediaPlayer != null) {
//            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mediaPlayer) {
//                    Log.d("ON_PREPARED","ON_PREPARED");
//                    mediaPlayer.start();
//                }
//            });
//            mediaPlayer.prepareAsync();
//        }

    }

    private MediaPlayer initMediaplayer() {
        mediaPlayer = MediaPlayer.create(SoundWord.this, R.raw.person1four);
        return mediaPlayer;
    }

    private void releaseMedia(MediaPlayer m) {
        m.release();

    }

    private void loadImages() {
        allImages.add(R.drawable.person1);
        allImages.add(R.drawable.person2);
        allImages.add(R.drawable.person3);
        allImages.add(R.drawable.person4);
        allImages.add(R.drawable.person5);
    }

    private void assignAllbuttons() {
        playAudio = findViewById(R.id.imageViewPlayAudioSWG);

    }
}
