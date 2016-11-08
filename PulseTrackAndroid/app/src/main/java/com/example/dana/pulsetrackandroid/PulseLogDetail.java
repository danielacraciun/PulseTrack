package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PulseLogDetail extends AppCompatActivity {

    PulseLog initLog;

    public final static String EXTRA_MESSAGE = "com.example.dana.pulsetrackandroid.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_log_detail);

        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        EditText editPulse = (EditText) findViewById(R.id.editNoPulse);
        EditText editFeeling = (EditText) findViewById(R.id.editFeeling);

        initLog = new PulseLog(Integer.parseInt(message.replaceAll("[^\\d.]", "")),
                message.split("feeling:")[1].replaceAll(" ", ""));

        editPulse.setText(initLog.getPulse().toString());
        editFeeling.setText(initLog.getFeeling());
    }

    public void updateLog(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.editNoPulse);
        EditText editText2 = (EditText) findViewById(R.id.editFeeling);
        String message1 = editText1.getText().toString();
        String message2 = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, initLog.getPulse().toString() + " " +
                initLog.getFeeling() + "," + message1 + " " + message2);
        intent.putExtra("source", "edit");
        startActivity(intent);
    }
}
