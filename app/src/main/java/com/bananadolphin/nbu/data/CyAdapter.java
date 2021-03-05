package com.bananadolphin.nbu.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bananadolphin.nbu.R;
import com.bananadolphin.nbu.activities.CyViewActivity;
import com.bananadolphin.nbu.model.Cy;

import java.util.ArrayList;

public class CyAdapter extends RecyclerView.Adapter<CyAdapter.CurrencyViewHolder> {

    private Context context;
    private ArrayList<Cy> currencies;

    public CyAdapter(Context context, ArrayList<Cy> currencies){
        this.context = context;
        this.currencies = currencies;
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vallet_item, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Cy currentCy = currencies.get(position);

        String currencyName = currentCy.getCurrencyName();
        String rate = currentCy.getRate();
        String cc = currentCy.getCc();

        holder.currencyNameTextView.setText(currencyName);
        holder.rateNameTextView.setText(rate + " UAH");
        holder.ccNameTextView.setText(cc);

    }

    @Override
    public int getItemCount() {
        return currencies.size();
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView currencyNameTextView;
        TextView rateNameTextView;
        TextView ccNameTextView;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTextView = itemView.findViewById(R.id.currencyNameTextView);
            rateNameTextView = itemView.findViewById(R.id.rateTextView);
            ccNameTextView = itemView.findViewById(R.id.ccTextView);
            itemView.setOnClickListener(this);
            Log.d("123", "123");
        }

        @Override
        public void onClick(View v) {

            int position = getAdapterPosition();
            Cy cy = currencies.get(position);
            Intent intent = new Intent(context, CyViewActivity.class);
            intent.putExtra("cc", cy.getCc());
            context.startActivity(intent);

        }
    }

}
