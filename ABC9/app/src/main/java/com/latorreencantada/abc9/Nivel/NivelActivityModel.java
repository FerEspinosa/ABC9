package com.latorreencantada.abc9.Nivel;

import com.latorreencantada.abc9.Global;

public class NivelActivityModel implements NivelActivityMVP.Model{
    private NivelRepository repository;

    public NivelActivityModel(NivelRepository repository) {

        this.repository = repository;
    }


    @Override
    public String[][] getLevelWords(int playerLevel) {
        return Global.niveles[playerLevel];
    }
}
