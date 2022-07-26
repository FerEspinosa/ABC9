package com.latorreencantada.abc9.Home;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Models.Card;
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
    public void SetView(@Nullable HomeMVP.View view) {
        this.view = view;
    }


    @Override
    public boolean MusicOn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(MUSIC, true)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void MusicSwitched(boolean musicOnOrOff) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(MUSIC, musicOnOrOff);
        editor.apply();
    }

    @Override
    public boolean SoundOn() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        if (sharedPreferences.getBoolean(SOUND, true)){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void SoundSwitched(boolean b) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(SOUND, b);
        editor.apply();
    }

    @Override
    public void BtJugarPressed() {
        assert view != null;
        view.GoToLevel();
    }

    @Override
    public void BtOptionsPressed() {

         //model.deleteAllData(getContext());
         //FillDbWithDefaultValues();

        if (optionMenuShown){
            // hacer invisible el menu de opciones
            assert view != null;
            view.SetOptionMenuInvisible();
            optionMenuShown = false;
        } else {
            // hacer visible el menu de opciones
            assert view != null;
            view.SetOptionMenuVisible();
            optionMenuShown = true;
        }
    }

    @Override
    public void SetInitialCapsMode() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(CAPSLOCK,true)){
            assert view != null;
            view.SetMayuscOn();

        } else {
            view.MinuscOn();
        }
    }



    @Override
    public void BtMinuscPressed() {

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CAPSLOCK, false);
        editor.apply();

        view.MinuscOn();

    }

    @Override
    public void BtMayuscPressed() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(CAPSLOCK, true);
        editor.apply();

        view.SetMayuscOn();
    }

    @Override
    public void FirstRun() {

        if (model.IsFirstRun(getContext())){
            model.SetNotFirstRun(getContext());
            FillDbWithDefaultValues();
        }
    }

    @Override
    public void DeleteAllData(Context context) {
        model.deleteAllData(context);
    }

    @Override
    public void FillDbWithDefaultValues() {

        Context context = getContext();
        //contar niveles default
        int levelCount = model.getGlobalLevelsCount();

        // para cada nivel
        for (int level_i =0; level_i<levelCount; level_i++){
            //contar palabras de level_i
            int levelWordsCount = model.GetLevelWordsCount(level_i);

            // para cada palabra de ese nivel:
            for (int word_i=0 ; word_i<levelWordsCount; word_i++) {
                String word= model.GetGlobalString(level_i, word_i,0);
                String syl1= model.GetGlobalString(level_i, word_i,1);
                String syl2= model.GetGlobalString(level_i, word_i,2);
                String syl3= model.GetGlobalString(level_i, word_i,3);
                String syl4= model.GetGlobalString(level_i, word_i,4);
                String level= model.GetGlobalString(level_i, word_i,5);


                word = word.replaceAll("Ñ", "NY").toLowerCase();

                // get resource Id by it's name
                int ResId = context.getResources().getIdentifier(word, "drawable",  context.getPackageName());

                // Save it as a Bitmap
                Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), ResId);

                // Convert it to String
                String image = Card.bitmapToString(imageBitmap);

                //-- End getting image and converting it to String

                // Crear un objeto que almacenará los datos que deseamos pasar a la base de datos
                ContentValues registro = new ContentValues();

                registro.put("word", word);
                registro.put("syl1", syl1);
                registro.put("syl2", syl2);
                registro.put("syl3", syl3);
                registro.put("syl4", syl4);
                registro.put("level", Integer.parseInt(level));
                registro.put("image", image);

                // Luego insertamos el objeto "registro" (que contiene los datos) en la BdD
                model.InsertCardIntoDb(registro, getContext());
            }
        }
    }

    //variable que hace referencia al modelo
    private final HomeMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public HomePresenter(HomeMVP.Model model){
        this.model = model;
    }

    private Context getContext() {
        assert view != null;
        return view.GetContext();
    }

}
