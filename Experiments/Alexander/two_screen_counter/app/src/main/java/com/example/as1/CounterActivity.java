package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    Button increaseBtn;
    Button decreaseBtn;
    Button resetBtn;
    Button backBtn;
    TextView numberTxt;

    int counter = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        increaseBtn = findViewById(R.id.increaseBtn);
        decreaseBtn = findViewById(R.id.decreaseBtn);
        resetBtn = findViewById(R.id.resetBtn);
        backBtn = findViewById(R.id.backBtn);
        numberTxt = findViewById(R.id.number);

        increaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Should now increment by multiplying itself by 2
                counter = counter * 2;
                numberTxt.setText(String.valueOf(counter));
            }
        });

        decreaseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Should now decrement by halving itself
                counter = counter / 2;
                numberTxt.setText(String.valueOf(counter));
            }
        });

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Should now reset counter
                counter = 1;
                numberTxt.setText(String.valueOf(counter));
            }
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(CounterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


    }
}