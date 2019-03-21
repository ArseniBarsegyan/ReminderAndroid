package com.arsbars.reminderandroid.view;

import android.arch.lifecycle.ViewModel;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.arsbars.reminderandroid.data.DbSettings;
import com.arsbars.reminderandroid.data.Note;
import com.arsbars.reminderandroid.data.NotesDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateNoteViewModel extends ViewModel {
    private NotesDbHelper dbHelper;

    public CreateNoteViewModel(NotesDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Note createNote(String description) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        Date today = Calendar.getInstance().getTime();
        String createDate = df.format(today);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbSettings.DBEntry.DESCRIPTION, description);
        values.put(DbSettings.DBEntry.CREATE_DATE, createDate);
        values.put(DbSettings.DBEntry.EDIT_DATE, createDate);
        long id = db.insertWithOnConflict(DbSettings.DBEntry.NOTES_TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        Note note = new Note(id, description, today, today);
        return new Note(note);
    }
}
