package com.example.newdiplwm;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Objects;
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;



public class SoundWordAdv extends Fragment implements  View.OnClickListener{

    private ImageView imgv1, imgv2, imgv3, imgv4;
    private SparseArray WordAdv = new SparseArray();



    private ArrayList<Integer> MpSoundsAdv = new ArrayList<>(3);
    private ArrayList<Integer> TzSoundsAdv = new ArrayList<>(3);
    private ArrayList<Integer> TsSoundsAdv = new ArrayList<>(3);
    private ArrayList<Integer> NtSoundsAdv = new ArrayList<>(3);
    private ArrayList<Integer> GkSoundsAdv = new ArrayList<>(3);

    private ArrayList<Integer> allImages = new ArrayList<>(5);
    private ArrayList<Integer> imageviews = new ArrayList<>(4);
    private ArrayList<Integer> pickedImage = new ArrayList<>(1);
    private SparseIntArray imageviewImage = new SparseIntArray(4);

    private View view;
    private SoundWordViewModel soundWordViewModel;
    private Vibrator vibe;
    private CountDownTimer Timer;

    private  OnDataPassSWAdv dataPasser;

    private int pickedSound;
    private int hit = 0 , miss = 0 , trueCounter =0;
    private boolean missPoints = false;
    private Timestamp endspeed;

    private int limitReplay = 0;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassSWAdv) context;
        setRetainInstance(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibe = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        soundWordViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SoundWordViewModel.class);
        limitReplay = 1;
        soundWordViewModel.setReplayLimit(limitReplay);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sound_word_med_grid_layout, container, false);
        assignAllButtons();
        fillListImageview();

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unclickable();
        displayGameAdv();
    }




    private void displayGameAdv(){

        loadMpAdv();
        loadTsAdv();
        loadTzAdv();
        loadNtAdv();
        loadGkAdv();
        loadImageSoundAdv();
        loadImages();
        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(allImages.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = view.findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(WordAdv.keyAt(randpickImgSound));

        pickedImage.add(WordAdv.keyAt(randpickImgSound));
        imageviewImage.put(imageviews.get(randompickedimgv), WordAdv.keyAt(randpickImgSound));

        for (int i : pickedImage) {
            ListIterator itr = allImages.listIterator();
            while (itr.hasNext()) {
                int temp = (int) itr.next();
                if (temp == i) {
                    itr.remove();
                    break;
                }
            }
        }
        ArrayList<Integer> temp = (ArrayList<Integer>) WordAdv.get(WordAdv.keyAt(randpickImgSound));
        pickedSound = temp.get(rand.nextInt(temp.size()));
        soundWordViewModel.setPickedSound(pickedSound);

        imageviews.remove(randompickedimgv);

        for (int imgv : imageviews) {
            ImageView ivv = view.findViewById(imgv);

            int i = rand.nextInt(allImages.size());

            ivv.setImageResource(allImages.get(i));
            imageviewImage.put(imgv, allImages.get(i));
            allImages.remove(i);
        }
    }


    public void unclickable() {
        imgv1.setClickable(false);
        imgv2.setClickable(false);
        imgv3.setClickable(false);
        imgv4.setClickable(false);
    }

    public void clickable() {
        imgv1.setClickable(true);
        imgv2.setClickable(true);
        imgv3.setClickable(true);
        imgv4.setClickable(true);
    }


    private void loadMpAdv(){
        MpSoundsAdv.add(R.raw.badvfirst);
        MpSoundsAdv.add(R.raw.badvsec);
        MpSoundsAdv.add(R.raw.badvthird);
    }
    private void loadTzAdv(){
        TzSoundsAdv.add(R.raw.tzadvfirst);
        TzSoundsAdv.add(R.raw.tzadvsec);
        TzSoundsAdv.add(R.raw.tzadvthird);
    }
    private void loadTsAdv(){
        TsSoundsAdv.add(R.raw.tsadvfirst);
        TsSoundsAdv.add(R.raw.tsadvsec);
        TsSoundsAdv.add(R.raw.tsadvthird);
    }

    private void loadNtAdv(){
        NtSoundsAdv.add(R.raw.dadvfirst);
        NtSoundsAdv.add(R.raw.dadvsec);
        NtSoundsAdv.add(R.raw.dadvthird);
    }

    private void loadGkAdv(){
        GkSoundsAdv.add(R.raw.gadvfirst);
        GkSoundsAdv.add(R.raw.gadvsec);
        GkSoundsAdv.add(R.raw.gadvthird);
    }
    private void loadImageSoundAdv(){
        WordAdv.put(R.drawable.mp,MpSoundsAdv);
        WordAdv.put(R.drawable.tz,TzSoundsAdv);
        WordAdv.put(R.drawable.ts,TsSoundsAdv);
        WordAdv.put(R.drawable.nt,NtSoundsAdv);
        WordAdv.put(R.drawable.gk,GkSoundsAdv);
    }


    private void loadImages() {
        allImages.add(R.drawable.mp);
        allImages.add(R.drawable.nt);
        allImages.add(R.drawable.ts);
        allImages.add(R.drawable.tz);
        allImages.add(R.drawable.gk);
    }


    private void fillListImageview() {
        imageviews.add(R.id.imageView1SWGMed);
        imageviews.add(R.id.imageView2SWGMed);
        imageviews.add(R.id.imageView3SWGMed);
        imageviews.add(R.id.imageView4SWGMed);

    }

    private void assignAllButtons(){
        imgv1 = view.findViewById(R.id.imageView1SWGMed);
        imgv2 = view.findViewById(R.id.imageView2SWGMed);
        imgv3 = view.findViewById(R.id.imageView3SWGMed);
        imgv4 = view.findViewById(R.id.imageView4SWGMed);

        imgv1.setOnClickListener(this);
        imgv2.setOnClickListener(this);
        imgv3.setOnClickListener(this);
        imgv4.setOnClickListener(this);


    }

    public interface OnDataPassSWAdv {
        public void onDataPass(int hit,int miss,long speedinSeconds,boolean misspoints,int truecounter);
    }

    private void passData(int b,int c,long d,boolean e,int f) {
        dataPasser.onDataPass(b,c,d,e,f);
    }

    @Override
    public void onClick(View view1) {
        unclickable();
        Timer = new CountDownTimer(1500, 1000) {
            @Override
            public void onTick(long l) {}

            @Override
            public void onFinish() {
                for (int imageviewId = 0; imageviewId < imageviewImage.size(); imageviewId++) {
                    ImageView v = view.findViewById(imageviewImage.keyAt(imageviewId));
                    v.setImageResource(0);
                }
                imageviewImage.clear();
                pickedImage.clear();

            }
        };
        allImages.clear();
        imageviews.clear();

        endspeed = new Timestamp(System.currentTimeMillis());

        if (!(imageviewImage.indexOfKey(view1.getId()) < 0)) {

            if (pickedImage.contains(imageviewImage.get(view1.getId()))) {
                hit++;
                trueCounter++;

            } else {

                Animation animShake = AnimationUtils.loadAnimation(getContext(), R.anim.shake);
                view1.startAnimation(animShake);
                int vibeduration = 1000;
                vibe.vibrate(vibeduration);
                miss++;
                missPoints = true;
            }

            Timer.start();
            passData(hit,miss,endspeed.getTime(),missPoints,trueCounter);
        }

    }
}
