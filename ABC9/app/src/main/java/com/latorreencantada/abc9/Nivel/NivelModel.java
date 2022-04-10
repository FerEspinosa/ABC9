package com.latorreencantada.abc9.Nivel;

public class NivelModel implements NivelMVP.Model{
    private NivelRepository repository;

    public NivelModel (NivelRepository repository) {

        this.repository = repository;
    }


}
