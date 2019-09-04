package com.arsbars.reminderandroid.viewmodels;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.business.mapper.Mapper;
import com.arsbars.reminderandroid.data.galleryItem.GalleryItemsRepository;
import com.arsbars.reminderandroid.data.note.NoteRepository;

import java.util.List;

public class NotesViewModel extends ViewModel {
    private NoteRepository noteRepository;
    private GalleryItemsRepository galleryItemsRepository;

    public NotesViewModel(NoteRepository noteRepository,
                          GalleryItemsRepository galleryItemsRepository) {
        this.noteRepository = noteRepository;
        this.galleryItemsRepository = galleryItemsRepository;
    }

    public List<GalleryItemViewModel> getGalleryItems(long noteId) {
        return Mapper.ToViewModels(this.galleryItemsRepository.getGalleryItems(noteId));
    }

    public List<NoteViewModel> getNotes(long userId) {
        return this.noteRepository.getNotes(userId);
    }

    public void removeNote(long id) {
        this.noteRepository.removeNote(id);
        this.galleryItemsRepository.removeGalleryItemsByNoteId(id);
    }
}
