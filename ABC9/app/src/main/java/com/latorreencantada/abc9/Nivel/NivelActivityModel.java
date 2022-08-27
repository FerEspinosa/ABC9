package com.latorreencantada.abc9.Nivel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;

import com.latorreencantada.abc9.Models.Card;

import java.util.ArrayList;


public class NivelActivityModel implements NivelActivityMVP.Model{

    private final NivelInterface dataInterface;

    public NivelActivityModel(NivelInterface dataInterface) {
        this.dataInterface = dataInterface;
    }


    @SuppressLint("Range")
    @Override
    public ArrayList<Card> GetLevelWords(int playerLevel, Context context) {

        // Obtener cursor con las palabaras del nivel del usuario
        Cursor c = dataInterface.getLevelWordsFromDb(playerLevel, context);

        // construir un ArrayList de cards del nivel recibido
        if (c !=null && c.moveToFirst()){

            ArrayList<Card> levelCardList = new ArrayList<>();
            c.moveToFirst();

            while(!c.isAfterLast()) {
                Card card = new Card(
                        c.getString(0),     // word
                        c.getString(1),     // syl1
                        c.getString(2),     // syl2
                        c.getString(3),     // syl3
                        c.getString   (4),  // Syl4
                        c.getInt   (5),  // level
                        c.getString(6) ,       // image
                        c.getString(7)      // id
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

    @Override
    public int GetHighestUnlockedLevel(Context context) {
        return dataInterface.GetHighestUnlockedLevelFromSharedPref(context);
    }

    @Override
    public void SetHighestUnlockedLevel(Context context, int highestUnlockedLevel) {
        dataInterface.SetHighestUnlockedLevelFromSharedPref(context, highestUnlockedLevel);
    }

    @Override
    public Bitmap getImageBitmap(String image) {

        return Card.stringToBitmap(image);
    }


}
