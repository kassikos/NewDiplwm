package com.example.newdiplwm;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


public class SuitcaseEz extends Fragment implements View.OnClickListener{

    private MaterialButton mb1, mb2, mb3, mb4;
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
        assignAllButtons();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


    public void createRound() {


    }

    public void assignAllButtons() {
//        mb1 = (MaterialButton) view.findViewById(R.id.Suitcase_Ez_imageView1);
//        mb2 = (MaterialButton) view.findViewById(R.id.Suitcase_Ez_imageView2);
//        mb3 = (MaterialButton) view.findViewById(R.id.Suitcase_Ez_imageView3);
//        mb4 = (MaterialButton) view.findViewById(R.id.Suitcase_Ez_imageView4);

        mb1.setOnClickListener(this);
        mb2.setOnClickListener(this);
        mb3.setOnClickListener(this);
        mb4.setOnClickListener(this);

    }

    @Override
    public void onClick(View view1) {


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
