package com.example.itirium.tempobserverjava;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.itirium.tempobserverjava.Data.TempContract;
import com.example.itirium.tempobserverjava.Data.TempDbHelper;


import java.util.Calendar;
import java.util.Date;


public class InputData extends AppCompatActivity {

    TextView inputTempField;
    TextView inputNotesField;
    Button btnSave;
    Button btnCancel;
    TempDbHelper mDbHelper;
    DatePicker mDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data);
        setTitle(getBaseContext().getString(R.string.app_name));

        inputTempField = (TextView)findViewById(R.id.input_temp_field);
        inputNotesField = (TextView) findViewById(R.id.input_notes_field);
        btnSave = (Button) findViewById(R.id.button_save);
        btnCancel = (Button) findViewById(R.id.button_cancel);
        mDatePicker = (DatePicker)findViewById(R.id.datePicker);
        setTodayDate();


        mDbHelper = new TempDbHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                insertTemp();
                finish();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(InputData.this, getBaseContext().getString(R.string.cancelled), Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    public void setTodayDate(){
        Calendar today = Calendar.getInstance();
        mDatePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                    }
                });
    }

    public  void insertTemp(){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Calendar cal = Calendar.getInstance();
        cal.set(mDatePicker.getYear(),mDatePicker.getMonth(),mDatePicker.getDayOfMonth());


        values.put(TempContract.TempEntry.COLUMN_DATE, cal.getTimeInMillis());
        //values.put(TempContract.TempEntry.COLUMN_DATE, System.currentTimeMillis());
        values.put(TempContract.TempEntry.COLUMN_TEMPERATURE, inputTempField.getText().toString().trim());
        values.put(TempContract.TempEntry.COLUMN_NOTES, inputNotesField.getText().toString().trim());

        long newRowId = db.insert(TempContract.TempEntry.TABLE_NAME, null, values);

        if(newRowId == -1){
            Toast.makeText(this, getBaseContext().getString(R.string.error_cannt_insert), Toast.LENGTH_SHORT).show();
        }
        else
            {
                Toast.makeText(this, getBaseContext().getString(R.string.new_data_inserted)+newRowId + getBaseContext().getString(R.string.to_record), Toast.LENGTH_SHORT).show();
            }
    }

    public void onclick(View view) {
    }
}
