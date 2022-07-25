package com.example.aptica;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    public Boolean updateDatabase(String id, String title, String note, String date) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery("select * from notes where id=?", new String[]{id});
        if(result.getCount()!=0) {
            database.execSQL("update notes set title=?, note=?, date=? where id=?", new String[]{title, note, date, id});
            return true;
        }
        return false;
    }

    public Boolean deleteRows(String id) {
        SQLiteDatabase database = getWritableDatabase();
        Cursor result = database.rawQuery("select * from notes where id=?", new String[]{id});
        if(result.getCount()!=0) {
            database.execSQL("delete from notes where id=?", new String[]{id});
            return true;
        }
        return false;
    }

    public Cursor getCurrentDataId(String updateId) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery("select * from notes where id=?", new String[]{updateId});
        return result;
    }

    public Cursor getData() {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor result = database.rawQuery("select * from notes", null);
        return result;
    }
}