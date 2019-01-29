package com.arsbars.reminderandroid.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDbHelper extends SQLiteOpenHelper {
    public NotesDbHelper(Context context) {
        super(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + DbSettings.DBEntry.NOTES_TABLE +" (" +
                DbSettings.DBEntry._ID + "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DbSettings.DBEntry.DESCRIPTION + " TEXT NOT NULL, " +
                DbSettings.DBEntry.CREATE_DATE + " DATETIME NOT NULL, " +
                DbSettings.DBEntry.EDIT_DATE + " DATETIME NOT NULL);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbSettings.DBEntry.NOTES_TABLE);
        onCreate(db);
    }
}
