package com.example.newdiplwm;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class SuitcaseEz extends Fragment implements View.OnClickListener{

    private ImageView lid1, lid2, lid3, lid4;
    private ImageView base1, base2, base3, base4;

    private LinearLayout LL2;

    HashMap<Integer, Integer> baseIDS = new HashMap<Integer, Integer>();
    HashMap<Integer, Integer> lidIDS = new HashMap<Integer, Integer>();

    private View view;

    private static final long START_TIME_IN_MILLIS = 6000;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    private ArrayList<Integer> easyList = new ArrayList<Integer>();
    private int[] arraySequence = new int[3];
    private HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();


    private int fading_counter = 0;
    private int sequence_counter;

    private int FailViewId;
    private CountDownTimer EasyFadingTimer, ClearFailTimer, ClearCorrectsTimer;

    private static final String EASYLIST = "EASYLIST";
    private static final String ARRAYSECUENSE = "ARRAYSECUENSE";
    private static final String HSH = "HSH";
    private static final String FADINGCOUNTER = "FADINGCOUNTER";
    private static final String SEQUENCECOUNTER = "SEQUENCECOUNTER";
    private static final String FAILED = "FAILED";
    private static final String CLOCK = "CLOCK";


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


        //edw na swzw ta dika mou
        if (savedInstanceState != null) {
            ids = (HashMap<Integer, Integer>) savedInstanceState.getSerializable(HSH);
            easyList = savedInstanceState.getIntegerArrayList(EASYLIST);
            arraySequence = savedInstanceState.getIntArray(ARRAYSECUENSE);
            fading_counter = savedInstanceState.getInt(FADINGCOUNTER);
            sequence_counter = savedInstanceState.getInt(SEQUENCECOUNTER);
            FailViewId = savedInstanceState.getInt(FAILED);
            mTimeLeftInMillis = savedInstanceState.getLong(CLOCK);

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


    }

    public void assignAllImageViews() {

        LL2 = view.findViewById(R.id.Suitcase_LinearLayout2);


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

        lidIDS.put(1, R.id.Suitcase_Lid_imageView1);
        lidIDS.put(2, R.id.Suitcase_Lid_imageView2);
        lidIDS.put(3, R.id.Suitcase_Lid_imageView3);
        lidIDS.put(4, R.id.Suitcase_Lid_imageView4);

    }

    @Override
    public void onClick(View view1) {
        flipIt(view1);


    }

    private void flipIt(View viewToFlip) {

        ObjectAnimator flip = ObjectAnimator.ofFloat(LL2, "rotationY", 0, -180f);
        flip.setDuration(2000);
        flip.start();

        ObjectAnimator slide = ObjectAnimator.ofFloat(LL2, "x", 25f);
        slide.setDuration(2000);
        slide.start();

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.playTogether(flip, slide);
        animSetXY.start();

//        ObjectAnimator flip = ObjectAnimator.ofFloat(viewToFlip, "rotationY", 180f, 0f);
//        flip.setDuration(1000);
//        flip.start();

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
        outState.putIntegerArrayList(EASYLIST,easyList);
        outState.putIntArray(ARRAYSECUENSE,arraySequence);
        outState.putSerializable(HSH,ids);
        outState.putInt(FADINGCOUNTER,fading_counter);
        outState.putInt(FAILED,FailViewId);
        outState.putInt(SEQUENCECOUNTER,sequence_counter);
        outState.putLong(CLOCK,mTimeLeftInMillis);

    }



}
