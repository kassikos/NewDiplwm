package com.example.newdiplwm;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;

public class GameEventViewModel extends AndroidViewModel {

    private UserRepository userRepository;

    public GameEventViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
    }

    public void  insertGameEvent(GameEvent gameEvent){
        userRepository.insertGameEvent(gameEvent);
    }
}