package com.arsbars.reminderandroid.data.user;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.arsbars.reminderandroid.data.base.DBEntry;
import com.arsbars.reminderandroid.data.base.DbSettings;

public class UserDbHelper extends SQLiteOpenHelper {
    public UserDbHelper(Context context) {
        super(context, DbSettings.DB_NAME, null, DbSettings.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE IF NOT EXISTS " + DBEntry.USERS_TABLE +" (" +
                DBEntry.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DBEntry.USERNAME + " TEXT NOT NULL, " +
                DBEntry.PASSWORD + " TEXT NOT NULL, " +
                DBEntry.IMAGE_CONTENT + " BLOB);";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DBEntry.USERS_TABLE);
        onCreate(db);
    }
}
