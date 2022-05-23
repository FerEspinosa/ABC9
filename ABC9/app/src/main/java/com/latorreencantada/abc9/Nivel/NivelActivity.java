package com.latorreencantada.abc9.Nivel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.OpcionesActivity;
import com.latorreencantada.abc9.Pantalla_Game_Over;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class NivelActivity extends AppCompatActivity implements NivelActivityMVP.View{

    @Inject
    NivelActivityMVP.Presenter presenter;

    private MediaPlayer wonderful;
    private MediaPlayer correctAnswer;
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
    private int playerLevel = 1;

    int textViewCount = 4 ;

    // prueba de shared preference
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String FIRST_RUN = "firstRun";
    public static final String SWITCH1 = "switch1";
    public static final String MUSIC = "music";
    private boolean firstRun;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);

        ((App) getApplication()).getComponent().inject(this);

        configView();
        presenter.setView(this);

        // por ahora mando el player level manualmente
        // más adelante (cuando desarrolle la clase "player") lo obtendré de manera programática
        presenter.NuevaCarta(playerLevel);
    }

    private void configView() {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //fuente supersonic
        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        // musica y sonidos
        wonderful = MediaPlayer.create(this, R.raw.wonderful);
        wrong = MediaPlayer.create(this, R.raw.bad);
        mp = MediaPlayer.create(this, R.raw.goats);
        correctAnswer = MediaPlayer.create(this,R.raw.correct);

        boolean capslock = Global.capsLock;

        if (sharedPreferences.getBoolean(MUSIC, true)){
            mp.start();
            mp.setLooping(true);
        }

        // elementos gráficos
        iv_estrellas = findViewById(R.id.iv_estrellas);
        iv = findViewById(R.id.mainImage);
        tv_score = findViewById(R.id.tv_puntaje);
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

        tv_score.setText("0");
        tv_respuesta.setText("");

        // botones con click listeners:

        bt_musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.bt_options_clicked();
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

        if (sharedPreferences.getBoolean(MUSIC, true)){
            presenter.startMusic(mp);
        }

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

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);


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
        correctAnswer.start();
    }

    @Override
    public void playNewLevelSound() {
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
    public void goToOptionScreen() {
        Intent intent = new Intent(getContext(), OpcionesActivity.class);
        intent.putExtra("comming_from", "level");
        startActivity(intent);
    }

    @Override
    public void checkFirstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(FIRST_RUN, true)){
            editor.putBoolean(FIRST_RUN, false);
            Toast.makeText(this, "first run!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "not first run", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public Context getContext() {
        return getApplicationContext();
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