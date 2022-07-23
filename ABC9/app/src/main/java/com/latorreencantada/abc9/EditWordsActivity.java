package com.latorreencantada.abc9;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.latorreencantada.abc9.Models.Card;

import java.io.IOException;
import java.io.InputStream;

public class EditWordsActivity extends AppCompatActivity {

    private static final int GALLERY_REQUEST_CODE = 100;
    private static final int CAMERA_REQUEST_CODE = 200;

    EditText et_word, et_syl1, et_syl2, et_syl3, et_syl4, et_level;
    Button bt_save;
    ImageView bt_uploadImage;

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
                //getInputs();
                //saveCard(word, syl1, syl2, syl3, syl4, stringLevel);
                save(view);
            }
        });
    }

    public void save(View view) {
        Bitmap image = Card.resizeBitmap(((BitmapDrawable)bt_uploadImage.getDrawable()).getBitmap());
        new AdminSQLiteOpenHelper(this).addMemory(new Card(
                et_word.getText().toString(),
                et_syl1.getText().toString(),
                et_syl2.getText().toString(),
                et_syl3.getText().toString(),
                et_syl4.getText().toString(),
                Integer.parseInt(et_level.getText().toString()),
                Card.bitmapToString(image)
        ));
        finish();
    }

    private void saveCard(String word, String syl1, String syl2, String syl3, String syl4, String stringLevel) {

        //get word and put it on the array

        //Crear objeto administrador de base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);

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
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this);

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



    public void openGallery(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_REQUEST_CODE);
    }

    public void openCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == GALLERY_REQUEST_CODE) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                bt_uploadImage.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bt_uploadImage.setImageBitmap(imageBitmap);
        }
    }

/*
    private String[][] getLevelsWords(int userLevel) {

        levelWordsArray = Global.niveles[userLevel];

        return levelWordsArray;
    }
    */
}