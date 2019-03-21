package com.arsbars.reminderandroid.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.arsbars.reminderandroid.MainActivity;
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

        final MainActivity activity = (MainActivity) getActivity();
        if (activity != null) {
            activity.findViewById(R.id.create_note_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String noteDescription = ((EditText)getActivity().findViewById(R.id.note_description)).getText().toString();
                    if (noteDescription.matches(".*\\w.*")) {
                        createNoteViewModel.createNote(noteDescription);
                        activity.navigateToRoot("Notes", NotesFragment.newInstance());
                    } else {
                        Toast.makeText(getContext(), "Can't create empty note", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
