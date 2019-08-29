package com.arsbars.reminderandroid.data.note

import java.util.Date

internal class Note {
    var id: Long = 0
    var description: String? = null
    var createDate: Date? = null
    var editDate: Date? = null
    var userId: Long = 0

    internal constructor(id: Long,
                         description: String,
                         createDate: Date,
                         editDate: Date,
                         userId: Long) {
        this.id = id
        this.description = description
        this.createDate = createDate
        this.editDate = editDate
        this.userId = userId
    }

    internal constructor(note: Note) {
        this.id = note.id
        this.createDate = note.createDate
        this.editDate = note.editDate
        this.description = note.description
    }
}
