package com.arsbars.reminderandroid.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.arsbars.reminderandroid.MainActivity;
import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.business.dto.GalleryItemDto;
import com.arsbars.reminderandroid.view.fragments.EditNoteFragment;
import com.arsbars.reminderandroid.view.fragments.dialogs.DeleteNoteDialogFragment;
import com.arsbars.reminderandroid.viewmodels.GalleryItemViewModel;
import com.arsbars.reminderandroid.viewmodels.NoteViewModel;
import com.arsbars.reminderandroid.viewmodels.NotesViewModel;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<NoteViewModel> noteViewModels;
    private List<GalleryItemViewModel> galleryItemViewModels;
    private NotesViewModel notesViewModel;

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        private ImageView noteTitleImage;
        private TextView descriptionView, noteEditDateView;
        private ImageButton deleteNoteButton;

        ViewHolder(View view){
            super(view);
            noteTitleImage = view.findViewById(R.id.note_title_photo);
            descriptionView = view.findViewById(R.id.noteDescription);
            noteEditDateView = view.findViewById(R.id.noteEditDate);
            deleteNoteButton = view.findViewById(R.id.delete_note_button);

            this.view = view;
        }
    }

    public RecycleAdapter(Context context, NotesViewModel viewModel, long userId) {
        this.noteViewModels = viewModel.getNotes(userId);
        this.galleryItemViewModels = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
        this.notesViewModel = viewModel;

        for (NoteViewModel noteViewModel : this.noteViewModels) {
            this.galleryItemViewModels.addAll(this.notesViewModel
                    .getGalleryItems(noteViewModel.getId()));
        }
    }

    @NonNull
    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.notes_list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleAdapter.ViewHolder holder, int position) {
        NoteViewModel noteViewModel = noteViewModels.get(position);

        GalleryItemViewModel firstGalleryItemViewModel = null;
        for (GalleryItemViewModel viewModel : this.galleryItemViewModels) {
            if (viewModel.getNoteId() == noteViewModel.getId()) {
                firstGalleryItemViewModel = viewModel;
            }
        }

        if (firstGalleryItemViewModel != null) {
            File imgFile = new File(firstGalleryItemViewModel.getThumbnail());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.noteTitleImage.setImageBitmap(myBitmap);
            }
        }

        holder.descriptionView.setText(noteViewModel.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        String dateString = dateFormat.format(noteViewModel.getEditDate());
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
            if (holder.deleteNoteButton.getVisibility() == View.VISIBLE) {
                holder.deleteNoteButton.setVisibility(View.INVISIBLE);
            } else {
                holder.deleteNoteButton.setVisibility(View.VISIBLE);
            }
            holder.deleteNoteButton.setOnClickListener(x -> {
                new ChangeViewBackgroundTask().execute(holder.view);
                DeleteNoteDialogFragment deleteNoteDialogFragment =
                        DeleteNoteDialogFragment.newInstance(notesViewModel,
                                noteViewModel.getId());
                FragmentManager manager = ((AppCompatActivity)v.getContext())
                        .getSupportFragmentManager();
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
                mainHandler.post(() -> view.setBackgroundColor(view.getResources()
                        .getColor(R.color.list_selected_color)));
            }
            SystemClock.sleep(50);
            for (View view : views) {
                mainHandler.post(() -> view.setBackgroundColor(view.getResources()
                        .getColor(R.color.list_unselected_color)));
            }
            return null;
        }
    }
}
