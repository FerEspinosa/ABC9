package com.latorreencantada.abc9.Home;

import static android.content.Context.MODE_PRIVATE;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;
import com.latorreencantada.abc9.Global;


public class HomeModel implements HomeMVP.Model{

    private static final String SHARED_PREFS = "SharedPrefs";
    private static final String FIRST_RUN = "firstRun";

    private MemoryInterface repository;

    public HomeModel (MemoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public String GetGlobalString(int level, int wordPosition, int cardPart) {
        return Global.defaultLevels[level][wordPosition][cardPart];
    }

    @Override
    public boolean IsFirstRun(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        return sharedPreferences.getBoolean(FIRST_RUN, true);
    }

    @Override
    public void SetNotFirstRun(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(FIRST_RUN, false);
        editor.apply();
    }

    @Override
    public int getGlobalLevelsCount() {
        return Global.defaultLevels.length;
    }

    @Override
    public int GetLevelWordsCount(int level_i) {
        return Global.defaultLevels[level_i].length;
    }

    @Override
    public void InsertCardIntoDb(ContentValues registro, Context context) {

        //Crear objeto administrador de base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        BD.insert("cards", null, registro);

        BD.close();
    }
}
