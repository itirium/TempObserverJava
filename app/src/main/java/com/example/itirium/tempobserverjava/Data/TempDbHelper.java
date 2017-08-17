package com.example.itirium.tempobserverjava.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.example.itirium.tempobserverjava.Data.TempContract.*;

/**
 * Created by Itirium on 17.08.2017.
 */

public class TempDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = TempDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "temp.db";

    private static final int DATABASE_VERSION = 1;

    public TempDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String SQL_CREATE_TEMP_TABLE = "CREATE TABLE "+ TempEntry.TABLE_NAME + " ("
                + TempEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TempEntry.COLUMN_DATE + " INT NOT NULL, "
                + TempEntry.COLUMN_TEMPERATURE + " REAL NOT NULL DEFAULT 0, "
                + TempEntry.COLUMN_NOTES + " TEXT );";

        sqLiteDatabase.execSQL(SQL_CREATE_TEMP_TABLE);
        Log.d(LOG_TAG,"Creating Table");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.w("SQLite", "Updating from "+ i + " version to "+ i1 + " version");

        sqLiteDatabase.execSQL("DROP TABLE IF IT EXIST "+ TempEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);

    }
}
