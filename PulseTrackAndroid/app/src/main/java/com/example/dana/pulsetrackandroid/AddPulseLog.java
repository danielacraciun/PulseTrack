package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPulseLog extends AppCompatActivity {

    public final static String EXTRA_MESSAGE = "com.example.dana.pulsetrackandroid.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pulse_log);

        Intent intent = getIntent();
    }

    public void sendLogBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.addPulse);
        EditText editText2 = (EditText) findViewById(R.id.editFeeling);
        String message1 = editText1.getText().toString();
        String message2 = editText2.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message1 + " " + message2);
        startActivity(intent);
    }
}
