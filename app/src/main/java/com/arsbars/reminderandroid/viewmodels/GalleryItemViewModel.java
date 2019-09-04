package com.arsbars.reminderandroid.viewmodels;

import com.arsbars.reminderandroid.business.dto.GalleryItemDto;

public class GalleryItemViewModel {
    private long id;
    private String imagePath;
    private String thumbnail;
    private boolean isVideo;
    private String videoPath;
    private boolean landscape;
    private long noteId;

    public GalleryItemViewModel() {
    }

    public GalleryItemViewModel(long id,
                                String imagePath,
                                String thumbnail,
                                boolean isVideo,
                                String videoPath,
                                boolean landscape,
                                long noteId) {
        this.id = id;
        this.imagePath = imagePath;
        this.thumbnail = thumbnail;
        this.isVideo = isVideo;
        this.videoPath = videoPath;
        this.landscape = landscape;
        this.noteId = noteId;
    }

    public GalleryItemViewModel(GalleryItemDto dto) {
        this.id = dto.getId();
        this.imagePath = dto.getImagePath();
        this.thumbnail = dto.getThumbnail();
        this.isVideo = dto.isVideo();
        this.videoPath = dto.getVideoPath();
        this.landscape = dto.isLandscape();
        this.noteId = dto.getNoteId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public boolean isVideo() {
        return isVideo;
    }

    public void setVideo(boolean video) {
        isVideo = video;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public boolean isLandscape() {
        return landscape;
    }

    public void setLandscape(boolean landscape) {
        this.landscape = landscape;
    }

    public long getNoteId() {
        return noteId;
    }

    public void setNoteId(long noteId) {
        this.noteId = noteId;
    }
}
