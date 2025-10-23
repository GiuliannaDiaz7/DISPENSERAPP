package com.devicedispenzer.dispenzermockup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {

    private Button btnIdeas, btnHistorial, btnIniciar, btnVaso;
    private EditText etTamano, etBotella1, etBotella2;
    private ProgressBar progressBotella1, progressBotella2;
    private GifImageView gifDispensador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referencias de botones
        btnIdeas = findViewById(R.id.btnIdeas);
        btnHistorial = findViewById(R.id.btnHistorial);
        btnIniciar = findViewById(R.id.btnIniciar);
        btnVaso = findViewById(R.id.btnVaso);

        // Referencias EditText
        etTamano = findViewById(R.id.etTamano);
        etBotella1 = findViewById(R.id.etBotella1);
        etBotella2 = findViewById(R.id.etBotella2);
        etBotella2.setEnabled(false);

        // Referencias ProgressBar
        progressBotella1 = findViewById(R.id.progressBotella1);
        progressBotella2 = findViewById(R.id.progressBotella2);

        // Referencia Gif
        gifDispensador = findViewById(R.id.gifDispensador);
        gifDispensador.setVisibility(GifImageView.GONE);

        // Colores botones
        btnIdeas.setBackgroundColor(Color.parseColor("#2196F3"));
        btnHistorial.setBackgroundColor(Color.parseColor("#FF9800"));
        btnIniciar.setBackgroundColor(Color.parseColor("#4CAF50"));
        btnVaso.setBackgroundColor(Color.parseColor("#8A2BE2"));

        // Navegación entre pantallas
        btnIdeas.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, IdeasActivity.class))
        );

        btnHistorial.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, HistorialActivity.class))
        );

        btnVaso.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, VasosActivity.class))
        );

        // Lógica de los EditText de botellas
        etBotella1.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!TextUtils.isEmpty(s)) {
                    try {
                        int value = Integer.parseInt(s.toString());
                        if (value >= 0 && value <= 100) {
                            progressBotella1.setProgress(value);
                            int restante = 100 - value;
                            etBotella2.setText(String.valueOf(restante));
                            progressBotella2.setProgress(restante);
                        } else {
                            etBotella1.setError("Debe estar entre 0 y 100");
                            etBotella2.setText("");
                            progressBotella2.setProgress(0);
                        }
                    } catch (NumberFormatException e) {
                        etBotella1.setError("Valor inválido");
                        etBotella2.setText("");
                        progressBotella2.setProgress(0);
                    }
                } else {
                    progressBotella1.setProgress(0);
                    etBotella2.setText("");
                    progressBotella2.setProgress(0);
                }
            }

            @Override public void afterTextChanged(Editable s) {}
        });

        // Acción botón iniciar
        btnIniciar.setOnClickListener(v -> {
            String tamanoStr = etTamano.getText().toString();
            String b1Str = etBotella1.getText().toString();
            String b2Str = etBotella2.getText().toString();

            if (TextUtils.isEmpty(tamanoStr) || TextUtils.isEmpty(b1Str) || TextUtils.isEmpty(b2Str)) {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            int tamano = Integer.parseInt(tamanoStr);
            int b1 = Integer.parseInt(b1Str);
            int b2 = Integer.parseInt(b2Str);

            if (b1 + b2 != 100) {
                Toast.makeText(this, "Los porcentajes deben sumar 100%", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mostrar GIF mientras “dispensa”
            gifDispensador.setVisibility(GifImageView.VISIBLE);

            String hora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
            String registro = "Vaso: " + tamano + "ml | Botella1: " + b1 + "% | Botella2: " + b2 + "% | Hora: " + hora;
            HistorialData.getInstance().addRegistro(registro);

            // Ocultar GIF después de 2 segundos
            gifDispensador.postDelayed(() -> gifDispensador.setVisibility(GifImageView.GONE), 3000);
        });
    }
}
