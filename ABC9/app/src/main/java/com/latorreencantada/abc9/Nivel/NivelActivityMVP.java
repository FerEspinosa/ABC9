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
    String getAnswerText();
    void setOneStar ();
    void setTwoStars ();
    void setThreeStars ();
    void changeTvBgImagePressed(int tvNum);
    void changeTvBgImageUnpressed(int tvNum);
    void goToGameOverScreen();
    void showOptionsMenu();
    void hideOptionMenu();
    void allowClickOnSend();
    void disableClickOnSend();
    Context getContext();
    void startMusic(int position);
    void pauseMusic();
    void stopMusic();
    void playCorrectSound();
    void playWrongSound();
    int getMusicPosition();

}

interface Presenter {

    //el primero es para indicar al presenter qué vista gobierna
    void setView (NivelActivityMVP.View view);

    void NuevaCarta(int playerLevel);
    void sylablePressed(android.view.View v);
    void bt_borrar_clicked();
    void bt_options_clicked();
    void setOptionMenuVisibility(boolean visibility);
    void bt_enviar_clicked();
    void startMusic(MediaPlayer mp);
    void pauseMusic(MediaPlayer mp);
    void stopMusic();
    void playCorrectAnswerSound ();
    void playWrongAnswerSound ();
    void musicSwitched(boolean b);
    boolean getMusicPreference();
    boolean getSoundPreference();

    Context getContext();

    void soundSwitched(boolean b);

}

interface Model {
    ArrayList<Card> getLevelWords(int level, Context context);
}

}
