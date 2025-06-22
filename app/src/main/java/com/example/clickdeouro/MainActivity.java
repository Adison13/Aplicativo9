package com.example.clickdeouro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnIniciar, btnHistorico, btnModoDificil;
    private TextView txtRecorde;
    private SharedPreferences preferences;
    private static final String PREFS = "recordePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnIniciar = findViewById(R.id.btnIniciar);
        btnHistorico = findViewById(R.id.btnHistorico);
        btnModoDificil = findViewById(R.id.btnModoDificil);
        txtRecorde = findViewById(R.id.txtRecorde);

        preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        int recorde = preferences.getInt("recorde", 0);
        txtRecorde.setText("Recorde: " + recorde);

        btnIniciar.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("modoDificil", false);
            startActivity(intent);
        });

        btnModoDificil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            intent.putExtra("modoDificil", true);
            startActivity(intent);
        });

        btnHistorico.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HistoricoActivity.class);
            startActivity(intent);
        });
    }
}

