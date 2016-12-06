package com.example.dana.pulsetrackandroid;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.dana.pulsetrackandroid.utils.Convertors.parsePulseLogString;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.listView) ListView myList;
    private static List<PulseLog> pulseLogObjects = new ArrayList<>();
    private static List<String> pulseLog = dummyDataLog();
                                           // new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pulseLog);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                detailLog(position);
            }
        });

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");

        if (source != null) {
            String pulse = intent.getStringExtra("pulse");
            String feeling = intent.getStringExtra("feeling");
            String positionString = intent.getStringExtra("position");

            if (Objects.equals(source, "add")) {
                String date = intent.getStringExtra("date");

                DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzzzzzz yyyy");
                Date final_date;

                try { final_date = df.parse(date);
                } catch (ParseException e) { final_date = null; }

                PulseLog item = new PulseLog(Integer.parseInt(pulse), feeling, final_date);
                insertLog(item);
                Toast.makeText(this, "Successfully added!", LENGTH_SHORT).show();

            } else if (Objects.equals(source, "edit")) {
                String newPulse = intent.getStringExtra("newPulse");
                String newFeeling = intent.getStringExtra("newFeeling");
                PulseLog newLog = new PulseLog(Integer.parseInt(newPulse), newFeeling);
                int pos = Integer.parseInt(positionString);

                updateLog(pos, newLog);
                Toast.makeText(this, "Successfully updated!", LENGTH_SHORT).show();

            } else if (Objects.equals(source, "remove")) {
                int pos = Integer.parseInt(positionString);

                deleteLog(pos);
                Toast.makeText(this, "Successfully removed!", LENGTH_SHORT).show();
            }
            adapter.notifyDataSetChanged();
        }
    }

    private void deleteLog(int pos) {
        pulseLog.remove(pos);
        pulseLogObjects.remove(pos);
    }

    private void updateLog(int pos, PulseLog newLog) {
        pulseLog.set(pos, newLog.toString());
        pulseLogObjects.set(pos, newLog);
    }

    private void insertLog(PulseLog item) {
        pulseLog.add(item.toString());
        pulseLogObjects.add(item);
    }

    private static ArrayList<String> dummyDataLog() {
        PulseLog p1 = new PulseLog(50, "good", new DateTime(2016,12,1,0,0).toDate());
        PulseLog p2 = new PulseLog(70, "good", new DateTime(2016,12,2,0,0).toDate());
        PulseLog p3 = new PulseLog(80, "good", new DateTime(2016,12,3,0,0).toDate());
        PulseLog p4 = new PulseLog(70, "good", new DateTime(2016,12,4,0,0).toDate());
        PulseLog p5 = new PulseLog(90, "good", new DateTime(2016,12,5,0,0).toDate());
        PulseLog p6 = new PulseLog(60, "good", new DateTime(2016,12,6,0,0).toDate());
        ArrayList<String> l = new ArrayList<>();
        l.add(p1.toString());
        l.add(p2.toString());
        l.add(p3.toString());
        l.add(p4.toString());
        l.add(p5.toString());
        l.add(p6.toString());
        pulseLogObjects.add(p1);
        pulseLogObjects.add(p2);
        pulseLogObjects.add(p3);
        pulseLogObjects.add(p4);
        pulseLogObjects.add(p5);
        pulseLogObjects.add(p6);
        return l;
    }

    @TargetApi(Build.VERSION_CODES.N)
    private List<String> sortItems(List<PulseLog> l) {
        Collections.sort(l, (o1, o2) -> o2.getTime().compareTo(o1.getTime()));
        return l.stream().map(PulseLog::toString).collect(Collectors.toList());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void viewStats(View view) {
        Intent intent = new Intent(this, Statistics.class);

        intent.putExtra("pulses", (Serializable) pulseLogObjects);
        startActivity(intent);
    }

    public void addLog(View view) {
        Intent intent = new Intent(this, AddPulseLog.class);
        startActivity(intent);
    }

    public void detailLog(int position) {
        Intent intent = new Intent(this, PulseLogDetail.class);
        PulseLog pulse = pulseLogObjects.get(position);
        intent.putExtra("pulse", pulse.getPulse().toString());
        intent.putExtra("feeling", pulse.getFeeling());
        intent.putExtra("date", pulse.getTime().toString());
        intent.putExtra("position", String.valueOf(position));

        startActivity(intent);
    }
}
