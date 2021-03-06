package com.bananadolphin.nbu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bananadolphin.nbu.R;
import com.bananadolphin.nbu.data.CyAdapter;
import com.bananadolphin.nbu.model.Cy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class DollarRateByYearActivity extends AppCompatActivity {

    private String[] years;
    private ArrayList<Cy> cyArrayList;
    private CyAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dollar_rate_by_year);

        recyclerView = findViewById(R.id.recyclerView);

        queue = Volley.newRequestQueue(this);

        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        years = new String[10];
        for (int i = 0; i < years.length; i++) {
            years[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, -365);
        }
        try {
            getDollarRateFor10Years(years);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getDollarRateFor10Years(String[] years) throws InterruptedException{
        cyArrayList = new ArrayList<>();
        for (int i = 0; i < years.length; i++) {
            String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=USD&date=" + years[i] + "&json";

            Log.d("myLogs", url);
            JsonArrayRequest arrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                            try {
                                JSONObject object = response.getJSONObject(0);
                                String currencyName = object.getString("txt");
                                String rate = object.getString("rate");
                                String cc = object.getString("cc");
                                String exchangeDate = object.getString("exchangedate");

                                Cy cy = new Cy();
                                cy.setCurrencyName(currencyName);
                                cy.setRate(rate);
                                cy.setCc(cc);
                                cy.setExchangeDate(exchangeDate);


                                cyArrayList.add(cy);
                                Log.d("myLogs", cy.toString());
                                adapter = new CyAdapter(DollarRateByYearActivity.this, cyArrayList);
                                recyclerView.setAdapter(adapter);
                            }

                            catch (JSONException e) {
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
                Thread.sleep(125);

        }

    }
}