package com.example.newdiplwm;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import static com.github.mikephil.charting.charts.Chart.LOG_TAG;

public class GameList extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener, NavigationView.OnNavigationItemSelectedListener {

    public static String DB_FILEPATH = "/data/com.example.newdiplwm/databases/myDB";
    public static String DB_DSTFILEPATH = "/data/com.example.newdiplwm/databases/backup/export.csv";

    private GameViewModel gameViewModel;
    private Session session;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    List<Game> allgamesList = new ArrayList<Game>();
    private boolean mToolBarNavigationListenerIsRegistered = false, rememberMe;
    private String preferenceDifficulty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        session = new Session(getApplicationContext());
        setupPreferences();

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.d("PERSMISSION","Permission error. You have permission");

            } else {

                Log.d("PERSMISSION","You have asked for permission");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

            }
        }

        if (GameHelper.sizeOfMap() <= 0) {
            GameHelper.initHasmap();
        }


//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
//
//        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbarLayout.setTitle("username");
//


        Toolbar toolbar = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(session.getUsernameSession());
        setSupportActionBar(toolbar);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
            recyclerView.setLayoutManager(mLayoutManager);
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

        }

//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
//        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapsingtoolbarLayout);
//        collapsingToolbar.setTitle("username");


        final GameAdapter gameAdapter = new GameAdapter();
        recyclerView.setAdapter(gameAdapter);

        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.gameList);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        allgamesList = gameViewModel.loadAllGames();

        gameAdapter.setGames(allgamesList);


        gameAdapter.setOnClickListener(new GameAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Game game) {
                Toast.makeText(GameList.this, "Επέλεξες: " + GameHelper.getGreekName(game.getName()), Toast.LENGTH_SHORT).show();


                session.setModeSession(preferenceDifficulty);
                if (game.getName().equals("Rock")) {

                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, RockPaperScissors.class);
                    startActivity(intent);
                } else if (game.getName().equals("Calcution")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, ScalingGame.class);
                    startActivity(intent);

                } else if (game.getName().equals("MemoryMatrix")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, MemoryMatrix.class);
                    startActivity(intent);

                } else if (game.getName().equals("ObjectSelector")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, ObjectSelector.class);
                    startActivity(intent);

                } else if (game.getName().equals("OrderGame")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, OrderGame.class);
                    startActivity(intent);

                } else if (game.getName().equals("Suitcase")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, Suitcase.class);
                    startActivity(intent);

                } else if (game.getName().equals("ShadowGame")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, ShadowGame.class);
                    startActivity(intent);

                } else if (game.getName().equals("PersonPickGame")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, AudioPersonPick.class);
                    startActivity(intent);

                } else if (game.getName().equals("SoundWord")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, SoundWord.class);

                    startActivity(intent);

                } else if (game.getName().equals("SoundImage")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, SoundImage.class);
                    startActivity(intent);

                } else if (game.getName().equals("RotationGame")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, RotationGame.class);
                    startActivity(intent);

                } else if (game.getName().equals("BOX")) {
                    session.setGameIdSession(game.getId());
                    Intent intent = new Intent(GameList.this, BucketGame.class);
                    startActivity(intent);
                }
                //finishAffinity();

            }
        });

    }


    private void enableViews(boolean enable) {

        // To keep states of ActionBar and ActionBarDrawerToggle synchronized,
        // when you enable on one, you disable on the other.
        // And as you may notice, the order for this operation is disable first, then enable - VERY VERY IMPORTANT.
        if (enable) {
            //You may not want to open the drawer on swipe from the left in this case
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // Remove hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
            // Show back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // when DrawerToggle is disabled i.e. setDrawerIndicatorEnabled(false), navigation icon
            // clicks are disabled i.e. the UP button will not work.
            // We need to add a listener, as in below, so DrawerToggle will forward
            // click events to this listener.
            if (!mToolBarNavigationListenerIsRegistered) {
                actionBarDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Doesn't have to be onBackPressed
                        onBackPressed();
                    }
                });

                mToolBarNavigationListenerIsRegistered = true;
            }

        } else {
            //You must regain the power of swipe for the drawer.
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Remove back button
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            // Show hamburger
            actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
            // Remove the/any drawer toggle listener
            actionBarDrawerToggle.setToolbarNavigationClickListener(null);
            mToolBarNavigationListenerIsRegistered = false;
        }

    }


    @Override
    public void onBackPressed() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ΕΞΟΔΟΣ");
        builder.setMessage("Έξοδος από την εφαρμογή;");
        builder.setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                finishAffinity();
                if (isTaskRoot()) {
                    moveTaskToBack(true);
                }

            }
        });
        builder.setNegativeButton("OXI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } //else {
//            super.onBackPressed();
//        }

        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(24);
                btnPositive.setTextColor(getResources().getColor(R.color.black));


                Button btnNegative = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                btnNegative.setTextSize(24);
                btnNegative.setTextColor(getResources().getColor(R.color.black));

                TextView textView = (TextView) dialog.findViewById(android.R.id.message);
                textView.setTextSize(28);
                textView.setTextColor(getResources().getColor(R.color.black));

            }
        });

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);

    }


    private void setupPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey), getString(R.string.mediumValue));
        rememberMe = sharedPreferences.getBoolean("rememberME", false);
        session.setRememberme(rememberMe);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        if (key.equals(getString(R.string.difficultyKey))) {
            preferenceDifficulty = sharedPreferences.getString(getString(R.string.difficultyKey), getString(R.string.mediumValue));
        }
        if (key.equals("rememberME")) {
            rememberMe = sharedPreferences.getBoolean("rememberME", false);
        }
        if (key.equals("testing")) {
            session.setPlayAgainVideo(sharedPreferences.getBoolean("testing", false));
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_settings:
                Intent intentSettings = new Intent(this, Settings.class);
                startActivity(intentSettings);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_statistics:
                Intent intentStats = new Intent(this, StatisticsTable.class);
                startActivity(intentStats);
                Animatoo.animateDiagonal(GameList.this);
                break;
            case R.id.nav_chart:
                Intent intentChart = new Intent(this, MenuChart.class);
                startActivity(intentChart);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_questionnaire:
                Intent intentQuestionnaire = new Intent(this, Questionnaire.class);
                startActivity(intentQuestionnaire);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_info:
                Intent intentInfo = new Intent(GameList.this, AboutActivity.class);
                startActivity(intentInfo);
                Animatoo.animateFade(GameList.this);
                break;
            case R.id.nav_export_db:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                } else {
                    new ExportDatabaseCSVTask().execute();
                }
                break;
            case R.id.nav_logout:
                session.setRememberme(false);
                finishAndRemoveTask();
                Intent intent = new Intent(GameList.this, Login.class);
                startActivity(intent);
                break;
        }


        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void exportDb() {


        try {
            File data = Environment.getDataDirectory();
            // String packageName = getPackageName();


            String currentDBPath = DB_FILEPATH;
            File currentDB = new File(data, currentDBPath);
            File dstDB = new File(data, DB_DSTFILEPATH);


            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(dstDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            Toast.makeText(getBaseContext(), dstDB.toString(),
                    Toast.LENGTH_LONG).show();


        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }


    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean> {
        private final ProgressDialog dialog = new ProgressDialog(GameList.this);
        //DBHelper dbhelper;
        private AppDatabase userDatabase;

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Exporting database...");
            this.dialog.show();
            //dbhelper = new DBHelper(MainActivity.this);
            userDatabase = AppDatabase.getInstance(GameList.this);
        }

        protected Boolean doInBackground(final String... args) {

            File exportDir = new File(Environment.getDataDirectory(), "/Roomcsv/");
            File data = Environment.getDataDirectory();
            File test = Environment.getExternalStorageDirectory();
           // File dstDB = new File(data, DB_DSTFILEPATH);
//            if (!exportDir.exists()) {
//                exportDir.mkdir();
//            }

            File file = new File("/sdcard/", "testing.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                String[] column = {"Id", "gameid", "nameid", "hit", "miss", "quits", "diff" , "score", "days", "playtotaltime", "accuracy"};
                csvWrite.writeNext(column);
                List<UserGameStats> statistics = userDatabase.userGameStatsDao().gellAllStats(session.getUserIdSession());
                for (int i = 0; i < statistics.size(); i++) {
                    String[] mySecondStringArray = {String.valueOf(statistics.get(i).statistic.getStatisticId()),String.valueOf(statistics.get(i).statistic.getGameIdForeign()),String.valueOf(statistics.get(i).statistic.getUserIdForeign()),String.valueOf(statistics.get(i).statistic.getHit()),String.valueOf(statistics.get(i).statistic.getMiss()),String.valueOf(statistics.get(i).statistic.getQuits())};
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        protected void onPostExecute(final Boolean success) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (success) {
                Toast.makeText(GameList.this, "Export successful!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameList.this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        }

    }


    public class CSVWriter {
        private PrintWriter pw;
        private char separator;
        private char escapechar;
        private String lineEnd;
        private char quotechar;
        public static final char DEFAULT_SEPARATOR = ',';
        public static final char NO_QUOTE_CHARACTER = '\u0000';
        public static final char NO_ESCAPE_CHARACTER = '\u0000';
        public static final String DEFAULT_LINE_END = "\n";
        public static final char DEFAULT_QUOTE_CHARACTER = '"';
        public static final char DEFAULT_ESCAPE_CHARACTER = '"';
        public CSVWriter(Writer writer) {
            this(writer, DEFAULT_SEPARATOR, DEFAULT_QUOTE_CHARACTER,
                    DEFAULT_ESCAPE_CHARACTER, DEFAULT_LINE_END);
        }
        public CSVWriter(Writer writer, char separator, char quotechar, char escapechar, String lineEnd) {
            this.pw = new PrintWriter(writer);
            this.separator = separator;
            this.quotechar = quotechar;
            this.escapechar = escapechar;
            this.lineEnd = lineEnd;
        }
        public void writeNext(String[] nextLine) {
            if (nextLine == null)
                return;
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < nextLine.length; i++) {

                if (i != 0) {
                    sb.append(separator);
                }
                String nextElement = nextLine[i];
                if (nextElement == null)
                    continue;
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
                for (int j = 0; j < nextElement.length(); j++) {
                    char nextChar = nextElement.charAt(j);
                    if (escapechar != NO_ESCAPE_CHARACTER && nextChar == quotechar) {
                        sb.append(escapechar).append(nextChar);
                    } else if (escapechar != NO_ESCAPE_CHARACTER && nextChar == escapechar) {
                        sb.append(escapechar).append(nextChar);
                    } else {
                        sb.append(nextChar);
                    }
                }
                if (quotechar != NO_QUOTE_CHARACTER)
                    sb.append(quotechar);
            }
            sb.append(lineEnd);
            pw.write(sb.toString());
        }
        public void close() throws IOException {
            pw.flush();
            pw.close();
        }
        public void flush() throws IOException {
            pw.flush();
        }
    }


}