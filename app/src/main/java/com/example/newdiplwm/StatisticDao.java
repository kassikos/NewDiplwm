package com.example.newdiplwm;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface StatisticDao {

    @Insert
    public void addStatistic(Statistic statistic);

    @Insert
    public void addStatistics(List<Statistic> statistics);


    @Query("UPDATE statistic SET hit = (Select SUM(gameEvent.hit) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) , miss = (Select SUM(gameEvent.miss) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) , quits = (Select SUM(gameEvent.quite) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) ,score = (Select SUM(gameEvent.score) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) , days = (Select COUNT(DISTINCT DATE(gameEvent.startTime)) FROM gameEvent where gameEvent.userIdForeign = :userid and gameEvent.gameIdForeign = :gameid/*and DATE(gameEvent.endTime) <= date('now')*/) , playTotalTime = (Select SUM(gameEvent.playTotalTime) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) , accuracy = (Select (SUM(gameEvent.accuracy)/COUNT(gameEvent.gameEventId)) FROM gameEvent WHERE gameEvent.gameIdForeign = :gameid AND gameEvent.userIdForeign = :userid) WHERE gameIdForeign = :gameid AND userIdForeign = :userid ")
    public void updateStats(int userid , int gameid);
//
//    @Query(value = "select * from gameEvent GE inner join statistic S on S.userIdForeign = GE.userIdForeign inner join users U on U.userId = GE.userIdForeign " +
//            "where U.nickName = ")
}
