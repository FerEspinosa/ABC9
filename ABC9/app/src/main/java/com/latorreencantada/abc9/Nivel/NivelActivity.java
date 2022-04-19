package com.latorreencantada.abc9.Nivel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Pantalla_Game_Over;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class NivelActivity extends AppCompatActivity implements NivelActivityMVP.View{

    @Inject
    NivelActivityMVP.Presenter presenter;

    private MediaPlayer wonderful;
    private MediaPlayer wrong;
    private MediaPlayer mp;

    private TextView tv_score;
    private TextView tv_respuesta;
    private TextView tv_nombre;

    private TextView[] textView = new TextView[4];

    private Button bt_borrar, bt_enviar, bt_musica;

    private ImageView iv_estrellas;
    private ImageView iv;

    String jugador;

    int textViewCount = 4 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);

        ((App) getApplication()).getComponent().inject(this);

        configView();
        presenter.setView(this);
        presenter.NuevaCarta();
    }

    private void configView() {

        //fuente supersonic
        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        // musica y sonidos
        wonderful = MediaPlayer.create(this, R.raw.wonderful);
        wrong = MediaPlayer.create(this, R.raw.bad);
        mp = MediaPlayer.create(this, R.raw.goats);


        if (Global.musica){
            mp.start();
            mp.setLooping(true);
        }

        // elementos gráficos
        iv_estrellas = findViewById(R.id.iv_estrellas);
        iv = findViewById(R.id.mainImage);
        tv_score = findViewById(R.id.tv_puntaje);
        tv_nombre = findViewById(R.id.tv_nombre);
        tv_respuesta = findViewById(R.id.tv_respuesta_2);
        bt_musica = findViewById(R.id.id_switch_musica);
        bt_borrar = findViewById(R.id.bt_borrar);
        bt_enviar = findViewById(R.id.bt_enviar);

        // Declarar Array de TextViews:
        for (int i=0; i<textViewCount; i++){
            textView[i]= new TextView(this);
        }

        textView[0] = findViewById(R.id.bt1);
        textView[1] = findViewById(R.id.bt2);
        textView[2] = findViewById(R.id.bt3);
        textView[3] = findViewById(R.id.bt4);

        jugador = getIntent().getStringExtra("nombre");

        tv_nombre.setText(jugador);
        tv_score.setText("0");
        tv_respuesta.setText("");


        // botones con click listeners:

        bt_musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_music_clicked(mp, view);
            }
        });

        bt_enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_enviar_clicked();
            }
        });

        bt_borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_borrar_clicked();
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

        presenter.startMusic(mp);

        View decorView = getWindow().getDecorView();
        int uiOptions = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
        }

        decorView.setSystemUiVisibility(uiOptions);

/*

        if (Global.BackgroundImage=="rojo"){
            fondo.setImageResource(R.drawable.fondo);
        } else {
            fondo.setImageResource(R.drawable.fondo_azul);
        }
*/
    }

    @Override
    protected void onPause() { super.onPause();
        presenter.pauseMusic(mp);
    }

    @Override
    public void onBackPressed() {
    }

    /////////////////////////////////////////////////////////////////////////////

    @Override
    public void buttonPress(View v) {
        presenter.sylablePressed(v);
    }

    @Override
    public void setPlayerName (String name){
        this.tv_nombre.setText(name);
    }

    @Override
    public void setScore (String score){
        this.tv_score.setText(score);
    }

    @Override
    public void setOneStar (){
        this.iv_estrellas.setImageResource(R.drawable.una_estrella);
    }

    @Override
    public void setTwoStars (){
        this.iv_estrellas.setImageResource(R.drawable.dos_estrellas);
    }

    @Override
    public void setThreeStars (){
        this.iv_estrellas.setImageResource(R.drawable.tres_estrellas);
    }

    @Override
    public void changeTvBgImagePressed (int tvNum) {
        this.textView[tvNum].setBackgroundResource(R.drawable.silaba_presionada);
    }

    @Override
    public void changeTvBgImageUnpressed (int tvNum) {
        this.textView[tvNum].setBackgroundResource(R.drawable.boton_silaba);
    }

    @Override
    public String getAnswerText() {
        return tv_respuesta.getText().toString();
    }

    @Override
    public void playWrongAnswerSound() {
        wrong.start();
    }

    @Override
    public void playCorrectAnswerSound() {
        wonderful.start();
    }

    @Override
    public void stopMusic() {
        mp.stop();
    }

    @Override
    public void goToGameOverScreen() {
        Intent intent = new Intent (getApplicationContext(), Pantalla_Game_Over.class);
        intent.putExtra("jugador", jugador);
        intent.putExtra("score", tv_score.getText().toString());
        startActivity(intent);
    }

    @Override
    public void setMainImage (String nombreDeImagen) {
        int id = getApplicationContext().getResources().getIdentifier(nombreDeImagen, "drawable", getPackageName());
        this.iv.setImageResource(id);
    }

    @Override
    public void setSyllableButtonText (String syllable, int tvNumber){
        textView[tvNumber].setText(syllable);
    }

    @Override
    public String getSyllableButtonText(int tvNumber) {

        return textView[tvNumber].getText().toString();
    }

    @Override
    public void setAnswer (String answer){
        tv_respuesta.setText(answer);
    }

}