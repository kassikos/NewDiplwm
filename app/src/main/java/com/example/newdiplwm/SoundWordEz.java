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
import java.util.Random;

import static android.content.Context.VIBRATOR_SERVICE;

public class SoundWordEz extends Fragment implements  View.OnClickListener{
    private ImageView imgv1, imgv2, imgv3;
    private SoundWordViewModel soundWordViewModel;
    private Vibrator vibe;
    private CountDownTimer Timer;
    private OnDataPassSWEz dataPasser;

    private ArrayList<Integer> imageviews = new ArrayList<>(3);
    private ArrayList<Integer> allImages = new ArrayList<>(3);
    private ArrayList<Integer> pickedImage = new ArrayList<>(1);



    private SparseIntArray imageviewImage = new SparseIntArray(4);
    private SparseArray WordEz = new SparseArray(5);


    private ArrayList<Integer> MpSoundsEz = new ArrayList<>(3);
    private ArrayList<Integer> TzSoundsEz = new ArrayList<>(3);
    private ArrayList<Integer> TsSoundsEz = new ArrayList<>(3);
    private ArrayList<Integer> NtSoundsEz = new ArrayList<>(3);
    private ArrayList<Integer> GkSoundsEz = new ArrayList<>(3);

    private int hit = 0 , miss = 0 , trueCounter =0;
    private boolean missPoints = false;
    private Timestamp endspeed;


    private int pickedSound = 0 , limitReplay;


    private View view;

    public SoundWordEz() {}


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dataPasser = (OnDataPassSWEz) context;
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vibe = (Vibrator) getContext().getSystemService(VIBRATOR_SERVICE);
        soundWordViewModel = ViewModelProviders.of(getActivity()).get(SoundWordViewModel.class);
        limitReplay = 3;
        soundWordViewModel.setReplayLimit(limitReplay);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.sound_word_ez_grid_layout, container, false);
        assignAllButtons();
        fillListImageview();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        displayGameEz();
        unclickable();
    }

    private void displayGameEz(){
        loadMpEz();
        loadTsEz();
        loadTzEz();
        loadNtEz();
        loadGkEz();
        loadImageSoundEz();
        loadImages();
        Random rand = new Random();

        final int randpickImgSound = rand.nextInt(allImages.size());
        int randompickedimgv = rand.nextInt(imageviews.size());

        ImageView iv = view.findViewById(imageviews.get(randompickedimgv));
        iv.setImageResource(WordEz.keyAt(randpickImgSound));

        pickedImage.add(WordEz.keyAt(randpickImgSound));
        imageviewImage.put(imageviews.get(randompickedimgv), WordEz.keyAt(randpickImgSound));

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
        ArrayList<Integer> temp = (ArrayList<Integer>) WordEz.get(WordEz.keyAt(randpickImgSound));
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



    public interface OnDataPassSWEz {
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




    private void loadMpEz(){
        MpSoundsEz.add(R.raw.bezfirst);
        MpSoundsEz.add(R.raw.bezsec);
        MpSoundsEz.add(R.raw.bezthird);
    }

    private void loadTzEz(){
        TzSoundsEz.add(R.raw.tzezfirst);
        TzSoundsEz.add(R.raw.tzezsec);
        TzSoundsEz.add(R.raw.tzezthird);
    }

    private void loadTsEz(){
        TsSoundsEz.add(R.raw.tsezfirst);
        TsSoundsEz.add(R.raw.tsezsec);
        TsSoundsEz.add(R.raw.tsezthird);
    }

    private void loadNtEz(){
        NtSoundsEz.add(R.raw.dezfirst);
        NtSoundsEz.add(R.raw.dezsec);
        NtSoundsEz.add(R.raw.dezthird);
    }

    private void loadGkEz(){
        GkSoundsEz.add(R.raw.gezfirst);
        GkSoundsEz.add(R.raw.gezsecond);
        GkSoundsEz.add(R.raw.gezthird);
    }


    private void loadImages() {
        allImages.add(R.drawable.mp);
        allImages.add(R.drawable.ts);
        allImages.add(R.drawable.tz);
        allImages.add(R.drawable.nt);
        allImages.add(R.drawable.gk);
    }


    private void loadImageSoundEz(){
        WordEz.put(R.drawable.mp,MpSoundsEz);
        WordEz.put(R.drawable.tz,TzSoundsEz);
        WordEz.put(R.drawable.ts,TsSoundsEz);
        WordEz.put(R.drawable.nt,NtSoundsEz);
        WordEz.put(R.drawable.gk,GkSoundsEz);
    }



    private void assignAllButtons(){
        imgv1 = view.findViewById(R.id.imageView1SWGEz);
        imgv2 = view.findViewById(R.id.imageView2SWGEz);
        imgv3 = view.findViewById(R.id.imageView3SWGEz);

        imgv1.setOnClickListener(this);
        imgv2.setOnClickListener(this);
        imgv3.setOnClickListener(this);

    }
    private void fillListImageview() {
        imageviews.add(R.id.imageView1SWGEz);
        imageviews.add(R.id.imageView2SWGEz);
        imageviews.add(R.id.imageView3SWGEz);

    }

    public void unclickable() {
        imgv1.setClickable(false);
        imgv2.setClickable(false);
        imgv3.setClickable(false);
    }

    public void clickable() {
        imgv1.setClickable(true);
        imgv2.setClickable(true);
        imgv3.setClickable(true);
    }

}
