package com.bananadolphin.nbu.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bananadolphin.nbu.R;
import com.bananadolphin.nbu.data.CyAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import com.bananadolphin.nbu.model.*;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<Cy> currencies;
    private RequestQueue queue;
    private CyAdapter adapter;
    private static final String LOG_TAG = "myLogs";
    private String pickedDate;
    private TextView dateTextView;

    private static final int SEARCH_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dateTextView = findViewById(R.id.dateTextView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        currencies = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        getAllCurrencies();
        //dateTextView.setText("Курс на " + pickedDate);
    }

    private void getAllCurrencies(){
        //Log.d(LOG_TAG, "getCurrencies()");
        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = response.getJSONObject(i);
                                String currencyName = object.getString("txt");
                                String rate = object.getString("rate");
                                String cc = object.getString("cc");
                                String exchangeDate = object.getString("exchangedate");

                                Cy cy = new Cy();
                                cy.setCurrencyName(currencyName);
                                cy.setRate(rate);
                                cy.setCc(cc);
                                cy.setExchangeDate(exchangeDate);

                                currencies.add(cy);
                            }

                            Log.d(LOG_TAG, currencies.toString());
                        adapter = new CyAdapter(MainActivity.this, currencies);
                        recyclerView.setAdapter(adapter);
                        }catch(JSONException e){
                                e.printStackTrace();
                            }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(arrayRequest);
    }

    private void getCurrenciesByDate(String pickedDate){

        String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?date=" +  pickedDate + "&json";

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {

                                JSONObject object = response.getJSONObject(i);
                                String currencyName = object.getString("txt");
                                String rate = object.getString("rate");
                                String cc = object.getString("cc");
                                String exchangeDate = object.getString("exchangedate");

                                Cy cy = new Cy();
                                cy.setCurrencyName(currencyName);
                                cy.setRate(rate);
                                cy.setCc(cc);
                                cy.setExchangeDate(exchangeDate);

                                currencies.add(cy);
                            }
                            Log.d(LOG_TAG, currencies.toString());
                            adapter = new CyAdapter(MainActivity.this, currencies);
                            recyclerView.setAdapter(adapter);
                        }catch(JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });


        queue.add(arrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.pickDate:
                Intent dateIntent = new Intent(this, DateSearchActivity.class);
                startActivityForResult(dateIntent, SEARCH_ACTIVITY_REQUEST_CODE);
                return true;
            case R.id.dollarRateByYear:
                Intent dollarIntent = new Intent(this, DollarRateByYearActivity.class);
                startActivity(dollarIntent);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        if(requestCode == SEARCH_ACTIVITY_REQUEST_CODE){
            clearRecyclerView();

            String datePicked = data.getStringExtra("datePicked");
            String formattedDate = data.getStringExtra("formattedDate");
            getCurrenciesByDate(datePicked);
            dateTextView.setText("Курс на " + formattedDate);
            dateTextView.setVisibility(View.VISIBLE);
        }
    }

    private void clearRecyclerView(){
        currencies.clear();
        recyclerView.setAdapter(null);
    }
}