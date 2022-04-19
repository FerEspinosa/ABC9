package com.latorreencantada.abc9;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.latorreencantada.abc9.Nivel.NivelActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private MediaPlayer mp;
    private ImageView fondo;

    Button bt_jugar, bt_opciones, bt_probar;

    int posicion=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configView();

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
        //mp.start();
    }

    private void configView() {
        et_nombre = findViewById(R.id.idet_nombre);

        bt_jugar = findViewById(R.id.bt_jugar);
        bt_jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, NivelActivity.class);
                intent.putExtra("nombre", et_nombre.getText().toString());
                posicion = mp.getCurrentPosition();
                mp.stop();
                startActivity(intent);

            }
        });

        bt_opciones = findViewById(R.id.bt_opciones);
        bt_opciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,OpcionesActivity.class));
            }
        });

        bt_probar = findViewById(R.id.id_bt_prueba);
        bt_probar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                // define initial array
                String numArray[]
                        = { "one", "two", "three", "four", "five", "six" };

                // print the original array
                System.out.println("Initial Array:\n"
                        + Arrays.toString(numArray));

                // new element to be added
                String newElement = "seven";

                // convert the array to arrayList
                List<String>numList
                        = new ArrayList<String>(
                        Arrays.asList(numArray));

                // Add the new element to the arrayList
                numList.add(newElement);

                // Convert the Arraylist back to array
                numArray = numList.toArray(numArray);

                // print the changed array
                System.out.println("\nArray with value " + newElement
                        + " added:\n"
                        + Arrays.toString(numArray));

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_FULLSCREEN|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}