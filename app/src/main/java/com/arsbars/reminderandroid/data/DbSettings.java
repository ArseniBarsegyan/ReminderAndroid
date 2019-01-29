package com.arsbars.reminderandroid.data;

import android.provider.BaseColumns;

public class DbSettings {
    public static final String DB_NAME = "ReminderDroid.db";
    public static final int DB_VERSION = 1;

    public class DBEntry implements BaseColumns {
        public static final String NOTES_TABLE = "notes";
        public static final String DESCRIPTION = "description";
        public static final String CREATE_DATE = "create_date";
        public static final String EDIT_DATE = "edit_date";
    }
}
