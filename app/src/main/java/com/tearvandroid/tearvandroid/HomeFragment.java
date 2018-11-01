package com.tearvandroid.tearvandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

    Button upArrow;
    Button downArrow;
    Button leftArrow;
    Button rightArrow;
    TextView textToChange;
    TextView tempTextView;
    TextView humidityTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        upArrow = (Button) view.findViewById(R.id.arrow_up);
        downArrow = (Button) view.findViewById(R.id.arrow_down);
        leftArrow = (Button) view.findViewById(R.id.arrow_left);
        rightArrow = (Button) view.findViewById(R.id.arrow_right);
        textToChange = (TextView) view.findViewById(R.id.txt);
        tempTextView = (TextView) view.findViewById(R.id.tempValue);
        humidityTextView = (TextView) view.findViewById(R.id.humidityValue);

        // the commented code below is the test to check if the temperature color changes if the value changes

//        new CountDownTimer(30000, 1000){
//            public void onTick(long millisUntilFinished) {
//                tempTextView.setText(""+(millisUntilFinished/1000 + 25));
//                humidityTextView.setText("" + (millisUntilFinished/1000 + 70));
//                //here you can have your logic to set text to edittext
//            }
//
//            public void onFinish() {
//                tempTextView.setText("40");
//            }
//        }.start();

        // Listener for when there is a change in the temp value
        tempTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int tempValue = Integer.parseInt(tempTextView.getText().toString());

                if (tempValue > 40) {
                    tempTextView.setTextColor(Color.RED);
                } else {
                    tempTextView.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Listener for when there is a change in the humidity value
        humidityTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                int humidityValue = Integer.parseInt(humidityTextView.getText().toString());
                if (humidityValue > 80) {
                    humidityTextView.setTextColor(Color.RED);
                } else {
                    humidityTextView.setTextColor(Color.GREEN);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        upArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToChange.setText("Moved Up");
            }
        });

        downArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToChange.setText("Moved Down");
            }
        });

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToChange.setText("Moved Left");
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToChange.setText("Moved Right");
            }
        });

        return view;
    }
}
