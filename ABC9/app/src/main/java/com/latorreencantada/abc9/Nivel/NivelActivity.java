package com.latorreencantada.abc9.Nivel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.latorreencantada.abc9.Home.HomeActivity;
import com.latorreencantada.abc9.GameOver.GameOverActivity;
import com.latorreencantada.abc9.R;
import com.latorreencantada.abc9.root.App;

import javax.inject.Inject;

public class NivelActivity extends AppCompatActivity implements NivelActivityMVP.View{

    @Inject
    NivelActivityMVP.Presenter presenter;

    private TextView tv_score;
    private TextView tv_respuesta;

    private final TextView[] textView = new TextView[4];

    private Button bt_enviar;
    private ImageView iv_estrellas;
    private ImageView iv;

    String jugador;

    int textViewCount = 4 ;

    FrameLayout optionMenu;
    private MediaPlayer mp;
    private MediaPlayer correctSound;
    private MediaPlayer wrongSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nivel);

        ((App) getApplication()).getComponent().injectNivel(this);

        presenter.setView(this);
        configView();
        configOptionMenu();

        // por ahora mando el player level manualmente
        // más adelante (cuando desarrolle la clase "player") lo obtendré de manera programática
        int playerLevel = 1;
        presenter.NuevaCarta(playerLevel);
    }

    private void configView() {

        //fuente supersonic
        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        // elementos gráficos
        iv_estrellas = findViewById(R.id.iv_estrellas);
        iv = findViewById(R.id.mainImage);
        tv_score = findViewById(R.id.tv_puntaje);
        tv_respuesta = findViewById(R.id.tv_respuesta_2);
        Button bt_borrar = findViewById(R.id.bt_borrar);
        bt_enviar = findViewById(R.id.bt_enviar);

        //mùsica y sonidos
        mp = MediaPlayer.create(this, R.raw.goats);
        correctSound = MediaPlayer.create(this, R.raw.correct);
        wrongSound = MediaPlayer.create(this, R.raw.bad);

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
        bt_enviar.setOnClickListener(view -> presenter.bt_enviar_clicked());
        bt_borrar.setOnClickListener(view -> presenter.bt_borrar_clicked());
        disableClickOnSend();
    }

    private void configOptionMenu() {

        optionMenu = findViewById(R.id.option_menu_frame);

        presenter.setOptionMenuVisibility(false);

        Button bt_opciones = findViewById(R.id.bt_Options);
        bt_opciones.setOnClickListener(view -> presenter.bt_options_clicked());

        Button bt_closeOptionMenu = findViewById(R.id.close_option_menu);
        bt_closeOptionMenu.setOnClickListener(view -> presenter.setOptionMenuVisibility(false));

        SwitchCompat sw_music = findViewById(R.id.sw_music);
        sw_music.setChecked(presenter.getMusicPreference());
        sw_music.setOnCheckedChangeListener((compoundButton, b) -> presenter.musicSwitched(b));

        SwitchCompat sw_sound = findViewById(R.id.sw_sound);
        sw_sound.setChecked(presenter.getSoundPreference());
        sw_sound.setOnCheckedChangeListener((compoundButton, b) -> presenter.soundSwitched(b));

        Button bt_goHome = findViewById(R.id.bt_goHome_from_lvl);
        bt_goHome.setOnClickListener(view -> startActivity(new Intent(NivelActivity.this, HomeActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);

        presenter.startMusic(mp);

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
    public void goToGameOverScreen() {
        Intent intent = new Intent (getApplicationContext(), GameOverActivity.class);
        intent.putExtra("jugador", jugador);
        intent.putExtra("score", tv_score.getText().toString());
        startActivity(intent);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void startMusic(int position) {
        mp.seekTo(position);
        mp.start();
    }

    @Override
    public void pauseMusic() {
        mp.pause();
    }

    @Override
    public void stopMusic() {
        mp.stop();
    }

    @Override
    public void playCorrectSound() {
        correctSound.start();
    }

    @Override
    public void playWrongSound() {
        wrongSound.start();
    }

    @Override
    public int getMusicPosition() {
        return mp.getCurrentPosition();
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
    public void buttonPress(View view) {
        presenter.sylablePressed(view);
    }

    public void allowClickOnSend() {
        bt_enviar.setClickable(true);
        bt_enviar.setBackgroundResource(R.drawable.boton_verde);
    }

    public void disableClickOnSend() {
        bt_enviar.setClickable(false);
        bt_enviar.setBackgroundResource(R.drawable.boton_verde_apagado);
    }

    @Override
    public void showOptionsMenu() {
        optionMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOptionMenu() {
        optionMenu.setVisibility(View.INVISIBLE);
    }
}