package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.galleryItem.GalleryItemsRepository;
import com.arsbars.reminderandroid.data.note.Note;
import com.arsbars.reminderandroid.data.note.NoteRepository;

import java.util.List;

public class NoteEditViewModel extends ViewModel {
    private NoteRepository noteRepository;
    private GalleryItemsRepository galleryItemsRepository;

    public String getNoteDescription(long noteId, long userId) {
        for (NoteViewModel viewModel : noteRepository.getNotes(userId)) {
            if (viewModel.getId() == noteId) {
                return viewModel.getDescription();
            }
        }
        return "";
    }

    public NoteEditViewModel(NoteRepository noteRepository,
                             GalleryItemsRepository galleryItemsRepository) {
        this.noteRepository = noteRepository;
        this.galleryItemsRepository = galleryItemsRepository;
    }

    public List<GalleryItemViewModel> getGalleryItems(long noteId) {
        return this.galleryItemsRepository.getGalleryItems(noteId);
    }

    public Note createNote(String description, long userId,
                           List<GalleryItemViewModel> galleryItemViewModels) {
        Note note = noteRepository.createNote(description, userId);

        for (GalleryItemViewModel viewModel : galleryItemViewModels) {
            viewModel.setNoteId(note.getId());
            galleryItemsRepository.createGalleryItem(viewModel);
        }
        return note;
    }

    public void editNote(long id, String description,
                         List<GalleryItemViewModel> galleryItemViewModels) {
        noteRepository.editNote(id, description);
        for (GalleryItemViewModel viewModel : galleryItemViewModels) {
            viewModel.setNoteId(id);
            if (viewModel.getId() == 0) {
                galleryItemsRepository.createGalleryItem(viewModel);
            } else {
                galleryItemsRepository.editGalleryItem(viewModel);
            }
        }
    }
}
