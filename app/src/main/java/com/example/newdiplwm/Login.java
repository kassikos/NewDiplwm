package com.example.newdiplwm;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;


public class Login extends AppCompatActivity {

    private TextInputEditText Lnickname;
    private MaterialButton loginbtn;
    private MaterialButton loginRegTv;

    private UserViewModel userViewModel;


    private static final String USERID = "USERID";
    private static final String USERNAME = "USERNAME";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

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

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                User user=  userViewModel.getUserByName(Lnickname.getText().toString());

                if (user != null)
                {
                    Toast.makeText(Login.this,"welcome "+ user.getNickName(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Login.this,GameList.class);
                    Bundle extras = new Bundle();
                    extras.putInt(USERID,user.getUserId());
                    extras.putString(USERNAME,user.getNickName());
                    intent.putExtras(extras);
                    startActivity(intent);
                    Animatoo.animateSplit(Login.this);
                }
                else
                {
                    Toast.makeText(Login.this,"wrong credits",Toast.LENGTH_LONG).show();
                    Lnickname.setText("");
                }
            }
        });

    }
}