package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SuitcaseEz extends Fragment implements View.OnClickListener{

    private ImageView lid1, lid2, lid3, lid4;
    private ImageView base1, base2, base3, base4;

    private LinearLayout Base,Lid;

    private ArrayList<Integer> baggage = new ArrayList<Integer>();
    private ArrayList<Integer> items = new ArrayList<Integer>();

    private ArrayList<Integer> tempNumberList = new ArrayList<Integer>();
    private ArrayList<Integer> slotList = new ArrayList<Integer>();

    private HashMap<Integer, Integer> baseIDS = new HashMap<Integer, Integer>();
    private HashMap<Integer, Integer> lidIDS = new HashMap<Integer, Integer>();


    private HashMap<Integer, Integer> sideToCheck = new HashMap<Integer, Integer>();

    private HashMap<Integer, Integer> side = new HashMap<Integer, Integer>();

    private View view;

    private static final long START_TIME_IN_MILLIS = 6000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;


    private boolean missPoints = false;

    private int hit=0, miss=0 , round=0 , trueCounter=0;
    private double speedseconds;
    private Timestamp startspeed,endspeed;
    private SuitcaseEz.OnDataPass dataPasser;


    public SuitcaseEz() { }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (SuitcaseEz.OnDataPass) context;
        setRetainInstance(true);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeBaseIdsHashMap();
        initializeLidIdsHashMap();

        initializeItemsList();


        //edw na swzw ta dika mou
        if (savedInstanceState != null) {

        }



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.suitcase_ez_layout, container, false);

        assignAllImageViews();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        createRound();

    }

    public void createRound() {


        clickAll();

        initializeTempItemList();
        initializeSlotList();

        baggage.clear();

        Random rand = new Random();

        int r = rand.nextInt(2)+1;

        if (r==1)
        {
            side = baseIDS;
            unClickBase();

            sideToCheck = lidIDS;
        }
        else
        {
            side = lidIDS;
            unClickLid();

            sideToCheck = baseIDS;
        }

        for (int i = 0; i <= 1; i++)
        {

             Collections.shuffle(tempNumberList);
             Collections.shuffle(slotList);

             ImageView iv = getActivity().findViewById(side.get(slotList.get(0)));

             baggage.add(slotList.get(0));

             slotList.remove(0);

             iv.setBackgroundResource(items.get(tempNumberList.get(0)));

             tempNumberList.remove(0);

        }

        Log.d("BAGGAGE",baggage.toString());

    }

    public void assignAllImageViews() {

        Base = view.findViewById(R.id.Suitcase_Base_LinearLayout);
        Lid = view.findViewById(R.id.Suitcase_Lid_LinearLayout);

        base1 = view.findViewById(R.id.Suitcase_Base_imageView1);
        base2 = view.findViewById(R.id.Suitcase_Base_imageView2);
        base3 = view.findViewById(R.id.Suitcase_Base_imageView3);
        base4 = view.findViewById(R.id.Suitcase_Base_imageView4);

        base1.setOnClickListener(this);
        base2.setOnClickListener(this);
        base3.setOnClickListener(this);
        base4.setOnClickListener(this);

        lid1 = view.findViewById(R.id.Suitcase_Lid_imageView1);
        lid2 = view.findViewById(R.id.Suitcase_Lid_imageView2);
        lid3 = view.findViewById(R.id.Suitcase_Lid_imageView3);
        lid4 = view.findViewById(R.id.Suitcase_Lid_imageView4);

        lid1.setOnClickListener(this);
        lid2.setOnClickListener(this);
        lid3.setOnClickListener(this);
        lid4.setOnClickListener(this);

    }

    public void initializeBaseIdsHashMap() {

        baseIDS.put(1, R.id.Suitcase_Base_imageView1);
        baseIDS.put(2, R.id.Suitcase_Base_imageView2);
        baseIDS.put(3, R.id.Suitcase_Base_imageView3);
        baseIDS.put(4, R.id.Suitcase_Base_imageView4);

    }

    public void initializeLidIdsHashMap() {

        lidIDS.put(2, R.id.Suitcase_Lid_imageView1);
        lidIDS.put(1, R.id.Suitcase_Lid_imageView2);
        lidIDS.put(4, R.id.Suitcase_Lid_imageView3);
        lidIDS.put(3, R.id.Suitcase_Lid_imageView4);

    }

    public void initializeItemsList()
    {
        items.add(R.drawable.suitcase_accesscard);
        items.add(R.drawable.suitcase_bankcard);
        items.add(R.drawable.suitcase_binoculars);
        items.add(R.drawable.suitcase_charger);
        items.add(R.drawable.suitcase_diabetes);
        items.add(R.drawable.suitcase_flashlight);
        items.add(R.drawable.suitcase_mosquitospray);
        items.add(R.drawable.suitcase_notepad);
        items.add(R.drawable.suitcase_safekit);
        items.add(R.drawable.suitcase_shoes);
        items.add(R.drawable.suitcase_shorts);
        items.add(R.drawable.suitcase_socks);
        items.add(R.drawable.suitcase_suitblue);
        items.add(R.drawable.suitcase_suityellow);
        items.add(R.drawable.suitcase_tie);
        items.add(R.drawable.suitcase_watch);
    }


    public void initializeTempItemList()
    {
        for (int i = 0; i <= 15; i++) {
            tempNumberList.add(i);
        }
    }

    public void initializeSlotList()
    {
        for (int i = 1; i <= 4; i++) {
            slotList.add(i);
        }
    }

    public void unClickBase() {
        base1.setClickable(false);
        base2.setClickable(false);
        base3.setClickable(false);
        base4.setClickable(false);
    }

    public void unClickLid() {
        lid1.setClickable(false);
        lid2.setClickable(false);
        lid3.setClickable(false);
        lid4.setClickable(false);
    }

    public void clickAll() {

        base1.setClickable(true);
        base2.setClickable(true);
        base3.setClickable(true);
        base4.setClickable(true);

        lid1.setClickable(true);
        lid2.setClickable(true);
        lid3.setClickable(true);
        lid4.setClickable(true);
    }

    public int getSlotFromID(int value) {

        //h synarthsh auth travaei ta kleidia apo thn apenanti pleura apo authn pou mphkan arxika ta antikeimena
        //to sideToCheck einai panta to antitheto hashmap apo to side

        for (int b : sideToCheck.keySet()) {
            if (sideToCheck.get(b).equals(value)) {
                return b;
            }
        }
        return 0;
    }



    @Override
    public void onClick(View view1) {

        Log.d("PATHSES TO",String.valueOf(getSlotFromID(view1.getId())));

        if (getResources().getResourceEntryName(view1.getId()).contains("Base"))
        {
            closeLid();
        }
        else
        {
            closeBase();
        }

        if (baggage.contains(getSlotFromID(view1.getId())))
        {
            //lathos
            Log.d("LATHOS","LATHOS");
        }
        else
        {
            //swstos
            Log.d("SWSTA","SWSTA");
        }



    }

    private void closeLid() {

        Lid.bringToFront();

        ObjectAnimator flip = ObjectAnimator.ofFloat(Lid, "rotationY", 0, -180f);
        flip.setDuration(2000);
        flip.start();

        ObjectAnimator slide = ObjectAnimator.ofFloat(Lid, "x", 25f);
        slide.setDuration(2000);
        slide.start();

        AnimatorSet close = new AnimatorSet();
        close.playTogether(flip, slide);
        close.start();
    }

    private void closeBase() {

        Base.bringToFront();

        ObjectAnimator flip = ObjectAnimator.ofFloat(Base, "rotationY", 0, 180f);
        flip.setDuration(2000);
        flip.start();

        ObjectAnimator slide = ObjectAnimator.ofFloat(Base, "x", 385f);
        slide.setDuration(2000);
        slide.start();

        AnimatorSet close = new AnimatorSet();
        close.playTogether(flip, slide);
        close.start();
    }




    public interface OnDataPass {
        public void onDataPass(int round,int hit,int miss,double speedinSeconds,boolean misspoints,int truecounter);
    }

    private void passData(int a,int b,int c,double d,boolean e,int f) {
        dataPasser.onDataPass(a,b,c,d,e,f);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

    }



}
