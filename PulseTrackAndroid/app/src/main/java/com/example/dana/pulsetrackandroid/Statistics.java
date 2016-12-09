package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import io.realm.Realm;
import io.realm.RealmResults;

public class Statistics extends AppCompatActivity {

    private Realm realm;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        realm = Realm.getDefaultInstance();
        RealmResults results = realm.where(PulseLog.class).findAll();
        List<PulseLog> list = new ArrayList<>();
        list.addAll(results);

        DataPoint[] dps = new DataPoint[list.size()];
        int position = 0;
        for (PulseLog p: list) {
            dps[position] = new DataPoint(p.getTime(), p.getPulse());
            position++;
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dps);
        graph.addSeries(series);

        // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this));
            graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
            graph.getViewport().setMinX(list.get(0).getTime().getTime());
            graph.getViewport().setMaxX(list.get(list.size() - 1).getTime().getTime());
            graph.getViewport().setXAxisBoundsManual(true);

        // as we use dates as labels, the human rounding to nice readable numbers
        // is not necessary
            graph.getGridLabelRenderer().setHumanRounding(false);
    }
}
