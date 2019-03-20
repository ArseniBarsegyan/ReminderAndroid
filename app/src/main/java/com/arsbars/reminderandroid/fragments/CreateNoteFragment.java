package com.arsbars.reminderandroid.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.CreateNoteViewModel;
import com.arsbars.reminderandroid.view.factory.CreateNoteViewModelFactory;

public class CreateNoteFragment extends Fragment {
    private CreateNoteViewModel createNoteViewModel;

    public static CreateNoteFragment newInstance() {
        return new CreateNoteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_note_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createNoteViewModel = ViewModelProviders
                .of(this, new CreateNoteViewModelFactory(new NotesDbHelper(getContext())))
                .get(CreateNoteViewModel.class);
    }
}
