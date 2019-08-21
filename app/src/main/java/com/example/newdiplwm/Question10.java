package com.example.newdiplwm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.button.MaterialButton;

public class Question10 extends Fragment implements View.OnClickListener{
    private View view;
    private MaterialButton mtb1,mtb2,mtb3,mtb4,mtb5 , next;
    private int picked = -1;
    private FragmentTransaction fragmentTransaction;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.question_ten, container, false);
        assignAllbuttons();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void loadFragment() {

        final Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("ΕΞΟΔΟΣ");
        builder.setMessage("Έξοδος από την εφαρμογή;");
        builder.setPositiveButton("ΝΑΙ", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


                Intent intent = new Intent(context,GameList.class);
                startActivity(intent);

            }
        });



        builder.show();
// create a FragmentManager
        FragmentManager fm = getFragmentManager();
         fragmentTransaction = fm.beginTransaction();
// replace the FrameLayout with new Fragment
        fragmentTransaction.remove(this);
        fragmentTransaction.commit();
//        fragmentTransaction.replace(R.id.frameLayoutQuestionnaire,fragment);// edw prepei na ginei h allagh toy fragment
         // save the changes
    }

    private void assignAllbuttons(){
        mtb1 = view.findViewById(R.id.firstB);
        mtb2 = view.findViewById(R.id.secB);
        mtb3 = view.findViewById(R.id.thirdB);
        mtb4 = view.findViewById(R.id.fourthB);
        mtb5 = view.findViewById(R.id.fifthB);
        next = view.findViewById(R.id.nextQ);

        mtb1.setOnClickListener(this);
        mtb2.setOnClickListener(this);
        mtb3.setOnClickListener(this);
        mtb4.setOnClickListener(this);
        mtb5.setOnClickListener(this);
    }

    private void clear(){
        mtb1.setBackgroundColor(0);
        mtb2.setBackgroundColor(0);
        mtb3.setBackgroundColor(0);
        mtb4.setBackgroundColor(0);
        mtb5.setBackgroundColor(0);
    }

    @Override
    public void onResume() {
        super.onResume();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picked != -1)
                {

                    loadFragment();
                }
                else
                {
                    Toast.makeText(getContext(),"Παρακαλω επέλεξε μια βαθμολογία στην κλίμακα",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view1) {
        picked = view1.getId();
        clear();
        view1.setBackgroundColor(getResources().getColor(R.color.gray));
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
