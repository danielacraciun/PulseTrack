package com.example.dana.pulsetrackandroid;

import android.app.Activity;
import android.content.Intent;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import java.util.Date;


import io.realm.Realm;

import static com.example.dana.pulsetrackandroid.utils.Validators.validateUpdateInput;

public class AddPulseLog extends AppCompatActivity {

    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pulse_log);
        setUpDatePicker();
        realm = Realm.getDefaultInstance();
    }

    public void sendLogBack(View view) {
        EditText editText1 = (EditText) findViewById(R.id.addPulse);
        EditText editText2 = (EditText) findViewById(R.id.editFeeling);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker3);

        String pulse = editText1.getText().toString();
        String feeling = editText2.getText().toString();
        if (validateUpdateInput(pulse, feeling, this)) {
            PulseLog item = new PulseLog(generateNextId(), Integer.parseInt(pulse),
                    feeling, getDateFromDatePicker(dp));
            realm.beginTransaction();
            PulseLog savedItem = realm.copyToRealm(item);
            realm.commitTransaction();
            // todo sendMail
            this.finish();
        }
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

    private void setUpDatePicker() {
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker3);
        dp.setOnTouchListener((v, event) -> {
            hideSoftKeyboard(AddPulseLog.this);
            return false;
        });
        dp.setMaxDate(new Date().getTime());
    }

    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year =  datePicker.getYear();

        return new DateTime(year, month, day, 0, 0).toDate();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    private int generateNextId() {
        Number maxId = realm.where(PulseLog.class).findAll().max("id");
        if (maxId != null) {
            return maxId.intValue() + 1;
        }
        return 0;
    }
}
