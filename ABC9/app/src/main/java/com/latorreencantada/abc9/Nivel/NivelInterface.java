package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.database.Cursor;

public interface
NivelInterface {
    String[][] getLevelWordsFromMemory(int playerLevel);
    Cursor getLevelWordsFromDb(int playerLevel, Context context);
}
