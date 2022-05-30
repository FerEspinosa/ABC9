package com.latorreencantada.abc9.Nivel;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.MainActivity;
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

    private TextView[] textView = new TextView[4];

    private Button bt_borrar, bt_enviar, bt_opciones, bt_closeOptionMenu, bt_goHome;

    private ImageView iv_estrellas;
    private ImageView iv;

    String jugador;
    private int playerLevel = 1;

    int textViewCount = 4 ;

    FrameLayout optionMenu;
    boolean optionMenuIsVisible;
    int posicion;

    // prueba de shared preference
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String FIRST_RUN = "firstRun";
    public static final String SWITCH1 = "switch1";
    public static final String MUSIC = "music";
    private final String SOUND = "sound";
    private boolean firstRun;
    private SwitchCompat sw_music, sw_sound;
    private boolean noButtonsPressed = true;

    int cardsToBeDrawn= (Global.defaultLevels.length) * Global.drawsPerLevel;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);

        ((App) getApplication()).getComponent().inject(this);

        configView();
        configOptionMenu();
        presenter.setView(this);

        // por ahora mando el player level manualmente
        // más adelante (cuando desarrolle la clase "player") lo obtendré de manera programática
        presenter.NuevaCarta(playerLevel);

    }

    private void configOptionMenu() {


        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // ocultar menu opciones
        optionMenu = (FrameLayout)findViewById(R.id.option_menu_frame);
        optionMenu.setVisibility(View.INVISIBLE);
        optionMenuIsVisible = false;

        bt_opciones = findViewById(R.id.bt_Options);
        bt_opciones.setOnClickListener(view -> OptionMenuIsVisible(!optionMenuIsVisible));

        bt_closeOptionMenu = findViewById(R.id.close_option_menu);
        bt_closeOptionMenu.setOnClickListener(view -> OptionMenuIsVisible(optionMenuIsVisible));

        sw_music = findViewById(R.id.sw_music);
        sw_music.setChecked(sharedPreferences.getBoolean(MUSIC,true));
        sw_music.setOnCheckedChangeListener((compoundButton, b) -> {

            editor.putBoolean(MUSIC, b);
            editor.apply();

            if (b) {
                startMusic();
            } else {
                pauseMusic();
            }
        });

        sw_sound = findViewById(R.id.sw_sound);
        boolean aux2 = sharedPreferences.getBoolean(SOUND,true);
        sw_sound.setChecked(sharedPreferences.getBoolean(SOUND,true));
        sw_sound.setOnCheckedChangeListener((compoundButton, b) -> {
            editor.putBoolean(SOUND, b);
            editor.apply();
        });

        bt_goHome = findViewById(R.id.bt_goHome_from_lvl);
        bt_goHome.setOnClickListener(view -> startActivity(new Intent(NivelActivity.this, MainActivity.class)));

    }

    private void OptionMenuIsVisible (boolean b) {

        if (b){
            optionMenu.setVisibility(View.VISIBLE);
        } else {
            optionMenu.setVisibility(View.INVISIBLE);
        }

        for (int tv_i=0; tv_i<textViewCount; tv_i++){
            textView[tv_i].setClickable(!b);
        }
        bt_borrar.setClickable(!b);
        bt_enviar.setClickable(!b);

    }

    private void pauseMusic() {
        posicion = mp.getCurrentPosition();
        mp.pause();
    }

    private void configView() {

        sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //fuente supersonic
        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);


        // musica y sonidos
      //  wonderful = MediaPlayer.create(this, R.raw.wonderful);
        wrong = MediaPlayer.create(this, R.raw.bad);
        mp = MediaPlayer.create(this, R.raw.goats);
        correctAnswer = MediaPlayer.create(this,R.raw.correct);

        boolean capslock = Global.capsLock;



        // elementos gráficos
        iv_estrellas = findViewById(R.id.iv_estrellas);
        iv = findViewById(R.id.mainImage);
        tv_score = findViewById(R.id.tv_puntaje);
        tv_respuesta = findViewById(R.id.tv_respuesta_2);
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

        bt_enviar.setOnClickListener(view -> presenter.bt_enviar_clicked());

        bt_borrar.setOnClickListener(view -> presenter.bt_borrar_clicked());

        noButtonsPressed = true;
        allowClickOnSend(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

        startMusic();

        View decorView = getWindow().getDecorView();
        int uiOptions;
        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;

        decorView.setSystemUiVisibility(uiOptions);

/*

        if (Global.BackgroundImage=="rojo"){
            fondo.setImageResource(R.drawable.fondo);
        } else {
            fondo.setImageResource(R.drawable.fondo_azul);
        }
*/
    }

    private void startMusic() {
        if (sharedPreferences.getBoolean(MUSIC, true)){
            mp.seekTo(posicion);
            mp.start();
            mp.setLooping(true);
        }
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
        allowClickOnSend(true);

        presenter.sylablePressed(v);

    }

    public void allowClickOnSend(boolean b) {
        bt_enviar.setClickable(b);
        noButtonsPressed = !b;
        if (b){
            bt_enviar.setBackgroundResource(R.drawable.boton_verde);
            // cambiar aquí tb "el color" del botón de borrar
        } else {
            // cambiar aqui el background del botón por uno que denote que NO está clickeable
            bt_enviar.setBackgroundResource(R.drawable.boton_verde_apagado);
        }
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
        if (sharedPreferences.getBoolean(SOUND, true)){
            correctAnswer.start();
        }
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
    public void showOptions() {
        if (!optionMenuIsVisible){
            optionMenu.setVisibility(View.VISIBLE);
            optionMenuIsVisible=true;
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

    @Override
    public void showToast (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}