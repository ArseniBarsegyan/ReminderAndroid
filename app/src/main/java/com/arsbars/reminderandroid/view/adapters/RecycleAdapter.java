package com.arsbars.reminderandroid.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.view.NoteViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<NoteViewModel> noteViewModels;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        final TextView descriptionView, noteEditDateView;

        public ViewHolder(View view){
            super(view);
            descriptionView = (TextView)view.findViewById(R.id.noteDescription);
            noteEditDateView = (TextView)view.findViewById(R.id.noteEditDate);
            this.view = view;
        }
    }

    public RecycleAdapter(Context context, List<NoteViewModel> phones) {
        this.noteViewModels = phones;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public RecycleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycleAdapter.ViewHolder holder, int position) {
        final NoteViewModel noteViewModel = noteViewModels.get(position);
        holder.descriptionView.setText(noteViewModel.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.US);
        String dateString = dateFormat.format(noteViewModel.getCreateDate());
        holder.noteEditDateView.setText(dateString);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Tap handled " + noteViewModel.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "Long tap handled " + noteViewModel.getId(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return noteViewModels.size();
    }
}
