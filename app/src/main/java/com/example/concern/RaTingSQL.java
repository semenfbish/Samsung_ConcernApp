package com.example.concern;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RaTingSQL extends SQLiteOpenHelper {
    static SQLiteDatabase db;
    public RaTingSQL(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String RAT = "CREATE TABLE RATING" +
                "    (id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    TIME  TEXT," +
                "NAME TEXT NOT NULL)";
        db.execSQL(RAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
