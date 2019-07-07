package com.example.newdiplwm;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "gameEvent",
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "userId",
                        childColumns = "userIdForeign",
                        onDelete = CASCADE,
                        onUpdate = CASCADE),
                @ForeignKey(entity = Game.class ,
                        parentColumns = "id",
                        childColumns = "gameIdForeign",
                        onDelete = CASCADE,
                        onUpdate = CASCADE)},indices = {@Index("gameIdForeign"),@Index("userIdForeign")})
public class GameEvent {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int gameEventId;

    @ColumnInfo(name = "gameIdForeign")
    private int gameIdForeign;

    @ColumnInfo(name = "userIdForeign")
    private int userIdForeign;

    @ColumnInfo(name = "hit")
    @NonNull
    private int hit;
    @ColumnInfo(name = "miss")
    @NonNull
    private int miss;
    @ColumnInfo(name = "quite")
    @NonNull
    private int quite;
    @ColumnInfo(name = "score")
    @NonNull
    private int score;
    @ColumnInfo(name = "accuracy" ,typeAffinity = ColumnInfo.REAL)
    @NonNull
    private double accuracy;
    @ColumnInfo(name = "avgSpeed" ,typeAffinity = ColumnInfo.REAL)
    private double avgSpeed;
    @ColumnInfo(name = "playTotalTime" , typeAffinity = ColumnInfo.REAL)
    private double playTotalTime;
    @ColumnInfo(name = "difficulty")
    @NonNull
    private String difficulty;
    @ColumnInfo(name = "startTime")
    private Date startTime;
    @ColumnInfo(name = "endTime")
    private Date endTime;


    @Ignore
    public GameEvent() {
    }

    public int getGameEventId() {
        return gameEventId;
    }

    public void setGameEventId(int gameEventId) {
        this.gameEventId = gameEventId;
    }

    public int getGameIdForeign() {
        return gameIdForeign;
    }

    public void setGameIdForeign(int gameIdForeign) {
        this.gameIdForeign = gameIdForeign;
    }

    public int getUserIdForeign() {
        return userIdForeign;
    }

    public void setUserIdForeign(int userIdForeign) {
        this.userIdForeign = userIdForeign;
    }

    public int getHit() {
        return hit;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public int getMiss() {
        return miss;
    }

    public void setMiss(int miss) {
        this.miss = miss;
    }

    public int getQuite() {
        return quite;
    }

    public void setQuite(int quite) {
        this.quite = quite;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(double avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public double getPlayTotalTime() {
        return playTotalTime;
    }

    public void setPlayTotalTime(double playTotalTime) {
        this.playTotalTime = playTotalTime;
    }

    @NonNull
    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(@NonNull String difficulty) {
        this.difficulty = difficulty;
    }

    public Date getStartTime() { return startTime; }

    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }

    public void setEndTime(Date endTime) { this.endTime = endTime; }


    @Ignore
    public GameEvent(int gameEventId, int gameIdForeign, int userIdForeign, int hit, int miss, int quite, int score, double accuracy, double avgSpeed, double playTotalTime, @NonNull String difficulty, Date startTime, Date endTime) {
        this.gameEventId = gameEventId;
        this.gameIdForeign = gameIdForeign;
        this.userIdForeign = userIdForeign;
        this.hit = hit;
        this.miss = miss;
        this.quite = quite;
        this.score = score;
        this.accuracy = accuracy;
        this.avgSpeed = avgSpeed;
        this.playTotalTime = playTotalTime;
        this.difficulty = difficulty;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public GameEvent(int gameIdForeign, int userIdForeign, int hit, int miss, int quite, int score, double accuracy, double avgSpeed, double playTotalTime, @NonNull String difficulty, Date startTime, Date endTime) {
        this.gameIdForeign = gameIdForeign;
        this.userIdForeign = userIdForeign;
        this.hit = hit;
        this.miss = miss;
        this.quite = quite;
        this.score = score;
        this.accuracy = accuracy;
        this.avgSpeed = avgSpeed;
        this.playTotalTime = playTotalTime;
        this.difficulty = difficulty;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}