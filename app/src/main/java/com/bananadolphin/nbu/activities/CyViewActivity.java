package com.bananadolphin.nbu.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bananadolphin.nbu.R;
import com.bananadolphin.nbu.data.CyAdapter;
import com.bananadolphin.nbu.model.Cy;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Queue;

public class CyViewActivity extends AppCompatActivity {

    private String cc;
    private String[] days;
    private ArrayList<Cy> cyArrayList;
    private RequestQueue queue;
    private LinearLayoutManager linearLayoutManager;
    private CyAdapter cyAdapter;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cy_view);

        recyclerView = findViewById(R.id.recyclerView);

        Intent intent = getIntent();
        cc = intent.getStringExtra("cc");
        Log.d("myLogs", cc);

        queue = Volley.newRequestQueue(this);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = dateFormat.format(calendar.getTime());
            calendar.add(Calendar.DATE, -1);
        }

        try {
            getWeeklyRateByCc(cc);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void getWeeklyRateByCc(String cc) throws InterruptedException {
        cyArrayList = new ArrayList<>();

        for(int i = 0; i < 7; i++){
            String url = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?valcode=" + cc + "&date=" + days[i] + "&json";
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
                                cyAdapter = new CyAdapter(CyViewActivity.this, cyArrayList);
                                recyclerView.setAdapter(cyAdapter);
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