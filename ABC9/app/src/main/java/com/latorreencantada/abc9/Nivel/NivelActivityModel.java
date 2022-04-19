package com.latorreencantada.abc9.Nivel;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;

public class NivelActivityModel implements NivelActivityMVP.Model{

    private final MemoryInterface memory;


    public NivelActivityModel(MemoryInterface memory) {
        this.memory = memory;
    }

    @Override
    public String[][] getLevelWords (int playerLevel) {

        //Crear objeto administrador de base de datos

        /*
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);


        SQLiteDatabase bd= admin.getWritableDatabase();
        Cursor fila = bd.rawQuery
                ("SELECT * FROM cards WHERE level ="+ playerLevel, null);

        int numOfwords = fila.getCount();
        System.out.println(numOfwords);
        */
        return null;
    }
}
