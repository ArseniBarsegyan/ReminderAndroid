package com.arsbars.reminderandroid.data.note;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arsbars.reminderandroid.data.base.DBEntry;
import com.arsbars.reminderandroid.data.base.DbSettings;

public class NotesDbHelper extends SQLiteOpenHelper {
    public NotesDbHelper(Context context) {
        super(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + DBEntry.NOTES_TABLE +" (" +
                DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBEntry.DESCRIPTION + " TEXT NOT NULL, " +
                DBEntry.CREATE_DATE + " DATETIME NOT NULL, " +
                DBEntry.EDIT_DATE + " DATETIME NOT NULL," +
                DBEntry.COLUMN_USER_ID  + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DBEntry.COLUMN_USER_ID + ") " +
                "REFERENCES " + DBEntry.USERS_TABLE +
                "(" + DBEntry.COLUMN_ID + ")" + ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.NOTES_TABLE);
        onCreate(db);
    }
}
