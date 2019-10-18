package com.example.assignment1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TextView d1;
    private TextView d2;
    private EditText input;
    private TextView output;
    private TextView historyTrack;
    private SharedPreferences myPrefs;
    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        d1 = findViewById(R.id.degree);
        d2 = findViewById(R.id.degree2);
        d1.setText("Fahrenheit Degree: ");
        d2.setText("Celsius Degree: ");

        input = findViewById(R.id.d_input);
        output = findViewById(R.id.d_input2);

        historyTrack = findViewById(R.id.history_tracker);

        myPrefs = getSharedPreferences("MY_PREFS", Context.MODE_PRIVATE);
        prefsEditor = myPrefs.edit();

        TextView scroll = findViewById(R.id.history_tracker);
        scroll.setMovementMethod(new ScrollingMovementMethod());

    }

    public void rb1Clicked(View v){
        RadioButton rb = (RadioButton) findViewById(R.id.f_to_c);
        if (rb.isChecked()) {
            d1.setText("Fahrenheit Degree: ");
            d2.setText("Celsius Degree: ");
        }
    }

    public void rb2Clicked(View v) {
        RadioButton rb = (RadioButton) findViewById(R.id.c_to_f);
        if (rb.isChecked()) {
            d1.setText("Celsius Degree: ");
            d2.setText("Fahrenheit Degree: ");
        }
    }

    public void convert(View v) {
        Button bt = (Button) findViewById(R.id.convert_button);
        RadioButton rb = (RadioButton) findViewById(R.id.f_to_c);
        RadioButton rb2 = (RadioButton) findViewById(R.id.c_to_f);
        DecimalFormat df = new DecimalFormat("#.#");
        String temp1;
        String temp2;

        if (bt.isClickable()) {
            if (rb.isChecked()) {
                Double in = Double.valueOf(input.getText().toString());
                Double out = (in - 32.0) / 1.8;
                output.setText(String.valueOf(df.format(out)));
                String i = input.getText().toString();
                String o = output.getText().toString();
                temp1 = " F";
                temp2 = " C";

                String histText = historyTrack.getText().toString();
                String hist = i + temp1 + " ---> "+ o + temp2;
                historyTrack.setText(hist + "\n" + histText);

                prefsEditor.putString("DATA", i);
                prefsEditor.putString("DATA", o);
                prefsEditor.apply();

                input.setText("");
            }
            if (rb2.isChecked()) {
                Double in = Double.valueOf(input.getText().toString());
                Double out = (in * 1.8) + 32;
                output.setText(String.valueOf(df.format(out)));
                String i = input.getText().toString();
                String o = output.getText().toString();
                temp1 = " C";
                temp2 = " F";

                String histText = historyTrack.getText().toString();
                String hist = i + temp1 + " ---> "+ o + temp2;
                historyTrack.setText(hist + "\n" + histText);

                prefsEditor.putString("DATA", i);
                prefsEditor.putString("DATA", o);
                prefsEditor.apply();

                input.setText("");
            }
        }
    }

    public void cleared(View v) {
        Button cl = (Button) findViewById(R.id.button2);
        TextView ht = (TextView) findViewById(R.id.history_tracker);
        if (cl.isClickable()) {
            ht.setText("");
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("HISTORY", historyTrack.getText().toString());
        outState.putString("CONV_DEGREE", d1.getText().toString());
        outState.putString("CONV_DEGREE2", d2.getText().toString());

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        historyTrack.setText(savedInstanceState.getString("HISTORY"));
        d1.setText(savedInstanceState.getString("CONV_DEGREE"));
        d2.setText(savedInstanceState.getString("CONV_DEGREE2"));
    }
}
