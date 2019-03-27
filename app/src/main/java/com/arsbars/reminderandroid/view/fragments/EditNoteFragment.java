package com.arsbars.reminderandroid.view.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.data.NotesDbHelper;
import com.arsbars.reminderandroid.data.Repository;
import com.arsbars.reminderandroid.viewmodels.NoteEditViewModel;
import com.arsbars.reminderandroid.viewmodels.factory.CreateNoteViewModelFactory;

public class EditNoteFragment extends Fragment {
    private long noteId;
    private NoteEditViewModel noteEditViewModel;

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
                .of(this, new CreateNoteViewModelFactory(new Repository(new NotesDbHelper(getContext()))))
                .get(NoteEditViewModel.class);

        final MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            EditText noteDescriptionText = activity.findViewById(R.id.note_description);
            noteDescriptionText.setText(noteEditViewModel.getNoteDescription(noteId));

            Button confirmButton = activity.findViewById(R.id.create_note_button);
            if (noteId == 0) {
                confirmButton.setText(getResources().getText(R.string.create));
            } else {
                confirmButton.setText(getResources().getText(R.string.edit));
            }

            confirmButton.setOnClickListener(v -> {
                String noteDescription = ((EditText)getActivity().findViewById(R.id.note_description))
                        .getText()
                        .toString();
                if (noteDescription.matches(".*\\w.*")) {
                    if (this.noteId == 0) {
                        noteEditViewModel.createNote(noteDescription);
                    } else {
                        noteEditViewModel.editNote(this.noteId, noteDescription);
                    }

                    activity.navigateToRoot(getString(R.string.notes), NotesFragment.newInstance());
                } else {
                    Toast.makeText(getContext(), getString(R.string.note_create_error), Toast.LENGTH_SHORT).show();
                }
            });
            activity.findViewById(R.id.cancel_note_create_button).setOnClickListener(v ->
                    activity.navigateToRoot(getResources().getString(R.string.notes), NotesFragment.newInstance()));
        }
    }
}
