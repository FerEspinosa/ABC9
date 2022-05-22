package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.latorreencantada.abc9.Nivel.NivelActivity;

public class Pantalla_Game_Over extends AppCompatActivity {

    TextView tv_congrats, tv_score, playAgain;
    ImageView bt_home;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_game_over);

        configView();
        mp.start();
    }

    private void configView() {

        mp = MediaPlayer.create(this, R.raw.end);
        tv_congrats = findViewById(R.id.txt_congrats);
        String congrats = "MUY BIEN "+getIntent().getStringExtra("jugador");
        tv_congrats.setText(congrats);

        tv_score = findViewById(R.id.score_gameover);
        tv_score.setText(getIntent().getStringExtra("score"));

        //botón jugar de nuevo
        playAgain = findViewById(R.id.txt_play_again);
        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Pantalla_Game_Over.this, NivelActivity.class);
                intent.putExtra("nombre", getIntent().getStringExtra("jugador"));
                startActivity(intent);
            }
        });

        //boton home (es un imageView)
        bt_home = findViewById(R.id.im_goHome);
        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Pantalla_Game_Over.this, MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility(uiOptions);
    }
}