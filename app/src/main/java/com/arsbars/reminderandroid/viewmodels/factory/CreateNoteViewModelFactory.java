package com.arsbars.reminderandroid.viewmodels.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.note.NoteRepository;
import com.arsbars.reminderandroid.viewmodels.NoteEditViewModel;

public class CreateNoteViewModelFactory implements ViewModelProvider.Factory {
    private NoteRepository noteRepository;

    public CreateNoteViewModelFactory(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NoteEditViewModel(noteRepository);
    }
}