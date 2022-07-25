package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

public class Database extends SQLiteOpenHelper {

    final String databaseName = "Notes.db";

    public Database(Context context) {
        super(context, "Notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table notes(id TEXT primary key, title TEXT, note TEXT, date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int j) {
        MyDB.execSQL("drop Table if exists notes");
    }

    public Boolean insertData(String id, String title, String note, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", id);
        contentValues.put("title", title);
        contentValues.put("note", note);
        contentValues.put("date", date);
        long result = database.insert("notes", null, contentValues);
        return result != -1;
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery("select * from notes", null);
        return result;
    }
}