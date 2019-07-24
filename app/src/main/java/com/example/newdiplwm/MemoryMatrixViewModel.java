package com.example.newdiplwm;

import android.os.CountDownTimer;

import androidx.lifecycle.ViewModel;

public class MemoryMatrixViewModel extends ViewModel {
    private CountDownTimer timer;

    public CountDownTimer getTimer() {
        return timer;
    }

    public void setTimer(CountDownTimer timer) {
        this.timer = timer;
    }
}
