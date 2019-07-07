package com.example.newdiplwm;


import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class Register extends AppCompatActivity {


    private MaterialButton Rbtn;
    private TextInputEditText nickname;
    private TextInputEditText birthdate;
    private RadioGroup radioSexGroup;
    private RadioButton radioSexButton;
    private boolean gender;
    private String education;
    private DatePickerDialog.OnDateSetListener datePickerDialog;
    private AutoCompleteTextView editTextFilledExposedDropdown;

    private UserViewModel userViewModel;
    private GameViewModel gameViewModel;
    Timestamp userdate;
    String[] eduTable = new String[] {"Καμία","Δημοτικό", "Γυμνάσιο", "Λύκειο", "Πανεπιστήμιο"};

    private static final String USERNAME = "USERNAME";
    private static final String BIRTHDATE = "BIRTHDATE";
    private static final String EDUCATION = "EDUCATION";
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);



        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
        nickname = (TextInputEditText) findViewById(R.id.RnicknameEditText);
        birthdate = (TextInputEditText) findViewById(R.id.RbirthdateEditText);
        Rbtn = (MaterialButton) findViewById(R.id.materialBtnRegister);

        if (savedInstanceState != null) {
            nickname.setText(savedInstanceState.getString(USERNAME));
            if (savedInstanceState.getString(BIRTHDATE) != null)
                userdate=Timestamp.valueOf(savedInstanceState.getString(BIRTHDATE));
            education = savedInstanceState.getString(EDUCATION);
        }

        editTextFilledExposedDropdown = (AutoCompleteTextView) findViewById(R.id.filled_exposed_dropdown);


        adapter =
                new ArrayAdapter<>(Register.this, R.layout.dropdown_popup_item, eduTable);


        editTextFilledExposedDropdown.setAdapter(adapter);
        editTextFilledExposedDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                education = adapter.getItem(position).toString();

            }
        });

        radioSexGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioSexButton  = (RadioButton)findViewById(checkedId);
                String s = radioSexButton.getText().toString();
                if (getResources().getString(R.string.radioF).equals(s)) {
                    gender = false;

                } else if (getResources().getString(R.string.radioM).equals(s)) {
                    gender = true;


                } else {
                    gender = true;
                }

            }
        });

        birthdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day  = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Register.this
                        ,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datePickerDialog,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month++;

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

                String Dates = year + "-" +(month<10?("0"+month):(month)) + "-" + (dayOfMonth<10?("0"+dayOfMonth):(dayOfMonth))+" 00:00:00";
                String DatesT = year + "-" +(month<10?("0"+month):(month)) + "-" + (dayOfMonth<10?("0"+dayOfMonth):(dayOfMonth));
                userdate = Timestamp.valueOf(Dates);
                birthdate.setText(DatesT);
                birthdate.setFocusable(false);
                birthdate.setClickable(true);


            }
        };


        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        gameViewModel = ViewModelProviders.of(this).get(GameViewModel.class);




//        appDatabase = Room.databaseBuilder(getApplicationContext(),
//               // AppDatabase.class, "myDB").allowMainThreadQueries().fallbackToDestructiveMigration().build();
//                AppDatabase.class, "myDB").allowMainThreadQueries().build();


        Rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Context context = Register.this;
                User checkUserIfExists = userViewModel.getUserByName(nickname.getText().toString());

                if (checkUserIfExists != null)
                {
                    nickname.setText("");
                    Toast.makeText(context,"The Nickname exists",Toast.LENGTH_LONG).show();

                }
                else {
                    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                    User user = new User(nickname.getText().toString(),userdate, gender, education);
                    userViewModel.insert(user);

                    User var = userViewModel.getUserByName(nickname.getText().toString());


                    List<Game> gamelist = gameViewModel.loadAllGames();
                    userViewModel.insertStatistics(StatisticHelper.createStatisticInstances(var.getUserId(), gamelist));

                    Toast.makeText(context, "egine", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                }
            }
        });


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(USERNAME,nickname.getText().toString());
        if (userdate != null){
            outState.putString(BIRTHDATE,userdate.toString());}
        outState.putString(EDUCATION,education);
    }
}