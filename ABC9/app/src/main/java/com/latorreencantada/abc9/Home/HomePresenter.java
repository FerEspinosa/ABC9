package com.latorreencantada.abc9.Home;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;
import com.latorreencantada.abc9.Global;
import com.latorreencantada.abc9.R;

public class HomePresenter implements HomeMVP.Presenter{

    private static final String SHARED_PREFS = "SharedPrefs";
    public static final String FIRST_RUN = "firstRun";
    private static final String MUSIC = "music";
    private static final String SOUND = "sound";
    private static final String CAPSLOCK = "capslock";
    public boolean optionMenuShown = false;

    @Nullable
    //variable que hace referencia a la vista
    private HomeMVP.View view;

    // constructor que configura la dependencia con la vista
    public void setView (@Nullable HomeMVP.View view) {
        this.view = view;
    }


    @Override
    public boolean musicOn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MUSIC, true)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void musicSwitched(boolean musicOnOrOff) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MUSIC, musicOnOrOff);
        editor.apply();
    }

    @Override
    public boolean soundOn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SOUND, true)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void soundSwitched(boolean b) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SOUND, b);
        editor.apply();
    }

    @Override
    public void btJugarPressed() {
        assert view != null;
        view.goToLevel();
    }

    @Override
    public void btOptionsPressed() {
        if (optionMenuShown){
            // hacer invisible el menu de opciones
            assert view != null;
            view.setOptionMenuInvisible();
            optionMenuShown = false;
        } else {
            // hacer visible el menu de opciones
            assert view != null;
            view.setOptionMenuVisible();
            optionMenuShown = true;
        }
    }

    @Override
    public void SetInitialCapsMode() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(CAPSLOCK,true)){
            assert view != null;
            view.setMayuscOn();

        } else {
            view.MinuscOn();
        }
    }

    @Override
    public void btMinuscPressed() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CAPSLOCK, false);
        editor.apply();

        view.MinuscOn();

    }

    @Override
    public void btMayuscPressed() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CAPSLOCK, true);
        editor.apply();

        view.setMayuscOn();
    }

    @Override
    public void fillDbWithDefaultValues() {
        if (itsTheFirstRun()){
            //Crear objeto administrador de base de datos
            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(getContext(), "administracion", null, 1);

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
    }

    @Override
    public void FirstRun() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(FIRST_RUN, true)){
            editor.putBoolean(FIRST_RUN, false);
            editor.apply();
            fillDbWithDefaultValues();
        }

    }



    //variable que hace referencia al modelo
    private final HomeMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public HomePresenter(HomeMVP.Model model){
        this.model = model;
    }

    public boolean itsTheFirstRun() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (sharedPreferences.getBoolean(FIRST_RUN, true)){
            editor.putBoolean(FIRST_RUN, false);
            editor.apply();
            return true;

        } else {
            return false;
        }
    }

    private Context getContext() {
        assert view != null;
        return view.getContext();
    }

}
