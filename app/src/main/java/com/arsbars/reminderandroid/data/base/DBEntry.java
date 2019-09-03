package com.arsbars.reminderandroid.data.base;

import android.provider.BaseColumns;

public class DBEntry implements BaseColumns {
    public static final String COLUMN_ID = "_id";
    public static final String NOTES_TABLE = "notes";
    public static final String DESCRIPTION = "description";
    public static final String CREATE_DATE = "create_date";
    public static final String EDIT_DATE = "edit_date";
    public static final String COLUMN_USER_ID = "user_id";

    public static final String USERS_TABLE = "users";
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String IMAGE_CONTENT = "image_content";

    public static final String GALLERY_ITEMS_TABLE = "gallery_items";
    public static final String IMAGE_PATH = "image_path";
    public static final String THUMBNAIL = "thumbnail";
    public static final String IS_VIDEO = "is_video";
    public static final String VIDEO_PATH = "video_path";
    public static final String LANDSCAPE = "landscape";
    public static final String COLUMN_NOTE_ID = "note_id";
}
