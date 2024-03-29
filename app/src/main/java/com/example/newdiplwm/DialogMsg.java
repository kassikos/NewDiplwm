package com.example.newdiplwm;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class DialogMsg extends DialogFragment {
    private int userId;
    private Context context;
    private int hit;
    private int totalPoints;

    private TextView textView;
    private TextView textViewPoints;
    private ImageView imageView;


    public DialogMsg(int userId,Context context) {
        this.context =context;
        this.userId = userId;
    }

    public DialogMsg(int userId, Context context, int hit) {
        this.userId = userId;
        this.context = context;
        this.hit = hit;
    }

    public DialogMsg(int userId, Context context, int hit, int totalPoints) {
        this.userId = userId;
        this.context = context;
        this.hit = hit;
        this.totalPoints = totalPoints;
    }

    public DialogMsg() { }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater factory = requireActivity().getLayoutInflater();
        final View content = factory.inflate(R.layout.custom_popup, null);
        textView = (TextView) content.findViewById(R.id.textCustomPopUp);
        imageView = (ImageView) content.findViewById(R.id.imageViewCustomPopUp);
        textViewPoints = (TextView) content.findViewById(R.id.pointsPopUp);
        fillViews();
        builder.setView(content)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,int id) {
                        Intent intent = new Intent(context,GameList.class);
                        startActivity(intent);
                    }
                });


        final AlertDialog dialog = builder.create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                Button btnPositive = dialog.getButton(Dialog.BUTTON_POSITIVE);
                btnPositive.setTextSize(24);
                btnPositive.setTextColor(getResources().getColor(R.color.black));

            }
        });

        return dialog;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        Intent intent = new Intent(context,GameList.class);
        startActivity(intent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow()
                .getAttributes().windowAnimations = R.style.DialogAnimation;
    }

    private void fillViews() {
        if (hit <=1)
        {
            textView.setText(R.string.bronze);
            imageView.setImageResource(R.drawable.trophy_bronze);

        }
        else if (hit == 2 )
        {
            textView.setText(R.string.silver);
            imageView.setImageResource(R.drawable.trophy_silver);
        }
        else
        {
            textView.setText(R.string.gold);
            imageView.setImageResource(R.drawable.trophy_gold);

        }
        textViewPoints.setText("Συγκέντρωσες " +totalPoints+ " πόντους!" );
    }
}

