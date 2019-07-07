package com.example.newdiplwm;


import android.app.Application;
import androidx.lifecycle.LiveData;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository {
    private UserDao userDao;
    private GameDao gameDao;
    private GameEventDao gameEventDao;
    private LiveData<List<User>> allusers;
    //private LiveData<List<Game>> allgames;
    private StatisticDao statisticDao;
    private UserGameStatsDao userGameStatsDao;

    public UserRepository(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        userDao = appDatabase.userDao();
        gameDao = appDatabase.gameDao();
        gameEventDao = appDatabase.gameEventDao();
        allusers = userDao.allusers();
        // allgames =gameDao.getAllGames();
        statisticDao = appDatabase.statisticDao();
        userGameStatsDao = appDatabase.userGameStatsDao();

    }

    public void insertStatistics(List<Statistic>  statistics)
    {
        new InsertStatisticsAsync(statisticDao).execute(statistics);
    }

    private static class InsertStatisticsAsync extends AsyncTask<List<Statistic>,Void,Void>
    {
        private StatisticDao statisticDao;
        private InsertStatisticsAsync(StatisticDao statisticDao)
        {
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(List<Statistic>... lists) {
            statisticDao.addStatistics(lists[0]);
            return null;
        }
    }

    public void insert(User user)
    {
        new InsertUserAsync(userDao).execute(user);

    }

    private static class InsertUserAsync extends AsyncTask<User , Void, Void>
    {
        private UserDao userDao;
        private InsertUserAsync(UserDao userDao)
        {
            this.userDao = userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.addUser(users[0]);
            return null;
        }
    }


    public User getUserByname(String nickname)
    {

        try {
            return new GetUserByNameUserAsync(userDao).execute(nickname).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static class GetUserByNameUserAsync extends AsyncTask<String,Void,User>
    {
        private UserDao userDao;
        private GetUserByNameUserAsync(UserDao userDao)
        {
            this.userDao = userDao;
        }


        @Override
        protected User doInBackground(String... strings) {
            return userDao.getUser(strings[0]);
        }

        @Override
        protected void onPostExecute(User user) {
            super.onPostExecute(user);
        }
    }


    public List<Game> getAllGamesss()
    {
        try {
            return new GetALLGameSAsync(gameDao).execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class GetALLGameSAsync extends AsyncTask<Void,Void,List<Game>>
    {
        private GameDao gameDao;
        private GetALLGameSAsync(GameDao gameDao)
        {
            this.gameDao = gameDao;
        }


        @Override
        protected List<Game> doInBackground(Void... voids) {
            return  gameDao.getAllGames();
        }
    }


    public LiveData<List<User>> getAllusers() {
        return allusers;
    }


    public Game getGameByName(String gameName)
    {

        try {
            return new GetGameByNameGameAsync(gameDao).execute(gameName).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }



    private static class GetGameByNameGameAsync extends AsyncTask<String,Void,Game>
    {
        private GameDao gameDao;
        private GetGameByNameGameAsync(GameDao gameDao)
        {
            this.gameDao = gameDao;
        }


        @Override
        protected Game doInBackground(String... strings) {
            return gameDao.getGameByName(strings[0]);
        }

    }


//    public LiveData<List<Game>> getAllGames() {
//        return allgames;
//    }

    public void insertGameEvent(GameEvent gameEvent){
        new InsertGameEventAsync(gameEventDao).execute(gameEvent);

    }
    public static class InsertGameEventAsync extends AsyncTask<GameEvent,Void,Void>
    {
        private GameEventDao gameEventDao;
        private  InsertGameEventAsync (GameEventDao gameEventDao){this.gameEventDao = gameEventDao;}

        @Override
        protected Void doInBackground(GameEvent... gameEvents) {
            gameEventDao.addGameEvent(gameEvents[0]);
            return null;
        }
    }




    public void insertStatisticTest(Statistic statistic){
        new InsertStatisticTest(statisticDao).execute(statistic);

    }

    private static class InsertStatisticTest extends AsyncTask<Statistic , Void, Void>
    {
        private StatisticDao statisticDao;
        private InsertStatisticTest(StatisticDao statisticDao)
        {
            this.statisticDao = statisticDao;
        }

        @Override
        protected Void doInBackground(Statistic... statistics) {
            statisticDao.addStatistic(statistics[0]);
            return null;
        }
    }
    private static class Test{
        int userid;
        int gameid;
        Test(int userid, int gameid)
        {
            this.gameid = gameid;
            this.userid = userid;
        }
    }

    public void updateStatistics (int userid, int gameid){
        Test test = new Test(userid,gameid);
        new UpdateStatisticsAsync(statisticDao).execute(test);

    }


    private static class UpdateStatisticsAsync extends AsyncTask<Test,Void,Void>
    {
        private StatisticDao statisticDao;
        private UpdateStatisticsAsync(StatisticDao statisticDao){ this.statisticDao = statisticDao;}
        @Override
        protected Void doInBackground(Test... tests) {
            statisticDao.updateStats(tests[0].userid,tests[0].gameid);
            return null;
        }
    }



    public List<UserGameStats> getAllStats(int userid)
    {

        try {
            return new getAllStatsAsync(userGameStatsDao).execute(userid).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static class getAllStatsAsync extends AsyncTask<Integer,Void,List<UserGameStats>>
    {
        private UserGameStatsDao userGameStatsDao;
        private getAllStatsAsync(UserGameStatsDao userGameStatsDao)
        {
            this.userGameStatsDao = userGameStatsDao;
        }

        @Override
        protected List<UserGameStats> doInBackground(Integer... integers) {
            return userGameStatsDao.gellAllStats(integers[0]);
        }
    }





//    private static class InsertGamesAsync extends AsyncTask<Game[],Void , Void>
//    {
//        private GameDao gameDao;
//
//        private InsertGamesAsync(GameDao gameDao)
//        {
//            this.gameDao = gameDao;
//        }
//
//        @Override
//        protected Void doInBackground(Game[]... games) {
//            int test = games.length;
//            return null;
//        }
//    }

}