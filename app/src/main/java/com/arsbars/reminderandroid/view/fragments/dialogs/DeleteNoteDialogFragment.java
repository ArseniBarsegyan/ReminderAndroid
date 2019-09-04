package com.arsbars.reminderandroid.view.fragments.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.view.fragments.NotesFragment;
import com.arsbars.reminderandroid.viewmodels.NotesViewModel;

public class DeleteNoteDialogFragment extends DialogFragment {
    private static NotesViewModel notesViewModel;
    private static long viewModelId;

    public static DeleteNoteDialogFragment newInstance(NotesViewModel viewModel, long id) {
        DeleteNoteDialogFragment fragment = new DeleteNoteDialogFragment();
        notesViewModel = viewModel;
        viewModelId = id;
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MainActivity activity = (MainActivity)getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(R.string.delete_note_question)
                .setPositiveButton(R.string.delete, (dialog, id) -> {
                    notesViewModel.removeNote(viewModelId);
                    if (activity != null) {
                        activity.navigateToRoot(getResources().getString(R.string.notes),
                                NotesFragment.newInstance());
                    }
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                });
        return builder.create();
    }
}
