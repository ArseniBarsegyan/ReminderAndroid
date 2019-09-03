package com.arsbars.reminderandroid.data.base;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsersTable = "CREATE TABLE IF NOT EXISTS " + DBEntry.USERS_TABLE +" (" +
                DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBEntry.USERNAME + " TEXT NOT NULL, " +
                DBEntry.PASSWORD + " BLOB NOT NULL, " +
                DBEntry.IMAGE_CONTENT + " BLOB);";
        String createNotesTable = "CREATE TABLE IF NOT EXISTS " + DBEntry.NOTES_TABLE +" (" +
                DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBEntry.DESCRIPTION + " TEXT NOT NULL, " +
                DBEntry.CREATE_DATE + " DATETIME NOT NULL, " +
                DBEntry.EDIT_DATE + " DATETIME NOT NULL," +
                DBEntry.COLUMN_USER_ID  + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DBEntry.COLUMN_USER_ID + ") " +
                "REFERENCES " + DBEntry.USERS_TABLE +
                "(" + DBEntry.COLUMN_ID + ")" + ");";
        String createNoteGalleryItemsTable = "CREATE TABLE IF NOT EXISTS " +
                DBEntry.GALLERY_ITEMS_TABLE +" (" +
                DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBEntry.IMAGE_PATH + " TEXT, " +
                DBEntry.THUMBNAIL + " TEXT NOT NULL, " +
                DBEntry.IS_VIDEO + " INTEGER NOT NULL, " +
                DBEntry.VIDEO_PATH + " TEXT," +
                DBEntry.LANDSCAPE  + " INTEGER NOT NULL, " +
                DBEntry.COLUMN_NOTE_ID + " INTEGER NOT NULL, " +
                "FOREIGN KEY (" + DBEntry.COLUMN_NOTE_ID + ") " +
                "REFERENCES " + DBEntry.NOTES_TABLE +
                "(" + DBEntry.COLUMN_ID + ")" + ");";

        db.execSQL(createUsersTable);
        db.execSQL(createNotesTable);
        db.execSQL(createNoteGalleryItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.USERS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.NOTES_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.GALLERY_ITEMS_TABLE);
        onCreate(db);
    }
}
