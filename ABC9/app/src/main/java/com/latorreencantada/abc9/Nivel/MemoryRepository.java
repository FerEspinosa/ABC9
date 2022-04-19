package com.latorreencantada.abc9.Nivel;

import com.latorreencantada.abc9.Global;

public class MemoryRepository implements MemoryInterface {

    @Override
    public String[][] getLevelWords(int playerLevel) {
        return Global.niveles[playerLevel];
    }
}
