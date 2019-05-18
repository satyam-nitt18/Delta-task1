package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;
import java.lang.String;

import static java.lang.Math.abs;

public class MainActivity extends AppCompatActivity {
    Random rand=new Random();
    int no;
    int age=rand.nextInt(100);
    EditText editText;
    Button guessButton, resButton;
    ConstraintLayout rLayout;
    int attempt=1;
    databaseHelper gamedb;
    EditText lTxt, wTxt;


    public void showToast(String s)
    {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public void viewData() {
        Cursor data = gamedb.showData();
        wTxt.setText(data.getColumnIndex("Win")+" times");
        lTxt.setText(data.getColumnIndex("Loss")+" times");
    }

    public void upgradeData() {
        boolean update;
        if(attempt==10)
            update=gamedb.upgradeData(1,0);
        else if(attempt==20)
            update=gamedb.upgradeData(0,1);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gamedb = new databaseHelper(this);

        wTxt = (EditText) findViewById(R.id.winTxt);
        lTxt = (EditText) findViewById(R.id.lossTxt);

        final NumberPicker numberPicker = (NumberPicker) findViewById(R.id.plain_text_input);
        if (numberPicker != null) {
            numberPicker.setMinValue(1);
            numberPicker.setMaxValue(100);
            numberPicker.setWrapSelectorWheel(true);
            numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    // String text = "Changed from " + oldVal + " to " + newVal;
                }
            });
        }

        rLayout = (ConstraintLayout) findViewById(R.id.rLayout);
        final Button guessButton = (Button) findViewById(R.id.start);
        Button dispButton = (Button) findViewById(R.id.button);
        resButton = (Button) findViewById(R.id.resetButton);
         viewData();

        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                no = Integer.valueOf(numberPicker.getValue());

                int pos = (abs(age - no) / 10);
                String[] redShades = getResources().getStringArray(R.array.red);
                String[] blueShades = getResources().getStringArray(R.array.blue);
                //{"#BBDEFB", "#90CAF9", "#64B5F6", "#42A5F5", "#2196F3", "#1E88E5", "#1976D2", "#1565C0", "#0D47A1"}
                //{"#FFCDD2", "#EF9A9A", "#E57373", "#EF5350", "#F44336", "#E53935", "#D32F2F", "#C62828", "#B71C1C"}
                if (attempt <= 5) {
                    if (age > no) {
                        showToast("Guess higher");

                        rLayout.setBackgroundColor(Color.parseColor(redShades[pos]));
                        attempt++;

                    } else if (age < no) {
                        showToast("Guess lower");

                        rLayout.setBackgroundColor(Color.parseColor(blueShades[pos]));
                        attempt++;
                    } else {
                        showToast("You guessed right");
                        rLayout.setBackgroundColor(Color.GREEN);
                        attempt = 10;
                    }

                } else {
                    showToast("You lost");
                    attempt = 20;
                    guessButton.setActivated(false);


                }
            }
        });

        if (attempt == 10)
            showToast("You won");


        dispButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(String.valueOf(age));
            }
        });

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                age = rand.nextInt(100);
                rLayout.setBackgroundColor(Color.WHITE);
                upgradeData();
                viewData();
            }
        });

    }



}