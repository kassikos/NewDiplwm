package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BucketGame extends AppCompatActivity {

    private ArrayList<Integer> fish = new ArrayList<>(3);
    private ArrayList<Integer> birds = new ArrayList<>(3);
    private ArrayList<Integer> insects = new ArrayList<>(3);

    private ArrayList<Integer> liquids = new ArrayList<>(3);
    private ArrayList<Integer> vegs = new ArrayList<>(3);
    private ArrayList<Integer> meats = new ArrayList<>(3);

    private ArrayList<Integer> shirts = new ArrayList<>(3);
    private ArrayList<Integer> pants = new ArrayList<>(3);
    private ArrayList<Integer> shoes = new ArrayList<>(3);

    private ArrayList<ArrayList> Animals = new ArrayList<>(3);
    private ArrayList<ArrayList> food = new ArrayList<>(3);
    private ArrayList<ArrayList> clothes = new ArrayList<>(3);

    private ArrayList<ArrayList> generalCategories = new ArrayList<>(3);


    private SparseArray matchbucketList = new SparseArray(12);


    private ArrayList<Integer> imageviews = new ArrayList<>();
    private ArrayList<Integer> generalbucketsImages = new ArrayList<>(3);


    ArrayList<Integer> AnimalsEz = new ArrayList<>(Arrays.asList(R.drawable.animals_fish_goldfish,
            R.drawable.animals_fish_shark, R.drawable.animals_fish_swordfish, R.drawable.animals_birds_owl, R.drawable.animals_birds_parrot,
            R.drawable.animals_birds_pigeon, R.drawable.animals_insects_bee, R.drawable.animals_insects_fly, R.drawable.animals_insects_mosquito));

    ArrayList<Integer> foodsEz = new ArrayList<>(Arrays.asList(R.drawable.foods_meats_salami,
            R.drawable.foods_meats_sausage, R.drawable.foods_meats_souvlaki, R.drawable.foods_veg_cucumber, R.drawable.foods_veg_lettuce,
            R.drawable.foods_veg_tomato, R.drawable.foods_liquids_drink, R.drawable.foods_liquids_juice, R.drawable.foods_liquids_milk));

    ArrayList<Integer> clothesEz = new ArrayList<>(Arrays.asList(R.drawable.clothes_pants_shorts,
            R.drawable.clothes_pants_skirt, R.drawable.clothes_pants_trousers, R.drawable.clothes_shirts_dress, R.drawable.clothes_shirts_suit,
            R.drawable.clothes_shirts_tshirt, R.drawable.clothes_shoes_allstars, R.drawable.clothes_shoes_heels, R.drawable.clothes_shoes_sayonares));

    private SparseIntArray imageviewImage = new SparseIntArray();
    private SparseIntArray bucketLinearImageBucket = new SparseIntArray();


    private SparseIntArray normalbucketEnterbucket = new SparseIntArray();


    private SparseArray labelsubBuckets = new SparseArray();
    private ArrayList<Integer> bucketsubAnimals = new ArrayList<>(3);
    private ArrayList<Integer> bucketsubClothes = new ArrayList<>(3);
    private ArrayList<Integer> bucketsubFoods = new ArrayList<>(3);


    private ImageView thing1, thing2, thing3, thing4, thing5, thing6, exit , replayTutorial;
    private LinearLayout bucket1Linear, bucket2Linear, bucket3Linear, logoLinear, textsLinear;
    private MaterialButton startButton;
    private TextView textRounds, animationTextPoints, textMsg , textMsgTime;;
    private Vibrator vibe;
    private GameEventViewModel gameEventViewModel;
    private UserViewModel userViewModel;
    private String menuDifficulty, currentDifficulty;
    private int user_id, game_id, currentRound = 0, TotalRounds = 0;
    private int hit = 0, miss = 0, trueCounter = 0, totalPoints = 0;
    private boolean missPoints = false;
    private Timestamp startTime;
    private Timestamp endTime;
    private Timestamp startspeed;
    private Timestamp endspeed;
    private double totalspeed = 0;

    private CountDownTimer cleanTimer , nextRoundTimer;
    private Session session;
    private int limitEz = 3, limitAdv = 6, limitMed = 4, limitcounter = 0;
    private HashMap<String, Integer> pointsHashMap = new HashMap<String, Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_game);
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        gameEventViewModel = ViewModelProviders.of(this).get(GameEventViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        pointsHashMap.put(getResources().getString(R.string.easyValue), 0);
        pointsHashMap.put(getResources().getString(R.string.mediumValue), 5);
        pointsHashMap.put(getResources().getString(R.string.advancedValue), 10);

        session = new Session(getApplicationContext());


        if (!session.getPlayAgainVideo() && currentRound == 0) {

            showTutorialPopUp();

        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("TutorialBucketGame");
        if (prev != null) {

            fragmentTransaction.remove(prev);
            fragmentTransaction.commit();
        }
        menuDifficulty = session.getModeSession();
        user_id = session.getUserIdSession();
        game_id = session.getGameIdSession();
        assignAllButtons();
        fillImageviews();
        loadAnimalImages();
        loadFoodsImages();
        loadClothedsImages();
        loadGeneralCategories();
        loadGeneralbucketsImages();
        matchnormalwithredbucket();
        matchGeneralCategorieswithsubbuckets();

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoLinear.setVisibility(View.GONE);
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();

                Fragment prev = fm.findFragmentByTag("TutorialBucketGame");
                if (prev != null) {

                    fragmentTransaction.remove(prev);
                    fragmentTransaction.commit();
                    fm.popBackStack();
                    //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                }
                createRound();
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onbackAndExitCode();
            }
        });
        replayTutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTutorialPopUp();
            }
        });

    }

    @Override
    public void onBackPressed() {
        onbackAndExitCode();

    }

    private void onbackAndExitCode() {
        if (nextRoundTimer != null)
        {
            nextRoundTimer.cancel();
        }
        if (currentRound == 0) {
            startTime = new Timestamp(System.currentTimeMillis());
            endTime = new Timestamp(System.currentTimeMillis());
            GameEvent gameEvent = new GameEvent(game_id, user_id, 0, 0, 1, 0, 0, 0, 0, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        } else {
            if (startspeed == null || endspeed == null) {
                totalspeed += 0;
            }
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 1, totalPoints, (double) hit / TotalRounds, totalspeed / TotalRounds, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            finish();

        }

    }

    private void displayGameEz() {
        setOntouchlistenerEZ();
        Collections.shuffle(generalCategories);
        for (int i = 0; i < 3; i++) {
            ImageView iv1 = findViewById(imageviews.get(i));

            ArrayList<ArrayList> tempGeneral = generalCategories.get(i);
            ArrayList<Integer> tempSubcategorie = tempGeneral.get(i);
            Collections.shuffle(tempSubcategorie);
            iv1.setImageResource(tempSubcategorie.get(i));
            imageviewImage.put(imageviews.get(i), tempSubcategorie.get(i));
        }

        Collections.shuffle(generalbucketsImages);
        bucket1Linear.setBackground(getResources().getDrawable(generalbucketsImages.get(0)));
        bucket2Linear.setBackground(getResources().getDrawable(generalbucketsImages.get(1)));
        bucket3Linear.setBackground(getResources().getDrawable(generalbucketsImages.get(2)));

        bucketLinearImageBucket.put(bucket1Linear.getId(), generalbucketsImages.get(0));
        bucketLinearImageBucket.put(bucket2Linear.getId(), generalbucketsImages.get(1));
        bucketLinearImageBucket.put(bucket3Linear.getId(), generalbucketsImages.get(2));

        startspeed = new Timestamp(System.currentTimeMillis());

    }


    private void displayGameMed() {
        setOntouchlistenerMed();
        Collections.shuffle(generalCategories);
        int first = 0;
        for (ArrayList<ArrayList> check : generalCategories) {
            if (check.equals(Animals)) {
                resetGeneralCategories(first, "subanimals");

            } else if (check.equals(clothes)) {
                resetGeneralCategories(first, "subclothes");
            } else if (check.equals(food)) {
                resetGeneralCategories(first, "subfoods");
            }
            first++;
        }

        int generalpick = 0;
        int getBucket = 0;

        ArrayList<Integer> Imageviewsforshuffle = new ArrayList<Integer>(4);

        for (int i = 0; i < 4; i++) {
            if (i < 2) {

                ArrayList<ArrayList> tempGeneral = generalCategories.get(0);
                ArrayList<Integer> tempSubcategorie = tempGeneral.get(i);
                if (i == 0) {

                    if (tempGeneral.equals(Animals)) {
                        getBucket = matchStringlabelWitGeneralbucketIcons("AnimalsBucket");

                    } else if (tempGeneral.equals(clothes)) {
                        getBucket = matchStringlabelWitGeneralbucketIcons("ClothesBucket");
                    } else if (tempGeneral.equals(food)) {
                        getBucket = matchStringlabelWitGeneralbucketIcons("FoodsBucket");
                    }
                    Collections.shuffle(tempSubcategorie);
                }

                Imageviewsforshuffle.add(tempSubcategorie.get(i));

            } else {
                generalpick++;

                ArrayList<ArrayList> tempGeneral = generalCategories.get(generalpick);
                ArrayList<Integer> tempSubcategorie = tempGeneral.get(generalpick);
                Collections.shuffle(tempSubcategorie);
                Imageviewsforshuffle.add(tempSubcategorie.get(generalpick));

            }

        }
        Collections.shuffle(Imageviewsforshuffle);

        for (int i = 0; i < 4; i++) {
            ImageView iv1 = findViewById(imageviews.get(i));
            iv1.setImageResource(Imageviewsforshuffle.get(i));
            imageviewImage.put(imageviews.get(i), Imageviewsforshuffle.get(i));

        }

        ArrayList<Integer> subbuckets1 = (ArrayList<Integer>) labelsubBuckets.get(generalpick - 1);
        ArrayList<Integer> subbuckets = (ArrayList<Integer>) labelsubBuckets.get(generalpick);


        ArrayList<Integer> generalbucketsforshuffle = new ArrayList<Integer>(3);
        generalbucketsforshuffle.add(subbuckets1.get(generalpick - 1));
        generalbucketsforshuffle.add(subbuckets.get(generalpick));
        generalbucketsforshuffle.add(getBucket);
        Collections.shuffle(generalbucketsforshuffle);


        bucket1Linear.setBackground(getResources().getDrawable(generalbucketsforshuffle.get(0)));
        bucket2Linear.setBackground(getResources().getDrawable(generalbucketsforshuffle.get(1)));
        bucket3Linear.setBackground(getResources().getDrawable(generalbucketsforshuffle.get(2)));


        bucketLinearImageBucket.put(bucket1Linear.getId(), generalbucketsforshuffle.get(0));
        bucketLinearImageBucket.put(bucket2Linear.getId(), generalbucketsforshuffle.get(1));
        bucketLinearImageBucket.put(bucket3Linear.getId(), generalbucketsforshuffle.get(2));

        startspeed = new Timestamp(System.currentTimeMillis());
    }


    private void displayGameAdv() {

        setOntouchlistenerADV();

        Collections.shuffle(imageviews);
        Random random = new Random();
        int iconspicked = 0;
        int temp = random.nextInt(3);
        ArrayList<ArrayList> tempGeneral = generalCategories.get(temp);


        for (int i = 0; i <= 2; i++) {

            ImageView iv1 = findViewById(imageviews.get(iconspicked));
            ImageView iv2 = findViewById(imageviews.get(iconspicked + 1));


            ArrayList<Integer> tempSubcategorie = tempGeneral.get(i);
            Collections.shuffle(tempSubcategorie);

            iv1.setImageResource(tempSubcategorie.get(0));
            iv2.setImageResource(tempSubcategorie.get(1));
            imageviewImage.put(imageviews.get(iconspicked), tempSubcategorie.get(0));
            imageviewImage.put(imageviews.get(iconspicked + 1), tempSubcategorie.get(1));

            iconspicked += 2;

        }

        ArrayList<Integer> GeneralCategorie = (ArrayList<Integer>) labelsubBuckets.get(temp);
        Collections.shuffle(GeneralCategorie);

        bucket1Linear.setBackground(getResources().getDrawable(GeneralCategorie.get(0)));
        bucket2Linear.setBackground(getResources().getDrawable(GeneralCategorie.get(1)));
        bucket3Linear.setBackground(getResources().getDrawable(GeneralCategorie.get(2)));


        bucketLinearImageBucket.put(bucket1Linear.getId(), GeneralCategorie.get(0));
        bucketLinearImageBucket.put(bucket2Linear.getId(), GeneralCategorie.get(1));
        bucketLinearImageBucket.put(bucket3Linear.getId(), GeneralCategorie.get(2));

        startspeed = new Timestamp(System.currentTimeMillis());
    }


    private void createRound() {
        startButton.setVisibility(View.INVISIBLE);

        if (currentRound == 0) {
            startTime = new Timestamp(System.currentTimeMillis());
        }

        if (menuDifficulty.equals(getResources().getString(R.string.easyValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameEz();
        } else if (menuDifficulty.equals(getResources().getString(R.string.mediumValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameMed();
        } else if (menuDifficulty.equals(getResources().getString(R.string.advancedValue))) {
            currentDifficulty = menuDifficulty;
            TotalRounds = 3;
            displayGameAdv();
        } else if (menuDifficulty.equals(getResources().getString(R.string.easymediumValue))) {
            TotalRounds = 4;

            if (currentRound <= 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            } else {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMed();
            }
        } else {
            TotalRounds = 5;
            if (currentRound < 1) {
                currentDifficulty = getResources().getString(R.string.easyValue);
                displayGameEz();
            } else if (currentRound >= 1 && currentRound <= 2) {
                currentDifficulty = getResources().getString(R.string.mediumValue);
                displayGameMed();
            } else {
                currentDifficulty = getResources().getString(R.string.advancedValue);
                displayGameAdv();
            }
        }
        setTouchlistenermethod(imageviews);
        currentRound++;
        textRounds.setText((currentRound) + "/" + TotalRounds);

    }


    //--------------------------------------------------------

    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {


            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("eeeeeee", "gdfgdf");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.VISIBLE);
                view.setEnabled(true);
                return true;
            }
            else
                return false;
        }
    }


    class MyDragListener implements View.OnDragListener {
        Context context = BucketGame.this;



        @Override
        public boolean onDrag(View v, DragEvent event) {


            LinearLayout container = (LinearLayout) v;

            int kadakiId = bucketLinearImageBucket.get(container.getId());

            Drawable enterShape = getResources().getDrawable(normalbucketEnterbucket.get(kadakiId));

            Drawable normalShape = getResources().getDrawable(bucketLinearImageBucket.get(container.getId()));
            int action = event.getAction();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    Log.d("ACTION_DRAG_STARTED", "ACTION_DRAG_STARTED");
                    disableReplayTut();
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    Log.d("ACTION_DRAG_ENTERED", "ACTION_DRAG_ENTERED");
                    v.setBackgroundDrawable(enterShape);
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    Log.d("ACTION_DRAG_EXITED", "ACTION_DRAG_EXITED");
                    v.setBackgroundDrawable(normalShape);
                    break;
                case DragEvent.ACTION_DROP:
                    Log.d("ACTION_DROP", "ACTION_DROP");
                    // Dropped, reassign View to ViewGroup


                    View view = (View) event.getLocalState();
                    ViewGroup owner = (ViewGroup) view.getParent();
                    owner.removeView(view);
                    ArrayList<Integer> temp = (ArrayList<Integer>) matchbucketList.get(kadakiId); //apo to id toy kadakioy pairnw to tin lista me ta antikeimena poy prepexei na exei o kados

                    view.requestLayout();
                    view.getLayoutParams().height = 50;
                    view.getLayoutParams().width = 50;
                    view.setEnabled(false);


                    container.addView(view);
                    view.setVisibility(View.VISIBLE);


                    if (temp.contains(imageviewImage.get(view.getId()))) //elegxw an sthn lista me ta anikeimena yparxei to antikeimeno poy m hr8e
                    {

                        //nikises

                        limitcounter++;

                        if ((limitcounter == limitEz && currentDifficulty.equals(getResources().getString(R.string.easyValue))) || (limitcounter == limitAdv && currentDifficulty.equals(getResources().getString(R.string.advancedValue))) || (limitcounter == limitMed && currentDifficulty.equals(getResources().getString(R.string.mediumValue)))) {
                            onActionMade(false);

                        }
                    } else {

                        Animation animShake = AnimationUtils.loadAnimation(context, R.anim.shake);
                        view.startAnimation(animShake);
                        int vibeduration = 1000;
                        vibe.vibrate(vibeduration);
                        onActionMade(true);
                    }


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    Log.d("ACTION_DRAG_ENDED", "ACTION_DRAG_ENDED");
                    enableReplayTut();
                    v.setBackgroundDrawable(normalShape);
                default:
                    break;
            }
            return true;
        }

    }



    private void setCleanTimer() {
        cleanTimer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {

                clearScreen();
            }
        }.start();
    }

    private void clearScreen() {

        final GridLayout layout = (GridLayout) findViewById(R.id.gridLayoutBucket);

        ViewTreeObserver vto = layout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

                layout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                setViews();

            }
        });


        bucket1Linear.setBackground(null);
        bucket2Linear.setBackground(null);
        bucket3Linear.setBackground(null);
