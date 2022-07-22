package com.latorreencantada.abc9;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.latorreencantada.abc9.Models.Card;
import com.latorreencantada.abc9.root.MemoryContract;

public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {

        private static final String TEXT_TYPE = " TEXT";
        private static final String INTEGER_TYPE = " INTEGER";
        private static final String COMMA_SEP = ",";
        private static final String DATABASE_NAME = "administracion";
        private static final int DATABASE_VERSION = 3;



        public AdminSQLiteOpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase BD) {
            onUpgrade(BD, 0, 3);
        }

        @Override
        public void onUpgrade(SQLiteDatabase BD, int oldVersion, int newVersion) {

            if (oldVersion<1){
                BD.execSQL("create table players (nombre text, level int, bestScore int)");
                BD.execSQL("create table cards (word text, syl1 text, syl2 text, syl3 text, syl4 text, level int)");
            }
            if (oldVersion<4){
                // perform updates
                BD.execSQL("ALTER TABLE players ADD COLUMN profileImage text");
                BD.execSQL("ALTER TABLE cards ADD COLUMN image text");
            }
        }

        public Integer deleteAllData () {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("cards", null, null);
        }

    public boolean addMemory(Card card) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MemoryContract.MemoryEntry.COLUMN_WORD, card.getWord());
        values.put(MemoryContract.MemoryEntry.COLUMN_SYL1, card.getSyl(1));
        values.put(MemoryContract.MemoryEntry.COLUMN_SYL2, card.getSyl(2));
        values.put(MemoryContract.MemoryEntry.COLUMN_SYL3, card.getSyl(3));
        values.put(MemoryContract.MemoryEntry.COLUMN_SYL4, card.getSyl(4));
        values.put(MemoryContract.MemoryEntry.COLUMN_LEVEL, card.getLevel());
        values.put(MemoryContract.MemoryEntry.COLUMN_IMAGE, card.getImageAsString());

        return db.insert("cards", null, values) != -1;
    }

    }
