package com.example.newdiplwm;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Relation;

public class UserGameStats {
    @Embedded
    Statistic statistic;

    @ColumnInfo(name = "nickName")
    String nickName;

    @ColumnInfo(name = "name")
    String name;

}