//        startButton.setText(getResources().getString(R.string.nextRound));
//        startButton.setVisibility(View.VISIBLE);

        if (currentRound == TotalRounds) {
            startButton.setVisibility(View.INVISIBLE);
        }


    }


    private void setViews() {
        imageviews.clear();
        GridLayout gridLayout = (GridLayout) findViewById(R.id.gridLayoutBucket);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(3);
        gridLayout.setRowCount(2);


        int idsetter = 0;

        for (int i = 0; i < gridLayout.getRowCount(); i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i, 1, 1);

            for (int j = 0; j < gridLayout.getColumnCount(); j++) {
                GridLayout.Spec colSpec = GridLayout.spec(j, 1, 1);

                ImageView imageView = new ImageView(this);
                imageView.setId(idsetter);
                GridLayout.LayoutParams myGLP = new GridLayout.LayoutParams();
                myGLP.leftMargin = 16;
                myGLP.rightMargin = 16;
                GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();
                layoutParams.width = 0;
                layoutParams.height = 0;
                layoutParams.setMargins(4, 4, 4, 4);
                layoutParams.rowSpec = rowSpec;
                layoutParams.columnSpec = colSpec;
                imageView.setLayoutParams(layoutParams);
                gridLayout.addView(imageView, myGLP);
                imageviews.add(imageView.getId());
                idsetter++;
            }
        }

    }

    private void onActionMade(boolean value) {
        endspeed = new Timestamp(System.currentTimeMillis());
        long diffspeed = endspeed.getTime() - startspeed.getTime();
        double speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
        totalspeed += speedseconds;

        if (value) {
            miss++;
            missPoints = true;
        } else {
            hit++;
            trueCounter++;
        }

        limitcounter = 0;
        countPoints();
        startAnimation();
        setCleanTimer();
        bucket1Linear.removeAllViews();
        bucket2Linear.removeAllViews();
        bucket3Linear.removeAllViews();
        checkifEnds();

    }

    private void setTouchlistenermethod(ArrayList<Integer> imageviews) {
        int i = 0;

        for (int imgv : imageviews) {
            if (currentDifficulty.equals(getResources().getString(R.string.easyValue)) && i <= 2) {
                ImageView imageView = findViewById(imgv);
                imageView.setOnTouchListener(new MyTouchListener());

            } else if (currentDifficulty.equals(getResources().getString(R.string.mediumValue)) && i <= 3) {
                ImageView imageView = findViewById(imgv);
                imageView.setOnTouchListener(new MyTouchListener());
            } else {
                ImageView imageView = findViewById(imgv);
                imageView.setOnTouchListener(new MyTouchListener());

            }
            i++;
        }
    }

    private void checkifEnds() {

        if (currentRound == TotalRounds) {
            startButton.setVisibility(View.INVISIBLE);
            textsLinear.setVisibility(View.VISIBLE);
            endTime = new Timestamp(System.currentTimeMillis());
            long longTime = endTime.getTime() - startTime.getTime();
            float totalPlayInSeconds = TimeUnit.MILLISECONDS.toSeconds(longTime);
            GameEvent gameEvent = new GameEvent(game_id, user_id, hit, miss, 0, totalPoints, (double) hit / (hit + miss), totalspeed / TotalRounds, totalPlayInSeconds, menuDifficulty, startTime, endTime);
            gameEventViewModel.insertGameEvent(gameEvent);
            userViewModel.updatestatsTest(user_id, game_id);
            shopPopUp();
        }
        else
        {
            nextRound();
        }

    }


    private void nextRound(){
        textsLinear.setVisibility(View.VISIBLE);


        nextRoundTimer = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long l) {

                disableReplayTut();
                if (currentRound == TotalRounds)
                {
                    textMsgTime.setText("");
                }
                else
                {
                    textMsgTime.setText(getResources().getString(R.string.nextRound)+l/1000);
                }


            }

            @Override
            public void onFinish() {

                if (currentRound == TotalRounds)
                {
                    textsLinear.setVisibility(View.INVISIBLE);
                    disableReplayTut();
                }
                else
                {
                    nextRoundTimer = null;
                    enableReplayTut();
                    textMsgTime.setText("");
                    textsLinear.setVisibility(View.INVISIBLE);
                    createRound();

                }

            }
        }.start();
        userViewModel.setNextRoundTimer(nextRoundTimer);

    }

    private void loadAnimalImages() {
        fish.add(R.drawable.animals_fish_goldfish);
        fish.add(R.drawable.animals_fish_shark);
        fish.add(R.drawable.animals_fish_swordfish);

        birds.add(R.drawable.animals_birds_owl);
        birds.add(R.drawable.animals_birds_parrot);
        birds.add(R.drawable.animals_birds_pigeon);

        insects.add(R.drawable.animals_insects_bee);
        insects.add(R.drawable.animals_insects_fly);
        insects.add(R.drawable.animals_insects_mosquito);

        Animals.add(fish);
        Animals.add(birds);
        Animals.add(insects);
    }


    private void loadFoodsImages() {
        meats.add(R.drawable.foods_meats_sausage);
        meats.add(R.drawable.foods_meats_salami);
        meats.add(R.drawable.foods_meats_souvlaki);

        vegs.add(R.drawable.foods_veg_lettuce);
        vegs.add(R.drawable.foods_veg_cucumber);
        vegs.add(R.drawable.foods_veg_tomato);

        liquids.add(R.drawable.foods_liquids_juice);
        liquids.add(R.drawable.foods_liquids_drink);
        liquids.add(R.drawable.foods_liquids_milk);

        food.add(meats);
        food.add(vegs);
        food.add(liquids);
    }


    private void loadClothedsImages() {
        shirts.add(R.drawable.clothes_shirts_dress);
        shirts.add(R.drawable.clothes_shirts_suit);
        shirts.add(R.drawable.clothes_shirts_tshirt);

        pants.add(R.drawable.clothes_pants_shorts);
        pants.add(R.drawable.clothes_pants_skirt);
        pants.add(R.drawable.clothes_pants_trousers);

        shoes.add(R.drawable.clothes_shoes_allstars);
        shoes.add(R.drawable.clothes_shoes_heels);
        shoes.add(R.drawable.clothes_shoes_sayonares);

        clothes.add(shirts);
        clothes.add(pants);
        clothes.add(shoes);
    }

    private void loadGeneralCategories() {
        generalCategories.add(Animals);
        generalCategories.add(food);
        generalCategories.add(clothes);
    }


    private void matchGeneralCategorieswithsubbuckets() {
        bucketsubAnimals.add(R.drawable.animals_fish);
        bucketsubAnimals.add(R.drawable.animals_birds);
        bucketsubAnimals.add(R.drawable.animals_insects);

        bucketsubFoods.add(R.drawable.foods_meats);
        bucketsubFoods.add(R.drawable.foods_veg);
        bucketsubFoods.add(R.drawable.foods_liquids);

        bucketsubClothes.add(R.drawable.clothes_shirts);
        bucketsubClothes.add(R.drawable.clothes_pants);
        bucketsubClothes.add(R.drawable.clothes_shoes);

        labelsubBuckets.put(0, bucketsubAnimals);
        labelsubBuckets.put(1, bucketsubFoods);
        labelsubBuckets.put(2, bucketsubClothes);
    }


    private void resetGeneralCategories(int first, String sec) {
        //     labelsubBuckets.clear();
        if (sec.equals("subanimals")) {
            labelsubBuckets.put(first, bucketsubAnimals);
        } else if (sec.equals("subfoods")) {
            labelsubBuckets.put(first, bucketsubFoods);
        } else {
            labelsubBuckets.put(first, bucketsubClothes);
        }

    }

    private int matchStringlabelWitGeneralbucketIcons(String bucketName) {

        if (bucketName.equals("AnimalsBucket")) {
            return R.drawable.general_animals;
        } else if (bucketName.equals("ClothesBucket")) {
            return R.drawable.general_clothes;
        } else
            return R.drawable.general_foods;
    }


    private void loadGeneralbucketsImages() {
        generalbucketsImages.add(R.drawable.general_clothes);
        generalbucketsImages.add(R.drawable.general_animals);
        generalbucketsImages.add(R.drawable.general_foods);

        matchbucketList.put(R.drawable.general_clothes, clothesEz);
        matchbucketList.put(R.drawable.general_animals, AnimalsEz);
        matchbucketList.put(R.drawable.general_foods, foodsEz);

        matchbucketList.put(R.drawable.animals_fish, fish);
        matchbucketList.put(R.drawable.animals_birds, birds);
        matchbucketList.put(R.drawable.animals_insects, insects);

        matchbucketList.put(R.drawable.foods_liquids, liquids);
        matchbucketList.put(R.drawable.foods_meats, meats);
        matchbucketList.put(R.drawable.foods_veg, vegs);

        matchbucketList.put(R.drawable.clothes_shirts, shirts);
        matchbucketList.put(R.drawable.clothes_pants, pants);
        matchbucketList.put(R.drawable.clothes_shoes, shoes);
    }

    private void matchnormalwithredbucket() {
        normalbucketEnterbucket.put(R.drawable.general_clothes, R.drawable.red_general_clothes);
        normalbucketEnterbucket.put(R.drawable.general_animals, R.drawable.red_general_animals);
        normalbucketEnterbucket.put(R.drawable.general_foods, R.drawable.red_general_foods);

        normalbucketEnterbucket.put(R.drawable.animals_fish, R.drawable.red_animals_fish);
        normalbucketEnterbucket.put(R.drawable.animals_birds, R.drawable.red_animals_birds);
        normalbucketEnterbucket.put(R.drawable.animals_insects, R.drawable.red_animals_insects);

        normalbucketEnterbucket.put(R.drawable.foods_liquids, R.drawable.red_foods_liquids);
        normalbucketEnterbucket.put(R.drawable.foods_meats, R.drawable.red_foods_meats);
        normalbucketEnterbucket.put(R.drawable.foods_veg, R.drawable.red_foods_veg);

        normalbucketEnterbucket.put(R.drawable.clothes_shirts, R.drawable.red_clothes_shirts);
        normalbucketEnterbucket.put(R.drawable.clothes_pants, R.drawable.red_clothes_pants);
        normalbucketEnterbucket.put(R.drawable.clothes_shoes, R.drawable.red_clothes_shoes);
    }

    private void setOntouchlistenerEZ() {
        thing1.setOnTouchListener(new MyTouchListener());
        thing2.setOnTouchListener(new MyTouchListener());
        thing3.setOnTouchListener(new MyTouchListener());
    }

    private void setOntouchlistenerMed() {
        thing1.setOnTouchListener(new MyTouchListener());
        thing2.setOnTouchListener(new MyTouchListener());
        thing3.setOnTouchListener(new MyTouchListener());
        thing4.setOnTouchListener(new MyTouchListener());
    }

    private void setOntouchlistenerADV() {
        thing1.setOnTouchListener(new MyTouchListener());
        thing2.setOnTouchListener(new MyTouchListener());
        thing3.setOnTouchListener(new MyTouchListener());
        thing4.setOnTouchListener(new MyTouchListener());
        thing5.setOnTouchListener(new MyTouchListener());
        thing6.setOnTouchListener(new MyTouchListener());
    }

    private void assignAllButtons() {
        thing1 = findViewById(R.id.imageView1Bucket);
        thing2 = findViewById(R.id.imageView2Bucket);
        thing3 = findViewById(R.id.imageView3Bucket);
        thing4 = findViewById(R.id.imageView4Bucket);
        thing5 = findViewById(R.id.imageView5Bucket);
        thing6 = findViewById(R.id.imageView6Bucket);
        animationTextPoints = findViewById(R.id.AnimTextPointsBucket);
        exit = findViewById(R.id.ExitBucket);
        replayTutorial = findViewById(R.id.ReplayTutorialBucket);
        logoLinear = findViewById(R.id.imageLogoDisplayBucket);
        textsLinear = findViewById(R.id.textsBucket);
        textMsg = findViewById(R.id.msgBucket);
        textMsgTime = findViewById(R.id.msgBucket1);

        bucket1Linear = findViewById(R.id.Bucket1Linear);
        bucket2Linear = findViewById(R.id.Bucket2Linear);
        bucket3Linear = findViewById(R.id.Bucket3Linear);


        bucket1Linear.setOnDragListener(new MyDragListener());
        bucket2Linear.setOnDragListener(new MyDragListener());
        bucket3Linear.setOnDragListener(new MyDragListener());

        startButton = findViewById(R.id.startButtonBucket);
        textRounds = findViewById(R.id.textRoundsBucket);
    }


    private void fillImageviews() {
        imageviews.add(R.id.imageView1Bucket);
        imageviews.add(R.id.imageView2Bucket);
        imageviews.add(R.id.imageView3Bucket);
        imageviews.add(R.id.imageView4Bucket);
        imageviews.add(R.id.imageView5Bucket);
        imageviews.add(R.id.imageView6Bucket);
    }

    private void enableReplayTut(){
        replayTutorial.setEnabled(true);
        replayTutorial.setAlpha(1f);
    }
    private void disableReplayTut(){
        replayTutorial.setEnabled(false);
        replayTutorial.setAlpha(0.5f);
    }

    private void countPoints() {

        int currentPoints = 0;

        if (!missPoints && trueCounter == 1) {
            currentPoints += 10;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win);
        } else if (!missPoints && trueCounter == 2) {
            currentPoints += 20;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win1);
        } else if (!missPoints && trueCounter >= 3) {
            currentPoints += 30;
            currentPoints += pointsHashMap.get(currentDifficulty);
            textMsg.setText(R.string.win2);
        } else if (missPoints) {
            currentPoints += 0;
            trueCounter = 0;
            missPoints = false;
            textMsg.setText(R.string.lose);
        }
        totalPoints += currentPoints;
        animationTextPoints.setText("+ " + currentPoints);
        if (currentPoints == 0) {
            animationTextPoints.setTextColor(Color.RED);
        } else
            animationTextPoints.setTextColor(Color.GREEN);
    }


    private void showTutorialPopUp() {
        DialogFragment dialogFragment = new Tutorial(BucketGame.this, R.raw.tutorial_boxgame, getPackageName());
        dialogFragment.show(getSupportFragmentManager(), "TutorialBucketGame");
    }

    private void shopPopUp() {
        DialogFragment newFragment = new DialogMsg(user_id, BucketGame.this, hit, totalPoints);
        newFragment.show(getSupportFragmentManager(), "BucketGame");
    }

    private void startAnimation() {
        long duration = 2000;
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(animationTextPoints, "y", 500f, -500f);
        objectAnimatorY.setDuration(duration);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(animationTextPoints, View.ALPHA, 1.0f, 0.0f);
        alpha.setDuration(duration);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(objectAnimatorY, alpha);
        animatorSet.start();
    }
}
