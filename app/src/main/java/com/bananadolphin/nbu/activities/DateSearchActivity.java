package com.bananadolphin.nbu.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.bananadolphin.nbu.R;

import java.util.Calendar;

public class DateSearchActivity extends AppCompatActivity implements View.OnClickListener {

    private Button searchButton;
    private DatePicker datePicker;
    private String mYear = "";
    private String mMonth = "";
    private String mDayOfMonth = "";
    private String datePicked = "";
    private String formattedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_search);

        searchButton = findViewById(R.id.searchButton);
        datePicker = findViewById(R.id.datePicker);
        searchButton.setOnClickListener(this);

        Calendar today = Calendar.getInstance();

        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear += 1;
                        if(monthOfYear < 10){
                            mMonth = "0" + String.valueOf(monthOfYear);
                        }else{
                            mMonth = String.valueOf(monthOfYear);
                        }
                        if(dayOfMonth < 10){
                            mDayOfMonth = "0" + String.valueOf(dayOfMonth);
                        }else{
                            mDayOfMonth = String.valueOf(dayOfMonth);
                        }
                        mYear = String.valueOf(year);
                        datePicked = mYear + mMonth + mDayOfMonth;
                        formattedDate = mDayOfMonth + "." + mMonth + "." + mYear;
                    }
                }
        );

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.putExtra("datePicked", datePicked);
        intent.putExtra("formattedDate", formattedDate);
        setResult(RESULT_OK, intent);
        finish();
    }
}