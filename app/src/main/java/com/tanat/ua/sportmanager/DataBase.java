package com.tanat.ua.sportmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by TaNaT on 10.05.2016.
 */
public class DataBase extends SQLiteOpenHelper {

    static SQLiteDatabase db;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "SportManager.db";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + "periods" + " ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT, "
                + "start_wegiht INTEGER, "
                + "end_wegiht INTEGER, "
                + "date TEXT, "
                + "description TEXT"
                + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static void insert(ContentValues contentValues, String nameTable){
        db.insert(nameTable, null, contentValues);
    }

    public static void update (ContentValues contentValues, String nameTable, String where){
        db.update(nameTable, contentValues, where, null);

        Log.d("mLog","update " + Integer.toString(db.update("periods", contentValues, where, null)));
    }

    public static void delete (String nameTable, String where){
        db.delete(nameTable, where, null);

        Log.d("mLog","delete " + where);
    }
}