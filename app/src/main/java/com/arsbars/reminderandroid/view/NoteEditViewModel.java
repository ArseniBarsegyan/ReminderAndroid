package com.arsbars.reminderandroid.view;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.Note;
import com.arsbars.reminderandroid.data.Repository;

public class NoteEditViewModel extends ViewModel {
    private Repository repository;

    public String getNoteDescription(long noteId) {
        for (NoteViewModel viewModel : repository.getNotes()) {
            if (viewModel.getId() == noteId) {
                return viewModel.getDescription();
            }
        }
        return "";
    }

    public NoteEditViewModel(Repository repository) {
        this.repository = repository;
    }

    public Note createNote(String description) {
        return repository.createNote(description);
    }

    public void editNote(long id, String description) {
        repository.editNote(id, description);
    }
}
