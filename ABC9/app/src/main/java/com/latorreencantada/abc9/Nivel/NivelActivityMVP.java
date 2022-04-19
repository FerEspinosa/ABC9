package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.media.MediaPlayer;

public interface NivelActivityMVP {

interface View {
    void setPlayerName (String name);
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

    void stopMusic();

    void goToGameOverScreen();
}

interface Presenter {

    //el primero es para indicar al presenter qué vista gobierna
    void setView (NivelActivityMVP.View view);

    void NuevaCarta();
    void sylablePressed(android.view.View v);
    void bt_borrar_clicked();
    void bt_music_clicked(MediaPlayer mp, android.view.View v);
    void bt_enviar_clicked();
    void startMusic(MediaPlayer mp);
    void pauseMusic(MediaPlayer mp);
    void playCorrectAnswerSound ();
    void playWrongAnswerSound ();
}

interface Model {
    String[][] getLevelWords(int level);
}

}
