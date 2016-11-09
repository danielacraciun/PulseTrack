package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class PulseLogDetail extends AppCompatActivity {

    PulseLog initLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_log_detail);

        Intent intent = getIntent();
        String initPulse = intent.getStringExtra("pulse");
        String initFeeling = intent.getStringExtra("feeling");

        EditText editPulse = (EditText) findViewById(R.id.editNoPulse);
        EditText editFeeling = (EditText) findViewById(R.id.editFeeling);

        initLog = new PulseLog(Integer.parseInt(initPulse), initFeeling);

        editPulse.setText(initPulse);
        editFeeling.setText(initFeeling);
    }

    public void updateLog(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.editNoPulse);
        EditText editText2 = (EditText) findViewById(R.id.editFeeling);

        String message1 = editText1.getText().toString();
        String message2 = editText2.getText().toString();

        intent.putExtra("newPulse", message1);
        intent.putExtra("newFeeling", message2);
        intent.putExtra("initPulse", initLog.getPulse().toString());
        intent.putExtra("initFeeling", initLog.getFeeling());
        intent.putExtra("source", "edit");

        startActivity(intent);
    }
}
