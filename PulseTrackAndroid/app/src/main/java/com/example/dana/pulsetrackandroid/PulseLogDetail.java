package com.example.dana.pulsetrackandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dana.pulsetrackandroid.utils.Dialogs.messageDialog;
import static com.example.dana.pulsetrackandroid.utils.Dialogs.optionDialog;

public class PulseLogDetail extends AppCompatActivity {

    @BindView(R.id.editNoPulse)
    EditText editPulse;
    @BindView(R.id.editFeeling)
    EditText editFeeling;
    @BindView(R.id.editDate)
    TextView editDate;
    int initPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_log_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String initPulse = intent.getStringExtra("pulse");
        String initFeeling = intent.getStringExtra("feeling");
        String date = intent.getStringExtra("date");

        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzzzzzzz yyyy");
        Date final_date = new Date();

        try { final_date = df.parse(date);
        } catch (ParseException ignored) { }

        initPosition = Integer.parseInt(intent.getStringExtra("position"));

        editPulse.setText(initPulse);
        editFeeling.setText(initFeeling);

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        String sDate= sdf.format(final_date);
        editDate.setText(sDate);
        editDate.setKeyListener(null);
    }

    public void removeLog(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        intent.putExtra("source", "remove");
        intent.putExtra("position", String.valueOf(initPosition));

        optionDialog("Are you sure you want to delete?", intent, this);
    }

    public void updateLog(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        String message1 = editPulse.getText().toString();
        String message2 = editFeeling.getText().toString();

        if (StringUtils.isBlank(message1) && StringUtils.isBlank(message2)) {
            messageDialog("Can't proceed with empty fields", null, this);
        } else if (StringUtils.isBlank(message1)) {
            messageDialog("Can't proceed with empty pulse field", null, this);
        } else if (StringUtils.isBlank(message2)) {
            messageDialog("Can't proceed with empty feeling field", null, this);
        } else {
            intent.putExtra("newPulse", message1);
            intent.putExtra("newFeeling", message2);
            intent.putExtra("source", "edit");
            intent.putExtra("position", String.valueOf(initPosition));

            startActivity(intent);
        }
    }
}
