package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.media.MediaPlayer;

import com.latorreencantada.abc9.Models.Card;

import java.util.ArrayList;

public interface NivelActivityMVP {

interface View {
    void SetMainImage(String nombreDeImagen);
    void SetScore(String score);
    void SetSyllableButtonText(String syllable, int tvNumber);
    String GetSyllableButtonText(int tvNumber);
    void ButtonPress(android.view.View v);
    void SetAnswer(String answer);
    String GetAnswerText();
    void SetOneStar();
    void SetTwoStars();
    void SetThreeStars();
    void ChangeTvBgImagePressed(int tvNum);
    void ChangeTvBgImageUnpressed(int tvNum);
    void GoToGameOverScreen();
    void ShowOptionsMenu();
    void HideOptionMenu();
    void AllowClickOnSend();
    void DisableClickOnSend();
    Context GetContext();
    void StartMusic(int position);
    void PauseMusic();
    void StopMusic();
    void PlayCorrectSound();
    void PlayWrongSound();
    int GetMusicPosition();

    void GoToLevelMapActivity();

    // for quick manual testing
    void ShowMsg(String msg);

}

interface Presenter {

    //el primero es para indicar al presenter qué vista gobierna
    void SetView(NivelActivityMVP.View view);

    void NuevaCarta(int playerLevel);
    void SylablePressed(android.view.View v);
    void Bt_borrar_clicked();
    void Bt_options_clicked();
    void SetOptionMenuVisibility(boolean visibility);
    void Bt_enviar_clicked();
    void StartMusic(MediaPlayer mp);
    void PauseMusic(MediaPlayer mp);
    void StopMusic();
    void PlayCorrectAnswerSound();
    void PlayWrongAnswerSound();
    void MusicSwitched(boolean b);
    boolean GetMusicPreference();
    boolean GetSoundPreference();

    Context GetContext();

    void SoundSwitched(boolean b);

}

interface Model {
    ArrayList<Card> GetLevelWords(int level, Context context);
    int GetHighestUnlockedLevel (Context context);
    void SetHighestUnlockedLevel (Context context, int highestUnlockedLevel);
}

}
