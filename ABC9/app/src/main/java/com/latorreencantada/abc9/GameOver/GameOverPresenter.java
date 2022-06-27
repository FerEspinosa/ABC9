package com.latorreencantada.abc9.GameOver;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Global;

public class GameOverPresenter implements GameOverMVP.Presenter{

    private static final String MUSIC = "music";
    private static final String SOUND = "sound";
    private static final String SHARED_PREFS = "SharedPrefs";

    @Nullable
    //variable que hace referencia a la vista
    private GameOverMVP.View view;

    // constructor que configura la dependencia con la vista
    @Override
    public void SetView(@Nullable GameOverMVP.View view) {
        this.view = view;
    }

    @Override
    public void SetFinalMessage(int score) {

        int cardsToBeDrawn= (Global.defaultLevels.length) * Global.drawsPerLevel;
        assert view != null;
        if (score<cardsToBeDrawn/10){
            view.SetFinalMessage("Ok. Nadie nace sabiendo");
        } else if (score < cardsToBeDrawn/5) {
            view.SetFinalMessage("¡Bien!");
        } else if (score < cardsToBeDrawn*3/10) {
            view.SetFinalMessage("¡Muy bien!");
        } else if (score < cardsToBeDrawn*4/10) {
            view.SetFinalMessage("¡cada vez mejor!");
        } else if (score < cardsToBeDrawn/2) {
            view.SetFinalMessage("¡Brillante!");
        } else if (score < cardsToBeDrawn*6/10) {
            view.SetFinalMessage("¡Tu progreso es un orgullo!");
        } else if (score < cardsToBeDrawn*7/10) {
            view.SetFinalMessage("¡Wow, increìble!");
        } else if (score < cardsToBeDrawn*8/10) {
            view.SetFinalMessage("¡Espectacular!");
        } else if (score < cardsToBeDrawn*9/10) {
            view.SetFinalMessage("¡¡¡Excelente!!!");
        } else if (score == cardsToBeDrawn-1) {
            view.SetFinalMessage("¡Puntuaciòn perfecta!");
        }
    }

    //variable que hace referencia al modelo
    private GameOverMVP.Model model;

    // constructor que configura la dependencia con el modelo
    public GameOverPresenter(GameOverMVP.Model model){
        this.model = model;
    }

    @Override
    public void PlayEndingTune() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        if (sharedPreferences.getBoolean(SOUND,true)||sharedPreferences.getBoolean(MUSIC,true)){
            assert view != null;
            view.PlayEndingTune();
        }

    }

    private Context getContext() {

            assert view != null;
            return view.GetContext();
    }
}
