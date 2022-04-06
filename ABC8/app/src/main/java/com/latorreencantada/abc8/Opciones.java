package com.latorreencantada.abc8;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Opciones extends AppCompatActivity {


    MediaPlayer mp;
    int posicion=0;
    private TextView textView;
    private Button boton_simbolo_musica;
    private  Button boton_fondo;
    private ToggleButton boton_sonidos;



    public void SonidosOnOff (View view) {
        Global.sonidos= !Global.sonidos;
    }


    public void imagenDeFondo (View view) {
        if (Global.BackgroundImage.equals("rojo")){
            Global.BackgroundImage="azul";
            boton_fondo.setText(R.string.txt_azul);
            // (Imageview_de_fondo_de_Menu_Opciones).setBackgroundResource(R.drawable.fondo_azul);
        } else {
            Global.BackgroundImage="rojo";
            boton_fondo.setText(R.string.txt_rojo);
            // (Imageview_de_fondo_de_Menu_Opciones).setBackgroundResource(R.drawable.fondo);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        mp = MediaPlayer.create(this, R.raw.please);
        boton_sonidos = findViewById(R.id.id_botonSonido);
        boton_fondo = findViewById(R.id.id_fondo);

        boton_simbolo_musica = findViewById(R.id.boton_simbolo_musica);

        if (Global.musica){
            mp.start();
            mp.setLooping(true);
        }


        //VENTANA FLOTANTE

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int) (ancho * 0.85) , (int)(alto*0.5));
        //------------------



    }

    @Override
    protected void onPause() { super.onPause();
        posicion = mp.getCurrentPosition();
        if (Global.musica){
            posicion = mp.getCurrentPosition();
            mp.pause();
        }
    }

    protected void onResume() {
        super.onResume();
        if (Global.musica){
            boton_simbolo_musica.setBackgroundResource(R.drawable.ic_music_note_black_24dp);
            mp.seekTo(posicion);
            mp.start();
            mp.setLooping(true);
        } else {
            boton_simbolo_musica.setBackgroundResource(R.drawable.ic_do_not_disturb_black_24dp);
        }

        if (Global.sonidos){
            boton_sonidos.setChecked(true);
        } else {
            boton_sonidos.setChecked(false);
        }
    }



    @Override
    public void onBackPressed() {
    }

    public void OnDefaultToggleClick(View view) {
        if (Global.musica){
            mp.pause();
            Global.musica= false;
            boton_simbolo_musica.setBackgroundResource(R.drawable.ic_do_not_disturb_black_24dp);

        } else {
            mp.start();
            mp.setLooping(true);
            Global.musica=true;
            boton_simbolo_musica.setBackgroundResource(R.drawable.ic_music_note_black_24dp);
        }


    }

    public void OnCustomToggleClick(View view) {

    }
}