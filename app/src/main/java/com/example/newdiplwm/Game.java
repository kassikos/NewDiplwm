package com.example.newdiplwm;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;


@Entity(tableName = "games")
public class Game {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "targetSkill")
    @NonNull
    private String targetSkill;

    @ColumnInfo(name = "description")
    @NonNull
    private String description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getTargetSkill() {
        return targetSkill;
    }

    public void setTargetSkill(@NonNull String targetSkill) {
        this.targetSkill = targetSkill;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Ignore
    public Game(@NonNull String name, @NonNull String targetSkill, @NonNull String description) {
        this.name = name;
        this.targetSkill = targetSkill;
        this.description = description;
    }

    public Game(@NonNull String name, @NonNull String targetSkill, @NonNull String description, byte[] image) {
        this.name = name;
        this.targetSkill = targetSkill;
        this.description = description;
        this.image = image;
    }
}