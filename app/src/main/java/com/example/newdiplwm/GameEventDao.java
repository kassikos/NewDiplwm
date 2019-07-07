package com.example.newdiplwm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameEventDao {

    @Query("SELECT * FROM gameEvent")
    LiveData<List<GameEvent>> allgameEvents();

    @Insert
    public void addGameEvent(GameEvent gameEventt);

//    @Query("Select * from gameEvent GE inner join users U on GE.userIdForeign = U.userId inner join games G on GE.gameIdForeign = G.id " +
//            "where U.nickName = :nick and G.name = :gameName ")
//    public List<GameEvent> allEventsForUserInASingleGame(String nick, String gameName);


}
