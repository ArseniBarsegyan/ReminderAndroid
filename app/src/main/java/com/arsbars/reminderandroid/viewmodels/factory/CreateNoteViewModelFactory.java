package com.arsbars.reminderandroid.viewmodels.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.Repository;
import com.arsbars.reminderandroid.viewmodels.NoteEditViewModel;

public class CreateNoteViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;

    public CreateNoteViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NoteEditViewModel(repository);
    }
}