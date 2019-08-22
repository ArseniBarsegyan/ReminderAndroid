package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.note.Note;
import com.arsbars.reminderandroid.data.note.NoteRepository;

public class NoteEditViewModel extends ViewModel {
    private NoteRepository noteRepository;

    public String getNoteDescription(long noteId) {
        for (NoteViewModel viewModel : noteRepository.getNotes()) {
            if (viewModel.getId() == noteId) {
                return viewModel.getDescription();
            }
        }
        return "";
    }

    public NoteEditViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(String description) {
        return noteRepository.createNote(description);
    }

    public void editNote(long id, String description) {
        noteRepository.editNote(id, description);
    }
}
