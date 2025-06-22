package com.example.clickdeouro;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoricoActivity extends AppCompatActivity {

    private LinearLayout layoutHistorico;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        layoutHistorico = findViewById(R.id.layoutHistorico);
        db = new DatabaseHelper(this);

        List<Jogada> jogadas = db.getUltimasJogadas(10);

        for (Jogada jogada : jogadas) {
            TextView txt = new TextView(this);
            String texto = "Data: " + jogada.getData() +
                    " | Pontos: " + jogada.getPontuacao();

            txt.setText(texto);
            txt.setTextSize(18f);
            txt.setPadding(10, 10, 10, 10);

            // Se foi recorde (TOP 1 na época da jogada), deixa o texto dourado
            if (jogada.getFoiRecorde() == 1) {
                txt.setTextColor(Color.parseColor("#FFD700"));  // Dourado
            } else {
                txt.setTextColor(Color.BLACK);  // Preto normal
            }

            layoutHistorico.addView(txt);
        }
    }
}



