package com.arsbars.reminderandroid.data.user;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arsbars.reminderandroid.data.base.DBEntry;
import com.arsbars.reminderandroid.data.base.DbSettings;

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
                    cursor.getBlob(userPasswordIndex), cursor.getBlob(imageContentIndex));
        }
        cursor.close();
        db.close();
        return null;
    }

    public User createUser(String username, byte[] password) {
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

    public User getUserByName(String name) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        User user = null;

        Cursor cursor = db.rawQuery("SELECT "
                + "[" + DBEntry.COLUMN_ID + "],"
                + "[" + DBEntry.USERNAME + "],"
                + "[" + DBEntry.IMAGE_CONTENT + "],"
                + "[" + DBEntry.PASSWORD
                + "] FROM "
                + DBEntry.USERS_TABLE
                + " WHERE "
                + DBEntry.USERNAME + " = ?", new String[] {name});
        if (cursor.moveToNext()) {
            int userIdIndex = cursor.getColumnIndex(DBEntry.COLUMN_ID);
            int userPasswordIndex = cursor.getColumnIndex(DBEntry.PASSWORD);
            int userNameIndex = cursor.getColumnIndex(DBEntry.USERNAME);
            int userImageIndex = cursor.getColumnIndex(DBEntry.IMAGE_CONTENT);

            user = new User(cursor.getLong(userIdIndex), cursor.getString(userNameIndex),
                    cursor.getBlob(userPasswordIndex), cursor.getBlob(userImageIndex));
        }
        cursor.close();
        db.close();
        return user;
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

    public void editUser(long userId, String username, byte[] password, byte[] imageContent) {
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
