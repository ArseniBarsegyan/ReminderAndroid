package com.arsbars.reminderandroid.data.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arsbars.reminderandroid.data.base.DBEntry;

public class UserRepository {
    private UserDbHelper dbHelper;

    public UserRepository(UserDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    private User loadUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBEntry.USERS_TABLE,
                new String[]{
                        DBEntry.COLUMN_ID,
                        DBEntry.USERNAME,
                        DBEntry.PASSWORD,
                        DBEntry.IMAGE_CONTENT
                },
                null, null, null, null, null);
        if (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DBEntry.COLUMN_ID);
            int userNameIndex = cursor.getColumnIndex(DBEntry.USERNAME);
            int userPasswordIndex = cursor.getColumnIndex(DBEntry.PASSWORD);
            int imageContentIndex = cursor.getColumnIndex(DBEntry.IMAGE_CONTENT);

            cursor.close();
            db.close();

            return new User(cursor.getLong(idIndex), cursor.getString(userNameIndex),
                    cursor.getString(userPasswordIndex), cursor.getBlob(imageContentIndex));
        }
        return null;
    }

    public User createUser(String username, String password) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBEntry.USERNAME, username);
        values.put(DBEntry.PASSWORD, password);
        values.put(DBEntry.IMAGE_CONTENT, new byte[0]);
        long id = db.insertWithOnConflict(DBEntry.USERS_TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return new User(id, username, password, new byte[0]);
    }

    public void removeUser(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                DBEntry.USERS_TABLE,
                DBEntry.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)}
        );
        db.close();
    }

    public void editUser(long userId, String username, String password, byte[] imageContent) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBEntry.USERNAME, username);
        values.put(DBEntry.PASSWORD, password);
        values.put(DBEntry.IMAGE_CONTENT, imageContent);
        long id = db.update(DBEntry.USERS_TABLE,
                values,
                DBEntry.COLUMN_ID + "=" + String.valueOf(userId),
                null);
        db.close();
    }
}
