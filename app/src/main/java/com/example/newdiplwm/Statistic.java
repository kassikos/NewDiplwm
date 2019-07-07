package com.example.newdiplwm;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "statistic",
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

public class Statistic {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int statisticId;

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

    @ColumnInfo(name = "quits")
    @NonNull
    private int quits;

    @ColumnInfo(name = "difficulty")
    @NonNull
    private String difficulty;

    @ColumnInfo(name = "score")
    @NonNull
    private int score;

    @ColumnInfo(name = "days")
    @NonNull
    private int days;

    @ColumnInfo(name = "playTotalTime")
    @NonNull
    private double playTotalTime;

    @ColumnInfo(name = "accuracy",typeAffinity = ColumnInfo.REAL)
    @NonNull
    private double accuracy;

    public int getStatisticId() {
        return statisticId;
    }

    public void setStatisticId(int statisticId) {
        this.statisticId = statisticId;
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

    public int getQuits() {
        return quits;
    }

    public void setQuits(int quits) {
        this.quits = quits;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public double getPlayTotalTime() {
        return playTotalTime;
    }

    public void setPlayTotalTime(double playTotalTime) {
        this.playTotalTime = playTotalTime;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public int getDays() { return days; }

    public void setDays(int days) { this.days = days; }

    @NonNull
    public String getDifficulty() { return difficulty; }

    public void setDifficulty(@NonNull String difficulty) { this.difficulty = difficulty; }

    @Ignore
    public Statistic(int statisticId, int gameIdForeign, int userIdForeign, int hit, int miss, int quits, @NonNull String difficulty, int score, int days, double playTotalTime, double accuracy) {
        this.statisticId = statisticId;
        this.gameIdForeign = gameIdForeign;
        this.userIdForeign = userIdForeign;
        this.hit = hit;
        this.miss = miss;
        this.quits = quits;
        this.difficulty = difficulty;
        this.score = score;
        this.days = days;
        this.playTotalTime = playTotalTime;
        this.accuracy = accuracy;
    }

    public Statistic(int gameIdForeign, int userIdForeign, int hit, int miss, int quits, @NonNull String difficulty, int score, int days, double playTotalTime, double accuracy) {
        this.gameIdForeign = gameIdForeign;
        this.userIdForeign = userIdForeign;
        this.hit = hit;
        this.miss = miss;
        this.quits = quits;
        this.difficulty = difficulty;
        this.score = score;
        this.days = days;
        this.playTotalTime = playTotalTime;
        this.accuracy = accuracy;
    }
}