package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class AudioPersonPick extends AppCompatActivity {

    private static final String USER_ID = "USER_ID";
    private static final String GAME_ID = "GAME_ID";
    private static final String DIFFICULTY = "DIFFICULTY";
    private int user_id, game_id;
    private String menuDifficulty;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_person_pick);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        user_id = extras.getInt(USER_ID);
        game_id = extras.getInt(GAME_ID);
        menuDifficulty = extras.getString(DIFFICULTY);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.adespoto);
        mediaPlayer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }
}
