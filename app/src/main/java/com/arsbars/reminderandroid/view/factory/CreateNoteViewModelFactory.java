package com.arsbars.reminderandroid.view.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.CreateNoteViewModel;

public class CreateNoteViewModelFactory implements ViewModelProvider.Factory {
    private NotesDbHelper dbHelper;

    public CreateNoteViewModelFactory(NotesDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CreateNoteViewModel(dbHelper);
    }
}