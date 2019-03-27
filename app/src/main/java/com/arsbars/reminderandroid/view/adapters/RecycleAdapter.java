package com.arsbars.reminderandroid.view.adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.view.fragments.EditNoteFragment;
import com.arsbars.reminderandroid.view.fragments.dialogs.DeleteNoteDialogFragment;
import com.arsbars.reminderandroid.viewmodels.NoteViewModel;
import com.arsbars.reminderandroid.viewmodels.NotesViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<NoteViewModel> noteViewModels;
    private NotesViewModel notesViewModel;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        private TextView descriptionView, noteEditDateView;
        private ImageButton deleteNoteButton;

        ViewHolder(View view){
            super(view);
            descriptionView = (TextView)view.findViewById(R.id.noteDescription);
            noteEditDateView = (TextView)view.findViewById(R.id.noteEditDate);
            deleteNoteButton = (ImageButton)view.findViewById(R.id.delete_note_button);

            this.view = view;
        }
    }

    public RecycleAdapter(Context context, NotesViewModel viewModel) {
        this.noteViewModels = viewModel.getNotes();
        this.inflater = LayoutInflater.from(context);
        this.notesViewModel = viewModel;
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        NoteViewModel noteViewModel = noteViewModels.get(position);
        holder.descriptionView.setText(noteViewModel.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        String dateString = dateFormat.format(noteViewModel.getCreateDate());
        holder.noteEditDateView.setText(dateString);

        holder.view.setOnClickListener(v -> {
            new ChangeViewBackgroundTask().execute(holder.view);
            MainActivity activity = (MainActivity) holder.view.getContext();
            if (noteViewModel.getId() == 0) {
                activity.push(activity.getResources().getString(R.string.create_note_title),
                        EditNoteFragment.newInstance(noteViewModel.getId()));
            } else {
                activity.push(activity.getResources().getString(R.string.edit_note_title),
                        EditNoteFragment.newInstance(noteViewModel.getId()));
            }
        });
        holder.view.setOnLongClickListener(v -> {
            holder.deleteNoteButton.setVisibility(View.VISIBLE);
            holder.deleteNoteButton.setOnClickListener(x -> {
                new ChangeViewBackgroundTask().execute(holder.view);
                DeleteNoteDialogFragment deleteNoteDialogFragment = DeleteNoteDialogFragment.newInstance(notesViewModel, noteViewModel.getId());
                FragmentManager manager = ((AppCompatActivity)v.getContext()).getSupportFragmentManager();
                deleteNoteDialogFragment.show(manager, "delete_note_popup");
            });
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return noteViewModels.size();
    }

    static class ChangeViewBackgroundTask extends AsyncTask<View, Integer, Void> {
        @Override
        protected Void doInBackground(View... views) {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            for (View view : views) {
                mainHandler.post(() -> view.setBackgroundColor(view.getResources().getColor(R.color.list_selected_color)));
            }
            SystemClock.sleep(50);
            for (View view : views) {
                mainHandler.post(() -> view.setBackgroundColor(view.getResources().getColor(R.color.list_unselected_color)));
            }
            return null;
        }
    }
}
