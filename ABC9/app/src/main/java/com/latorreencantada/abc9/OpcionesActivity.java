package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.latorreencantada.abc9.Nivel.NivelActivity;

public class OpcionesActivity extends AppCompatActivity {


    SwitchCompat sw_sonido, sw_musica, sw_mayuscMinusc;
    Button bt_editar_palabra, bt_goHome;
    ImageView bt_close;
    public static final String SHARED_PREFS = "SharedPrefs";
    private String CAPSLOCK = "capslock";
    private String SOUND = "sound";
    private String MUSIC = "music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        configView();
    }

    private void configView() {

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        bt_goHome = findViewById(R.id.bt_goHome);
        bt_goHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        String commingFrom = getIntent().getStringExtra("comming_from");
        if (commingFrom!=null && commingFrom.equals("level")){
            bt_goHome.setVisibility(View.VISIBLE);
        }

        bt_close = findViewById(R.id.img_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        sw_musica = findViewById(R.id.sw_musica);
        sw_musica.setChecked(sharedPreferences.getBoolean(MUSIC, true));
        sw_musica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(MUSIC, b);
                editor.apply();
            }
        });

        sw_sonido = findViewById(R.id.sw_sonido);
        sw_sonido.setChecked(sharedPreferences.getBoolean(SOUND, true));
        sw_sonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(SOUND, b);
                editor.apply();
            }
        });

         sw_mayuscMinusc = findViewById(R.id.sw_mayúsculas);
         sw_mayuscMinusc.setChecked(!sharedPreferences.getBoolean(CAPSLOCK, true));
         sw_mayuscMinusc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                 if (b){
                     // modo minúsculas
                     editor.putBoolean(CAPSLOCK, false);
                 } else {
                     editor.putBoolean(CAPSLOCK, true);
                 }
                 editor.apply();
             }
         });

         bt_editar_palabra = findViewById(R.id.bt_editCard);
         bt_editar_palabra.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 gotoEditWords();
             }
         });
    }

    private void gotoEditWords() {
        startActivity(new Intent(OpcionesActivity.this, EditWordsActivity.class));
    }
}