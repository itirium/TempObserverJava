package com.example.itirium.tempobserverjava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.itirium.tempobserverjava.Data.TempContract;
import com.example.itirium.tempobserverjava.Data.TempDbHelper;
import com.jjoe64.graphview.GraphView;

import java.util.Date;
import java.util.Locale;

import static com.example.itirium.tempobserverjava.Data.TempContract.*;

public class MainActivity extends AppCompatActivity {

    private TempDbHelper mDbHelper;
    Locale myLocale;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getBaseContext().getString(R.string.app_name));


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_floating_button);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InputData.class);
                startActivity(intent);
            }
        });

        FloatingActionButton fabGraph = (FloatingActionButton) findViewById(R.id.add_floating_button2);
        fabGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
            }
        });


        mDbHelper = new TempDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(getBaseContext().getString(R.string.app_name));
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TempEntry._ID,
                TempEntry.COLUMN_DATE,
                TempEntry.COLUMN_TEMPERATURE,
                TempEntry.COLUMN_NOTES};

        Cursor cursor = db.query(
                TempEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        TextView displayTextView = (TextView) findViewById(R.id.text_view_info);

        try {
            displayTextView.setText( getBaseContext().getString(R.string.table_consist)+ cursor.getCount() + " "+getBaseContext().getString(R.string.records)+"\n\n");
            //display titles of columns
            displayTextView.append(TempEntry._ID + " - "
                    + TempEntry.COLUMN_DATE + " - "
                    + TempEntry.COLUMN_TEMPERATURE + " - "
                    + TempEntry.COLUMN_NOTES + "\n");

            int idColumnIndex = cursor.getColumnIndex(TempEntry._ID);
            int dateColumnIndex = cursor.getColumnIndex(TempEntry.COLUMN_DATE);
            int tempColumnIndex = cursor.getColumnIndex(TempEntry.COLUMN_TEMPERATURE);
            int notesColumnIndex = cursor.getColumnIndex(TempEntry.COLUMN_NOTES);

            //read all cells
            while (cursor.moveToNext()){
                int currentID = cursor.getInt(idColumnIndex);
                Date currentDate =  new Date(cursor.getLong(dateColumnIndex));
                float currentTemperature = cursor.getFloat(tempColumnIndex);
                String currentNotes = cursor.getString(notesColumnIndex);

                displayTextView.append(("\n" + currentID + " - " +
                currentDate.toString() + " - " +
                currentTemperature + " - " +
                currentNotes));
            }

        }
        finally {
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent intent;
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_show_input_dialog:
                intent = new Intent(MainActivity.this, InputData.class);
                startActivity(intent);
                break;
            case R.id.menu_show_graph:
                intent = new Intent(MainActivity.this, GraphActivity.class);
                startActivity(intent);
                break;
            case R.id.action_settings:
                intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
                break;
        }



        return super.onOptionsItemSelected(item);
    }


}
