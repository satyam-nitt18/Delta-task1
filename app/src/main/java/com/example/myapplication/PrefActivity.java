package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PrefActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pref);




        final TextView mytv = (TextView) findViewById(R.id.textView4);
        final EditText myet = (EditText) findViewById(R.id.et4);
        Button mybt = (Button) findViewById(R.id.bt4);
        final SharedPreferences sharedPref = PrefActivity.this.getPreferences(Context.MODE_PRIVATE);

        final SharedPreferences.Editor editor = sharedPref.edit();
        int highScore1 = sharedPref.getInt(getString(R.string.saved_high_score_key), 89);
        mytv.setText(Integer.toString(highScore1));

        mybt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int newHighScore = Integer.parseInt(myet.getText().toString());
                editor.putInt(getString(R.string.saved_high_score_key), newHighScore);
                editor.commit();


                int highScore = sharedPref.getInt(getString(R.string.saved_high_score_key), 89);
                mytv.setText(Integer.toString(highScore));
            }
        });


    }
}
