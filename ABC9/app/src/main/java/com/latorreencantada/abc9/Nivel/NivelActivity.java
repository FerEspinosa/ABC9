package com.latorreencantada.abc9.Nivel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.Home.HomeActivity;
import com.latorreencantada.abc9.GameOver.GameOverActivity;
import com.latorreencantada.abc9.LevelMapActivity;
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

        presenter.SetView(this);
        configView();
        configOptionMenu();

        presenter.NuevaCarta(Integer.parseInt(Global.currentLevel.getLevel_num()));
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
        bt_enviar.setOnClickListener(view -> presenter.Bt_enviar_clicked());
        bt_borrar.setOnClickListener(view -> presenter.Bt_borrar_clicked());
        DisableClickOnSend();
    }

    private void configOptionMenu() {

        optionMenu = findViewById(R.id.option_menu_frame);

        presenter.SetOptionMenuVisibility(false);

        Button bt_opciones = findViewById(R.id.bt_Options);
        bt_opciones.setOnClickListener(view -> presenter.Bt_options_clicked());

        Button bt_closeOptionMenu = findViewById(R.id.close_option_menu);
        bt_closeOptionMenu.setOnClickListener(view -> presenter.SetOptionMenuVisibility(false));

        SwitchCompat sw_music = findViewById(R.id.sw_music);
        sw_music.setChecked(presenter.GetMusicPreference());
        sw_music.setOnCheckedChangeListener((compoundButton, b) -> presenter.MusicSwitched(b));

        SwitchCompat sw_sound = findViewById(R.id.sw_sound);
        sw_sound.setChecked(presenter.GetSoundPreference());
        sw_sound.setOnCheckedChangeListener((compoundButton, b) -> presenter.SoundSwitched(b));

        Button bt_goHome = findViewById(R.id.bt_goHome_from_lvl);
        bt_goHome.setOnClickListener(view -> startActivity(new Intent(NivelActivity.this, HomeActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.SetView(this);

        presenter.StartMusic(mp);

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
        presenter.PauseMusic(mp);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

    }

    @Override
    public void SetScore(String score){
        this.tv_score.setText(score);
    }

    @Override
    public void SetOneStar(){
        this.iv_estrellas.setImageResource(R.drawable.una_estrella);
    }

    @Override
    public void SetTwoStars(){
        this.iv_estrellas.setImageResource(R.drawable.dos_estrellas);
    }

    @Override
    public void SetThreeStars(){
        this.iv_estrellas.setImageResource(R.drawable.tres_estrellas);
    }

    @Override
    public void ChangeTvBgImagePressed(int tvNum) {
        this.textView[tvNum].setBackgroundResource(R.drawable.silaba_presionada);
    }

    @Override
    public void ChangeTvBgImageUnpressed(int tvNum) {
        this.textView[tvNum].setBackgroundResource(R.drawable.boton_silaba);
    }

    @Override
    public String GetAnswerText() {
        return tv_respuesta.getText().toString();
    }

    @Override
    public void GoToGameOverScreen() {
        Intent intent = new Intent (getApplicationContext(), GameOverActivity.class);
        intent.putExtra("jugador", jugador);
        intent.putExtra("score", tv_score.getText().toString());
        startActivity(intent);
    }

    @Override
    public Context GetContext() {
        return this;
    }

    @Override
    public void StartMusic(int position) {
        mp.seekTo(position);
        mp.start();
    }

    @Override
    public void PauseMusic() {
        mp.pause();
    }

    @Override
    public void StopMusic() {
        mp.stop();
    }

    @Override
    public void PlayCorrectSound() {
        correctSound.start();
    }

    @Override
    public void PlayWrongSound() {
        wrongSound.start();
    }

    @Override
    public int GetMusicPosition() {
        return mp.getCurrentPosition();
    }

    @Override
    public void GoToLevelMapActivity() {
        Intent intent = new Intent(NivelActivity.this, LevelMapActivity.class);
        startActivity(intent);
    }

    @Override
    public void ShowMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SetMainImage(Bitmap imagenBitmap) {
        this.iv.setImageBitmap(imagenBitmap);
    }

    @Override
    public void SetSyllableButtonText(String syllable, int tvNumber){
        textView[tvNumber].setText(syllable);
    }

    @Override
    public String GetSyllableButtonText(int tvNumber) {
        return textView[tvNumber].getText().toString();
    }

    @Override
    public void SetAnswer(String answer){
        tv_respuesta.setText(answer);
    }

    @Override
    public void ButtonPress(View view) {
        presenter.SylablePressed(view);
    }

    public void AllowClickOnSend() {
        bt_enviar.setClickable(true);
        bt_enviar.setBackgroundResource(R.drawable.boton_verde);
    }

    public void DisableClickOnSend() {
        bt_enviar.setClickable(false);
        bt_enviar.setBackgroundResource(R.drawable.boton_verde_apagado);
    }

    @Override
    public void ShowOptionsMenu() {
        optionMenu.setVisibility(View.VISIBLE);
    }

    @Override
    public void HideOptionMenu() {
        optionMenu.setVisibility(View.INVISIBLE);
    }
}