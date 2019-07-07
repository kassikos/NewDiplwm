package com.example.newdiplwm;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM users")
    LiveData<List<User>> allusers();

    @Insert
    public void addUser(User user);

    @Query("Select * FROM users where nickName = :nick")
    User getUser(String nick);


}
