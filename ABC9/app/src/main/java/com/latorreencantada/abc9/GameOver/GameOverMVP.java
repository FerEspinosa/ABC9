package com.latorreencantada.abc9.GameOver;

import android.content.Context;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Nivel.NivelActivityMVP;

public interface GameOverMVP {

    interface View {
        Context GetContext();

        void PlayEndingTune();

        void SetFinalMessage(String message);

    }

    interface Presenter {
        void SetView(GameOverMVP.View view);

        void SetFinalMessage(int score);
        void PlayEndingTune();
    }


    interface Model {

    }
}
