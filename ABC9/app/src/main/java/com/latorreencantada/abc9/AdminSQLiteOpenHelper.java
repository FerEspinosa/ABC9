package com.latorreencantada.abc9;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

    public class AdminSQLiteOpenHelper  extends SQLiteOpenHelper {
        public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase BD) {
            BD.execSQL("create table players (nombre text, level int, bestScore int)");
            BD.execSQL("create table cards (word text, syl1 text, syl2 text, syl3 text, syl4 text, level int)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase BD, int oldVersion, int newVersion) {

        }

        public Integer deleteAllData () {
            SQLiteDatabase db = this.getWritableDatabase();
            return db.delete("cards", null, null);
        }
    }
