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
import com.latorreencantada.abc9.MainActivity;
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
    int cardsToBeDrawn= (Global.defaultLevels.length) * Global.drawsPerLevel;
    public static final String MUSIC = "music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_game_over);

        ((App) getApplication()).getComponent().injectGameOver(this);

        configView();
        presenter.setView(this);
        presenter.playEndingTune();

    }

    private void configView() {

        endingTune = MediaPlayer.create(this, R.raw.end);
        tv_congrats = findViewById(R.id.txt_congrats);

        tv_score = findViewById(R.id.score_gameover);
        score = Integer.parseInt(getIntent().getStringExtra("score"));
        tv_score.setText(String.valueOf(score));

        setResultText();

        //botón jugar de nuevo
        playAgain = findViewById(R.id.txt_play_again);
        playAgain.setOnClickListener(view -> startActivity(new Intent(GameOverActivity.this, NivelActivity.class)));

        //boton home (es un imageView)
        bt_home = findViewById(R.id.im_goHome);
        bt_home.setOnClickListener(view -> startActivity(new Intent(GameOverActivity.this, MainActivity.class)));
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
    public void setResultText() {
        if (score<cardsToBeDrawn/10){
            tv_congrats.setText(R.string.final_message_1);
        } else if (score < cardsToBeDrawn/5) {
            tv_congrats.setText(R.string.final_message_2);
        } else if (score < cardsToBeDrawn*3/10) {
            tv_congrats.setText(R.string.final_message_3);
        } else if (score < cardsToBeDrawn*4/10) {
            tv_congrats.setText(R.string.final_message_4);
        } else if (score < cardsToBeDrawn/2) {
            tv_congrats.setText(R.string.final_message_5);
        } else if (score < cardsToBeDrawn*6/10) {
            tv_congrats.setText(R.string.final_message_6);
        } else if (score < cardsToBeDrawn*7/10) {
            tv_congrats.setText(R.string.final_message_7);
        } else if (score < cardsToBeDrawn*8/10) {
            tv_congrats.setText(R.string.final_message_8);
        } else if (score < cardsToBeDrawn*9/10) {
            tv_congrats.setText(R.string.final_message_9);
        } else if (score == cardsToBeDrawn-1) {
            tv_congrats.setText(R.string.final_message_10);
        }
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
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void onBackPressed() {
    }
}