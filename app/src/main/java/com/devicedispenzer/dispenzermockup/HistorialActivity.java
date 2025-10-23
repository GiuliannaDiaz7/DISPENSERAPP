package com.devicedispenzer.dispenzermockup;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.List;


public class HistorialActivity extends AppCompatActivity {

    private LinearLayout historialContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        historialContainer = findViewById(R.id.historialContainer);

        mostrarRegistros();
    }

    private void mostrarRegistros() {
        historialContainer.removeAllViews();
        List<String> registros = HistorialData.getInstance().getRegistros();
        for (String r : registros) {
            CardView card = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 8, 0, 8);
            card.setLayoutParams(cardParams);
            card.setRadius(16);
            card.setCardBackgroundColor(Color.parseColor("#F5F5F5"));
            card.setElevation(8);

            TextView tv = new TextView(this);
            tv.setText(r);
            tv.setTextColor(Color.BLACK);
            tv.setTextSize(16);
            tv.setPadding(24, 24, 24, 24);

            card.addView(tv);
            historialContainer.addView(card);
        }
    }
}
