package com.latorreencantada.abc9.GameOver;


public class GameOverModel implements GameOverMVP.Model{

    private final MemoryInterface repository;

    public GameOverModel(MemoryInterface repository) {
        this.repository = repository;
    }
}
