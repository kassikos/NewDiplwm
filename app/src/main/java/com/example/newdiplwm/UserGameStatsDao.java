package com.example.newdiplwm;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserGameStatsDao {


    @Query("select users.nickName , statistic.*, games.name from statistic INNER JOIN users on users.userId = statistic.userIdForeign  Left JOIN games on games.id = statistic.gameIdForeign where statistic.userIdForeign = :userid " )
    public List<UserGameStats> gellAllStats(int userid);
}
