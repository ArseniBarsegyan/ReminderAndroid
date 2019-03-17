package com.arsbars.reminderandroid.view.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.NotesViewModel;

public class NotesViewModelFactory implements ViewModelProvider.Factory {
    private NotesDbHelper dbHelper;

    public NotesViewModelFactory(NotesDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NotesViewModel(dbHelper);
    }
}
