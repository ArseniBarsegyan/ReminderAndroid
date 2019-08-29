package com.arsbars.reminderandroid.data.note;

import java.util.Date;

public class Note {
    private long id;
    private String description;
    private Date createDate;
    private Date editDate;
    private long userId;

    Note(long id, String description, Date createDate, Date editDate, long userId) {
        this.id = id;
        this.description = description;
        this.createDate = createDate;
        this.editDate = editDate;
        this.userId = userId;
    }

    Note(Note note) {
        this.id = note.id;
        this.createDate = note.createDate;
        this.editDate = note.editDate;
        this.description = note.description;
    }

    public long getId() {
        return id;
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

    public long getUserId() { return userId; }

    public void setUserId(long userId) { this.userId = userId; }
}
