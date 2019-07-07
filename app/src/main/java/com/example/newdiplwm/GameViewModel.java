package com.example.newdiplwm;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class GameViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<Game>> getAllGames;

    public GameViewModel (@NonNull Application application){
        super(application);
        userRepository = new UserRepository(application);
        //getAllGames = userRepository.getAllGames();

    }
//    public LiveData<List<Game>> getGetallgames() {
//        return getAllGames;
//    }

    public Game getGameByName(String gameName){return userRepository.getGameByName(gameName);}

    public List<Game> loadAllGames(){return  userRepository.getAllGamesss();}
}
