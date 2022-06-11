package com.latorreencantada.abc9.GameOver;


public class GameOverModel implements GameOverMVP.Model{

    private final MemoryInterface memory;

    public GameOverModel(MemoryInterface memory) {
        this.memory = memory;
    }
}
