package com.example.newdiplwm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.Date;


@Entity(tableName = "users",indices = {@Index(value = {"nickName"},
        unique = true)})
public class User {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int userId;

    @ColumnInfo(name = "nickName")
    @NonNull
    private String nickName;

    @ColumnInfo(name = "birthDate")
    // @TypeConverters({Converters.class})
    private Date birthDate;

    @ColumnInfo(name = "gender")
    @NonNull
    private boolean gender;

    @ColumnInfo(name = "education")
    @NonNull
    private String education;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    @Ignore
    public User(int userId, @NonNull String nickName, @NonNull Date birthDate, boolean gender, @NonNull String education) {
        this.userId = userId;
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.education = education;
    }

    public User(@NonNull String nickName, @NonNull Date birthDate, boolean gender, @NonNull String education) {
        this.nickName = nickName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.education = education;
    }

}