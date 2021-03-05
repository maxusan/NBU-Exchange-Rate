package com.bananadolphin.nbu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bananadolphin.nbu.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class CyViewActivity extends AppCompatActivity {
    private String cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_view);

        Intent intent = getIntent();
        cc = intent.getStringExtra("cc");
        Log.d("myLogs", cc);


    }

    private void getWeeklyRateByCc(String cc){


    }



}