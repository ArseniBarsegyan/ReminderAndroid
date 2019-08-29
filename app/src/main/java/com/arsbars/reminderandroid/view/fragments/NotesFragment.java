package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.base.DatabaseHelper;
import com.arsbars.reminderandroid.data.note.NoteRepository;
import com.arsbars.reminderandroid.data.user.User;
import com.arsbars.reminderandroid.data.user.UserRepository;
import com.arsbars.reminderandroid.viewmodels.NotesViewModel;
import com.arsbars.reminderandroid.view.adapters.RecycleAdapter;
import com.arsbars.reminderandroid.viewmodels.factory.NotesViewModelFactory;

public class NotesFragment extends Fragment {
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
        NotesViewModel notesViewModel = ViewModelProviders
                .of(this, new NotesViewModelFactory(
                        new NoteRepository(
                                new DatabaseHelper(getContext()))))
                .get(NotesViewModel.class);

        activity = (MainActivity)getActivity();

        RecyclerView notesRecycleView = activity.findViewById(R.id.notesRecycleView);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String currentUsername = prefs.getString(getResources().getString(R.string.user_name_preference),
                "");
        if (!currentUsername.equals("")) {
            UserRepository repository = new UserRepository(
                    new DatabaseHelper(activity.getApplicationContext()));
            User currentUser = repository.getUserByName(currentUsername);

            if (currentUser != null) {
                RecycleAdapter recycleAdapter = new RecycleAdapter(getContext(), notesViewModel,
                        currentUser.getId());
                notesRecycleView.setAdapter(recycleAdapter);

                FloatingActionButton fab = activity.findViewById(R.id.push_create_note_view_button);
                fab.setOnClickListener(view -> activity.push(getString(R.string.create_note_title),
                        EditNoteFragment.newInstance(0)));
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        activity.navigateToRoot(getString(R.string.notes), this);
    }
}
