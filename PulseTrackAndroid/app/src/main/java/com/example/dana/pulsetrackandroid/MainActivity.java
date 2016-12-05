package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.dana.pulsetrackandroid.utils.Convertors.parsePulseLogString;

public class MainActivity extends AppCompatActivity {

    private static List<String> pulseLog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myList = (ListView) findViewById(R.id.listView);
        final ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, pulseLog);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                String[] split = item.replaceAll(" ", "").split("feeling:");
                String pulse = split[0].replaceAll("[^\\d.]", "");
                String feeling = split[1];
                detailLog(pulse, feeling);
            }
        });

        Intent intent = getIntent();
        String source = intent.getStringExtra("source");
        if (source != null) {
            if (Objects.equals(source, "add")) {
                String pulse = intent.getStringExtra("pulse");
                String feeling = intent.getStringExtra("feeling");

                if (!Objects.equals(pulse, "") && feeling != null) {
                    PulseLog item = new PulseLog(Integer.parseInt(pulse), feeling);
                    pulseLog.add(item.toString());

                    adapter.notifyDataSetChanged();
                }
            } else if (Objects.equals(source, "edit")) {
                String initPulse = intent.getStringExtra("initPulse");
                String initFeeling = intent.getStringExtra("initFeeling");
                String newPulse = intent.getStringExtra("newPulse");
                String newFeeling = intent.getStringExtra("newFeeling");

                PulseLog initLog = null;
                PulseLog newLog = null;

                if(!Objects.equals(initPulse, "") && initFeeling != null) {
                    initLog = new PulseLog(Integer.parseInt(initPulse), initFeeling);
                }
                if(!Objects.equals(newPulse, "") && newFeeling != null) {
                    newLog = new PulseLog(Integer.parseInt(newPulse), newFeeling);
                }

                if (initLog != null && newLog != null) {
                    for (int i = 0; i < pulseLog.size(); i++) {
                        PulseLog ps = parsePulseLogString(pulseLog.get(i));
                        if (compareLog(initLog, ps)) {
                            pulseLog.set(i, newLog.toString());
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            } else if (Objects.equals(source, "remove")) {
                String initPulse = intent.getStringExtra("initPulse");
                String initFeeling = intent.getStringExtra("initFeeling");

                PulseLog initLog = null;

                System.out.println(initFeeling);
                System.out.println(initPulse);

                if(!Objects.equals(initPulse, "") && initFeeling != null) {
                    initLog = new PulseLog(Integer.parseInt(initPulse), initFeeling);
                }

                if (initLog != null) {
                    for (int i = 0; i < pulseLog.size(); i++) {
                        PulseLog ps = parsePulseLogString(pulseLog.get(i));
                        if (compareLog(initLog, ps)) {
                            pulseLog.remove(i);
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }
    }

    private boolean compareLog(PulseLog thisLog, PulseLog otherLog) {
        return Objects.equals(
                thisLog.getPulse(), otherLog.getPulse()) && Objects.equals(thisLog.getFeeling(), otherLog.getFeeling());
    }

    public void addLog(View view) {
        Intent intent = new Intent(this, AddPulseLog.class);
        startActivity(intent);
    }

    public void detailLog(String pulse, String feeling) {
        Intent intent = new Intent(this, PulseLogDetail.class);
        intent.putExtra("pulse", pulse);
        intent.putExtra("feeling", feeling);
        startActivity(intent);
    }
}
