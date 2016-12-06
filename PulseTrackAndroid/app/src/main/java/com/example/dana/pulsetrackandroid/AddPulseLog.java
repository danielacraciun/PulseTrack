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

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.dana.pulsetrackandroid.utils.Dialogs.messageDialog;

public class AddPulseLog extends AppCompatActivity {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pulse_log);
        Intent intent = getIntent();
        setUpDatePicker();
    }

    public void sendLogBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText1 = (EditText) findViewById(R.id.addPulse);
        EditText editText2 = (EditText) findViewById(R.id.editFeeling);
        DatePicker dp = (DatePicker) findViewById(R.id.datePicker3);

        String message1 = editText1.getText().toString();
        String message2 = editText2.getText().toString();
        Date d = getDateFromDatePicker(dp);
        if (StringUtils.isBlank(message1) && StringUtils.isBlank(message2)) {
            messageDialog("Can't proceed with empty fields", null, this);
        } else if (StringUtils.isBlank(message1)) {
            messageDialog("Can't proceed with empty pulse field", null, this);
        } else if (StringUtils.isBlank(message2)) {
            messageDialog("Can't proceed with empty feeling field", null, this);
        } else {
            intent.putExtra("pulse", message1);
            intent.putExtra("feeling", message2);
            intent.putExtra("date", d.toString());
            intent.putExtra("source", "add");

            // Not working yet
            // sendMail("pulse:" + message1 + " feeling:" + message2);

            startActivity(intent);
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

    /**
     *
     * @param datePicker
     * @return a java.util.Date
     */
    public static Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth() + 1;
        int year =  datePicker.getYear();

        return new DateTime(year, month, day, 0, 0).toDate();
    }
}
