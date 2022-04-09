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
            BD.execSQL("create table puntaje (nombre text, score int)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase BD, int oldVersion, int newVersion) {

        }
    }
