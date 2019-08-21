package com.example.newdiplwm;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Process;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class Login extends AppCompatActivity {

    private TextInputEditText Lnickname;
    private MaterialButton loginbtn;
    private MaterialButton loginRegTv;

    private Session session;
    private Intent intent;
    private UserViewModel userViewModel;


    private static final String USERID = "USERID";
    private static final String USERNAME = "USERNAME";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Context context = getApplicationContext();
        session = new Session(context);

        if (session.getRememberme())
        {
            intent = new Intent(Login.this,GameList.class);
            Toast.makeText(Login.this,"Καλώς ήρθες "+ session.getUsernameSession(),Toast.LENGTH_SHORT).show();
            startActivity(intent);
            Animatoo.animateSplit(Login.this);
        }

        Lnickname = (TextInputEditText) findViewById(R.id.TextInputEditText);
        loginbtn = (MaterialButton) findViewById(R.id.material_text_button);
        loginRegTv = (MaterialButton) findViewById(R.id.btnText);

        loginRegTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });//mporei na mpei kai h ontouck na dw pio einai kalitero

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        intent = getIntent();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=  userViewModel.getUserByName(Lnickname.getText().toString());

                if (user != null)
                {
                    session.setUsernameSession(user.getNickName());
                    session.setUserIdSession(user.getUserId());
                    Toast.makeText(Login.this,"Καλώς ήρθες "+ user.getNickName(),Toast.LENGTH_SHORT).show();
                    intent = new Intent(Login.this,GameList.class);

                    startActivity(intent);
                    Animatoo.animateSplit(Login.this);
                }
                else
                {
                    Toast.makeText(Login.this,"Λάθος όνομα χρήστη!",Toast.LENGTH_LONG).show();
                    Lnickname.setText("");
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
       moveTaskToBack(true);


    }


}