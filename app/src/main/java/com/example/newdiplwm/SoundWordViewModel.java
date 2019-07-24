package com.example.newdiplwm;


import android.os.CountDownTimer;

import androidx.lifecycle.ViewModel;

public class SoundWordViewModel extends ViewModel {

    private int pickedSound , replayLimit;
    private CountDownTimer timer;

    public int getPickedSound() {
        return pickedSound;
    }

    public void setPickedSound(int pickedSound) {
        this.pickedSound = pickedSound;
    }

    public  int getReplaylimit(){return  replayLimit;}

    public void setReplayLimit(int replayLimit) {
        this.replayLimit = replayLimit;
    }

}
