package com.arsbars.reminderandroid.data.note

internal class GalleryItemModel {
    var id: Long = 0
    var imagePath: String? = null
    var thumbnail: String? = null
    var isVideo: Int = 0
    var videoPath: String? = null
    var landscape: Int = 0
    var noteId: Long = 0

    internal constructor(id: Long,
                imagePath: String?,
                thumbnail: String?,
                isVideo: Int,
                videoPath: String?,
                landscape: Int,
                noteId: Long) {
        this.id = id
        this.imagePath = imagePath
        this.thumbnail = thumbnail
        this.isVideo = isVideo
        this.videoPath = videoPath
        this.landscape = landscape
        this.noteId = noteId
    }

    internal constructor(galleryItemModel: GalleryItemModel) {
        this.id = galleryItemModel.id
        this.imagePath = galleryItemModel.imagePath
        this.thumbnail = galleryItemModel.thumbnail
        this.isVideo = galleryItemModel.isVideo
        this.videoPath = galleryItemModel.videoPath
        this.landscape = galleryItemModel.landscape
        this.noteId = galleryItemModel.noteId
    }
}