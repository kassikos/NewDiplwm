package com.example.newdiplwm;

import android.app.Application;
import android.os.CountDownTimer;
import android.util.SparseIntArray;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository userRepository;
    private LiveData<List<User>> allusers;
    private CountDownTimer timer;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allusers = userRepository.getAllusers();
    }

    public void insert(User user)
    {
        userRepository.insert(user);
    }

    public User getUserByName(String nickname)
    {
        return userRepository.getUserByname(nickname);
    }

    public LiveData<List<User>> getAllusers() {
        return allusers;
    }

    public void insertStatistics(List<Statistic> statistics) {userRepository.insertStatistics(statistics);}

    public void insertStatisticsTest(Statistic statistic) {userRepository.insertStatisticTest(statistic);}

    public void updatestatsTest(int b, int c){userRepository.updateStatistics(b,c);}

    public List<UserGameStats> getAllStatsModel(int userid){return userRepository.getAllStats(userid);}

    public void saveTimer (CountDownTimer t)
    {
        this.timer = t;
    }
    public CountDownTimer getTimer()
    {
        return timer;
    }
}
