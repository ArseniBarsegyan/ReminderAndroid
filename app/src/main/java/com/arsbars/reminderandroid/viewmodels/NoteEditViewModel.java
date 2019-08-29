package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.note.Note;
import com.arsbars.reminderandroid.data.note.NoteRepository;

public class NoteEditViewModel extends ViewModel {
    private NoteRepository noteRepository;

    public String getNoteDescription(long noteId, long userId) {
        for (NoteViewModel viewModel : noteRepository.getNotes(userId)) {
            if (viewModel.getId() == noteId) {
                return viewModel.getDescription();
            }
        }
        return "";
    }

    public NoteEditViewModel(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note createNote(String description, long userId) {
        return noteRepository.createNote(description, userId);
    }

    public void editNote(long id, String description) {
        noteRepository.editNote(id, description);
    }
}
