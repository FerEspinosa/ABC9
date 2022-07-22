package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.content.SharedPreferences;
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

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Cursor c = bd.rawQuery
                ("SELECT * FROM cards WHERE level = "+playerLevel, null);

        return c;
    }

    @Override
    public int GetHighestUnlockedLevelFromSharedPref(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPrefs",Context.MODE_PRIVATE);
        return sharedPreferences.getInt("HighestUnlockedLevel", 0);
    }

    @Override
    public void SetHighestUnlockedLevelFromSharedPref(Context context, int highestUnlockedLevel) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("SharedPrefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("HighestUnlockedLevel", highestUnlockedLevel);
        editor.apply();
    }


}
