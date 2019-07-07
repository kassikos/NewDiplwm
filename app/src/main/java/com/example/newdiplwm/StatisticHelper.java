package com.example.newdiplwm;

import java.util.ArrayList;
import java.util.List;

public class StatisticHelper {



    public static List<Statistic> createStatisticInstances(int userdId, List<Game> a){
        List<Statistic> list = new ArrayList<Statistic>();

        for (Game game :a)
        {

            Statistic s = new Statistic(game.getId(),userdId,0,0,0,"NONE",0,0,0,0);
            list.add(s);

        }
        return  list;


    }

    public static Statistic createStatisticInstance(int userdId, int gameId){

        return new Statistic(gameId,userdId,0,0,0," ",0,0,0,0);

    }
}
