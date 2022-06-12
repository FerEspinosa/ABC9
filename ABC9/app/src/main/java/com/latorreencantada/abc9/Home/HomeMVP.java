package com.latorreencantada.abc9.Home;


import android.content.Context;

public interface HomeMVP {

    interface View {

        void goToLevel();
        void setOptionMenuVisible();
        void setOptionMenuInvisible();
        void MinuscOn();

        Context getContext();

        void setMayuscOn();
    }

    interface Presenter {
        void setView(HomeMVP.View view);

        void musicSwitched(boolean musicOn);
        void soundSwitched(boolean b);

        boolean musicOn();
        boolean soundOn();
        void btJugarPressed();
        void btOptionsPressed();
        void btMinuscPressed();
        void btMayuscPressed();
        void fillDbWithDefaultValues();

        void FirstRun();

        void SetInitialCapsMode();
    }

    interface Model {

    }


}
