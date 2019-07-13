package com.example.newdiplwm;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MemoryMatrixEz extends Fragment implements View.OnClickListener {
    private MaterialButton mb1, mb2, mb3, mb4, mb5, mb6, mb7, mb8, mb9;
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
    private OnDataPass dataPasser;

    public MemoryMatrixEz() { }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPass) context;
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        sequence_counter = 0;
        initializeIdsHashMap();
        initializeEasyList();

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
        view = inflater.inflate(R.layout.memory_matrix_ez_layout, container, false);
        assignAllButtons();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        EasyFadingTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {


            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;

                unClickMatrix();

                if (millisUntilFinished >= 4000) {
                    fading_counter = 0;
                    MaterialButton v = view.findViewById(ids.get(arraySequence[fading_counter]));
                    v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 1 ");
                } else if (millisUntilFinished > 2000 && millisUntilFinished < 4000) {
                    fading_counter = 1;
                    MaterialButton v = view.findViewById(ids.get(arraySequence[fading_counter]));
                    v.setTextColor(getResources().getColor(R.color.red));
                    v.setText(" 2 ");
                } else if (millisUntilFinished <= 2000) {
                    fading_counter = 2;//axreiasto gt exei dhlwthei apo 0
                    MaterialButton v = view.findViewById(ids.get(arraySequence[fading_counter]));
                    v.setTextColor(getResources().getColor(R.color.red));
                    v.setText(" 3 ");
                }
            }

            public void onFinish() {

                MaterialButton b1 = view.findViewById(ids.get(arraySequence[2]));
                MaterialButton b2 = view.findViewById(ids.get(arraySequence[1]));
                MaterialButton b3 = view.findViewById(ids.get(arraySequence[0]));

                b1.setText("");
                b2.setText("");
                b3.setText("");

                ClickMatrix();
                startspeed = new Timestamp(System.currentTimeMillis());
            }

        };

        createRound();

        ClearCorrectsTimer = new CountDownTimer(2000, 1000) {


            @Override
            public void onTick(long l) { }

            public void onFinish() {

                MaterialButton b1 = view.findViewById(ids.get(arraySequence[2]));
                MaterialButton b2 = view.findViewById(ids.get(arraySequence[1]));
                MaterialButton b3 = view.findViewById(ids.get(arraySequence[0]));

                b1.setBackgroundColor(0);
                b2.setBackgroundColor(0);
                b3.setBackgroundColor(0);

                b1.setText("");
                b2.setText("");
                b3.setText("");

            }

        };

        ClearFailTimer = new CountDownTimer(2000, 1000) {


            @Override
            public void onTick(long l) { }

            public void onFinish() {

                MaterialButton fb = view.findViewById(FailViewId);
                fb.setBackgroundColor(0);
            }

        };
    }

    public void createRound() {

        Collections.shuffle(easyList);

        for (int i = 0; i < 3; i++) {
            arraySequence[i] = easyList.get(i);
        }


        EasyFadingTimer.start();
    }


    public void initializeIdsHashMap() {

        ids.put(1, R.id.imageView1MatrixEz);
        ids.put(2, R.id.imageView2MatrixEz);
        ids.put(3, R.id.imageView3MatrixEz);
        ids.put(4, R.id.imageView4MatrixEz);
        ids.put(5, R.id.imageView5MatrixEz);
        ids.put(6, R.id.imageView6MatrixEz);
        ids.put(7, R.id.imageView7MatrixEz);
        ids.put(8, R.id.imageView8MatrixEz);
        ids.put(9, R.id.imageView9MatrixEz);

    }


    public void initializeEasyList() {
        for (int i = 1; i <= 9; i++) {
            easyList.add(i);
        }
    }

    public void disableMatrix() {
        mb1.setEnabled(false);
        mb1.setAlpha(0.5f);

        mb2.setEnabled(false);
        mb2.setAlpha(0.5f);

        mb3.setEnabled(false);
        mb3.setAlpha(0.5f);

        mb4.setEnabled(false);
        mb4.setAlpha(0.5f);

        mb5.setEnabled(false);
        mb5.setAlpha(0.5f);

        mb6.setEnabled(false);
        mb6.setAlpha(0.5f);

        mb7.setEnabled(false);
        mb7.setAlpha(0.5f);

        mb8.setEnabled(false);
        mb8.setAlpha(0.5f);

        mb9.setEnabled(false);
        mb9.setAlpha(0.5f);

    }

    public void enableMatrix() {
        mb1.setEnabled(true);
        mb1.setAlpha(1f);

        mb2.setEnabled(true);
        mb2.setAlpha(1f);

        mb3.setEnabled(true);
        mb3.setAlpha(1f);

        mb4.setEnabled(true);
        mb4.setAlpha(1f);

        mb5.setEnabled(true);
        mb5.setAlpha(1f);

        mb6.setEnabled(true);
        mb6.setAlpha(1f);

        mb7.setEnabled(true);
        mb7.setAlpha(1f);

        mb8.setEnabled(true);
        mb8.setAlpha(1f);

        mb9.setEnabled(true);
        mb9.setAlpha(1f);

    }

    public void unClickMatrix() {
        mb1.setClickable(false);
        mb2.setClickable(false);
        mb3.setClickable(false);
        mb4.setClickable(false);
        mb5.setClickable(false);
        mb6.setClickable(false);
        mb7.setClickable(false);
        mb8.setClickable(false);
        mb9.setClickable(false);

    }

    public void ClickMatrix() {
        mb1.setClickable(true);
        mb2.setClickable(true);
        mb3.setClickable(true);
        mb4.setClickable(true);
        mb5.setClickable(true);
        mb6.setClickable(true);
        mb7.setClickable(true);
        mb8.setClickable(true);
        mb9.setClickable(true);

    }

    public void assignAllButtons() {
        mb1 = (MaterialButton) view.findViewById(R.id.imageView1MatrixEz);
        mb2 = (MaterialButton) view.findViewById(R.id.imageView2MatrixEz);
        mb3 = (MaterialButton) view.findViewById(R.id.imageView3MatrixEz);
        mb4 = (MaterialButton) view.findViewById(R.id.imageView4MatrixEz);
        mb5 = (MaterialButton) view.findViewById(R.id.imageView5MatrixEz);
        mb6 = (MaterialButton) view.findViewById(R.id.imageView6MatrixEz);
        mb7 = (MaterialButton) view.findViewById(R.id.imageView7MatrixEz);
        mb8 = (MaterialButton) view.findViewById(R.id.imageView8MatrixEz);
        mb9 = (MaterialButton) view.findViewById(R.id.imageView9MatrixEz);

        mb1.setOnClickListener(this);
        mb2.setOnClickListener(this);
        mb3.setOnClickListener(this);
        mb4.setOnClickListener(this);
        mb5.setOnClickListener(this);
        mb6.setOnClickListener(this);
        mb7.setOnClickListener(this);
        mb8.setOnClickListener(this);
        mb9.setOnClickListener(this);

    }


    @Override
    public void onClick(View view1) {
        flipIt(view1);


        if (view1.getId() == ids.get(arraySequence[sequence_counter])) {
            //to phra to v se koumpi
            MaterialButton b = view.findViewById(ids.get(arraySequence[sequence_counter]));

            sequence_counter++;

            b.setBackgroundColor(getResources().getColor(R.color.green));
            b.setTextColor(getResources().getColor(R.color.black));
            b.setText(" " + sequence_counter + " ");


            if (sequence_counter == 3) {
                hit++;
                round++;
                trueCounter++;
                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime() - startspeed.getTime();
                speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);

                passData(round,hit,miss,speedseconds,missPoints,trueCounter);
                disableMatrix();

                ClearCorrectsTimer.start();

            }


        } else {
            miss++;
            round++;
            missPoints =true;

            endspeed = new Timestamp(System.currentTimeMillis());
            long diffspeed = endspeed.getTime() - startspeed.getTime();
            speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
            passData(round,hit,miss,speedseconds,missPoints,trueCounter);


            FailViewId = view1.getId();

            view1.setBackgroundColor(getResources().getColor(R.color.red));

            disableMatrix();

            ClearCorrectsTimer.start();

            ClearFailTimer.start();

        }


    }

    private void flipIt(View viewToFlip) {
        ObjectAnimator flip = ObjectAnimator.ofFloat(viewToFlip, "rotationY", 0f, 360f);
        flip.setDuration(1000);
        flip.start();

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
