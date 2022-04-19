package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class OpcionesActivity extends AppCompatActivity {

    Switch sw_sonido, sw_musica;
    Button bt_editar_palabra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        configView();
    }

    private void configView() {
     sw_musica = findViewById(R.id.sw_musica);
     sw_sonido = findViewById(R.id.sw_sonido);
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