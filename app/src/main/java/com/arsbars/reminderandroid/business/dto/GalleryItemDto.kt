package com.arsbars.reminderandroid.business.dto

class GalleryItemDto {
    var id: Long = 0
    var imagePath: String? = null
    var thumbnail: String? = null
    var isVideo: Boolean? = false
    var videoPath: String? = null
    var isLandscape: Boolean? = false
    var noteId: Long = 0

    internal constructor(id: Long,
                         imagePath: String?,
                         thumbnail: String?,
                         isVideo: Boolean?,
                         videoPath: String?,
                         landscape: Boolean?,
                         noteId: Long) {
        this.id = id
        this.imagePath = imagePath
        this.thumbnail = thumbnail
        this.isVideo = isVideo
        this.videoPath = videoPath
        this.isLandscape = landscape
        this.noteId = noteId
    }
}