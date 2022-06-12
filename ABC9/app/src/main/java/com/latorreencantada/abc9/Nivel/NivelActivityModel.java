package com.latorreencantada.abc9.Nivel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.latorreencantada.abc9.AdminSQLiteOpenHelper;
import com.latorreencantada.abc9.Card;

import java.util.ArrayList;


public class NivelActivityModel implements NivelActivityMVP.Model{

    private final MemoryInterface memory;

    public NivelActivityModel(MemoryInterface memory) {
        this.memory = memory;
    }


    @SuppressLint("Range")
    @Override
    public ArrayList<Card> getLevelWords (int playerLevel, Context context) {

        //Crear objeto administrador de base de datos

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion", null, 1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        Cursor c = bd.rawQuery
                ("SELECT * FROM cards WHERE level = "+playerLevel, null);

        // construir un ArrayList de cards del nivel recibido

        if (c !=null && c.moveToFirst()){

            ArrayList<Card> levelCardList = new ArrayList<Card>();
            c.moveToFirst();

            while(!c.isAfterLast()) {
                Card card = new Card(
                        c.getString(0), // word
                        c.getString(1), // syl1
                        c.getString(2), // syl2
                        c.getString(3), // syl3
                        c.getString(4), // syl4
                        c.getInt   (5)  // level
                );

                levelCardList.add(card);
                c.moveToNext();
            }

            return levelCardList;

        } else {
            // si el cursor es nulo o está vacío:
            return null;
        }

    }
}
