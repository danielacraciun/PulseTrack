package com.example.dana.pulsetrackandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddPulseLog extends AppCompatActivity {

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

        intent.putExtra("pulse", message1);
        intent.putExtra("feeling", message2);
        intent.putExtra("source", "add");

        // Not working yet
         sendMail("pulse:" + message1 + " feeling:" + message2);

        startActivity(intent);
    }

    public void sendMail(String message) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"craciundaniela19@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "New pulse log!");
        i.putExtra(Intent.EXTRA_TEXT   , message);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(AddPulseLog.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
