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

public class MainActivity extends AppCompatActivity {

    private List<String> pulseLog = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView myList = (ListView) findViewById(R.id.listView);
        final ArrayList<String> itemList = new ArrayList<>();
        for (int i=0;i<10;i++) {
            PulseLog item = new PulseLog(i + 10, "good");
            pulseLog.add(item.toString());
        }

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
                System.out.println(item);
            }
        });

        Intent intent = getIntent();
        String message = intent.getStringExtra(AddPulseLog.EXTRA_MESSAGE);
        PulseLog item = new PulseLog(Integer.parseInt(message.split(" ")[0]), message.split(" ")[1]);
        pulseLog.add(item.toString());
        adapter.notifyDataSetChanged();
    }

    public void addLog(View view) {
        Intent intent = new Intent(this, AddPulseLog.class);
        startActivity(intent);
    }
}
