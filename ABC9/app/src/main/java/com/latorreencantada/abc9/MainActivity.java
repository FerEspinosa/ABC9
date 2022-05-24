package com.latorreencantada.abc9;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.latorreencantada.abc9.Nivel.NivelActivity;

public class MainActivity extends AppCompatActivity {

    private static final String FIRST_RUN = "firstRun";
    public static final String SHARED_PREFS = "SharedPrefs";
    public static final String MUSIC = "music";
    public static final String SOUND = "sound";
    LinearLayout optionMenu;

    private EditText et_nombre;
    //private MediaPlayer mp;
    private ImageView fondo;
    private TextView tv_bestScore;

    Button bt_jugar, bt_opciones, bt_probar;
    SwitchCompat sw_sonido, sw_musica;

    int posicion=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configView();
        //mp = MediaPlayer.create(this, R.raw.alphabet_song);

        fondo = findViewById(R.id.id_fondo);

        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);


        /*
        // gestionar BdD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "BD", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery(
                "select * from puntaje where score = (select max (score) from puntaje)", null);

        if (consulta.moveToFirst()){
            String temp_nom = consulta.getString(0);
            String temp_score = consulta.getString(1);
            String txt_bestScoreIs="Record: "+ temp_score + " de " + temp_nom;
            tv_bestScore.setText(txt_bestScoreIs);

            consulta.close();
            BD.close();

        } else {
            BD.close();
        }

         */

    }

    private void configView() {

        if (itsTheFirstRun()){
            fillDbWithDefaultValues();
        }

        optionMenu = (LinearLayout)findViewById(R.id.layout_opciones_home);
        optionMenu.setVisibility(View.INVISIBLE);

        bt_jugar = findViewById(R.id.bt_jugar);
        bt_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NivelActivity.class);
                //mp.stop();
                startActivity(intent);
                finish();
            }
        });

        bt_opciones = findViewById(R.id.bt_opciones);
        bt_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // hacer visible el menu de opciones
                optionMenu.setVisibility(View.VISIBLE);
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        sw_musica = findViewById(R.id.sw_musica_home);
        sw_musica.setChecked(sharedPreferences.getBoolean(MUSIC, true));
        sw_musica.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(MUSIC, b);
                editor.apply();
            }
        });


        sw_sonido = findViewById(R.id.sw_sonido_home);
        sw_sonido.setChecked(sharedPreferences.getBoolean(SOUND, true));
        sw_sonido.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean(SOUND, b);
                editor.apply();
            }
        });


        /* Reemplazar el siguiente switch por botones

        sw_mayuscMinusc = findViewById(R.id.sw_mayúsculas);
        sw_mayuscMinusc.setChecked(!sharedPreferences.getBoolean(CAPSLOCK, true));
        sw_mayuscMinusc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b){
                    // modo abc
                    editor.putBoolean(CAPSLOCK, false);
                } else {
                    // modo ABCabc
                    editor.putBoolean(CAPSLOCK, true);
                }
                editor.apply();
            }
        });
        */

    }



    private void fillDbWithDefaultValues() {

        //Crear objeto administrador de base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Se utiliza el objeto "admin" para obtener la base de datos (en modo lectura y escritura)
        SQLiteDatabase BD = admin.getWritableDatabase();

        //contar niveles
        int levelCount = Global.defaultLevels.length;

        // para cada nivel
        for (int level_i =0; level_i<levelCount; level_i++){
            //contar palabras de level_i
            int levelWordsCount = Global.defaultLevels[level_i].length;

            // para cada palabra de ese nivel:
            for (int word_i=0 ; word_i<levelWordsCount; word_i++) {
                String word= Global.defaultLevels[level_i][word_i][0];
                String syl1= Global.defaultLevels[level_i][word_i][1];
                String syl2= Global.defaultLevels[level_i][word_i][2];
                String syl3= Global.defaultLevels[level_i][word_i][3];
                String syl4= Global.defaultLevels[level_i][word_i][4];
                String level = Global.defaultLevels[level_i][word_i][5] ;

                // Crear un objeto que almacenará los datos que deseamos pasar a la base de datos
                ContentValues registro = new ContentValues();

                registro.put("word", word);
                registro.put("syl1", syl1);
                registro.put("syl2", syl2);
                registro.put("syl3", syl3);
                registro.put("syl4", syl4);
                registro.put("level", Integer.parseInt(level));

                // Luego insertamos el objeto "registro" (que contiene los datos) en la BdD
                BD.insert("cards", null, registro);
            }
        }

        //close DB
        BD.close();
    }

    public boolean itsTheFirstRun() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(FIRST_RUN, true)){
            editor.putBoolean(FIRST_RUN, false);
            editor.apply();
            //Toast.makeText(this, "first run!", Toast.LENGTH_SHORT).show();
            return true;

        } else {
            //Toast.makeText(this, "not first run", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
/*
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);


        if (sharedPreferences.getBoolean("music",true)){
            mp.seekTo(posicion);
            mp.start();
        }
*/
        getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    protected void onPause() {
        super.onPause();
/*
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean("musica", true)){
            posicion = mp.getCurrentPosition();
            mp.pause();
        }
*/
    }
}