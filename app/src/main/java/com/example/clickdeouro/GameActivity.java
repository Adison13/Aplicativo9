package com.example.clickdeouro;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class GameActivity extends AppCompatActivity {

    private TextView txtPontuacao, txtTempo;
    private Button btnClick;
    private int pontuacao = 0;
    private int tempoRestante;
    private int tempoTotal = 30;
    private SharedPreferences preferences;
    private static final String PREFS = "recordePrefs";
    private CountDownTimer timer;
    private MediaPlayer somClique;
    private DatabaseHelper db;

    @Override
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Sair do Jogo")
                .setMessage("Tem certeza que deseja sair?")
                .setPositiveButton("Sim", (dialog, which) -> finish())
                .setNegativeButton("Não", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        txtPontuacao = findViewById(R.id.txtPontuacao);
        txtTempo = findViewById(R.id.txtTempo);
        btnClick = findViewById(R.id.btnClick);

        preferences = getSharedPreferences(PREFS, MODE_PRIVATE);
        db = new DatabaseHelper(this);
        somClique = MediaPlayer.create(this, R.raw.click_sound);

        boolean modoDificil = getIntent().getBooleanExtra("modoDificil", false);
        if (modoDificil) {
            tempoTotal = 15;
        }

        btnClick.setOnClickListener(v -> {
            pontuacao++;
            txtPontuacao.setText("Pontos: " + pontuacao);
            somClique.start();
            animarBotao();
        });

        iniciarTimer();
    }

    private void animarBotao() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_fade);
        btnClick.startAnimation(anim);
    }

    private void iniciarTimer() {
        tempoRestante = tempoTotal;

        timer = new CountDownTimer(tempoTotal * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tempoRestante--;
                txtTempo.setText("Tempo: " + tempoRestante + "s");
            }

            @Override
            public void onFinish() {
                verificarRecorde();
            }
        }.start();
    }

    private void verificarRecorde() {
        int recordeAnterior = preferences.getInt("recorde", 0);
        boolean novoRecorde = false;

        if (pontuacao > recordeAnterior) {
            preferences.edit().putInt("recorde", pontuacao).apply();
            novoRecorde = true;
        }

        salvarNoBanco(novoRecorde);
        mostrarDialogGameOver(novoRecorde);
    }

    private void mostrarDialogGameOver(boolean novoRecorde) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_game_over_dialog);

        Window window = dialog.getWindow();
        if (window != null) {
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            WindowManager.LayoutParams params = window.getAttributes();
            params.gravity = Gravity.BOTTOM;
            window.setAttributes(params);
        }

        TextView txtResultado = dialog.findViewById(R.id.txtResultado);
        String mensagem = "Sua Pontuação: " + pontuacao;
        if (novoRecorde) {
            mensagem += "\n🏆 Novo Recorde! 🏆";
        }
        txtResultado.setText(mensagem);

        Button btnJogarNovamente = dialog.findViewById(R.id.btnJogarNovamente);
        Button btnVoltarMenu = dialog.findViewById(R.id.btnVoltarMenu);

        btnJogarNovamente.setOnClickListener(v -> {
            dialog.dismiss();
            recreate();
        });

        btnVoltarMenu.setOnClickListener(v -> {
            dialog.dismiss();
            finish();
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    private void salvarNoBanco(boolean foiRecorde) {
        String data = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
        db.inserirJogada(data, pontuacao, tempoTotal, foiRecorde ? 1 : 0);
    }
}






