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

public class SoundWordMed extends Fragment implements View.OnClickListener{

    private ImageView imgv1, imgv2, imgv3 , imgv4;
    private SoundWordViewModel soundWordViewModel;
    private Vibrator vibe;
    private View view;
    private CountDownTimer Timer;

    private OnDataPassSWMed dataPasser;

    private SparseArray WordMed = new SparseArray();

    private ArrayList<Integer> MpSoundsMed = new ArrayList<>(3);
    private ArrayList<Integer> TzSoundsMed = new ArrayList<>(3);
    private ArrayList<Integer> TsSoundsMed = new ArrayList<>(3);
    private ArrayList<Integer> NtSoundsMed = new ArrayList<>(3);
    private ArrayList<Integer> GkSoundsMed = new ArrayList<>(3);


    private ArrayList<Integer> imageviews = new ArrayList<>(4);
    private ArrayList<Integer> allImages = new ArrayList<>(4);
    private ArrayList<Integer> pickedImage = new ArrayList<>(1);

    private SparseIntArray imageviewImage = new SparseIntArray(4);

    private int pickedSound;
    private int hit = 0 , miss = 0 , trueCounter =0;
    private boolean missPoints = false;
    private int limitReplay = 0;
    private Timestamp endspeed;


    public SoundWordMed() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassSWMed) context;
        setRetainInstance(true);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibe = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        soundWordViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(SoundWordViewModel.class);
        limitReplay = 3;
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
        displayGameMedium();

    }

    private void displayGameMedium(){
        loadMpMed();
        loadTsMed();
        loadTzMed();
        loadNtMed();
        loadGkMed();
        loadImageSoundMed();
        loadImages();
        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(allImages.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = view.findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(WordMed.keyAt(randpickImgSound));

        pickedImage.add(WordMed.keyAt(randpickImgSound));
        imageviewImage.put(imageviews.get(randompickedimgv), WordMed.keyAt(randpickImgSound));

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
        ArrayList<Integer> temp = (ArrayList<Integer>) WordMed.get(WordMed.keyAt(randpickImgSound));
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

    private void loadMpMed(){
        MpSoundsMed.add(R.raw.bmedfirst);
        MpSoundsMed.add(R.raw.bmedsec);
        MpSoundsMed.add(R.raw.bmedthird);
    }

    private void loadTzMed(){
        TzSoundsMed.add(R.raw.tzmedfirst);
        TzSoundsMed.add(R.raw.tzmedsec);
        TzSoundsMed.add(R.raw.tzmedthird);
    }

    private void loadTsMed(){
        TsSoundsMed.add(R.raw.tsmedfirst);
        TsSoundsMed.add(R.raw.tsmedsec);
        TsSoundsMed.add(R.raw.tsmedthird);
    }

    private void loadNtMed(){
        NtSoundsMed.add(R.raw.dmedfirst);
        NtSoundsMed.add(R.raw.dmedsec);
        NtSoundsMed.add(R.raw.dmedthird);
    }

    private void loadGkMed(){
        GkSoundsMed.add(R.raw.gmedfirst);
        GkSoundsMed.add(R.raw.gmedsec);
        GkSoundsMed.add(R.raw.gmedthird);
    }

    private void loadImageSoundMed(){
        WordMed.put(R.drawable.mp,MpSoundsMed);
        WordMed.put(R.drawable.tz,TzSoundsMed);
        WordMed.put(R.drawable.ts,TsSoundsMed);
        WordMed.put(R.drawable.nt,NtSoundsMed);
        WordMed.put(R.drawable.gk,GkSoundsMed);
    }

    private void loadImages() {
        allImages.add(R.drawable.mp);
        allImages.add(R.drawable.ts);
        allImages.add(R.drawable.tz);
        allImages.add(R.drawable.nt);
        allImages.add(R.drawable.gk);
    }

    private void fillListImageview() {
        imageviews.add(R.id.imageView1SWGMed);
        imageviews.add(R.id.imageView2SWGMed);
        imageviews.add(R.id.imageView3SWGMed);
        imageviews.add(R.id.imageView4SWGMed);

    }

    @Override
    public void onClick(View view1) {


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

    public interface OnDataPassSWMed {
        public void onDataPass(int hit,int miss,long speedinSeconds,boolean misspoints,int truecounter);
    }

    private void passData(int b,int c,long d,boolean e,int f) {
        dataPasser.onDataPass(b,c,d,e,f);
    }
}
