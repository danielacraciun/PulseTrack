package com.example.dana.pulsetrackandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
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
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

import static com.example.dana.pulsetrackandroid.utils.Dialogs.messageDialog;
import static com.example.dana.pulsetrackandroid.utils.Dialogs.optionDialog;
import static com.example.dana.pulsetrackandroid.utils.Validators.validateUpdateInput;

public class PulseLogDetail extends AppCompatActivity {

    @BindView(R.id.editNoPulse)
    EditText editPulse;

    @BindView(R.id.editFeeling)
    EditText editFeeling;

    @BindView(R.id.editDate)
    TextView editDate;

    Realm realm;

    int objId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pulse_log_detail);
        ButterKnife.bind(this);
        realm = Realm.getDefaultInstance();

        Intent intent = getIntent();
        objId = intent.getIntExtra("id", -1);

        if (objId >= 0) {
            System.out.println(objId);
            PulseLog p = realm.where(PulseLog.class).equalTo("id", objId).findFirst();
            System.out.println(p.getId());
            editPulse.setText(p.getPulse().toString());
            editFeeling.setText(p.getFeeling());
            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            String sDate= df.format(p.getTime());
            editDate.setText(sDate);
            editDate.setKeyListener(null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void removeLog(View view) {
        optionDialog("Are you sure you want to delete?", this, () -> {
            realm.executeTransaction(realm1 -> realm.where(PulseLog.class)
                    .equalTo("id", objId)
                    .findAll()
                    .deleteAllFromRealm()
            );
            this.finish();
            return true;
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateLog(View view) {
        String pulse = editPulse.getText().toString();
        String feeling = editFeeling.getText().toString();

        if (validateUpdateInput(pulse, feeling, this)) {
            realm.executeTransaction(realm -> {
                PulseLog p = realm.where(PulseLog.class).equalTo("id", objId).findFirst();
                p.setPulse(Integer.valueOf(pulse));
                p.setFeeling(feeling);
            });
            this.finish();
        }
    }


}
