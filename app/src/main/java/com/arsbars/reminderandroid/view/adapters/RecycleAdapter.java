package com.arsbars.reminderandroid.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.arsbars.reminderandroid.R;
import com.arsbars.reminderandroid.view.NoteViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.ViewHolder> {
    private LayoutInflater inflater;
    private List<NoteViewModel> noteViewModels;

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
        NoteViewModel noteViewModel = noteViewModels.get(position);
        holder.descriptionView.setText(noteViewModel.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd  HH:mm");
        String dateString = dateFormat.format(noteViewModel.getCreateDate());
        holder.noteEditDateView.setText(dateString);
    }

    @Override
    public int getItemCount() {
        return noteViewModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final TextView descriptionView, noteEditDateView;
        ViewHolder(View view){
            super(view);
            descriptionView = (TextView)view.findViewById(R.id.noteDescription);
            noteEditDateView = (TextView)view.findViewById(R.id.noteEditDate);
        }
    }
}
