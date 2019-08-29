package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.note.NoteRepository;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private NoteRepository noteRepository;

    public NotesViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<NoteViewModel> getNotes(long userId) {
        return this.noteRepository.getNotes(userId);
    }

    public void removeNote(long id) {
        this.noteRepository.removeNote(id);
    }
}
