package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditWordsActivity extends AppCompatActivity {

    EditText et_word, et_syl1, et_syl2, et_syl3, et_syl4, et_level;
    Button bt_uploadImage, bt_save;

    String [][] levelWordsArray;
    String [] wordAndSyllablesArray;
    String word, syl1, syl2, syl3, syl4, stringLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_words);

        configView();

    }

    private void configView() {
        et_word = findViewById(R.id.word);
        et_syl1 = findViewById(R.id.syl1);
        et_syl2 = findViewById(R.id.syl2);
        et_syl3 = findViewById(R.id.syl3);
        et_syl4 = findViewById(R.id.syl4);
        et_level = findViewById(R.id.et_level_editword_scr);
        bt_uploadImage = findViewById(R.id.bt_upload_Image);

        bt_save = findViewById(R.id.bt_save_word);
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputs();
                saveWord(word, syl1, syl2, syl3, syl4, stringLevel);
            }
        });
    }

    private void saveWord(String word, String syl1, String syl2, String syl3, String syl4, String stringLevel) {

        //get word and put it on the array

        //Crear objeto administrador de base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Se utiliza el objeto "admin" para obtener la base de datos (en modo lectura y escritura)
        SQLiteDatabase BD = admin.getWritableDatabase();


        if (!word.isEmpty()&&!syl1.isEmpty()||!stringLevel.isEmpty()){
            // Crear un objeto que almacenará los datos que deseamos pasar a la base de datos
            ContentValues registro = new ContentValues();

            registro.put("word", word);
            registro.put("syl1", syl1);
            registro.put("syl2", syl2);
            registro.put("syl3", syl3);
            registro.put("syl4", syl4);
            registro.put("level", Integer.parseInt(stringLevel));

            // Luego insertamos el objeto "registro" (que contiene los datos) en la BdD
            BD.insert("cards", null, registro);

            BD.close();

            Toast.makeText(this, "tarjeta agregada", Toast.LENGTH_SHORT).show();
        }

    }

    private void getFirstSyl (String word){

        String firstSyl = "";

        //Crear objeto administrador de base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Se utiliza el objeto "admin" para obtener la base de datos (en modo lectura y escritura)
        SQLiteDatabase BD = admin.getWritableDatabase();

        // agregar una card al array de cards del nivel actual

        Toast.makeText(this, firstSyl, Toast.LENGTH_SHORT).show();
    }

    // el siguiente método iría en el presenter
    private void getInputs() {
        word = viewGetWord();
        syl1 = viewGetSyl1();
        syl2 = viewGetSyl2();
        syl3 = viewGetSyl3();
        syl4 = viewGetSyl4();
        stringLevel = viewGetLevel();
    }

    private String viewGetWord() {
        return et_word.getText().toString();
    }
    private String viewGetSyl1() {
        return et_syl1.getText().toString();
    }
    private String viewGetSyl2() {
        return et_syl2.getText().toString();
    }
    private String viewGetSyl3() {
        return et_syl3.getText().toString();
    }
    private String viewGetSyl4() {
        return et_syl4.getText().toString();
    }

    private String viewGetLevel() {
        return et_level.getText().toString();
    }

/*
    private String[][] getLevelsWords(int userLevel) {

        levelWordsArray = Global.niveles[userLevel];

        return levelWordsArray;
    }
    */
}