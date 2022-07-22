package com.latorreencantada.abc9.Home;


import android.content.ContentValues;
import android.content.Context;

public interface HomeMVP {

    interface View {

        void GoToLevel();
        void SetOptionMenuVisible();
        void SetOptionMenuInvisible();
        void MinuscOn();

        Context GetContext();

        void SetMayuscOn();
    }

    interface Presenter {
        void SetView(HomeMVP.View view);

        void FirstRun();
        void FillDbWithDefaultValues();

        void MusicSwitched(boolean musicOn);
        boolean MusicOn();

        void SoundSwitched(boolean b);
        boolean SoundOn();

        void BtJugarPressed();
        void BtOptionsPressed();
        void BtMinuscPressed();
        void BtMayuscPressed();

        void SetInitialCapsMode();
    }

    interface Model {

        int getGlobalLevelsCount();
        String GetGlobalString(int level, int wordPosition, int cardPart);
        void InsertCardIntoDb(ContentValues registro, Context context);
        boolean IsFirstRun(Context context);

        void SetNotFirstRun(Context context);

        int GetLevelWordsCount(int level);

        void deleteAllData(Context context);
    }


}
