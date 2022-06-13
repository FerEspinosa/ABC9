package com.latorreencantada.abc9.GameOver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Home.HomeActivity;
import com.latorreencantada.abc9.Nivel.NivelActivity;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class GameOverActivity extends AppCompatActivity implements GameOverMVP.View{

    @Inject
    GameOverMVP.Presenter presenter;

    TextView tv_congrats, tv_score, playAgain;
    Button bt_home;
    int score;
    private MediaPlayer endingTune;
    public static final String MUSIC = "music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_game_over);

        ((App) getApplication()).getComponent().injectGameOver(this);

        presenter.setView(this);

        configView();
        presenter.playEndingTune();
    }

    private void configView() {

        endingTune = MediaPlayer.create(this, R.raw.end);
        tv_congrats = findViewById(R.id.txt_congrats);

        tv_score = findViewById(R.id.score_gameover);
        score = Integer.parseInt(getIntent().getStringExtra("score"));
        tv_score.setText(String.valueOf(score));

        presenter.setFinalMessage(score);

        //botón jugar de nuevo
        playAgain = findViewById(R.id.txt_play_again);
        playAgain.setOnClickListener(view -> startActivity(new Intent(GameOverActivity.this, NivelActivity.class)));

        //boton home (es un imageView)
        bt_home = findViewById(R.id.im_goHome);
        bt_home.setOnClickListener(view -> startActivity(new Intent(GameOverActivity.this, HomeActivity.class)));
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void playEndingTune() {
        endingTune.start();
    }

    @Override
    public void setFinalMessage(String message) {
        tv_congrats.setText(message);
    }

    @Override
    protected void onStart() {
        super.onStart();

        View decorView = getWindow().getDecorView();
        int uiOptions;
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility(uiOptions);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }
}