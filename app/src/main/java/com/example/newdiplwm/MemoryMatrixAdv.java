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
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MemoryMatrixAdv extends Fragment implements View.OnClickListener {
    private MaterialButton mb1, mb2, mb3, mb4, mb5, mb6, mb7, mb8, mb9, mb10, mb11, mb12, mb13, mb14, mb15, mb16;
    private View view;
    private MemoryMatrixViewModel memoryMatrixViewModel;


    private ArrayList<Integer> mediumList = new ArrayList<Integer>();
    private Integer[] arraySequenceMed = new Integer[4];
    private Integer[] arraySequenceAdv = new Integer[5];
    private HashMap<Integer, Integer> ids = new HashMap<Integer, Integer>();

    private int FailViewId;
    private CountDownTimer MediumFadingTimer, ClearFailTimer, ClearCorrectsTimer, AdvancedFadingTimer;

    private int fading_counter = 0;
    private int sequence_counter;


    private boolean missPoints = false;
    private int trueCounter = 0;

    private int round = 0;

    private int hit = 0, miss = 0;
    private double speedseconds;
    private Timestamp startspeed;
    private Timestamp endspeed;

    private OnDataPassAdv dataPasser;

    private String diff;

    public MemoryMatrixAdv(){}

    public MemoryMatrixAdv(String diff) {
        this.diff = diff;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassAdv) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sequence_counter = 0;
        initializeIdsHashMap();
        initializeMedList();


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.memory_matrix_adv_layout, container, false);
        assignAllButtons();

        memoryMatrixViewModel = ViewModelProviders.of(getActivity()).get(MemoryMatrixViewModel.class);
        return view;

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (diff.equals(getResources().getString(R.string.mediumValue))) {

            MediumFadingTimer = new CountDownTimer(8000, 1000) {


                public void onTick(long millisUntilFinished) {

                    unClickMatrix();

                    if (millisUntilFinished >= 6000) {
                        fading_counter = 0;
                        MaterialButton v = view.findViewById(ids.get(arraySequenceMed[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 1 ");
                    } else if (millisUntilFinished >= 4000 && millisUntilFinished < 6000) {
                        fading_counter = 1;
                        MaterialButton v = view.findViewById(ids.get(arraySequenceMed[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 2 ");

                    } else if (millisUntilFinished >= 2000 && millisUntilFinished < 4000) {
                        fading_counter = 2;//axreiasto gt exei dhlwthei apo 0
                        MaterialButton v = view.findViewById(ids.get(arraySequenceMed[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 3 ");
                    } else if (millisUntilFinished <= 2000) {
                        fading_counter = 3;//axreiasto gt exei dhlwthei apo 0
                        MaterialButton v = view.findViewById(ids.get(arraySequenceMed[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 4 ");

                    }
                }

                public void onFinish() {

                    MaterialButton b0 = view.findViewById(ids.get(arraySequenceMed[3]));
                    MaterialButton b1 = view.findViewById(ids.get(arraySequenceMed[2]));
                    MaterialButton b2 = view.findViewById(ids.get(arraySequenceMed[1]));
                    MaterialButton b3 = view.findViewById(ids.get(arraySequenceMed[0]));


                    b1.setText("");
                    b2.setText("");
                    b3.setText("");
                    b0.setText("");

                    ClickMatrix();
                    startspeed = new Timestamp(System.currentTimeMillis());
                }

            };

            createRound();

            ClearCorrectsTimer = new CountDownTimer(2000, 1000) {


                @Override
                public void onTick(long l) {
                }

                public void onFinish() {

                    MaterialButton b0 = view.findViewById(ids.get(arraySequenceMed[3]));
                    MaterialButton b1 = view.findViewById(ids.get(arraySequenceMed[2]));
                    MaterialButton b2 = view.findViewById(ids.get(arraySequenceMed[1]));
                    MaterialButton b3 = view.findViewById(ids.get(arraySequenceMed[0]));

                    b0.setBackgroundColor(0);
                    b1.setBackgroundColor(0);
                    b2.setBackgroundColor(0);
                    b3.setBackgroundColor(0);

                    b0.setText("");
                    b1.setText("");
                    b2.setText("");
                    b3.setText("");

                }

            };

            ClearFailTimer = new CountDownTimer(2000, 1000) {


                @Override
                public void onTick(long l) {
                }

                public void onFinish() {

                    MaterialButton fb = view.findViewById(FailViewId);
                    fb.setBackgroundColor(0);
                }

            };

        } else {

            AdvancedFadingTimer = new CountDownTimer(10000, 1000) {


                public void onTick(long millisUntilFinished) {

                    unClickMatrix();

                    if (millisUntilFinished >= 8000) {
                        fading_counter = 0;
                        MaterialButton v = view.findViewById(ids.get(arraySequenceAdv[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 1 ");
                    } else if (millisUntilFinished >= 6000 && millisUntilFinished < 8000) {
                        fading_counter = 1;
                        MaterialButton v = view.findViewById(ids.get(arraySequenceAdv[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 2 ");

                    } else if (millisUntilFinished >= 4000 && millisUntilFinished < 6000) {
                        fading_counter = 2;//axreiasto gt exei dhlwthei apo 0
                        MaterialButton v = view.findViewById(ids.get(arraySequenceAdv[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 3 ");
                    } else if (millisUntilFinished >= 2000 && millisUntilFinished < 4000) {
                        fading_counter = 3;//axreiasto gt exei dhlwthei apo 0
                        MaterialButton v = view.findViewById(ids.get(arraySequenceAdv[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 4 ");
                    } else if (millisUntilFinished < 2000) {
                        fading_counter = 4;//axreiasto gt exei dhlwthei apo 0
                        MaterialButton v = view.findViewById(ids.get(arraySequenceAdv[fading_counter]));
                        v.setTextColor(getResources().getColor(R.color.red));
                        v.setText(" 5 ");

                    }
                }

                public void onFinish() {

                    MaterialButton b = view.findViewById(ids.get(arraySequenceAdv[4]));
                    MaterialButton b0 = view.findViewById(ids.get(arraySequenceAdv[3]));
                    MaterialButton b1 = view.findViewById(ids.get(arraySequenceAdv[2]));
                    MaterialButton b2 = view.findViewById(ids.get(arraySequenceAdv[1]));
                    MaterialButton b3 = view.findViewById(ids.get(arraySequenceAdv[0]));


                    b1.setText("");
                    b2.setText("");
                    b3.setText("");
                    b0.setText("");
                    b.setText("");

                    ClickMatrix();
                    startspeed = new Timestamp(System.currentTimeMillis());
                }

            };

            createRoundAdv();

            ClearCorrectsTimer = new CountDownTimer(2000, 1000) {


                @Override
                public void onTick(long l) {
                }

                public void onFinish() {

                    MaterialButton b = view.findViewById(ids.get(arraySequenceAdv[4]));
                    MaterialButton b0 = view.findViewById(ids.get(arraySequenceAdv[3]));
                    MaterialButton b1 = view.findViewById(ids.get(arraySequenceAdv[2]));
                    MaterialButton b2 = view.findViewById(ids.get(arraySequenceAdv[1]));
                    MaterialButton b3 = view.findViewById(ids.get(arraySequenceAdv[0]));

                    b.setBackgroundColor(0);
                    b0.setBackgroundColor(0);
                    b1.setBackgroundColor(0);
                    b2.setBackgroundColor(0);
                    b3.setBackgroundColor(0);

                    b.setText("");
                    b0.setText("");
                    b1.setText("");
                    b2.setText("");
                    b3.setText("");

                }

            };

            ClearFailTimer = new CountDownTimer(2000, 1000) {


                @Override
                public void onTick(long l) {
                }

                public void onFinish() {

                    MaterialButton fb = view.findViewById(FailViewId);
                    fb.setBackgroundColor(0);
                }

            };

        }
    }


    private void createRound() {

        Collections.shuffle(mediumList);

        for (int i = 0; i < 4; i++) {
            arraySequenceMed[i] = mediumList.get(i);
        }
        memoryMatrixViewModel.setTimer(MediumFadingTimer);
        MediumFadingTimer.start();
    }

    private void createRoundAdv() {

        Collections.shuffle(mediumList);

        for (int i = 0; i < 5; i++) {
            arraySequenceAdv[i] = mediumList.get(i);
        }
        memoryMatrixViewModel.setTimer(AdvancedFadingTimer);
        AdvancedFadingTimer.start();
    }


    private void unClickMatrix() {
        mb1.setClickable(false);
        mb2.setClickable(false);
        mb3.setClickable(false);
        mb4.setClickable(false);
        mb5.setClickable(false);
        mb6.setClickable(false);
        mb7.setClickable(false);
        mb8.setClickable(false);
        mb9.setClickable(false);
        mb10.setClickable(false);
        mb11.setClickable(false);
        mb12.setClickable(false);
        mb13.setClickable(false);
        mb14.setClickable(false);
        mb15.setClickable(false);
        mb16.setClickable(false);

    }

    private void ClickMatrix() {
        mb1.setClickable(true);
        mb2.setClickable(true);
        mb3.setClickable(true);
        mb4.setClickable(true);
        mb5.setClickable(true);
        mb6.setClickable(true);
        mb7.setClickable(true);
        mb8.setClickable(true);
        mb9.setClickable(true);
        mb10.setClickable(true);
        mb11.setClickable(true);
        mb12.setClickable(true);
        mb13.setClickable(true);
        mb14.setClickable(true);
        mb15.setClickable(true);
        mb16.setClickable(true);

    }

    private void initializeIdsHashMap() {

        ids.put(1, R.id.imageView2MatrixMed);
        ids.put(2, R.id.imageView3MatrixMed);
        ids.put(3, R.id.imageView4MatrixMed);
        ids.put(4, R.id.imageView5MatrixMed);
        ids.put(5, R.id.imageView6MatrixMed);
        ids.put(6, R.id.imageView7MatrixMed);
        ids.put(7, R.id.imageView8MatrixMed);
        ids.put(8, R.id.imageView9MatrixMed);
        ids.put(9, R.id.imageView10MatrixMed);
        ids.put(10, R.id.imageView11MatrixMed);
        ids.put(11, R.id.imageView12MatrixMed);
        ids.put(12, R.id.imageView13MatrixMed);
        ids.put(13, R.id.imageView14MatrixMed);
        ids.put(14, R.id.imageView15MatrixMed);
        ids.put(15, R.id.imageView16MatrixMed);
        ids.put(16, R.id.imageView17MatrixMed);

    }

    private void assignAllButtons() {
        mb1 = (MaterialButton) view.findViewById(R.id.imageView2MatrixMed);
        mb2 = (MaterialButton) view.findViewById(R.id.imageView3MatrixMed);
        mb3 = (MaterialButton) view.findViewById(R.id.imageView4MatrixMed);
        mb4 = (MaterialButton) view.findViewById(R.id.imageView5MatrixMed);
        mb5 = (MaterialButton) view.findViewById(R.id.imageView6MatrixMed);
        mb6 = (MaterialButton) view.findViewById(R.id.imageView7MatrixMed);
        mb7 = (MaterialButton) view.findViewById(R.id.imageView8MatrixMed);
        mb8 = (MaterialButton) view.findViewById(R.id.imageView9MatrixMed);
        mb9 = (MaterialButton) view.findViewById(R.id.imageView10MatrixMed);
        mb10 = (MaterialButton) view.findViewById(R.id.imageView11MatrixMed);
        mb11 = (MaterialButton) view.findViewById(R.id.imageView12MatrixMed);
        mb12 = (MaterialButton) view.findViewById(R.id.imageView13MatrixMed);
        mb13 = (MaterialButton) view.findViewById(R.id.imageView14MatrixMed);
        mb14 = (MaterialButton) view.findViewById(R.id.imageView15MatrixMed);
        mb15 = (MaterialButton) view.findViewById(R.id.imageView16MatrixMed);
        mb16 = (MaterialButton) view.findViewById(R.id.imageView17MatrixMed);

        mb1.setOnClickListener(this);
        mb2.setOnClickListener(this);
        mb3.setOnClickListener(this);
        mb4.setOnClickListener(this);
        mb5.setOnClickListener(this);
        mb6.setOnClickListener(this);
        mb7.setOnClickListener(this);
        mb8.setOnClickListener(this);
        mb9.setOnClickListener(this);
        mb10.setOnClickListener(this);
        mb11.setOnClickListener(this);
        mb12.setOnClickListener(this);
        mb13.setOnClickListener(this);
        mb14.setOnClickListener(this);
        mb15.setOnClickListener(this);
        mb16.setOnClickListener(this);

    }

    private void disableMatrix() {
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

        mb10.setEnabled(false);
        mb10.setAlpha(0.5f);

        mb11.setEnabled(false);
        mb11.setAlpha(0.5f);

        mb12.setEnabled(false);
        mb12.setAlpha(0.5f);

        mb13.setEnabled(false);
        mb13.setAlpha(0.5f);

        mb14.setEnabled(false);
        mb14.setAlpha(0.5f);

        mb15.setEnabled(false);
        mb15.setAlpha(0.5f);

        mb16.setEnabled(false);
        mb16.setAlpha(0.5f);
    }

    private void initializeMedList() {
        for (int i = 1; i <= 16; i++) {
            mediumList.add(i);
        }
    }

    private void flipIt(View viewToFlip) {
        ObjectAnimator flip = ObjectAnimator.ofFloat(viewToFlip, "rotationY", 0f, 360f);
        flip.setDuration(1000);
        flip.start();

    }


    public interface OnDataPassAdv {
        public void onDataPassAdv(int round, int hit, int miss, double speedinSeconds, boolean misspoints, int truecounter);
    }

    private void passData(int a, int b, int c, double d, boolean e, int f) {
        dataPasser.onDataPassAdv(a, b, c, d, e, f);
    }

    @Override
    public void onClick(View view1) {
        flipIt(view1);


        if (diff.equals("MEDIUM")) {


            if (view1.getId() == ids.get(arraySequenceMed[sequence_counter])) {
                //to phra to v se koumpi
                MaterialButton b = view.findViewById(ids.get(arraySequenceMed[sequence_counter]));

                sequence_counter++;

                b.setBackgroundColor(getResources().getColor(R.color.green));
                b.setTextColor(getResources().getColor(R.color.black));
                b.setText(" " + sequence_counter + " ");


                if (sequence_counter == 4) {
                    hit++;
                    round++;
                    trueCounter++;
                    endspeed = new Timestamp(System.currentTimeMillis());
                    long diffspeed = endspeed.getTime() - startspeed.getTime();
                    speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);

                    passData(round, hit, miss, speedseconds, missPoints, trueCounter);
                    disableMatrix();

                    ClearCorrectsTimer.start();

                }


            } else {
                miss++;
                round++;
                missPoints = true;

                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime() - startspeed.getTime();
                speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
                passData(round, hit, miss, speedseconds, missPoints, trueCounter);


                FailViewId = view1.getId();

                view1.setBackgroundColor(getResources().getColor(R.color.red));

                disableMatrix();

                ClearCorrectsTimer.start();

                ClearFailTimer.start();

            }
        }
        else
        {
            if (view1.getId() == ids.get(arraySequenceAdv[sequence_counter])) {
                //to phra to v se koumpi
                MaterialButton b = view.findViewById(ids.get(arraySequenceAdv[sequence_counter]));

                sequence_counter++;

                b.setBackgroundColor(getResources().getColor(R.color.green));
                b.setTextColor(getResources().getColor(R.color.black));
                b.setText(" " + sequence_counter + " ");


                if (sequence_counter == 5) {
                    hit++;
                    round++;
                    trueCounter++;
                    endspeed = new Timestamp(System.currentTimeMillis());
                    long diffspeed = endspeed.getTime() - startspeed.getTime();
                    speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);

                    passData(round, hit, miss, speedseconds, missPoints, trueCounter);
                    disableMatrix();

                    ClearCorrectsTimer.start();

                }


            } else {
                miss++;
                round++;
                missPoints = true;

                endspeed = new Timestamp(System.currentTimeMillis());
                long diffspeed = endspeed.getTime() - startspeed.getTime();
                speedseconds = TimeUnit.MILLISECONDS.toSeconds(diffspeed);
                passData(round, hit, miss, speedseconds, missPoints, trueCounter);


                FailViewId = view1.getId();

                view1.setBackgroundColor(getResources().getColor(R.color.red));

                disableMatrix();

                ClearCorrectsTimer.start();

                ClearFailTimer.start();

            }

        }

    }
}
