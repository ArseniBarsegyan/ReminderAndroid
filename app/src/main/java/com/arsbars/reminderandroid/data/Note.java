package com.arsbars.reminderandroid.data;

import java.util.Date;

public class Note {
    private long id;
    private String description;
    private Date createDate;
    private Date editDate;

    public long getId() {
        return id;
    }

    public Note(long id, String description, Date createDate, Date editDate) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.editDate = editDate;
    }

    public Note(Note note) {
        this.id = note.id;
        this.createDate = note.createDate;
        this.editDate = note.editDate;
        this.description = note.description;
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
