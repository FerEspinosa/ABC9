package com.latorreencantada.abc9.Home;

public class HomeModel implements HomeMVP.Model{

    private MemoryInterface repository;

    public HomeModel (MemoryInterface repository) {
        this.repository = repository;
    }
}
