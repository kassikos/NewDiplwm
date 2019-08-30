package com.example.newdiplwm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipDescription;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class BucketGame extends AppCompatActivity {

    private ArrayList<Integer> fish = new ArrayList<>(3);
    private ArrayList<Integer> birds = new ArrayList<>(3);
    private ArrayList<Integer> insects = new ArrayList<>(3);

    private ArrayList<ArrayList> Animals = new ArrayList<>(3);
    private ArrayList<ArrayList> food = new ArrayList<>(3);
    private ArrayList<ArrayList> clothes = new ArrayList<>(3);

    private SparseArray GeneralCategories = new SparseArray(3);
    private SparseArray bucketfish = new SparseArray(3);

    ImageView img;
    String msg;
    private android.widget.RelativeLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_game);

        img=(ImageView)findViewById(R.id.imageView);


    }


    //--------------------------------------------------------
//
//
//
//    package com.example.myapplication;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.ClipData;
//import android.content.ClipDescription;
//import android.content.Intent;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.util.Log;
//import android.util.SparseArray;
//import android.util.SparseIntArray;
//import android.view.DragEvent;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.RelativeLayout;
//
//import java.util.ArrayList;
//
//    public class MainActivity extends AppCompatActivity {
//
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//            findViewById(R.id.myimage1).setOnTouchListener(new MyTouchListener());
//            findViewById(R.id.myimage2).setOnTouchListener(new MyTouchListener());
//            findViewById(R.id.myimage3).setOnTouchListener(new MyTouchListener());
//            findViewById(R.id.myimage4).setOnTouchListener(new MyTouchListener());
//
//
//            findViewById(R.id.topleft).setOnDragListener(new MyDragListener());
//            findViewById(R.id.topright).setOnDragListener(new MyDragListener());
//            findViewById(R.id.bottomleft).setOnDragListener(new MyDragListener());
//            findViewById(R.id.bottomright).setOnDragListener(new MyDragListener());
//
//        }
//
//        private final class MyTouchListener implements View.OnTouchListener {
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
//                    ClipData data = ClipData.newPlainText("eeeeeee", "gdfgdf");
//                    View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
//                            view);
//                    view.startDrag(data, shadowBuilder, view, 0);
//                    view.setVisibility(View.INVISIBLE);
//                    view.setEnabled(false);
//                    return true;
//                } else {
//                    return false;
//                }
//            }
//        }
//
//        class MyDragListener implements View.OnDragListener {
//            Drawable enterShape = getResources().getDrawable(R.drawable.shape_droptarget);
//
//            Drawable normalShape = getResources().getDrawable(R.drawable.shape);
//
//            @Override
//            public boolean onDrag(View v, DragEvent event) {
//                int action = event.getAction();
//                switch (event.getAction()) {
//                    case DragEvent.ACTION_DRAG_STARTED:
//                        Log.d("ACTION_DRAG_STARTED","ACTION_DRAG_STARTED");
//                        break;
//                    case DragEvent.ACTION_DRAG_ENTERED:
//                        Log.d("ACTION_DRAG_ENTERED","ACTION_DRAG_ENTERED");
//                        v.setBackgroundDrawable(enterShape);
//                        break;
//                    case DragEvent.ACTION_DRAG_EXITED:
//                        Log.d("ACTION_DRAG_EXITED","ACTION_DRAG_EXITED");
//                        v.setBackgroundDrawable(normalShape);
//                        break;
//                    case DragEvent.ACTION_DROP:
//                        Log.d("ACTION_DROP","ACTION_DROP");
//                        // Dropped, reassign View to ViewGroup
//                        View view = (View) event.getLocalState();
//                        ViewGroup owner = (ViewGroup) view.getParent();
//                        owner.removeView(view);
//                        LinearLayout container = (LinearLayout) v;
//                        container.addView(view);
//                        view.setVisibility(View.VISIBLE);
//                        break;
//                    case DragEvent.ACTION_DRAG_ENDED:
//                        Log.d("ACTION_DRAG_ENDED","ACTION_DRAG_ENDED");
//                        v.setBackgroundDrawable(normalShape);
//
////                    image_view.getLayoutParams().height = 20;
//                    default:
//                        break;
//                }
//                return true;
//            }
//        }
//    }

    //--------------------------------------------------------

//    private void loadAnimalImages(){
//        fish.add(R.drawable.scissors);
//        fish.add(R.drawable.paper);
//        fish.add(R.drawable.rock);
//
//        birds.add(R.drawable.og_e_mouse);
//        birds.add(R.drawable.og_e_keyboard);
//        birds.add(R.drawable.og_e_speakers);
//
//        Animals.add(fish);
//        Animals.add(birds);
//    }
//
//    private void loadGeneralCategories(){
//        GeneralCategories.put(R.drawable.arrowdown24,Animals);
//        GeneralCategories.put(R.drawable.calendar24,food);
//        GeneralCategories.put(R.drawable.baseline_feedback_black_24dp,clothes);
//    }
//
//
//    private void loadAnimalsSubCategoriesBucket(){
//        ArrayList<Integer> fish1 = (ArrayList<Integer>) GeneralCategories.valueAt(0);
//        ArrayList<Integer> birds1 = (ArrayList<Integer>) GeneralCategories.valueAt(1);
//        ArrayList<Integer> insects1 = (ArrayList<Integer>) GeneralCategories.valueAt(2);
//        bucketfish.put(R.drawable.arrowdown24,fish1);
//        bucketfish.put(R.drawable.calendar24,birds1);
//        bucketfish.put(R.drawable.baseline_feedback_black_24dp,insects1);
//    }


}
