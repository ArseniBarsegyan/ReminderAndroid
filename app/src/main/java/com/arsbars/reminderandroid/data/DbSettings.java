package com.arsbars.reminderandroid.data;

import android.provider.BaseColumns;

class DbSettings {
    static final String DB_NAME = "ReminderDroid.db";
    static final int DB_VERSION = 1;

    class DBEntry implements BaseColumns {
        static final String COLUMN_ID = "_id";
        static final String NOTES_TABLE = "notes";
        static final String DESCRIPTION = "description";
        static final String CREATE_DATE = "create_date";
        static final String EDIT_DATE = "edit_date";
    }
}
