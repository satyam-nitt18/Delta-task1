package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    int attempt=0;

    TextView lossTxt, winTxt;
    SharedPreferences sharedpreferences;
    public static final String mypreference= "myPrefFile";
    public static final String win="winKey";
    public static final String loss="lossKey";


    ;

    public void showToast(String s)
    {
        Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();
    }

    public int gameReset()
    {
        age = rand.nextInt(100);
        rLayout.setBackgroundColor(Color.WHITE);
        winTxt.setText(Integer.toString(sharedpreferences.getInt(win, 0)));
        lossTxt.setText(Integer.toString(sharedpreferences.getInt(loss, 0)));

        return 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Intent intent = new Intent(this, PrefActivity.class);
        //startActivity(intent);

        sharedpreferences= getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor=sharedpreferences.edit();

        winTxt = (TextView) findViewById(R.id.winTxt);
        lossTxt = (TextView) findViewById(R.id.lossTxt);
        winTxt.setText(Integer.toString(sharedpreferences.getInt(win, 0)));

        lossTxt.setText(Integer.toString(sharedpreferences.getInt(loss,0)));

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



        guessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                no = Integer.valueOf(numberPicker.getValue());


                int pos = (abs(age - no) / 10);
                String[] redShades = getResources().getStringArray(R.array.red);
                String[] blueShades = getResources().getStringArray(R.array.blue);
                //{"#BBDEFB", "#90CAF9", "#64B5F6", "#42A5F5", "#2196F3", "#1E88E5", "#1976D2", "#1565C0", "#0D47A1"}
                //{"#FFCDD2", "#EF9A9A", "#E57373", "#EF5350", "#F44336", "#E53935", "#D32F2F", "#C62828", "#B71C1C"}
                if (attempt <= 4) {
                    if (age > no) {
                        showToast("Guess higher");

                        rLayout.setBackgroundColor(Color.parseColor(redShades[pos]));
                        attempt++;

                    } else if (age < no) {
                        showToast("Guess lower");

                        rLayout.setBackgroundColor(Color.parseColor(blueShades[pos]));
                        attempt++;
                    } else {
                        showToast("You guessed right \n You won");
                        int w=Integer.valueOf((sharedpreferences.getInt(win, 0)));
                        editor.putInt(win, w + 1);
                        editor.commit();
                        attempt=gameReset();
                    }

                } else {
                    showToast("You lost");
                    guessButton.setActivated(false);
                    int l=Integer.valueOf(sharedpreferences.getInt(loss, 0));
                    editor.putInt(loss, l + 1);
                    editor.commit();
                    attempt=gameReset();
                }
            }
        });

        resButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attempt=gameReset();
            }
        });

        dispButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(String.valueOf(age));
            }
        });

    }



}