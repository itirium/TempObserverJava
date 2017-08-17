package com.example.itirium.tempobserverjava.Data;

import android.provider.BaseColumns;

/**
 * Created by Itirium on 17.08.2017.
 */

public final class TempContract {
    private TempContract(){

    };

    public static final class TempEntry implements BaseColumns{
        public static final String TABLE_NAME = "temp";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_NOTES = "notes";

    }
}
