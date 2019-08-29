package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.base.DatabaseHelper;
import com.arsbars.reminderandroid.data.note.NoteRepository;
import com.arsbars.reminderandroid.data.user.User;
import com.arsbars.reminderandroid.data.user.UserRepository;
import com.arsbars.reminderandroid.viewmodels.NoteEditViewModel;
import com.arsbars.reminderandroid.viewmodels.factory.CreateNoteViewModelFactory;

public class EditNoteFragment extends Fragment {
    private long noteId;
    private NoteEditViewModel noteEditViewModel;
    private MainActivity activity;

    public static EditNoteFragment newInstance(long noteId) {
        EditNoteFragment fragment = new EditNoteFragment();
        fragment.noteId = noteId;
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_note_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        noteEditViewModel = ViewModelProviders
                .of(this, new CreateNoteViewModelFactory(new NoteRepository(new DatabaseHelper(getContext()))))
                .get(NoteEditViewModel.class);

        this.activity = (MainActivity) getActivity();
        if (activity != null) {
            EditText noteDescriptionText = activity.findViewById(R.id.note_description);

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String userName = prefs.getString(getResources().getString(R.string.user_name_preference),
                    "");

            if (!userName.equals("")) {
                UserRepository repository = new UserRepository(
                        new DatabaseHelper(activity.getApplicationContext()));
                User currentUser = repository.getUserByName(userName);

                if (currentUser != null) {
                    noteDescriptionText.setText(noteEditViewModel.getNoteDescription(noteId,
                            currentUser.getId()));

                    Button confirmButton = this.activity.findViewById(R.id.create_note_button);
                    if (noteId == 0) {
                        confirmButton.setText(getResources().getText(R.string.create));
                    } else {
                        confirmButton.setText(getResources().getText(R.string.edit));
                    }

                    confirmButton.setOnClickListener(v -> {
                        String noteDescription = ((EditText)getActivity().findViewById(R.id.note_description))
                                .getText()
                                .toString();
                        if (noteDescription.trim().equals("")) {
                            Toast.makeText(getContext(), getString(R.string.note_create_error),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            if (this.noteId == 0) {
                                noteEditViewModel.createNote(noteDescription, currentUser.getId());
                            } else {
                                noteEditViewModel.editNote(this.noteId, noteDescription);
                            }
                            this.activity.navigateToRoot(getString(R.string.notes),
                                    NotesFragment.newInstance());
                        }
                    });
                    this.activity.findViewById(R.id.cancel_note_create_button).setOnClickListener(v ->
                            this.activity.navigateToRoot(getResources().getString(R.string.notes),
                                    NotesFragment.newInstance()));
                }
            }
        }
    }
}
