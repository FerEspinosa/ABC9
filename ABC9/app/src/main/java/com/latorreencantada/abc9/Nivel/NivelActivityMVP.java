package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.latorreencantada.abc9.Card;
import com.latorreencantada.abc9.Global;

import java.util.ArrayList;

public interface NivelActivityMVP {

interface View {
    void setMainImage (String nombreDeImagen);
    void setScore (String score);
    void setSyllableButtonText (String syllable, int tvNumber);
    String getSyllableButtonText (int tvNumber);
    void buttonPress(android.view.View v);
    void setAnswer (String answer);
    void setOneStar ();
    void setTwoStars ();
    void setThreeStars ();
    void changeTvBgImagePressed(int tvNum);
    void changeTvBgImageUnpressed(int tvNum);
    String getAnswerText();
    void playWrongAnswerSound();
    void playCorrectAnswerSound();
    void playNewLevelSound();
    void stopMusic();
    void goToGameOverScreen();
    void showOptions();
    void allowClickOnSend(boolean b);
    Context getContext();

    //para pruebas
    void showToast(String message);

}

interface Presenter {

    //el primero es para indicar al presenter qué vista gobierna
    void setView (NivelActivityMVP.View view);

    void NuevaCarta(int playerLevel);
    void sylablePressed(android.view.View v);
    void bt_borrar_clicked();
    void bt_options_clicked();
    void bt_enviar_clicked();
    void pauseMusic(MediaPlayer mp);
    void playCorrectAnswerSound ();
    void playWrongAnswerSound ();
    Context getContext();
}

interface Model {
    ArrayList<Card> getLevelWords(int level, Context context);
}

}
