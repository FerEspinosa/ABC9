package com.latorreencantada.abc9.GameOver;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Nivel.NivelActivityMVP;

public class GameOverPresenter implements GameOverMVP.Presenter{

    private static final String MUSIC = "music";
    private static final String SOUND = "sound";
    private static final String SHARED_PREFS = "SharedPrefs";

    @Nullable
    //variable que hace referencia a la vista
    private GameOverMVP.View view;

    // constructor que configura la dependencia con la vista
    @Override
    public void setView(@Nullable GameOverMVP.View view) {
        this.view = view;
    }

    //variable que hace referencia al modelo
    private GameOverMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public GameOverPresenter(GameOverMVP.Model model){
        this.model = model;
    }

    @Override
    public void playEndingTune() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        if (sharedPreferences.getBoolean(SOUND,true)||sharedPreferences.getBoolean(MUSIC,true)){
            assert view != null;
            view.playEndingTune();
        }

    }

    private Context getContext() {

            assert view != null;
            return view.getContext();
    }
}
