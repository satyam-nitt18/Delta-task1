package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.lang.annotation.Target;

public class databaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="Scoreboard.db";
    public static final String TABLE_NAME="Scoreboard_table";
    public static final String COL1="Win";
    public static final String COL2="Loss";



    public databaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
            String createTable="CREATE TABLE "+TABLE_NAME+ "(Win INTEGER, Loss INTEGER)";
            db.execSQL(createTable);


        ContentValues contentValues=new ContentValues();
        contentValues.put("Win",0);
        contentValues.put("Loss", 0);

        db.insert(TABLE_NAME, null, contentValues);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public Cursor showData()
    {
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM "+TABLE_NAME, null);
        return data;
    }







    public boolean upgradeData(int win, int loss) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Win", contentValues.getAsInteger("Win")+win);
        contentValues.put("Loss", contentValues.getAsInteger("Loss")+loss);
        db.update(TABLE_NAME, contentValues, "Win + Loss", new String[]{"Win", "Loss"});
      return true;
    }

}