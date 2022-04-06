package com.latorreencantada.abc8;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private MediaPlayer mp;
    private ImageView fondo;

    int posicion=0;


    public void Jugar (View view) {

        String nombre = et_nombre.getText().toString();

        if (nombre.isEmpty()){
            Toast.makeText(this, "Ingrese un nombre", Toast.LENGTH_SHORT).show();
        } else {

            Intent intent = new Intent(this, Nivel1.class);
            intent.putExtra("jugador", nombre);
            startActivity(intent);

            mp.stop();

            finish();
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fondo = findViewById(R.id.id_fondo);

        String fuente1 = "fuentes/supersonic.ttf";
        Typeface supersonic = Typeface.createFromAsset(getAssets(), fuente1);

        TextView tv_bestScore = findViewById(R.id.idtv_bestScore);
        tv_bestScore.setTypeface(supersonic);

        et_nombre = findViewById(R.id.idet_nombre);

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

        mp = MediaPlayer.create(this, R.raw.alphabet_song);

    }

    public void Opciones (View view) {

        Intent intent = new Intent (this, Opciones.class);
        posicion = mp.getCurrentPosition();
        intent.putExtra("posicion", posicion);
        posicion = 0;
        startActivity(intent);

    }

    public void EditarTarjetas (View view) {
        Intent intent = new Intent (this, Editar_tarjeta.class);
        if (Global.musica) {
            posicion = mp.getCurrentPosition();
            mp.pause();
        }
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onPause() { super.onPause();

        if (Global.musica) {
            posicion = mp.getCurrentPosition();
            mp.pause();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();

        if (Global.BackgroundImage.equals("rojo")){
            fondo.setImageResource(R.drawable.fondo);
        } else {
            fondo.setImageResource(R.drawable.fondo_azul);
        }

        if (Global.musica){
            mp.seekTo(posicion);
            mp.start();
            mp.setLooping(true);
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
    }

}