package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.Repository;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private Repository repository;

    public NotesViewModel(Repository repository) {
        this.repository = repository;
    }

    public List<NoteViewModel> getNotes() {
        return this.repository.getNotes();
    }

    public void removeNote(long id) {
        this.repository.removeNote(id);
    }
}
