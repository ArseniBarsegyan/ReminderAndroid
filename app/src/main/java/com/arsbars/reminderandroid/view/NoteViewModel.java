package com.arsbars.reminderandroid.view;

import android.arch.lifecycle.ViewModel;

import com.arsbars.reminderandroid.data.Note;

import java.util.Date;

public class NoteViewModel extends ViewModel {
    private long id;
    private String description;
    private Date createDate;
    private Date editDate;

    public long getId() {
        return id;
    }

    public NoteViewModel(long id, String description, Date createDate, Date editDate) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.editDate = editDate;
    }

    public NoteViewModel(Note note) {
        this.id = note.getId();
        this.createDate = note.getCreateDate();
        this.editDate = note.getEditDate();
        this.description = note.getDescription();
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getEditDate() {
        return editDate;
    }

    public void setEditDate(Date editDate) {
        this.editDate = editDate;
    }
}
