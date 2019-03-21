package com.arsbars.reminderandroid.view;

import android.arch.lifecycle.ViewModel;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.arsbars.reminderandroid.data.DbSettings;
import com.arsbars.reminderandroid.data.Note;
import com.arsbars.reminderandroid.data.NotesDbHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NotesViewModel extends ViewModel {
    private NotesDbHelper dbHelper;
    private ArrayList<Note> notes;

    public NotesViewModel(NotesDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<NoteViewModel> getNotes() {
        if (notes == null) {
            notes = new ArrayList<>();
            loadNotes();
        }
        ArrayList<NoteViewModel> clonedNotes = new ArrayList<>(notes.size());
        for (int i = 0; i < notes.size(); i++) {
            clonedNotes.add(new NoteViewModel(notes.get(i)));
        }
        return clonedNotes;
    }

    private void loadNotes() {
        notes.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DbSettings.DBEntry.NOTES_TABLE,
                new String[]{
                        DbSettings.DBEntry.COLUMN_ID,
                        DbSettings.DBEntry.DESCRIPTION,
                        DbSettings.DBEntry.CREATE_DATE,
                        DbSettings.DBEntry.EDIT_DATE
                },
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            try {
                int idIndex = cursor.getColumnIndex(DbSettings.DBEntry.COLUMN_ID);
                int descriptionIndex = cursor.getColumnIndex(DbSettings.DBEntry.DESCRIPTION);
                int createdDateIndex = cursor.getColumnIndex(DbSettings.DBEntry.CREATE_DATE);
                int editDateIndex = cursor.getColumnIndex(DbSettings.DBEntry.EDIT_DATE);

                Date createdDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)
                        .parse(cursor.getString(createdDateIndex));
                Date editDate = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.US)
                        .parse(cursor.getString(createdDateIndex));

                Note note = new Note(cursor.getLong(idIndex), cursor.getString(descriptionIndex),
                        createdDate, editDate);
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
    }

    public void removeNote(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                DbSettings.DBEntry.NOTES_TABLE,
                DbSettings.DBEntry.COLUMN_ID + " = ?",
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
}
