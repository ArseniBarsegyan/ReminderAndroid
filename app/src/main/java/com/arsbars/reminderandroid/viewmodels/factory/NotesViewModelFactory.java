package com.arsbars.reminderandroid.viewmodels.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.note.NoteRepository;
import com.arsbars.reminderandroid.viewmodels.NotesViewModel;

public class NotesViewModelFactory implements ViewModelProvider.Factory {
    private NoteRepository noteRepository;

    public NotesViewModelFactory(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NotesViewModel(noteRepository);
    }
}
