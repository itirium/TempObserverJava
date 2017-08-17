package com.example.itirium.tempobserverjava;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.itirium.tempobserverjava.Data.TempContract;
import com.example.itirium.tempobserverjava.Data.TempDbHelper;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.NumberFormat;
import java.util.ArrayList;

public class GraphActivity extends AppCompatActivity {

    private TempDbHelper mDbHelper;
    GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        setTitle(getBaseContext().getString(R.string.app_name));
        mDbHelper = new TempDbHelper(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        displayGraph();
    }

    private void displayGraph() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                TempContract.TempEntry.COLUMN_DATE,
                TempContract.TempEntry.COLUMN_TEMPERATURE};

        Cursor cursor = db.query(
                TempContract.TempEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);


        try {



            int dateColumnIndex = cursor.getColumnIndex(TempContract.TempEntry.COLUMN_DATE);
            int tempColumnIndex = cursor.getColumnIndex(TempContract.TempEntry.COLUMN_TEMPERATURE);

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            int max = 1;

            //read all cells
            while (cursor.moveToNext()){
                series.appendData(new DataPoint(cursor.getLong(dateColumnIndex), cursor.getFloat(tempColumnIndex)), true, ++max);

            }
            GraphView graphView = (GraphView)findViewById(R.id.graph_view);
            //series.setTitle(getBaseContext().getString(R.string.note_temperature));
            series.setColor(Color.GREEN);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(10);
            series.setThickness(8);
          //  series.setSpacing(20);
            graphView.addSeries(series);



            //NumberFormat nf = NumberFormat.getInstance();
            // nf.setMinimumFractionDigits(1);
            //nf.setMinimumIntegerDigits(2);

            //graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf,nf));

            // set date label formatter
            graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graphView.getGridLabelRenderer().setNumHorizontalLabels(5); // only 4 because of the space


            // set manual x bounds to have nice steps
            //graphView.getViewport().setMinX(d1.getTime());
            //graphView.getViewport().setMaxX(d3.getTime());
            graphView.getViewport().setMinY(35.0);
            graphView.getViewport().setMaxY(38.0);

            graphView.getViewport().setYAxisBoundsManual(true);
            //graphView.getViewport().setXAxisBoundsManual(true);

// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary
            graphView.getGridLabelRenderer().setHumanRounding(true);
            graphView.getViewport().setScrollable(true); // enables horizontal scrolling
           //graphView.getViewport().setScrollableY(true); // enables vertical scrolling
            graphView.getViewport().setScalable(true); // enables horizontal zooming and scrolling
          //  graphView.getViewport().setScalableY(true); // enables vertical zooming and scrolling
           // graphView.getViewport().scrollToEnd();
            graphView.getGridLabelRenderer().setHorizontalLabelsAngle(90);
            graphView.getGridLabelRenderer().setVerticalLabelsAlign(Paint.Align.CENTER);

        }
        finally {
            cursor.close();
        }
    }
}
