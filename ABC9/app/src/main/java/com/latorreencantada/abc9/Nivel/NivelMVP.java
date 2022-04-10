package com.latorreencantada.abc9.Nivel;

public interface NivelMVP {

interface View {

}

interface Presenter {

    //el primero es para indicar al presenter qué vista gobierna
    void setView (NivelMVP.View view);

    void NuevaCarta();
    void sylablePressed(android.view.View v);
    void bt_borrar_clicked();
    void bt_music_clicked(android.view.View view);
    void bt_enviar_clicked();

}

interface Model {

}

}
