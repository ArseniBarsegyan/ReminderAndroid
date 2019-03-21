package com.arsbars.reminderandroid.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.NotesViewModel;
import com.arsbars.reminderandroid.view.adapters.RecycleAdapter;
import com.arsbars.reminderandroid.view.factory.NotesViewModelFactory;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;
    private MainActivity activity;

    public static NotesFragment newInstance() {
        return new NotesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.notes_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        notesViewModel = ViewModelProviders
                .of(this, new NotesViewModelFactory(new NotesDbHelper(getContext())))
                .get(NotesViewModel.class);

        RecyclerView notesRecycleView = getActivity().findViewById(R.id.notesRecycleView);
        RecycleAdapter recycleAdapter = new RecycleAdapter(getContext(), notesViewModel.getNotes());
        notesRecycleView.setAdapter(recycleAdapter);

        activity = (MainActivity)getActivity();
        activity.navigateToRoot("Notes", this);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.push_create_note_view_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.push("Create note", CreateNoteFragment.newInstance());
            }
        });
    }
}
