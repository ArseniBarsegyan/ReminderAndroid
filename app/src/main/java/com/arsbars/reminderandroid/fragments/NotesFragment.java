package com.arsbars.reminderandroid.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.view.NotesViewModel;
import com.arsbars.reminderandroid.view.adapters.RecycleAdapter;
import com.arsbars.reminderandroid.view.factory.NotesViewModelFactory;

public class NotesFragment extends Fragment {
    private NotesViewModel notesViewModel;

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

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateNoteFragment createNoteFragment = CreateNoteFragment.newInstance();
                fragmentTransaction.replace(R.id.root_layout, createNoteFragment);
                fragmentTransaction.commit();
            }
        });
    }
}
