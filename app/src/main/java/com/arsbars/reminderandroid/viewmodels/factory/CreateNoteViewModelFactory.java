package com.arsbars.reminderandroid.viewmodels.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.galleryItem.GalleryItemsRepository;
import com.arsbars.reminderandroid.data.note.NoteRepository;
import com.arsbars.reminderandroid.viewmodels.NoteEditViewModel;

public class CreateNoteViewModelFactory implements ViewModelProvider.Factory {
    private NoteRepository noteRepository;
    private GalleryItemsRepository galleryItemsRepository;

    public CreateNoteViewModelFactory(NoteRepository noteRepository,
                                      GalleryItemsRepository galleryItemsRepository) {
        this.noteRepository = noteRepository;
        this.galleryItemsRepository = galleryItemsRepository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NoteEditViewModel(noteRepository, galleryItemsRepository);
    }
}