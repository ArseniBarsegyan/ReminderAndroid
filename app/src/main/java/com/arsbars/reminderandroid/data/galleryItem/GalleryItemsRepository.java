package com.arsbars.reminderandroid.data.galleryItem;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.arsbars.reminderandroid.business.dto.GalleryItemDto;
import com.arsbars.reminderandroid.business.mapper.Mapper;
import com.arsbars.reminderandroid.data.base.DBEntry;
import com.arsbars.reminderandroid.data.base.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GalleryItemsRepository {
    private DatabaseHelper dbHelper;
    private ArrayList<GalleryItemModel> galleryItems;

    public GalleryItemsRepository(DatabaseHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public List<GalleryItemDto> getGalleryItems(long noteId) {
        if (galleryItems == null) {
            galleryItems = new ArrayList<>();
            loadGalleryItems(noteId);
        }
        ArrayList<GalleryItemDto> clonedGalleryItems = new ArrayList<>(galleryItems.size());
        for (int i = 0; i < galleryItems.size(); i++) {
            clonedGalleryItems.add(Mapper.ToDto(galleryItems.get(i)));
        }
        return clonedGalleryItems;
    }

    private void loadGalleryItems(long noteId) {
        galleryItems.clear();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(DBEntry.GALLERY_ITEMS_TABLE,
                new String[]{
                        DBEntry.COLUMN_ID,
                        DBEntry.IMAGE_PATH,
                        DBEntry.THUMBNAIL,
                        DBEntry.IS_VIDEO,
                        DBEntry.VIDEO_PATH,
                        DBEntry.LANDSCAPE,
                        DBEntry.COLUMN_NOTE_ID
                },
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idIndex = cursor.getColumnIndex(DBEntry.COLUMN_ID);
            int imagePathIndex = cursor.getColumnIndex(DBEntry.IMAGE_PATH);
            int thumbnailIndex = cursor.getColumnIndex(DBEntry.THUMBNAIL);
            int isVideoIndex = cursor.getColumnIndex(DBEntry.IS_VIDEO);
            int videoPathIndex = cursor.getColumnIndex(DBEntry.VIDEO_PATH);
            int landscapeIndex = cursor.getColumnIndex(DBEntry.LANDSCAPE);
            int noteIdIndex = cursor.getColumnIndex(DBEntry.COLUMN_NOTE_ID);

            GalleryItemModel galleryItemModel = new GalleryItemModel(cursor.getLong(idIndex),
                    cursor.getString(imagePathIndex),
                    cursor.getString(thumbnailIndex),
            cursor.getInt(isVideoIndex),
            cursor.getString(videoPathIndex),
            cursor.getInt(landscapeIndex),
            cursor.getLong(noteIdIndex));
            galleryItems.add(galleryItemModel);
        }
        cursor.close();
        db.close();
        Collections.reverse(galleryItems);
    }

    public GalleryItemModel createGalleryItem(GalleryItemDto dto) {
        return createGalleryItem(dto.getImagePath(),
                dto.getThumbnail(),
                dto.isVideo(),
                dto.getVideoPath(),
                dto.isLandscape(),
                dto.getNoteId());
    }

    private GalleryItemModel createGalleryItem(String imagePath,
                                              String thumbnail,
                                              Boolean isVideo,
                                              String videoPath,
                                              Boolean landscape,
                                              long noteId) {

        int isVideoInt = (isVideo) ? 1 : 0;
        int landscapeInt = (landscape) ? 1 : 0;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DBEntry.IMAGE_PATH, imagePath);
        values.put(DBEntry.THUMBNAIL, thumbnail);
        values.put(DBEntry.IS_VIDEO, isVideoInt);
        values.put(DBEntry.VIDEO_PATH, videoPath);
        values.put(DBEntry.LANDSCAPE, landscapeInt);
        values.put(DBEntry.COLUMN_NOTE_ID, noteId);

        long id = db.insertWithOnConflict(DBEntry.GALLERY_ITEMS_TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

        return new GalleryItemModel(id,
                imagePath,
                thumbnail,
                isVideoInt,
                videoPath,
                landscapeInt,
                noteId);
    }

    public void removeGalleryItemsByNoteId(long noteId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                DBEntry.GALLERY_ITEMS_TABLE,
                DBEntry.COLUMN_NOTE_ID + " = ?",
                new String[]{Long.toString(noteId)}
        );
        db.close();
    }

    public void removeGalleryItem(long id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(
                DBEntry.GALLERY_ITEMS_TABLE,
                DBEntry.COLUMN_ID + " = ?",
                new String[]{Long.toString(id)}
        );
        db.close();

        int index = -1;
        for (int i = 0; i < galleryItems.size(); i++) {
            GalleryItemModel note = galleryItems.get(i);
            if (note.getId() == id) {
                index = i;
            }
        }
        if (index != -1) {
            galleryItems.remove(index);
        }
    }

    public void editGalleryItem(GalleryItemDto dto) {
        editGalleryItem(dto.getId(),
                dto.getImagePath(),
                dto.getThumbnail(),
                dto.isVideo(),
                dto.getVideoPath(),
                dto.isLandscape(),
                dto.getNoteId());
    }

    private void editGalleryItem(long galleryItemId,
                                String imagePath,
                                String thumbnail,
                                Boolean isVideo,
                                String videoPath,
                                Boolean landscape,
                                long noteId) {
        int isVideoInt = (isVideo) ? 1 : 0;
        int landscapeInt = (landscape) ? 1 : 0;

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DBEntry.IMAGE_PATH, imagePath);
        values.put(DBEntry.THUMBNAIL, thumbnail);
        values.put(DBEntry.IS_VIDEO, isVideoInt);
        values.put(DBEntry.VIDEO_PATH, videoPath);
        values.put(DBEntry.LANDSCAPE, landscapeInt);
        values.put(DBEntry.COLUMN_NOTE_ID, noteId);

        long id = db.update(DBEntry.GALLERY_ITEMS_TABLE,
                values,
                DBEntry.COLUMN_ID + "=" + String.valueOf(galleryItemId),
                null);

        db.close();
    }
}
