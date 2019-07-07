package com.example.newdiplwm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {

    @Query("select * from games")
    public List<Game> getAllGames();

    @Insert
    public void addGame(Game game);

    @Insert
    public void addGames(Game[] games);

    @Query("Select * FROM games where name = :name")
    Game getGameByName(String name);
}
