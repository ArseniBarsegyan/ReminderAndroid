package com.arsbars.reminderandroid.data.note;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.arsbars.reminderandroid.data.base.DBEntry;
import com.arsbars.reminderandroid.data.base.DatabaseHelper;
import com.arsbars.reminderandroid.viewmodels.NoteViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteRepository {
    private ArrayList<Note> notes;
    private DatabaseHelper dbHelper;

    public NoteRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<NoteViewModel> getNotes(long userId) {
        if (notes == null) {
            notes = new ArrayList<>();
            loadNotes(userId);
        }
        ArrayList<NoteViewModel> clonedNotes = new ArrayList<>(notes.size());
        for (int i = 0; i < notes.size(); i++) {
            clonedNotes.add(new NoteViewModel(notes.get(i)));
        }
        return clonedNotes;
    }

    private void loadNotes(long userId) {
        notes.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBEntry.NOTES_TABLE,
                new String[]{
                        DBEntry.COLUMN_ID,
                        DBEntry.DESCRIPTION,
                        DBEntry.CREATE_DATE,
                        DBEntry.EDIT_DATE
                },
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            try {
                int idIndex = cursor.getColumnIndex(DBEntry.COLUMN_ID);
                int descriptionIndex = cursor.getColumnIndex(DBEntry.DESCRIPTION);
                int createdDateIndex = cursor.getColumnIndex(DBEntry.CREATE_DATE);
                int editDateIndex = cursor.getColumnIndex(DBEntry.EDIT_DATE);

                Date createdDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US)
                        .parse(cursor.getString(createdDateIndex));
                Date editDate = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US)
                        .parse(cursor.getString(editDateIndex));

                Note note = new Note(cursor.getLong(idIndex), cursor.getString(descriptionIndex),
                        createdDate, editDate, userId);
                notes.add(note);
            } catch (ParseException e) {
                Log.d("DateParsingException",
                        " Error during parsing date when reading Note instance from database "
                                + e.getMessage());
                e.printStackTrace();
            }
        }
        cursor.close();
        db.close();
        Collections.reverse(notes);
    }

    public Note createNote(String description, long userId) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String createDate = df.format(today);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBEntry.DESCRIPTION, description);
        values.put(DBEntry.CREATE_DATE, createDate);
        values.put(DBEntry.EDIT_DATE, createDate);
        values.put(DBEntry.COLUMN_USER_ID, userId);
        long id = db.insertWithOnConflict(DBEntry.NOTES_TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        return new Note(id, description, today, today, userId);
    }

    public void removeNote(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                DBEntry.NOTES_TABLE,
                DBEntry.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)}
        );
        db.close();

        int index = -1;
        for (int i = 0; i < notes.size(); i++) {
            Note note = notes.get(i);
            if (note.getId() == id) {
                index = i;
            }
        }
        if (index != -1) {
            notes.remove(index);
        }
    }

    public void editNote(long noteId, String description) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String editDate = df.format(today);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBEntry.DESCRIPTION, description);
        values.put(DBEntry.EDIT_DATE, editDate);
        long id = db.update(DBEntry.NOTES_TABLE,
                values,
                DBEntry.COLUMN_ID + "=" + String.valueOf(noteId),
                null);
        db.close();
    }
}
