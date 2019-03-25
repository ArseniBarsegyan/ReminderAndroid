package com.arsbars.reminderandroid.view.factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.data.Repository;
import com.arsbars.reminderandroid.view.NotesViewModel;

public class NotesViewModelFactory implements ViewModelProvider.Factory {
    private Repository repository;

    public NotesViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new NotesViewModel(repository);
    }
}
