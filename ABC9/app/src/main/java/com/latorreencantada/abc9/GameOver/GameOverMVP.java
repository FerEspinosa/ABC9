package com.latorreencantada.abc9.GameOver;

import android.content.Context;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Nivel.NivelActivityMVP;

public interface GameOverMVP {

    interface View {
        Context getContext();

        void playEndingTune();

        void setFinalMessage(String message);

    }

    interface Presenter {
        void setView (GameOverMVP.View view);

        void setFinalMessage(int score);
        void playEndingTune();
    }


    interface Model {

    }
}
