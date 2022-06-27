package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;
import com.latorreencantada.abc9.Global;

public class NivelRepository implements NivelInterface {

    @Override
    public String[][] getLevelWordsFromMemory(int playerLevel) {
        return Global.defaultLevels[playerLevel];
    }

    @Override
    public Cursor getLevelWordsFromDb(int playerLevel, Context context) {

        //Crear objeto administrador de base de datos

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Cursor c = bd.rawQuery
                ("SELECT * FROM cards WHERE level = "+playerLevel, null);

        return c;
    }
}
