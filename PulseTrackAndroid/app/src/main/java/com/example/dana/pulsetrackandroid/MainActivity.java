package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.dana.pulsetrackandroid.MESSAGE";
    private static List<String> pulseLog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myList = (ListView) findViewById(R.id.listView);
        final ArrayList<String> itemList = new ArrayList<>();

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, pulseLog);
        myList.setAdapter(adapter);
        myList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(1000).alpha(0)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                itemList.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                        });
                editItem(item);
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(AddPulseLog.EXTRA_MESSAGE);
        String source = intent.getStringExtra("source");
        if (message != null && source != null) {
            if (Objects.equals(source, "add")) {
                PulseLog item = new PulseLog(Integer.parseInt(message.split(" ")[0]), message.split(" ")[1]);
                pulseLog.add(item.toString());
                adapter.notifyDataSetChanged();
                source = null;
            } else if (Objects.equals(source, "edit")) {
                String init = message.split(",")[0];
                String newl = message.split(",")[1];
                PulseLog initLog = new PulseLog(Integer.parseInt(init.split(" ")[0]), init.split(" ")[1]);
                PulseLog newLog = new PulseLog(Integer.parseInt(newl.split(" ")[0]), newl.split(" ")[1]);
                for (int i = 0; i < pulseLog.size(); i++) {
                    PulseLog ps = new PulseLog(Integer.parseInt(pulseLog.get(i).replaceAll("[^\\d.]", "")), pulseLog.get(i).split("feeling:")[1].replaceAll(" ", ""));
                    if (Objects.equals(ps.getPulse(), initLog.getPulse()) && Objects.equals(ps.getFeeling(), initLog.getFeeling())) {
                        System.out.println(ps.toString());
                        pulseLog.set(i, newLog.toString());
                    }
                }
                adapter.notifyDataSetChanged();
                source = null;
            }
        }
    }

    public void addLog(View view) {
        Intent intent = new Intent(this, AddPulseLog.class);
        startActivity(intent);
    }

    public void editItem(String item) {
        Intent intent = new Intent(this, PulseLogDetail.class);
        intent.putExtra(EXTRA_MESSAGE, item);
        startActivity(intent);
    }
}
